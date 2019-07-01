package com.example.slatielly.app.dress;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.slatielly.model.Image;

import java.util.ArrayList;


public class ScreenSlideAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<Image> images;

    public ScreenSlideAdapter(FragmentManager fm, ArrayList<Image> images)
    {
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int position)
    {
        return ScreenSlideFragment.newInstance(images.get(position));
    }

    @Override
    public int getCount()
    {
        return images.size();
    }

    @Override
    public Parcelable saveState()
    {
        return null;
    }
}
