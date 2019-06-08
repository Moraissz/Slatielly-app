package com.example.slatielly.app.dress.comments;

import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;

public class CommentsPresenter implements CommentsContract.Presenter, OnSuccessListener<Dress> {
    private CommentsContract.View view;
    private FirestoreRepository<Dress> repository;

    public CommentsPresenter(CommentsContract.View view,  FirestoreRepository<Dress> repository) {
        this.view = view;
        this.repository = repository;
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
}
