package com.example.slatielly.model;

import java.sql.Timestamp;
import java.util.Date;

public class Like
{
    private User user;
    private Date dateLike;
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Date getDateLike()
    {
        return dateLike;
    }

    public void setDateLike(Date dateLike)
    {
        this.dateLike = dateLike;
    }
}
