package com.example.slatielly.view.dress.editPhotos;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.slatielly.R;

public class EditPhotosHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener
{
    private ImageView image_edit_photo;
    public EditPhotosHolder(@NonNull View itemView)
    {
        super(itemView);

        image_edit_photo = (ImageView)  itemView.findViewById(R.id.image_edit_photo);

        image_edit_photo.setOnLongClickListener(this);
    }

    public void bind(Bitmap bitmap)
    {
        image_edit_photo.setImageBitmap(bitmap);
    }

    @Override
    public boolean onLongClick(View v)
    {
        image_edit_photo.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
        return false;
    }
}
