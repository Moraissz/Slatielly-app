package com.example.slatielly.app.dress;

import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;

public class DressPresenter implements DressContract.Presenter, OnSuccessListener<Dress> {

    private DressContract.View view;
    private FirestoreRepository<Dress> repository;

    public DressPresenter(DressContract.View view, FirestoreRepository<Dress> repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadDress(String id) {
        this.repository
                .get(id)
                .addOnSuccessListener(this);
    }

    @Override
    public void onSuccess(Dress dress) {
        this.view.setDressViews(dress);
        this.view.setScreenSlideAdapter(dress.getImages());
    }
}
