package com.example.slatielly.app.rent.rentRequests.registerRent;

import android.support.annotation.NonNull;

import com.example.slatielly.model.Rent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class RegisterRentPresenter implements RegisterRentContract.Presenter, OnFailureListener, OnSuccessListener<DocumentReference> {
    @Override
    public void createRent(Rent rent) {

    }

    @Override
    public void createValidationSchema() {

    }

    @Override
    public void onFailure(@NonNull Exception e) {

    }

    @Override
    public void onSuccess(DocumentReference documentReference) {

    }
}
