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
        void saveImage(String imagePath);
        void setImage(Bitmap image);
        Bitmap getImage();
        void saveComment(String description,String id);
    }
}
