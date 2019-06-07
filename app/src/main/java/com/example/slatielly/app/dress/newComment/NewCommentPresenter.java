package com.example.slatielly.app.dress.newComment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;

import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

public class NewCommentPresenter implements NewCommentContract.Presenter, OnSuccessListener<DocumentSnapshot>
{
    private User currentUser;
    private NewCommentContract.View view;
    private FirestoreRepository<Dress> repository;
    private Dress dress;
    private Bitmap image;

    public NewCommentPresenter(NewCommentContract.View view,  FirestoreRepository<Dress> repository)
    {
        this.view = view;
        this.repository = repository;
    }

    public void setUser()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(this);
    }


    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot)
    {
        this.currentUser = documentSnapshot.toObject(User.class);
    }

    @Override
    public void saveImage(String imagePath) {
        Bitmap image = this.compressedBitmap(imagePath);
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public FirestoreRepository<Dress> getRepository()
    {
        return repository;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setDress(Dress dress) {
        this.dress = dress;
    }

    public void saveComment(String description,String id)
    {
        Calendar aux = Calendar.getInstance();

        setUser();

        Comment comment = new Comment();
        comment.setUser(this.currentUser);
        comment.setDescription(description);
        comment.setDate(new Timestamp(aux.getTimeInMillis()));
    }

    private Bitmap compressedBitmap(String imagePath) {
        Bitmap thumbnail = (BitmapFactory.decodeFile(imagePath));
        float maxHeight;
        float maxWidth;

        if (thumbnail.getHeight() > thumbnail.getWidth()) {
            maxHeight = 1080.0f;
            maxWidth = 1920.0f;
        } else {
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

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
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

        try {
            bmp = BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
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
        try {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);

        byte[] byteArray = out.toByteArray();

        Bitmap updatedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        return updatedBitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }
}
