package com.example.slatielly.model;

import java.sql.Timestamp;

public class Like
{
    private User user;
    private Timestamp dateLike;
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Timestamp getDateLike()
    {
        return dateLike;
    }

    public void setDateLike(Timestamp dateLike)
    {
        this.dateLike = dateLike;
    }
}
