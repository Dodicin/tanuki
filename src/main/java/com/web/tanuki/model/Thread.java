package com.web.tanuki.model;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Thread extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean archived;
    private String title;

    @OneToMany(mappedBy="thread", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name="board_id", nullable=false)
    private Board board;

    public Thread(boolean archived, String title) {
        this.archived = archived;
        this.title = title;
        this.posts = new LinkedList<>();
    }

    public Thread() {
        posts = new LinkedList<>();
    }


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

    public void addPost(Post post) {
        this.posts.add(post);
        post.setThread(this);
    }

    public void removePost(Post post){
        this.posts.remove(post);
        post.setThread(null);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
