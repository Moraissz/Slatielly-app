package com.example.slatielly.view.dress.editPhotos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.slatielly.R;
import com.example.slatielly.view.LargePhoto;

import java.io.ByteArrayOutputStream;

public class EditPhotosHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener
{
    private EditPhotosAdapter editPhotosAdapter;

    private ImageView image_edit_photo;
    private ImageView image_delete_edit_photo;

    public static boolean onLongclick = false;

    private boolean onLongClickPhoto =  false;

    private Bitmap image;

    private boolean click = false;

    private View view;

    public EditPhotosHolder(@NonNull View itemView, EditPhotosAdapter editPhotosAdapter)
    {
        super(itemView);

        image_edit_photo = (ImageView)  itemView.findViewById(R.id.image_edit_photo);
        image_delete_edit_photo = (ImageView)  itemView.findViewById(R.id.image_delete_edit_photo);

        image_edit_photo.setOnLongClickListener(this);
        image_edit_photo.setOnClickListener(this);

        editPhotosAdapter = editPhotosAdapter;

        view = itemView;
    }

    public void bind(Bitmap bitmap)
    {
        image_edit_photo.setColorFilter(null);
        image_delete_edit_photo.setVisibility(View.GONE);
        image_edit_photo.setImageBitmap(bitmap);

        this.image = bitmap;
    }

    @Override
    public boolean onLongClick(View v)
    {
        if(v == image_edit_photo)
        {
            if(!onLongClickPhoto)
            {
                image_edit_photo.setColorFilter(R.color.colorBlue);
                image_delete_edit_photo.setVisibility(View.VISIBLE);

                EditPhotosHolder.onLongclick = true;

                onLongClickPhoto = true;

                editPhotosAdapter.addImageDelete(this.image);

                editPhotosAdapter.getEditPhotosDress().menuItemVisible();
            }

            click = true;
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

                    if(editPhotosAdapter.getImagesDeleteRegister().size() == 0)
                    {
                        EditPhotosHolder.onLongclick = false;

                        editPhotosAdapter.getEditPhotosDress().menuItemInvisible();
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
                    else
                    {
                        Intent intent = new Intent(itemView.getContext(), LargePhoto.class);

                        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

                        image.compress(Bitmap.CompressFormat.JPEG, 50, byteStream);

                        byte[] data = byteStream.toByteArray();

                        intent.putExtra(LargePhoto.KeyOption,"bitmap");

                        intent.putExtra(LargePhoto.KeyPhoto,data);

                        view.getContext().startActivity(intent);
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
