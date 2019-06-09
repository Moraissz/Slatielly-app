package com.example.slatielly.app.dress.comments;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Like;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.comment.CommentViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class CommentsPresenter implements CommentsContract.Presenter, OnSuccessListener<Dress> {
    private CommentsContract.View view;
    private FirestoreRepository<Dress> repository;
    private CommentViewHolder view2;

    private static final String TAG = "FirestoreRepository";

    public CommentsPresenter(CommentsContract.View view,  FirestoreRepository<Dress> repository) {
        this.view = view;
        this.repository = repository;
    }

    public CommentsPresenter(CommentViewHolder view2)
    {
        this.view2 = view2;
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
                Boolean state = true;

                User currentUser = documentSnapshot.toObject(User.class);

                System.out.println("ALOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO2: "+comment.getNumberLikes());

                for(int i=0;i<comment.getLikes().size();i=i+1)
                {
                    if(currentUser.getId().equals(comment.getLikes().get(i).getUser().getId()))
                    {
                        state = false;
                        break;
                    }
                }

                if(state)
                {
                    view2.addLike(currentUser);
                }
                else
                {
                    view2.subtractLike(currentUser);
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

                for(int i=0;i<comment.getLikes().size();i=i+1)
                {
                    if(currentUser.getId().equals(comment.getLikes().get(i).getUser().getId()))
                    {
                        state = false;
                        break;
                    }
                }

                if(!state)
                {
                    view2.setLike();
                }
                else
                {
                    view2.setLike2();
                }
            }
        });
    }

    public void updateCommentAddLike(final String commentId, final String dressId, final User currentUser, final CommentsFragment.OnNavigationListener listener)
    {
        final Like like = new Like();
        like.setUser(currentUser);

        Calendar aux = Calendar.getInstance();
        like.setDateLike(new Timestamp(aux.getTimeInMillis()));

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                        comments.get(i).getLikes().add(like);
                        comments.get(i).setNumberLikes(comments.get(i).getNumberLikes()+1);
                        break;
                    }
                }
                db.collection( "dresses" ).document(dress.getId()).update("comments",comments);
                //listener.onNavigateToComments2(dressId);
            }
        });
    }

    public void updateCommentSubtractLike(final Comment comment, final String dressId, final User currentUser, final CommentsFragment.OnNavigationListener listener)
    {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "dresses" ).document(dressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                final Dress dress = documentSnapshot.toObject(Dress.class);

                ArrayList<Comment> comments = dress.getComments();

                for(int i=0;i<comments.size();i=i+1)
                {
                    if(comment.getId().equals(comments.get(i).getId()))
                    {
                        comments.get(i).setNumberLikes(comments.get(i).getNumberLikes()-1);

                        for(int j=0;j<comments.get(i).getLikes().size();j=j+1)
                        {
                            if(currentUser.getId().equals(comments.get(i).getLikes().get(j).getUser().getId()))
                            {
                                comments.get(i).getLikes().remove(comments.get(i).getLikes().get(j));
                                break;
                            }
                        }
                        break;
                    }
                }
                db.collection( "dresses" ).document(dress.getId()).update("comments",comments);
                //listener.onNavigateToComments2(dressId);
            }
        });
    }
}
