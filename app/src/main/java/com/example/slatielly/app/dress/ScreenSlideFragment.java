package com.example.slatielly.app.dress;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.slatielly.R;
import com.example.slatielly.model.Image;

import java.util.ArrayList;

public class ScreenSlideFragment extends Fragment
{
    public ArrayList<Uri> uris;
    public int posição = 0;
    public static ScreenSlideFragment newInstance(Image image) {
        ScreenSlideFragment fragment = new ScreenSlideFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("image", image);

        fragment.setArguments(bundle);

        return fragment;
    }

    public ScreenSlideFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Fresco.initialize(container.getContext());

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page_dress, container, false);
        ImageView image_dress_fragment_screen_slide_page_dress = rootView.findViewById(R.id.image_dress_fragment_screen_slide_page_dress);
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        if (this.getArguments() != null)
        {
            Image image = this.getArguments().getParcelable("image");

            if (image != null)
            {
                Glide.with(this).load(image.getdownloadLink()).listener(new RequestListener<Drawable>()
                {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
                    {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
                    {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(image_dress_fragment_screen_slide_page_dress);
            }
        }

        return rootView;
    }
}
