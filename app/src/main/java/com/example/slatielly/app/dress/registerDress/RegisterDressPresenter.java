package com.example.slatielly.app.dress.registerDress;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Image;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.service.ValidationService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisterDressPresenter implements RegisterDressContract.Presenter, OnFailureListener, OnSuccessListener<DocumentReference> {

    private RegisterDressContract.View view;
    private ValidationService validationService;
    private FirestoreRepository<Dress> repository;

    private List<Bitmap> images;
    private Dress dress;

    public RegisterDressPresenter(RegisterDressContract.View view, ValidationService validationService,
                                  FirestoreRepository<Dress> repository) {
        this.view = view;
        this.validationService = validationService;
        this.repository = repository;
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
    public void createDress(Dress dress, List<Bitmap> images) {
        if (this.validationService.validate()) {
            this.view.setLoadingStatus(true);

            this.images = images;
            this.dress = dress;

            this.repository
                    .add(dress)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        this.view.setLoadingStatus(false);
        this.view.setErrorMessage(e.getLocalizedMessage());
    }

    @Override
    public void onSuccess(DocumentReference documentReference)
    {
        this.SaveImages(documentReference);

        this.view.navigateToAllDresses();
    }

    public void SaveImages(DocumentReference documentReference)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Random generator = new Random();
        final ArrayList<Image> imagesAux = new ArrayList<>();

        for(int i=0;i<this.images.size();i=i+1)
        {
            final String address = "images/dresses/"+documentReference.getId()+"/"+ generator.nextInt(1000000000);

            final StorageReference imageRef = storageRef.child(address);

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            images.get(i).compress(Bitmap.CompressFormat.JPEG, 100, byteStream);

            byte[] data = byteStream.toByteArray();

            final int finalConstant = i;

            imageRef.putBytes(data).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>()
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

                            imagesAux.add(image);

                            if(finalConstant == imagesAux.size()-1)
                            {
                                dress.setImages(imagesAux );
                                repository.update(dress);
                            }
                        }
                    });
                }


            });
        }
    }

    public Bitmap CompressedBitmap(String imagePath)
    {
        Bitmap thumbnail = (BitmapFactory.decodeFile(imagePath));
        float maxHeight;
        float maxWidth;

        if(thumbnail.getHeight()>thumbnail.getWidth())
        {
            maxHeight = 1080.0f;
            maxWidth = 1920.0f;
        }
        else
        {
            maxHeight = 1920.0f;
            maxWidth = 1080.0f;
        }

        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth)
        {
            if (imgRatio < maxRatio)
            {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            }
            else if (imgRatio > maxRatio)
            {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            }
            else
            {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

        options.inSampleSize = (int) calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try
        {
            bmp = BitmapFactory.decodeFile(imagePath, options);
        }
        catch (OutOfMemoryError exception)
        {
            exception.printStackTrace();
        }
        try
        {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        }
        catch (OutOfMemoryError exception)
        {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        ExifInterface exif = null;
        try
        {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6)
            {
                matrix.postRotate(90);
            }
            else if (orientation == 3)
            {
                matrix.postRotate(180);
            }
            else if (orientation == 8)
            {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);

        byte[] byteArray = out.toByteArray();

        Bitmap updatedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        return updatedBitmap;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap)
        {
            inSampleSize++;
        }
        return inSampleSize;
    }
}
