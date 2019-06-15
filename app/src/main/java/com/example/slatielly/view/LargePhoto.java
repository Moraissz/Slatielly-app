package com.example.slatielly.view;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.slatielly.R;

public class LargePhoto extends DialogFragment
{
    private ImageView Image_View_Large_Photo;
    private String link;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.large_photo,container,false);
        Image_View_Large_Photo = (android.widget.ImageView) view.findViewById(R.id.Image_View_Large_Photo);
        Glide.with(this).load(link).into(Image_View_Large_Photo);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null)
        {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        }
    }

    public void setImage(String link)
    {
        this.link = link;
    }
}
