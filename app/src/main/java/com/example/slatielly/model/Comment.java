package com.example.slatielly.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Comment {
    private String id;
    private Timestamp date;
    private String description;
    private User user;
    private Image image;
    private List<Answer> answers = new ArrayList<>();
    private int numberLikes = 0;
    private List<Like> likes =  new ArrayList<>();

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Like> getLikes()
    {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public int getNumberLikes() {
        return numberLikes;
    }

    public void setNumberLikes(int numberLikes) {
        this.numberLikes = numberLikes;
    }
}
