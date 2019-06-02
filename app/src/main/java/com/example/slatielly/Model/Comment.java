package com.example.slatielly.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Comment
{
    private String id;
    private Timestamp date;
    private String description;
    private User user;
    private List<Image> images= new ArrayList<>(  );
    private List<Ansher> anshers=new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Image> getImagens() {
        return images;
    }

    public void setImagens(List<Image> imagens) {
        this.images = imagens;
    }

    public List<Ansher> getAnshers() {
        return anshers;
    }

    public void setAnshers(List<Ansher> anshers) {
        this.anshers = anshers;
    }
}
