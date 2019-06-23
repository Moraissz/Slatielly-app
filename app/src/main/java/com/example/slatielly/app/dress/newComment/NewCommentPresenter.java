package com.example.slatielly.app.dress.newComment;


import android.graphics.Bitmap;
import android.net.Uri;

import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Image;
import com.example.slatielly.model.User;
import com.example.slatielly.model.image.BitMapCompression;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
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
        BitMapCompression bitMapCompression = new BitMapCompression();

        Bitmap image = bitMapCompression.compressedBitmap(imagePath);
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

    public byte[] getData()
    {
        return data;
    }
}
