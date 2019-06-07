package com.example.slatielly.app.dress.newComment;

import android.graphics.Bitmap;

import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;

import java.util.ArrayList;

public interface NewCommentContract
{
    interface View
    {

    }

    interface Presenter
    {
        void setUser();
        void saveImage(String imagePath);
        void setImage(Bitmap image);
        void setDress(Dress dress);
        Bitmap getImage();
        FirestoreRepository<Dress> getRepository();
        void saveComment(String comment,String id);
    }
}
