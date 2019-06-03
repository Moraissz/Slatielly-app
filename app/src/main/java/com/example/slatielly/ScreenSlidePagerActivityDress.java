package com.example.slatielly;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.slatielly.model.Dress;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScreenSlidePagerActivityDress extends AppCompatActivity
{

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    private Dress dress;//INSTANCIAR ELE!

    private TextView textView_description_fragment_screen_slide_dress;
    private TextView textView_size_fragment_screen_slide_dress;
    private TextView textView_color_fragment_screen_slide_dress;
    private TextView textView_price_fragment_screen_slide_dress;
    private TextView textView_type_fragment_screen_slide_dress;
    private TextView textView_material_fragment_screen_slide_dress;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_screen_slide_dress);


        mPager = (ViewPager) findViewById(R.id.pager_dress_fragment_screen_slide);

        setTextsViewsReferences();
        setTextsViewsDressElements(dress);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onBackPressed()
    {
        if (mPager.getCurrentItem() == 0)
        {
            super.onBackPressed();
        }
        else
        {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return new ScreenSlidePageFragmentDress(position,dress);
        }

        @Override
        public int getCount()
        {
            return dress.getImagens().size();
        }
    }

    @SuppressLint("ValidFragment")
    private static class ScreenSlidePageFragmentDress extends Fragment
    {
        private ImageView image_dress_fragment_screen_slide_page_dress;

        private int position;

        private Dress dress;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page_dress, container, false);


            image_dress_fragment_screen_slide_page_dress = (ImageView) rootView.findViewById(R.id.image_dress_fragment_screen_slide_page_dress);


            Glide.with(this).load(dress.getImagens().get(position).getdownloadLink()).into(image_dress_fragment_screen_slide_page_dress);


            return rootView;
        }

        public ScreenSlidePageFragmentDress(int position, Dress dress)
        {
            this.position = position;
            this.dress = dress;
        }
    }

    public void setTextsViewsReferences()
    {
        textView_description_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_description_fragment_screen_slide_dress);
        textView_size_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_size_fragment_screen_slide_dress);
        textView_color_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_color_fragment_screen_slide_dress);
        textView_price_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_price_fragment_screen_slide_dress);
        textView_type_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_type_fragment_screen_slide_dress);
        textView_material_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_material_fragment_screen_slide_dress);
    }


    public void setTextsViewsDressElements(Dress dress)
    {
        textView_description_fragment_screen_slide_dress.setText(dress.getDescription());
        textView_size_fragment_screen_slide_dress.setText(dress.getSize());
        textView_color_fragment_screen_slide_dress.setText(dress.getColor());
        textView_price_fragment_screen_slide_dress.setText(dress.getPrice());
        textView_type_fragment_screen_slide_dress.setText(dress.getType());
        textView_material_fragment_screen_slide_dress.setText(dress.getMaterial());
    }


    public void Order_click(View view)
    {

    }

    public void Comments_click(View view)
    {

    }

}