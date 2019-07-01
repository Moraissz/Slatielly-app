package com.example.slatielly.app.calendar.ConfirmRent;

import android.support.annotation.NonNull;

import com.example.slatielly.model.Rent;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class ConfirmRentPresenter implements ConfirmRentContract.Presenter, OnSuccessListener<DocumentReference>, OnFailureListener
{
    private ConfirmRentContract.View view;
    private FirestoreRepository<Rent> repository;

    public ConfirmRentPresenter(ConfirmRentContract.View view, FirestoreRepository<Rent> rentFirestoreRepository)
    {
        this.view = view;
        this.repository = rentFirestoreRepository;
    }

    public void saveRent(Rent rent)
    {
        this.repository.add(rent).addOnSuccessListener(this).addOnFailureListener(this);
    }

    @Override
    public void onSuccess(DocumentReference documentReference)
    {
        view.navigateToDresses();
    }

    @Override
    public void onFailure(@NonNull Exception e)
    {

    }
}
