package com.web.tanuki.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TanukiUser extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String lastname;

    @NotNull
    private String email;

    @NotNull
    private String username;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {@JoinColumn(name = "following_id")}, inverseJoinColumns = {@JoinColumn(name = "followers_id")})
    private Set<TanukiUser> following;

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<TanukiUser> followers;

    @OneToMany(mappedBy="user")
    private Set<Post> posts;

    public TanukiUser(String name, String lastname, String email, String username) {
        super();
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.username = username;

        this.followers = new HashSet<TanukiUser>();
        this.following = new HashSet<TanukiUser>();
    }

    public TanukiUser() {
        super();
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Set<TanukiUser> getFollowers() {
        return followers;
    }

    private void addFollower(TanukiUser user) {
        this.followers.add(user);
    }

    private void removeFollower(TanukiUser user) {
        if(user != null && user != this) {
            this.followers.remove(user);
        }
    }

    public Set<TanukiUser> getFollowing() {
        return following;
    }

    public void addFollowing(TanukiUser user) {
        if(user != null && user != this) {
            this.following.add(user);
            user.addFollower(this);
        }
    }

    public void removeFollowing(TanukiUser user) {
        if(user != null && user != this) {
            this.following.remove(user);
            user.removeFollower(this);
        }
    }

    @Override
    public String toString() {
        return "TanukiUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
