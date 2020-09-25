package no.hvl.dat110.rest.todo;

import javax.persistence.*;

@Entity
@Table(name="TODO")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="id")
    private int id;
    private String title;
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    ToDo(String title, String description){
        this.title = title;
        this.description = description;
    }

    public ToDo() {

    }

    public int getIndex() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Title=" + title + ", Description=" + description;
    }
}
