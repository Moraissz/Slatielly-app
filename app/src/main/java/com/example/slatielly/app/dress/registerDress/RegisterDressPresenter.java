package com.example.slatielly.app.dress.registerDress;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Image;
import com.example.slatielly.model.image.BitMapCompression;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.service.ValidationService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class RegisterDressPresenter implements RegisterDressContract.Presenter, OnFailureListener, OnSuccessListener<DocumentReference> {

    private RegisterDressContract.View view;
    private ValidationService validationService;
    private FirestoreRepository<Dress> repository;
    private ArrayList<Bitmap> images;
    private Dress dress;


    public RegisterDressPresenter(RegisterDressContract.View view, ValidationService validationService, FirestoreRepository<Dress> repository) {
        this.view = view;
        this.validationService = validationService;
        this.repository = repository;
        this.images = new ArrayList<>();
    }

    @Override
    public void createValidationSchema() {
        this.validationService.addValidation(R.id.tilMaterial, "^(?=\\s*\\S).*$", R.string.err_material);
        this.validationService.addValidation(R.id.tilSize, "^(?=\\s*\\S).*$", R.string.err_size);
        this.validationService.addValidation(R.id.tilDescription, "^(?=\\s*\\S).*$", R.string.err_description);
        this.validationService.addValidation(R.id.tilPriceDress, "^(?=\\s*\\S).*$", R.string.err_price);
        this.validationService.addValidation(R.id.tilDaysOfWashing, "^(?=\\s*\\S).*$", R.string.err_washing);
        this.validationService.addValidation(R.id.tilDaysOfPrepare, "^(?=\\s*\\S).*$", R.string.err_prepare);
        this.validationService.addValidation(R.id.tilTypeDress, "^(?=\\s*\\S).*$", R.string.err_type);
        this.validationService.addValidation(R.id.tilColor, "^(?=\\s*\\S).*$", R.string.err_color);
    }

    @Override

    public void createDress(Dress dress)
    {
        if (this.validationService.validate())
        {
            this.view.setLoadingStatus(true);
            this.dress = dress;

            this.repository
                    .add(dress)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);
        }
    }

    @Override
    public void saveImage(String imagePath)
    {
        BitMapCompression bitMapCompression = new BitMapCompression();

        Bitmap image = bitMapCompression.compressedBitmap(imagePath);
        this.images.add(image);

        System.out.println("ALOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO: "+ images.size());
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        this.view.setLoadingStatus(false);
        this.view.setErrorMessage(e.getLocalizedMessage());
    }

    @Override
    public void onSuccess(DocumentReference documentReference) {
        this.saveImages(documentReference);
    }

    private void saveImages(DocumentReference documentReference)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Random generator = new Random();
        final ArrayList<Image> imagesAux = new ArrayList<>();

        for (Bitmap image : this.images) {
            final String address = "images/dresses/" + documentReference.getId() + "/" + generator.nextInt(1000000000);

            final StorageReference imageRef = storageRef.child(address);

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 50, byteStream);

            byte[] data = byteStream.toByteArray();

            final int finalConstant = this.images.indexOf(image);

            imageRef.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            Image image = new Image();
                            image.setaddressStorage(address);
                            image.setdownloadLink(uri.toString());

                            imagesAux.add(image);

                            if (finalConstant == imagesAux.size() - 1)
                            {
                                dress.setImages(imagesAux);
                                repository.update(dress).addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        view.navigateToAllDresses();
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }

    public ArrayList<Bitmap> getImages()
    {
        return this.images;
    }
}
