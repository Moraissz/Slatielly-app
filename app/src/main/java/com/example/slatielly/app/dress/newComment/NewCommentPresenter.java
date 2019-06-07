package com.example.slatielly.app.dress.newComment;


import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewCommentPresenter implements NewCommentContract.Presenter, OnSuccessListener<DocumentSnapshot>
{
    private User currentUser;
    private NewCommentContract.View view;
    private FirestoreRepository<Dress> repository;
    private String dressId;

    public NewCommentPresenter(NewCommentContract.View view,  FirestoreRepository<Dress> repository)
    {
        this.view = view;
        this.repository = repository;
    }

    public void getUser()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(this);
    }


    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot)
    {
        this.currentUser = documentSnapshot.toObject( User.class);
    }

    public void saveComment(Comment comment, String dressId)
    {

    }
}
