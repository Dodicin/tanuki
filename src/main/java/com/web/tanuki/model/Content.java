package com.web.tanuki.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="content_type", discriminatorType = DiscriminatorType.INTEGER)
public class Content extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="channel_id", nullable=false)
    private Channel channel;

    private String title;
    private String description;
    private String path;
    private Date uploaded_date;
    private String size;
    private String format;
    private int views;

    public Content(Channel channel, String title, String description, String path, Date uploaded_date, String size, String format, int views) {
        this.channel = channel;
        this.title = title;
        this.description = description;
        this.path = path;
        this.uploaded_date = uploaded_date;
        this.size = size;
        this.format = format;
        this.views = views;
    }

    public Content() {}

    public Long getId() {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getUploaded_date() {
        return uploaded_date;
    }

    public void setUploaded_date(Date uploaded_date) {
        this.uploaded_date = uploaded_date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getViews(){
        return views;
    }

    public void addView(){
        views+=1;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}