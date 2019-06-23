package com.example.slatielly.app.dress.edit;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ArrayAdapter;

import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Image;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class EditDressPresenter implements EditDressContract.Presenter
{
    private EditDressContract.View view;
    private FirestoreRepository<Dress> repository;
    private Dress dress;
    private ArrayList<Object> imagesDress = new ArrayList<>();
    private ArrayList<Image> imagesDressDelete = new ArrayList<>();

    public EditDressPresenter(EditDressContract.View view, FirestoreRepository<Dress> repository)
    {
        this.view = view;
        this.repository = repository;
    }

    public void setDress(Dress dress)
    {
        if(this.dress == null)
        {
            this.dress = dress;

            for(Image image: dress.getImages())
            {
                imagesDress.add(image);
            }
        }
    }

    @Override
    public void loadDress(String id)
    {
        this.repository.get(id).addOnSuccessListener(new OnSuccessListener<Dress>()
        {
            @Override
            public void onSuccess(Dress dress)
            {
                view.setDressView(dress);
            }
        });
    }

    public void updateImages(ArrayList<Object> imagesDelete)
    {
        for(Object object : imagesDelete)
        {
            if(object instanceof Image)
            {
                imagesDress.remove((Image) object);
                imagesDressDelete.add((Image) object);
            }
            else
            {
                imagesDress.remove((Bitmap) object);
            }
        }
    }

    public void updateDress(final Dress dressUpdate)
    {
        dressUpdate.setImages(new ArrayList<Image>());

        final ArrayList<Bitmap> images = new ArrayList<>();

        for(Object object : imagesDress)
        {
            if (object instanceof Image)
            {
                dressUpdate.getImages().add((Image) object);
            }
            else
            {
                images.add((Bitmap) object);
            }
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        for (Image image : imagesDressDelete)
        {
            final StorageReference imageRef = storageRef.child(image.getaddressStorage());

            imageRef.delete();
        }

        Random generator = new Random();
        for (Bitmap image : images)
        {
            final String address = "images/dresses/" + dressUpdate.getId() + "/" + generator.nextInt(1000000000);

            final StorageReference imageRef = storageRef.child(address);

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 50, byteStream);

            byte[] data = byteStream.toByteArray();

            final int finalConstant = images.indexOf(image);

            imageRef.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            Image image = new Image();
                            image.setaddressStorage(address);
                            image.setdownloadLink(uri.toString());

                            dressUpdate.getImages().add(image);

                            if (finalConstant == images.size() - 1)
                            {
                                repository.update(dressUpdate).addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        view.navigateToAllDress();
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

        if(images.size() == 0)
        {
            repository.update(dressUpdate).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                @Override
                public void onSuccess(Void aVoid)
                {
                    view.navigateToAllDress();
                }
            });
        }
    }

    public EditDressContract.View getView()
    {
        return view;
    }

    public ArrayList<Object> getImagesDress()
    {
        return imagesDress;
    }
}
