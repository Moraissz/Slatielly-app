package com.example.slatielly.view;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.slatielly.R;
import com.example.slatielly.view.comment.CommentViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ortiz.touchview.TouchImageView;

public class LargePhoto extends AppCompatActivity
{
    public static final String KeyPhoto = "com.example.slatielly.LargePhoto";

    private TouchImageView Image_View_Large_Photo;

    private ProgressBar progressBarLargePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.large_photo);

        getSupportActionBar().hide();
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String address = getIntent().getStringExtra(KeyPhoto);

        Image_View_Large_Photo = (TouchImageView) findViewById(R.id.Image_View_Large_Photo);

        progressBarLargePhoto = (ProgressBar) findViewById(R.id.progressBarLargePhoto);

        setImage(address);
    }

    private void setImage(String address)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference photoRef = storageRef.child(address);

        photoRef.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>()
        {
            @Override
            public void onSuccess(byte[] bytes)
            {
                Bitmap photoBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                progressBarLargePhoto.setVisibility(View.GONE);
                Image_View_Large_Photo.setImageBitmap(photoBitmap);
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception exception)
            {

            }
        });
    }
}
