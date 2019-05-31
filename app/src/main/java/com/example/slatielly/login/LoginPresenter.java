package com.example.slatielly.login;

import android.support.annotation.NonNull;
import android.util.Patterns;

import com.example.slatielly.R;
import com.example.slatielly.service.ValidationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter implements LoginContract.Presenter, OnCompleteListener<AuthResult> {

    private LoginContract.View view;
    private ValidationService validationService;

    public LoginPresenter(LoginContract.View view, ValidationService validationService) {
        this.view = view;
        this.validationService = validationService;
    }

    @Override
    public void createValidationSchema() {
        this.validationService.addValidation(R.id.tilEmail, Patterns.EMAIL_ADDRESS, R.string.err_email);
        this.validationService.addValidation(R.id.tilPassword, "^(?=\\s*\\S).*$", R.string.err_password);
    }

    @Override
    public void loginUser(String email, String password) {
        if (this.validationService.validate()) {
            this.view.setLoadingStatus(true);

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            this.view.navigateToMainScreen();
            return;
        }

        this.view.setLoadingStatus(false);
        String error = task.getException().getLocalizedMessage();
        this.view.setErrorMessage(error);
    }
}
