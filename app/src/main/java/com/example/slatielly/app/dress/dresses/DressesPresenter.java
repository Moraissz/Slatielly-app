package com.example.slatielly.app.dress.dresses;

import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DressesPresenter implements DressesContract.Presenter {

    private DressesContract.View view;
    private FirestoreRepository<Dress> firestoreRepository;

    public DressesPresenter(DressesContract.View view, FirestoreRepository<Dress> firestoreRepository) {
        this.view = view;
        this.firestoreRepository = firestoreRepository;
    }

    @Override
    public FirestoreRecyclerOptions<Dress> getRecyclerOptions() {
        return new FirestoreRecyclerOptions
                .Builder<Dress>()
                .setQuery(this.firestoreRepository.getQuery(), Dress.class)
                .build();
    }
}
