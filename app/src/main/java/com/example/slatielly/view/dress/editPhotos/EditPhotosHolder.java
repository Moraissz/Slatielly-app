package com.example.slatielly.view.dress.editPhotos;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.slatielly.R;

import java.util.ArrayList;

public class EditPhotosHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener
{
    private EditPhotosAdapter editPhotosAdapter;

    private ImageView image_edit_photo;
    private ImageView image_delete_edit_photo;

    public static boolean onLongclick = false;

    private boolean onLongClickPhoto =  false;

    private Bitmap image;

    private boolean click = false;

    public EditPhotosHolder(@NonNull View itemView, EditPhotosAdapter editPhotosAdapter)
    {
        super(itemView);

        image_edit_photo = (ImageView)  itemView.findViewById(R.id.image_edit_photo);
        image_delete_edit_photo = (ImageView)  itemView.findViewById(R.id.image_delete_edit_photo);

        image_edit_photo.setOnLongClickListener(this);
        image_edit_photo.setOnClickListener(this);

        this.editPhotosAdapter = editPhotosAdapter;
    }

    public void bind(Bitmap bitmap)
    {
        image_edit_photo.setImageBitmap(bitmap);

        this.image = bitmap;
    }

    @Override
    public boolean onLongClick(View v)
    {
        if(v == image_edit_photo)
        {
            image_edit_photo.setColorFilter(R.color.colorBlue);
            image_delete_edit_photo.setVisibility(View.VISIBLE);

            EditPhotosHolder.onLongclick = true;

            onLongClickPhoto = true;

            click = true;

            editPhotosAdapter.addImageDelete(this.image);

        }

        return false;
    }

    @Override
    public void onClick(View v)
    {
        if(v == image_edit_photo)
        {
            if(!click)
            {
                if(onLongClickPhoto)
                {
                    image_edit_photo.setColorFilter(null);

                    image_delete_edit_photo.setVisibility(View.GONE);

                    onLongClickPhoto = false;

                    editPhotosAdapter.removeImageDelete(image);

                    if(editPhotosAdapter.getImagesDelete().size() == 0 )
                    {
                        EditPhotosHolder.onLongclick = false;
                    }
                }
                else
                {
                    if(EditPhotosHolder.onLongclick)
                    {
                        image_edit_photo.setColorFilter(R.color.colorBlue);

                        image_delete_edit_photo.setVisibility(View.VISIBLE);

                        onLongClickPhoto = true;

                        editPhotosAdapter.addImageDelete(this.image);
                    }
                }
            }
            else
            {
                click = false;
            }
        }
    }
}
