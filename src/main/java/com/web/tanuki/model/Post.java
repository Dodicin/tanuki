package com.web.tanuki.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @Temporal(TemporalType.DATE)
    private Calendar date;

    @Temporal(TemporalType.TIME)
    private Date time;

    @ManyToOne
    @JoinColumn(name="user_id")
    private TanukiUser user;

    @ManyToOne
    @JoinColumn(name="thread_id")
    private Thread thread;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {@JoinColumn(name = "answersTo_id")}, inverseJoinColumns = {@JoinColumn(name = "answeredBy_id")})
    private Set<Post> answersTo;

    @ManyToMany(mappedBy = "answersTo", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Post> answeredBy;

    public Post(String text, Calendar date, Date time) {
        this.text = text;
        this.date = date;
        this.time = time;

        this.answersTo = new HashSet<>();
        this.answeredBy = new HashSet<>();

    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public TanukiUser getUser() {
        return user;
    }

    public void setUser(TanukiUser user) {
        this.user = user;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread t) {
        this.thread=t;
    }

    public Set<Post> getAnswersTo() {
        return answersTo;
    }

    public void addAnswersTo(Post referencedPost) {
        if(referencedPost != null && referencedPost != this) {
            this.answersTo.add(referencedPost);
            referencedPost.addAnsweredBy(this);
        }
    }

    public Set<Post> getAnsweredBy() {
        return answeredBy;
    }

    private void addAnsweredBy(Post answer) {
        this.answeredBy.add(answer);
    }


}
