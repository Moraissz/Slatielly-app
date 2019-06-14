package com.example.slatielly.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {
    private String id;
    private Date date;
    private String description;
    private User user;
    private Image image;
    private ArrayList<Answer> answers = new ArrayList<>();
    private int numberLikes = 0;
    private ArrayList<Like> likes =  new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public ArrayList<Like> getLikes()
    {
        return likes;
    }

    public void setLikes(ArrayList<Like> likes) {
        this.likes = likes;
    }

    public int getNumberLikes() {
        return numberLikes;
    }

    public void setNumberLikes(int numberLikes) {
        this.numberLikes = numberLikes;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Comment)
        {
            Comment comment = (Comment) object;
            return this.getId().equals(comment.getId());
        }
        else
        {
            return false;
        }
    }
}
