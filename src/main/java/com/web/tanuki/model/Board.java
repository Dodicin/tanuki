package com.web.tanuki.model;
import javax.persistence.*;
import java.util.Set;

@Entity
public class Board extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToMany(mappedBy="board")
    private Set<Thread> threads;

    public Board(String title) {
        this.title = title;
    }

    public Board() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Thread> getThreads() {
        return threads;
    }
}
