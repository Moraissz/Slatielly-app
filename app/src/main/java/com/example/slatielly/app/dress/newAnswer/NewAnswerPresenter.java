package com.example.slatielly.app.dress.newAnswer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;

import com.example.slatielly.model.Answer;
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
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class NewAnswerPresenter implements NewAswerContract.Presenter, OnSuccessListener<DocumentSnapshot>
{
    private Bitmap image;
    private byte[] data;
    private String dressId;
    private String commentId;
    private String description;
    private NewAswerContract.View view;

    public NewAnswerPresenter(NewAswerContract.View view)
    {
        this.view = view;
    }

    @Override
    public void saveComment(String description, String dressId, String commentId)
    {
        this.dressId = dressId;
        this.description = description;
        this.commentId = commentId;

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

        final Answer answer = new Answer();

        final String id = (String.valueOf(aux.getTimeInMillis()))+"-"+currentUser.getName();
        answer.setId(id);
        answer.setUser(currentUser);
        answer.setDescription(this.description);
        answer.setDate(new Timestamp(aux.getTimeInMillis()));
        answer.setNumberLikes(0);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(!(this.data == null))
        {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            Random generator = new Random();

            final String address = "images/comments/dresses/"+dressId+"/"+commentId+"/answers/"+id+"/"+generator.nextInt(1000000000);

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

                            answer.setImage(imageAux);

                            db.collection( "dresses" ).document(dressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                            {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot)
                                {

                                    final Dress dress = documentSnapshot.toObject(Dress.class);

                                    ArrayList<Comment> comments = dress.getComments();

                                    for(int i=0;i<comments.size();i=i+1)
                                    {
                                        if(commentId.equals(comments.get(i).getId()))
                                        {
                                            comments.get(i).getAnswers().add(answer);
                                            break;
                                        }
                                    }

                                    db.collection( "dresses" ).document(dress.getId()).update("comments", comments);
                                    view.navigateToAnswers();
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

                    ArrayList<Comment> comments = dress.getComments();

                    for(int i=0;i<comments.size();i=i+1)
                    {
                        if(commentId.equals(comments.get(i).getId()))
                        {
                            comments.get(i).getAnswers().add(answer);
                            break;
                        }
                    }

                    db.collection( "dresses" ).document(dress.getId()).update("comments", comments);
                    view.navigateToAnswers();
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

    @Override
    public void setImage(Bitmap image)
    {
        this.image = image;
        this.data = null;
    }

    @Override
    public Bitmap getImage()
    {
        return image;
    }

    public byte[] getData()
    {
        return data;
    }
}
