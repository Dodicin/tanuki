package com.web.tanuki.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Channel extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @NotNull
    private TanukiUser user;

    @OneToMany(mappedBy="channel")
    private Set<Content> content;

    public Channel(String name, String description) {
        this.name = name;
        this.description = description;

        this.content = new HashSet<>();
    }

    public Channel() {
        content = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TanukiUser getUser() {
        return user;
    }

    public void setUser(TanukiUser user) {
        this.user = user;
    }

    public Set<Content> getContent() {
        return content;
    }

    public void addContent(Content content) {
        this.content.add(content);
        content.setChannel(this);
    }

    public void deleteContent(Content content){
        this.content.remove(content);
        content.setChannel(null);
    }
}
