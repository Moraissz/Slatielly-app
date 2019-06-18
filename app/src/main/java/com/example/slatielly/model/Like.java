package com.example.slatielly.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Like)
        {
            Like like = (Like) object;
            return this.getUser().getId().equals(like.getUser().getId());
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return this.getUser().getId().hashCode();
    }
}
