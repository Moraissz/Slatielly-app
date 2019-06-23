package com.example.slatielly.view.dress.editPhotos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.slatielly.R;
import com.example.slatielly.model.Image;
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

    private int option;

    private Image image2;

    public EditPhotosHolder(@NonNull View itemView, EditPhotosAdapter editPhotosAdapter)
    {
        super(itemView);

        image_edit_photo = (ImageView)  itemView.findViewById(R.id.image_edit_photo);
        image_delete_edit_photo = (ImageView)  itemView.findViewById(R.id.image_delete_edit_photo);

        image_edit_photo.setOnLongClickListener(this);
        image_edit_photo.setOnClickListener(this);

        this.editPhotosAdapter = editPhotosAdapter;

        view = itemView;
    }

    public void bind(Bitmap bitmap, int option)
    {
        image_edit_photo.setColorFilter(null);
        image_delete_edit_photo.setVisibility(View.GONE);
        image_edit_photo.setImageBitmap(bitmap);

        this.image = bitmap;
        this.option = option;
    }

    public void bind(Object object, int option)
    {
        if(object instanceof Image)
        {
            image_edit_photo.setColorFilter(null);
            image_delete_edit_photo.setVisibility(View.GONE);

            this.image2 = (Image) object;

            Glide.with(view.getContext()).load(this.image2.getdownloadLink()).into(image_edit_photo);

        }
        else
        {
            this.image = (Bitmap) object;

            image_edit_photo.setColorFilter(null);
            image_delete_edit_photo.setVisibility(View.GONE);

            image_edit_photo.setImageBitmap(image);
        }

        this.option = 1;
    }

    @Override
    public boolean onLongClick(View v)
    {
        if(v == image_edit_photo)
        {
            if(option == 0)
            {
                if(!onLongClickPhoto)
                {
                    image_edit_photo.setColorFilter(R.color.colorBlue);
                    image_delete_edit_photo.setVisibility(View.VISIBLE);

                    EditPhotosHolder.onLongclick = true;

                    onLongClickPhoto = true;

                    editPhotosAdapter.addImageDeleteRegister(this.image);

                    editPhotosAdapter.getEditPhotosDress().menuItemVisible();

                    click = true;
                }
            }

            if(option == 1)
            {
                if(!onLongClickPhoto)
                {
                    image_edit_photo.setColorFilter(R.color.colorBlue);
                    image_delete_edit_photo.setVisibility(View.VISIBLE);

                    EditPhotosHolder.onLongclick = true;

                    onLongClickPhoto = true;

                    if(image == null)
                    {
                        editPhotosAdapter.getImageDressDelete().add(this.image2);
                    }
                    else
                    {
                        editPhotosAdapter.getImageDressDelete().add(this.image);
                    }

                    editPhotosAdapter.getEditPhotosDress().menuItemVisible();

                    click = true;
                }
            }
        }

        return false;
    }

    @Override
    public void onClick(View v)
    {
        if(v == image_edit_photo)
        {
            if(option == 0)
            {
                if(!click)
                {
                    if(onLongClickPhoto)
                    {
                        image_edit_photo.setColorFilter(null);

                        image_delete_edit_photo.setVisibility(View.GONE);

                        onLongClickPhoto = false;

                        editPhotosAdapter.removeImageDeleteRegister(image);

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

                            editPhotosAdapter.addImageDeleteRegister(this.image);
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

            else
            {
                if(!click)
                {
                    if(onLongClickPhoto)
                    {
                        image_edit_photo.setColorFilter(null);

                        image_delete_edit_photo.setVisibility(View.GONE);

                        onLongClickPhoto = false;

                        if(image == null)
                        {
                            editPhotosAdapter.getImageDressDelete().remove(this.image2);
                        }
                        else
                        {
                            editPhotosAdapter.getImageDressDelete().remove(this.image);
                        }

                        if(editPhotosAdapter.getImageDressDelete().size() == 0)
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

                            if(image == null)
                            {
                                editPhotosAdapter.getImageDressDelete().add(this.image2);
                            }
                            else
                            {
                                editPhotosAdapter.getImageDressDelete().add(this.image);
                            }

                        }
                        else
                        {
                            Intent intent = new Intent(itemView.getContext(), LargePhoto.class);

                            if(image == null)
                            {
                                intent.putExtra(LargePhoto.KeyOption,"firebaseStorage");
                                intent.putExtra(LargePhoto.KeyPhoto,image2.getaddressStorage());
                            }
                            else
                            {
                                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

                                image.compress(Bitmap.CompressFormat.JPEG, 50, byteStream);

                                byte[] data = byteStream.toByteArray();

                                intent.putExtra(LargePhoto.KeyOption,"bitmap");

                                intent.putExtra(LargePhoto.KeyPhoto,data);
                            }
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
}
