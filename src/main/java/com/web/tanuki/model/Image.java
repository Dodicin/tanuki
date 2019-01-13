package com.web.tanuki.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("0")
public class Image extends Content{

    public Image(Channel channel, String title, String description, String path, Date uploaded_date, String size, String format, int views) {
        super(channel, title, description, path, uploaded_date, size, format, views);
    }

    public Image() {
    }
}
