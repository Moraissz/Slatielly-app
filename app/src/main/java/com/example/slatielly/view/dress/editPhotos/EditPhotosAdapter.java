package com.example.slatielly.view.dress.editPhotos;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;

import java.util.ArrayList;

public class EditPhotosAdapter extends RecyclerView.Adapter<EditPhotosHolder>
{
    private ArrayList<Bitmap> images;

    public EditPhotosAdapter(ArrayList<Bitmap> images)
    {
        this.images = images;
    }

    @NonNull
    @Override
    public EditPhotosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate( R.layout.edit_photo_view_holder,viewGroup,false);

        EditPhotosHolder editPhotosHolder = new EditPhotosHolder(view);

        return editPhotosHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditPhotosHolder editPhotosHolder, int i)
    {
        editPhotosHolder.bind(this.images.get(i));
    }

    @Override
    public int getItemCount()
    {
        return this.images.size();
    }
}
