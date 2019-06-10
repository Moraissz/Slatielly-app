package com.example.slatielly.app.rent.rentRequests;

import com.example.slatielly.model.Rent;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RentRequestsContract {
    interface View {
    }

    interface Presenter {
        FirestoreRecyclerOptions<Rent> getRecyclerOptions();

        void acceptRentRequest(Rent rent);

        void declineRentRequest(Rent rent);
    }
}
