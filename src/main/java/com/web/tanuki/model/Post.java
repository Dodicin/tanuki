package com.web.tanuki.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
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
    @JoinColumn(name="user_id", nullable=false)
    private TanukiUser user;

    @ManyToOne
    @JoinColumn(name="thread_id", nullable=false)
    private Thread thread;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {@JoinColumn(name = "answersTo_id")}, inverseJoinColumns = {@JoinColumn(name = "answeredBy_id")})
    private Set<Post> answersTo;

    @ManyToMany(mappedBy = "answersTo", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Post> answeredBy;

    public Post(String text, Calendar date, Date time, TanukiUser user, Thread thread) {
        this.text = text;
        this.date = date;
        this.time = time;
        this.user = user;
        this.thread = thread;
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

    public Thread getThread() {
        return thread;
    }

    public Set<Post> getAnswersTo() {
        return answersTo;
    }

    public void addAnswersTo(Post answersTo) {
        this.answersTo.add(answersTo);
        answersTo.addAnsweredBy(this);
    }

    public Set<Post> getAnsweredBy() {
        return answeredBy;
    }

    private void addAnsweredBy(Post answer) {
        this.answeredBy.add(answer);
    }
}
