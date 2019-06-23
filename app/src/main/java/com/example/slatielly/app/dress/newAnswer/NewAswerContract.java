package com.example.slatielly.app.dress.newAnswer;

import android.graphics.Bitmap;

public class NewAswerContract
{
    interface View
    {
        void navigateToAnswers();
    }

    interface Presenter
    {
        void saveImage(String imagePath);
        void setImage(Bitmap image);
        Bitmap getImage();
        void saveComment(String description,String id, String commentId);
        byte[] getData();
    }
}
