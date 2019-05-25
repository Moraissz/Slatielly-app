package com.example.slatielly;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ScreenSlidePagerActivityDress extends AppCompatActivity
{
    private static final int NUM_PAGES = 5;

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

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

        textView_description_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_description_fragment_screen_slide_dress);
        textView_size_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_size_fragment_screen_slide_dress);
        textView_color_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_color_fragment_screen_slide_dress);
        textView_price_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_price_fragment_screen_slide_dress);
        textView_type_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_type_fragment_screen_slide_dress);
        textView_material_fragment_screen_slide_dress =  (TextView) findViewById(R.id.textView_material_fragment_screen_slide_dress);

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

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return new ScreenSlidePageFragmentDress(position);
        }

        @Override
        public int getCount()
        {
            return 6;
        }
    }

    public void Rent_click(View view)
    {

    }

    public void Comments_click(View view)
    {

    }

}