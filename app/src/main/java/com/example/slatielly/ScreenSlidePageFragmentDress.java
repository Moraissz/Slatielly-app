package com.example.slatielly;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

@SuppressLint("ValidFragment")
public class ScreenSlidePageFragmentDress extends Fragment
{
    private ImageView image_dress_fragment_screen_slide_page_dress;

    private List<Bitmap> fotos;

    public int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page_dress, container, false);

        //instanciar a foto


        image_dress_fragment_screen_slide_page_dress = (ImageView) rootView.findViewById(R.id.image_dress_fragment_screen_slide_page_dress);


        Picasso.with(container.getContext()).load("").into(image_dress_fragment_screen_slide_page_dress);


        return rootView;
    }

    @SuppressLint("ValidFragment")
    public ScreenSlidePageFragmentDress(int position)
    {
        this.position = position;
    }
}