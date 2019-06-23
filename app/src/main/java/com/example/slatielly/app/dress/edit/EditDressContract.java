package com.example.slatielly.app.dress.edit;

import android.graphics.Bitmap;

import com.example.slatielly.model.Dress;

import java.util.ArrayList;

public interface EditDressContract
{
    interface View
    {
        void setDressView(Dress dress);
        void navigateToAllDress();
    }

    interface Presenter
    {
        void loadDress(String id);
        EditDressContract.View getView();
        ArrayList<Object> getImagesDress();
        void setDress(Dress dress);
        void updateImages(ArrayList<Object> imagesDelete);
        void updateDress(Dress dressUpdate);
    }
}
