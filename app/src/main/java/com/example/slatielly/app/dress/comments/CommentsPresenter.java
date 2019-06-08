package com.example.slatielly.app.dress.comments;

import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.comment.CommentViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CommentsPresenter implements CommentsContract.Presenter, OnSuccessListener<Dress> {
    private CommentsContract.View view;
    private FirestoreRepository<Dress> repository;
    private CommentViewHolder view2;

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

    public void checkUser(final Comment comment, final int option)
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
                if(state && option == 0)
                {
                    view2.addLike(currentUser);
                }
                else if(!state && option == 1)
                {
                    view2.setLike();
                }
                else if(state && option == 1)
                {
                    view2.setLike2();
                }
                else
                {
                    view2.subtractLike(currentUser);
                }
            }
        });
    }

    public void updateComment(final Comment comment, String dressId)
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
                        comments.get(i).setLikes(comment.getLikes());
                        comments.get(i).setNumberLikes(comment.getNumberLikes());
                        break;
                    }
                }
                db.collection( "dresses" ).document(dress.getId()).update("comments", comments);
            }
        });
    }
}
