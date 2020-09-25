package no.hvl.dat110.rest.todo;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @OneToMany
    private final List<ToDo> toDos = new ArrayList<>();

    public void addTask(String title, String description) {
        ToDo toDo = new ToDo(title, description);
        toDos.add(toDo);
    }

    public void addTask(ToDo task) {
        toDos.add(task);
    }

    String toJson () {

        Gson gson = new Gson();

        String jsonInString = gson.toJson(this);

        return jsonInString;
    }

    public void updateTask(ToDo toDo) {
        if (toDo.getIndex() == -1) return;
        toDos.set(toDo.getIndex(), toDo);
    }

    public void deleteTask(int id) {
        for (int i = 0; i < toDos.size(); i++) {
            if (id == toDos.get(i).getId()) toDos.remove(i);
        }
    }

    public List<ToDo> getToDos() {
        return toDos;
    }
}
