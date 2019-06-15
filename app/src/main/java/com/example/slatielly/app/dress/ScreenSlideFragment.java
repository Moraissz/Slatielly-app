package com.example.slatielly.app.dress;

import android.content.Intent;
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
import com.example.slatielly.R;
import com.example.slatielly.model.Image;
import com.example.slatielly.view.LargePhoto;


public class ScreenSlideFragment extends Fragment implements View.OnClickListener
{
    ImageView image_dress_fragment_screen_slide_page_dress;

    Image image;
    public static ScreenSlideFragment newInstance(Image image)
    {
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page_dress, container, false);
        image_dress_fragment_screen_slide_page_dress = rootView.findViewById(R.id.image_dress_fragment_screen_slide_page_dress);

        image_dress_fragment_screen_slide_page_dress.setOnClickListener(this);

        if (this.getArguments() != null)
        {
            this.image = this.getArguments().getParcelable("image");

            if (this.image != null)
            {
                Glide.with(this).load(this.image.getdownloadLink()).into(image_dress_fragment_screen_slide_page_dress);
            }
        }
        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        if(v == image_dress_fragment_screen_slide_page_dress)
        {
            Intent intent = new Intent(this.getContext(), LargePhoto.class);

            intent.putExtra(LargePhoto.KeyPhoto,image.getaddressStorage());

            startActivity(intent);
        }

    }
}
