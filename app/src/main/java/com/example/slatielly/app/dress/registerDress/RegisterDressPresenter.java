package com.example.slatielly.app.dress.registerDress;

import android.support.annotation.NonNull;

import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.service.ValidationService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class RegisterDressPresenter implements RegisterDressContract.Presenter, OnFailureListener, OnSuccessListener<DocumentReference> {

    private RegisterDressContract.View view;
    private ValidationService validationService;
    private FirestoreRepository<Dress> repository;

    public RegisterDressPresenter(RegisterDressContract.View view, ValidationService validationService,
                                  FirestoreRepository<Dress> repository) {
        this.view = view;
        this.validationService = validationService;
        this.repository = repository;
    }

    @Override
    public void createValidationSchema() {
        this.validationService.addValidation(R.id.tilMaterial, "^(?=\\s*\\S).*$", R.string.err_material);
        this.validationService.addValidation(R.id.tilSize, "^(?=\\s*\\S).*$", R.string.err_size);
        this.validationService.addValidation(R.id.tilDescription, "^(?=\\s*\\S).*$", R.string.err_description);
        this.validationService.addValidation(R.id.tilPriceDress, "^(?=\\s*\\S).*$", R.string.err_price);
        this.validationService.addValidation(R.id.tilDaysOfWashing, "^(?=\\s*\\S).*$", R.string.err_washing);
        this.validationService.addValidation(R.id.tilDaysOfPrepare, "^(?=\\s*\\S).*$", R.string.err_prepare);
        this.validationService.addValidation(R.id.tilTypeDress, "^(?=\\s*\\S).*$", R.string.err_type);
        this.validationService.addValidation(R.id.tilColor, "^(?=\\s*\\S).*$", R.string.err_color);
    }

    @Override
    public void createDress(Dress dress) {
        if (this.validationService.validate()) {
            this.view.setLoadingStatus(true);

            this.repository
                    .add(dress)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        this.view.setLoadingStatus(false);
        this.view.setErrorMessage(e.getLocalizedMessage());
    }

    @Override
    public void onSuccess(DocumentReference documentReference) {
        this.view.navigateToAllDresses();
    }
}
