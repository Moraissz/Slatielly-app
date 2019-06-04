package com.example.slatielly.app.dress.comments;

import com.example.slatielly.model.Comment;

import java.util.ArrayList;

public interface CommentsContract {
    interface View {
        void setComments(ArrayList<Comment> comments);
    }

    interface Presenter {
        void loadComments(String dressId);
    }
}
