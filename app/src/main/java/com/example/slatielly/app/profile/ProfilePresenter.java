package com.example.slatielly.app.profile;

import android.support.annotation.NonNull;
import android.util.Patterns;

import com.example.slatielly.model.Address;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.R;
import com.example.slatielly.service.ValidationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilePresenter implements ProfileContract.Presenter, OnCompleteListener<Void>, OnSuccessListener<Void>, OnFailureListener {

    private ProfileContract.View view;
    private ValidationService vsEditProfile;
    private ValidationService vsEditAddress;
    private FirestoreRepository<User> userRepository;
    private User user;

    public ProfilePresenter(ProfileContract.View view, ValidationService vsEditProfile,
                            ValidationService vsEditAddress, FirestoreRepository<User> userRepository) {
        this.view = view;
        this.vsEditProfile = vsEditProfile;
        this.vsEditAddress = vsEditAddress;
        this.userRepository = userRepository;
    }

    @Override
    public void createValidationSchema() {
        this.vsEditProfile.addValidation(R.id.tilName, "^(?=\\s*\\S).*$", R.string.err_name);
        this.vsEditProfile.addValidation(R.id.tilEmail, Patterns.EMAIL_ADDRESS, R.string.err_email);
        this.vsEditProfile.addValidation(R.id.tilPhone, Patterns.PHONE, R.string.err_phone);

        this.vsEditAddress.addValidation(R.id.tilCep, "\\d{8}", R.string.err_cep);
        this.vsEditAddress.addValidation(R.id.tilCity, "^(?=\\s*\\S).*$", R.string.err_city);
        this.vsEditAddress.addValidation(R.id.tilNeighborhood, "^(?=\\s*\\S).*$", R.string.err_neighborhood);
        this.vsEditAddress.addValidation(R.id.tilStreet, "^(?=\\s*\\S).*$", R.string.err_street);
        this.vsEditAddress.addValidation(R.id.tilNumber, "^(?=\\s*\\S).*$", R.string.err_number);
    }

    @Override
    public void loadUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.userRepository
                .get(firebaseUser.getUid())
                .addOnCompleteListener(new OnCompleteListener<User>() {
                    @Override
                    public void onComplete(@NonNull Task<User> task) {
                        if (task.isSuccessful()) {
                            user = task.getResult();
                            view.showUser(user);
                        }
                    }
                });
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();

        this.view.navigateToAuthScreen();
    }

    @Override
    public void editProfile(String email, String phone, String name) {
        if (this.vsEditProfile.validate()) {
            this.view.setSubmittingEp(true);
            this.view.setLoadingStatus(true);

            this.user.setEmail(email);
            this.user.setName(name);
            this.user.setPhone(phone);


            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            firebaseUser.updateEmail(email)
                    .addOnCompleteListener(this);
        }
    }

    @Override
    public void editAddress(Address address) {
        if (this.vsEditAddress.validate()) {
            this.view.setSubmittingEp(false);
            this.view.setLoadingStatus(true);

            this.user.setAddress(address);

            this.userRepository
                    .update(this.user)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            this.userRepository
                    .update(this.user)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);

            return;
        }

        String errorMessage = task.getException().getLocalizedMessage();
        this.view.setErrorMessage(errorMessage);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        this.view.setErrorMessage(e.getLocalizedMessage());
    }

    @Override
    public void onSuccess(Void aVoid) {
        this.view.setLoadingStatus(false);
        this.view.setSuccessMessage();
    }
}
