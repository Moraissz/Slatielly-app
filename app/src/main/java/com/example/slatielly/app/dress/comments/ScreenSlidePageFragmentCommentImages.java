package com.example.slatielly.app.dress.comments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.slatielly.R;
import com.squareup.picasso.Picasso;

@SuppressLint("ValidFragment")
public class ScreenSlidePageFragmentCommentImages extends Fragment
{
    public int position;

    private ImageView image_fragment_screen_slide_page_comment_images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page_comment_images, container, false);

        //instanciar a foto


        image_fragment_screen_slide_page_comment_images = (ImageView) rootView.findViewById(R.id.image_fragment_screen_slide_page_comment_images);


        Picasso.with(container.getContext()).load("").into(image_fragment_screen_slide_page_comment_images);


        return rootView;
    }

    @SuppressLint("ValidFragment")
    public ScreenSlidePageFragmentCommentImages(int position)
    {
        this.position = position;
    }
}
