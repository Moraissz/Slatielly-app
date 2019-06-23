package com.example.slatielly.view.dress.editPhotos;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.app.dress.registerDress.EditPhotosDress;
import com.example.slatielly.model.Image;

import java.util.ArrayList;

public class EditPhotosAdapter extends RecyclerView.Adapter<EditPhotosHolder>
{
    private ArrayList<Bitmap> imagesRegister;

    private ArrayList<Bitmap> imagesDeleteRegister;

    private ArrayList<Object> imagesDress;

    private ArrayList<Object> imageDressDelete;

    private EditPhotosDress editPhotosDress;

    public EditPhotosAdapter(ArrayList<Bitmap> imagesRegister, ArrayList<Object> imagesDress, EditPhotosDress editPhotosDress)
    {
        this.imagesRegister = imagesRegister;
        this.imagesDress = imagesDress;
        this.editPhotosDress = editPhotosDress;

        imagesDeleteRegister = new ArrayList<>();
        imageDressDelete = new ArrayList<>();

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
        if(imagesDress == null)
        {
            editPhotosHolder.bind(this.imagesRegister.get(i),0);
        }
        else
        {
            editPhotosHolder.bind(this.imagesDress.get(i),1);
        }
    }

    @Override
    public int getItemCount()
    {
        if(imagesDress == null)
        {
            return this.imagesRegister.size();
        }
        else
        {
            return this.imagesDress.size();
        }
    }

    public ArrayList<Bitmap> getImagesDeleteRegister()
    {
        return imagesDeleteRegister;
    }

    public void addImageDeleteRegister(Bitmap bitmap)
    {
        imagesDeleteRegister.add(bitmap);
    }

    public void removeImageDeleteRegister(Bitmap bitmap)
    {
        imagesDeleteRegister.remove(bitmap);
    }

    public EditPhotosDress getEditPhotosDress()
    {
        return editPhotosDress;
    }

    public void setImagesRegister(ArrayList<Bitmap> imagesRegister) {
        this.imagesRegister = imagesRegister;
    }

    public void setImagesDeleteRegister(ArrayList<Bitmap> imagesDeleteRegister) {
        this.imagesDeleteRegister = imagesDeleteRegister;
    }

    public ArrayList<Object> getImageDressDelete()
    {
        return imageDressDelete;
    }

    public void setImageDressDelete(ArrayList<Object> imageDressDelete)
    {
        this.imageDressDelete = imageDressDelete;
    }

    public void setImagesDress(ArrayList<Object> imagesDress)
    {
        this.imagesDress = imagesDress;
    }


}
