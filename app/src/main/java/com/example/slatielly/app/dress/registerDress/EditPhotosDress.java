package com.example.slatielly.app.dress.registerDress;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.slatielly.R;
import com.example.slatielly.app.dress.edit.EditDressContract;
import com.example.slatielly.app.dress.edit.EditDressPresenter;
import com.example.slatielly.model.Image;
import com.example.slatielly.model.image.BitMapCompression;
import com.example.slatielly.view.dress.editPhotos.EditPhotosAdapter;
import com.example.slatielly.view.dress.editPhotos.EditPhotosHolder;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class EditPhotosDress extends Fragment
{
    private RecyclerView R_edit_photos_dress;
    private EditPhotosAdapter editPhotosAdapter;


    private RegisterDressContract.Presenter presenterRegister;

    private EditDressContract.Presenter presenterEdit;

    private ArrayList<Bitmap> imagesRegister;

    private MenuItem menuItemTrash;

    private MenuItem menuItemAdd;

    private OnNavigationListener onNavigationListener;

    @SuppressLint("ValidFragment")
    public EditPhotosDress(RegisterDressContract.Presenter presenterRegister, EditDressContract.Presenter presenterEdit)
    {
        this.presenterEdit = presenterEdit;
        this.presenterRegister = presenterRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return inflater.inflate( R.layout.fragment_edit_photos_dress, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_edit_photo, menu);

        menuItemTrash = menu.findItem(R.id.trash_image);
        menuItemAdd = menu.findItem(R.id.add_image);

        menuItemTrash.setVisible(false);
        menuItemAdd.setVisible(false);

        if(presenterRegister == null)
        {
            menuItemAdd.setVisible(true);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        R_edit_photos_dress = (RecyclerView) view.findViewById(R.id.R_edit_photos_dress);

        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration();

        R_edit_photos_dress.addItemDecoration(verticalSpaceItemDecoration);

        this.onNavigationListener.enableViews(true);

        if(this.presenterEdit == null)
        {
            this.imagesRegister = this.presenterRegister.getImages();
            editPhotosAdapter = new EditPhotosAdapter(this.imagesRegister,null,this);
        }
        else
        {
            editPhotosAdapter = new EditPhotosAdapter( null, presenterEdit.getImagesDress(), this );
        }

        R_edit_photos_dress.setAdapter(editPhotosAdapter);

        R_edit_photos_dress.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(this.getContext(),3,GridLayoutManager.VERTICAL,false);

        R_edit_photos_dress.setLayoutManager(manager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.trash_image)
        {
            EditPhotosHolder.onLongclick = false;

            if(this.presenterEdit == null)
            {
                presenterRegister.updateImages(editPhotosAdapter.getImagesDeleteRegister());
                editPhotosAdapter.setImagesDeleteRegister(new ArrayList<Bitmap>());
                editPhotosAdapter.setImagesRegister(presenterRegister.getImages());
                menuItemInvisible();
                editPhotosAdapter.notifyDataSetChanged();
            }
            else
            {
                presenterEdit.updateImages(editPhotosAdapter.getImageDressDelete());
                editPhotosAdapter.setImageDressDelete(new ArrayList<Object>());
                editPhotosAdapter.setImagesDress(presenterEdit.getImagesDress());
                menuItemInvisible();
                editPhotosAdapter.notifyDataSetChanged();
            }

            return true;
        }

        if(id == R.id.add_image)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, 1);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1)
        {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();

            BitMapCompression bitMapCompression = new BitMapCompression();
            presenterEdit.getImagesDress().add(bitMapCompression.compressedBitmap(picturePath));

            editPhotosAdapter.notifyDataSetChanged();
        }
    }

    public void menuItemVisible()
    {
        menuItemTrash.setVisible(true);
    }

    public void menuItemInvisible()
    {
        menuItemTrash.setVisible(false);
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

            if(parent.getChildAdapterPosition(view)%3 != 0)
            {
                outRect.left = 0;
            }
        }
    }

    public interface OnNavigationListener
    {
        void enableViews(boolean enable);
    }

    public void setOnNavigationListener(EditPhotosDress.OnNavigationListener listener)
    {
        this.onNavigationListener = listener;
    }

    public EditDressContract.Presenter getPresenterEdit()
    {
        return presenterEdit;
    }
}
