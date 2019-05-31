package com.example.slatielly.app.register;

import android.support.annotation.NonNull;
import android.util.Patterns;

import com.example.slatielly.Model.User;
import com.example.slatielly.Model.repository.FirestoreRepository;
import com.example.slatielly.R;
import com.example.slatielly.service.ValidationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPresenter implements RegisterContract.Presenter,
        OnCompleteListener<AuthResult>, OnSuccessListener<Void>, OnFailureListener {

    private RegisterContract.View view;
    private ValidationService validationService;
    private FirestoreRepository<User> userRepository;

    private FirebaseAuth firebaseAuth;
    private User user;

    public RegisterPresenter(RegisterContract.View view, ValidationService validationService,
                             FirestoreRepository<User> userRepository) {
        this.view = view;
        this.validationService = validationService;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userRepository = userRepository;
    }

    @Override
    public void createValidationSchema() {
        this.validationService.addValidation(R.id.tilName, "^(?=\\s*\\S).*$", R.string.err_name);
        this.validationService.addValidation(R.id.tilEmail, Patterns.EMAIL_ADDRESS, R.string.err_email);
        this.validationService.addValidation(R.id.tilPhone, Patterns.PHONE, R.string.err_phone);
        this.validationService.addValidation(R.id.tilPassword, "^(?=\\s*\\S).*$", R.string.err_password);
        this.validationService.addValidation(R.id.tilCep, "\\d{8}", R.string.err_cep);
        this.validationService.addValidation(R.id.tilCity, "^(?=\\s*\\S).*$", R.string.err_city);
        this.validationService.addValidation(R.id.tilNeighborhood, "^(?=\\s*\\S).*$", R.string.err_neighborhood);
        this.validationService.addValidation(R.id.tilStreet, "^(?=\\s*\\S).*$", R.string.err_street);
        this.validationService.addValidation(R.id.tilNumber, "^(?=\\s*\\S).*$", R.string.err_number);
    }

    @Override
    public void registerUser(User user, String password) {
        if (this.validationService.validate()) {
            this.view.setLoadingStatus(true);

            this.user = user;
            this.firebaseAuth
                    .createUserWithEmailAndPassword(user.getEmail(), password)
                    .addOnCompleteListener(this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            FirebaseUser firebaseUser = this.firebaseAuth.getCurrentUser();
            String id = firebaseUser.getUid();
            this.user.setId(id);

            this.userRepository
                    .create(this.user)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);

            return;
        }

        this.view.setLoadingStatus(false);
        String errorMessage = task.getException().getLocalizedMessage();
        this.view.setErrorMessage(errorMessage);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        this.view.setLoadingStatus(false);
        this.view.setErrorMessage(e.getLocalizedMessage());
    }

    @Override
    public void onSuccess(Void aVoid) {
        this.view.navigateToMainScreen();
    }
}
