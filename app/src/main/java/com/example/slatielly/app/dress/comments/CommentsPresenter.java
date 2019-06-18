package com.example.slatielly.app.dress.comments;

import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Like;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.comment.CommentViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class CommentsPresenter implements CommentsContract.Presenter, OnSuccessListener<Dress> {
    private CommentsContract.View view;
    private FirestoreRepository<Dress> repository;
    private CommentViewHolder viewHolder;

    public CommentsPresenter(CommentsContract.View view,  FirestoreRepository<Dress> repository) {
        this.view = view;
        this.repository = repository;
    }

    public CommentsPresenter(CommentViewHolder view2)
    {
        this.viewHolder = view2;
    }

    @Override
    public void loadComments(String dressId) {
        this.repository
                .get(dressId)
                .addOnSuccessListener(this);
    }

    @Override
    public void onSuccess(Dress dress) {
        this.view.setComments(dress.getComments());
    }


    public void checkUser(final Comment comment)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {

                User currentUser = documentSnapshot.toObject(User.class);

                Set<Like> likes = new HashSet<>(comment.getLikes());
                Like like = new Like();
                like.setUser(currentUser);
                if(likes.contains(like))
                {
                    viewHolder.subtractLike(currentUser);
                }
                else
                {
                    viewHolder.addLike(currentUser);
                }
            }
        });
    }

    public void checkUserBind(final Comment comment)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                Boolean state = true;

                User currentUser = documentSnapshot.toObject(User.class);

                Set<Like> likes = new HashSet<>(comment.getLikes());
                Like like = new Like();
                like.setUser(currentUser);

                if(likes.contains(like))
                {
                    viewHolder.setLike();
                }
                else
                {
                    viewHolder.setLike2();
                }
            }
        });
    }

    public void updateCommentAddLike(final Comment comment, final String dressId, final User currentUser)
    {
        Calendar aux = Calendar.getInstance();

        final Like like = new Like();
        like.setUser(currentUser);
        like.setDateLike(new Timestamp(aux.getTimeInMillis()));

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "dresses" ).document(dressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {

                Dress dress = documentSnapshot.toObject(Dress.class);

                int i = dress.getComments().indexOf(comment);
                dress.getComments().get(i).getLikes().add(like);
                dress.getComments().get(i).setNumberLikes(dress.getComments().get(i).getNumberLikes()+1);

                db.collection( "dresses" ).document(dress.getId()).update("comments",dress.getComments());
            }
        });
    }

    public void updateCommentSubtractLike(final Comment comment, final String dressId, final User currentUser)
    {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "dresses" ).document(dressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                final Dress dress = documentSnapshot.toObject(Dress.class);


                int i = dress.getComments().indexOf(comment);
                dress.getComments().get(i).setNumberLikes(dress.getComments().get(i).getNumberLikes()-1);

                Like like = new Like();
                like.setUser(currentUser);

                int j = dress.getComments().get(i).getLikes().indexOf(like);
                dress.getComments().get(i).getLikes().remove(dress.getComments().get(i).getLikes().get(j));

                db.collection( "dresses" ).document(dress.getId()).update("comments",dress.getComments());
            }
        });
    }
}
