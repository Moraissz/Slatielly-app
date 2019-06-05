package com.example.slatielly.app.dress.registerDress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.slatielly.app.FormContract;
import com.example.slatielly.model.Dress;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public interface RegisterDressContract {
    interface View extends FormContract.View {
        void navigateToAllDresses();

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    interface Presenter extends FormContract.Presenter {
        void createDress(Dress dress, List<Bitmap> images);

        void SaveImages(DocumentReference documentReference);

        Bitmap CompressedBitmap(String imagePath);

        int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight);
    }
}
