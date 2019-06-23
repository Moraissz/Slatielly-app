package com.example.slatielly.app.dress.registerDress;

import android.graphics.Bitmap;

import com.example.slatielly.app.FormContract;
import com.example.slatielly.model.Dress;

import java.util.ArrayList;

public interface RegisterDressContract {
    interface View extends FormContract.View {
        void navigateToAllDresses();
    }

    interface Presenter extends FormContract.Presenter {
        void createDress(Dress dress);

        void saveImage(String imagePath);

        ArrayList<Bitmap> getImages();

        void updateImages(ArrayList<Bitmap> imagesDelete);
    }
}
