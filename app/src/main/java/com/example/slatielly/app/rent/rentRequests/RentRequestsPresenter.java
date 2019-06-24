package com.example.slatielly.app.rent.rentRequests;

import com.example.slatielly.model.Rent;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class RentRequestsPresenter implements RentRequestsContract.Presenter {

    private RentRequestsContract.View view;
    private FirestoreRepository<Rent> repository;

    public RentRequestsPresenter(RentRequestsContract.View view, FirestoreRepository<Rent> repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public FirestoreRecyclerOptions<Rent> getRecyclerOptions() {
        return new FirestoreRecyclerOptions
                .Builder<Rent>()
                .setQuery(this.repository.getQuery("status", Rent.PENDENT), Rent.class)
                .build();
    }

    @Override
    public void acceptRentRequest(final Rent rent) {
        rent.setStatus(Rent.ACCEPTED);
        this.repository.update(rent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                view.removeRent();
            }
        });
    }

    @Override
    public void declineRentRequest(final Rent rent) {
        rent.setStatus(Rent.DECLINED);
        this.repository.update(rent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                view.removeRent();
            }
        });
    }
}
