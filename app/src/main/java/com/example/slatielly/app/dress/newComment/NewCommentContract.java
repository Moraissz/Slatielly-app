package com.example.slatielly.app.dress.newComment;

import com.example.slatielly.model.Comment;

import java.util.ArrayList;

public interface NewCommentContract
{
    interface View
    {
        void getComment();
    }

    interface Presenter
    {
        void saveComment(Comment comment, String dressId);
    }
}
