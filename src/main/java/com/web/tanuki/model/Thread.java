package com.web.tanuki.model;
import javax.persistence.*;
import java.util.List;

@Entity
public class Thread extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean archived;
    private String title;

    @OneToMany(mappedBy="thread")
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name="board_id", nullable=false)
    private Board board;

    public Thread(boolean archived, String title, Board board) {
        this.archived = archived;
        this.board = board;
        this.title = title;
    }

    public Thread() {}


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
