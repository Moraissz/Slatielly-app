package com.example.slatielly.view.dress.editPhotos;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.app.dress.registerDress.EditPhotosDress;

import java.util.ArrayList;

public class EditPhotosAdapter extends RecyclerView.Adapter<EditPhotosHolder>
{
    private ArrayList<Bitmap> images;

    private ArrayList<Bitmap> imagesDeleteRegister;

    private EditPhotosDress editPhotosDress;

    public EditPhotosAdapter(ArrayList<Bitmap> images, EditPhotosDress editPhotosDress)
    {
        this.images = images;
        this.editPhotosDress = editPhotosDress;

        imagesDeleteRegister = new ArrayList<>();

        EditPhotosHolder.onLongclick = false;
    }

    @NonNull
    @Override
    public EditPhotosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate( R.layout.edit_photo_view_holder,viewGroup,false);

        EditPhotosHolder editPhotosHolder = new EditPhotosHolder(view, this);

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

    public ArrayList<Bitmap> getImagesDeleteRegister()
    {
        return imagesDeleteRegister;
    }

    public void addImageDelete(Bitmap bitmap)
    {
        imagesDeleteRegister.add(bitmap);
    }

    public void removeImageDelete(Bitmap bitmap)
    {
        imagesDeleteRegister.remove(bitmap);
    }

    public EditPhotosDress getEditPhotosDress()
    {
        return editPhotosDress;
    }


    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
    }

    public void setImagesDeleteRegister(ArrayList<Bitmap> imagesDeleteRegister) {
        this.imagesDeleteRegister = imagesDeleteRegister;
    }

}
