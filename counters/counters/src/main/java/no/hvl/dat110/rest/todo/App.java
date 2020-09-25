package no.hvl.dat110.rest.todo;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App {

    private static final String PERSISTENCE_UNIT_NAME = "todo";
    private static EntityManagerFactory factory;

    static ToDoList toDos;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8081);
        }

        if (em.find(ToDoList.class,1) == null) {
            toDos = new ToDoList();
        } else {
            toDos = em.find(ToDoList.class,1);
        }


        after((req, res) -> {
            res.type("application/json");
        });

        get("/todo/api/getTodoList", (req, res) -> {
            return toDos.toJson();
        });

        post("/todo/api/addTask", (req,res) -> {

            Gson gson = new Gson();
            em.getTransaction().begin();
            toDos.getToDos().add(gson.fromJson(req.body(), ToDo.class));
            em.persist(toDos);
            em.persist(toDos.getToDos().get(toDos.getToDos().size()-1));
            em.getTransaction().commit();
            return toDos.toJson();

        });

        put("/todo/api/updateTask", (req,res) -> {

            Gson gson = new Gson();
            em.getTransaction().begin();
            ToDo updatedTask = gson.fromJson(req.body(), ToDo.class);
            ToDo oldTask = em.find(ToDo.class, updatedTask.getId());
            oldTask.setDescription(updatedTask.getDescription());
            oldTask.setTitle(updatedTask.getTitle());
            em.persist(oldTask);
            em.getTransaction().commit();
            return toDos.toJson();

        });

        delete("/todo/api/deleteTask", (req,res) -> {

            Gson gson = new Gson();
            em.getTransaction().begin();
            toDos.deleteTask(gson.fromJson(req.body(), ToDo.class).getId());
            em.persist(toDos);
            em.getTransaction().commit();
            return toDos.toJson();

        });
    }

}

