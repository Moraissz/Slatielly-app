package com.example.slatielly.app.dress.comments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.slatielly.R;

public class ScreenSlidePagerActivityCommentImages extends AppCompatActivity
{
    private static final int NUM_PAGES = 5;

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_screen_slide_comment_images);


        mPager = (ViewPager) findViewById(R.id.pager_fragment_screen_slide_comment_images);
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
     * A simple pager adapter that represents 5 ScreenSlideFragment objects, in
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
            return new ScreenSlidePageFragmentCommentImages(position);
        }

        @Override
        public int getCount()
        {
            return 6;
        }
    }
}
