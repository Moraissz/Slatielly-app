package com.example.slatielly.app.rent.rentRequests;

import com.example.slatielly.model.Rent;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

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
    public void acceptRentRequest(Rent rent) {
        rent.setStatus(Rent.ACCEPTED);
        this.repository.update(rent);
    }

    @Override
    public void declineRentRequest(Rent rent) {
        rent.setStatus(Rent.DECLINED);
        this.repository.update(rent);
    }
}
