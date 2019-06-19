package com.example.slatielly.app.dress.registerDress;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.app.dress.DressContract;
import com.example.slatielly.view.dress.editPhotos.EditPhotosAdapter;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class EditPhotosDress extends Fragment
{
    private RecyclerView R_edit_photos_dress;
    private EditPhotosAdapter editPhotosAdapter;

    private RegisterDressContract.Presenter presenterRegister;
    private DressContract.Presenter presenterDress;

    private ArrayList<Bitmap> images;

    @SuppressLint("ValidFragment")
    public EditPhotosDress(RegisterDressContract.Presenter presenterRegister, DressContract.Presenter presenterDress)
    {
        this.presenterDress = presenterDress;
        this.presenterRegister = presenterRegister;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate( R.layout.fragment_edit_photos_dress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        R_edit_photos_dress = (RecyclerView) view.findViewById(R.id.R_edit_photos_dress);

        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration();

        R_edit_photos_dress.addItemDecoration(verticalSpaceItemDecoration);

        if(this.presenterDress == null)
        {
            this.images = this.presenterRegister.getImages();
        }
        else
        {

        }

        editPhotosAdapter = new EditPhotosAdapter(this.images);
        R_edit_photos_dress.setAdapter(editPhotosAdapter);

        GridLayoutManager manager = new GridLayoutManager(this.getContext(),2,GridLayoutManager.VERTICAL,false);

        R_edit_photos_dress.setLayoutManager(manager);
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration
    {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = 10;
            outRect.bottom = 0;
            outRect.right = 10;
            outRect.left = 10;

            if(parent.getChildAdapterPosition(view)%2 != 0)
            {
                outRect.left = 0;
            }
        }
    }
}
