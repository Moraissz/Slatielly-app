package com.example.slatielly.app.dress.newComment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Image;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class NewCommentPresenter implements NewCommentContract.Presenter, OnSuccessListener<DocumentSnapshot>
{
    private Bitmap image;
    private byte[] data;
    private String dressId;
    private String description;
    private NewCommentContract.View view;

    public NewCommentPresenter(NewCommentContract.View view)
    {
        this.view = view;
    }

    public void saveComment(String description,String dressId)
    {
        this.dressId = dressId;
        this.description = description;

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(this);
    }

    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot)
    {
        User currentUser = documentSnapshot.toObject(User.class);
        currentUser.setAddress(null);

        Calendar aux = Calendar.getInstance();

        final Comment comment = new Comment();

        final String id = (String.valueOf(aux.getTimeInMillis()))+"-"+currentUser.getName();
        comment.setId(id);
        comment.setUser(currentUser);
        comment.setDescription(this.description);
        comment.setDate(new Timestamp(aux.getTimeInMillis()));
        comment.setNumberLikes(0);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(!(this.data == null))
        {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            Random generator = new Random();

            final String address = "images/comments/dresses/"+dressId+"/"+id+"/"+generator.nextInt(1000000000);

            final StorageReference imageRef = storageRef.child(address);

            imageRef.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri) {
                            Image imageAux = new Image();
                            imageAux.setaddressStorage(address);
                            imageAux.setdownloadLink(uri.toString());

                            comment.setImage(imageAux);

                            db.collection( "dresses" ).document(dressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                            {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot)
                                {

                                    final Dress dress = documentSnapshot.toObject(Dress.class);

                                    dress.getComments().add(comment);

                                    db.collection( "dresses" ).document(dress.getId()).update("comments", dress.getComments());
                                    view.navigateToComments();
                                }
                            });
                        }
                    });
                }
            });
        }
        else
        {
            db.collection( "dresses" ).document(this.dressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
            {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot)
                {

                    final Dress dress = documentSnapshot.toObject(Dress.class);

                    dress.getComments().add(comment);

                    db.collection( "dresses" ).document(dress.getId()).update("comments", dress.getComments());
                    view.navigateToComments();
                }
            });
        }
    }

    @Override
    public void saveImage(String imagePath)
    {
        Bitmap image = this.compressedBitmap(imagePath);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteStream);
        byte[] data = byteStream.toByteArray();

        this.image = image;
        this.data = data;
    }

    public Bitmap getImage()
    {
        return image;
    }
    public void setImage(Bitmap image)
    {
        this.image = image;
        this.data = null;
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
