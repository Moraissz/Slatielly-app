package com.example.slatielly.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.slatielly.LoginActivity;
import com.example.slatielly.MainActivity;
import com.example.slatielly.R;
import com.example.slatielly.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity implements AuthContract.View {

    private AuthContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        this.presenter = new AuthPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.presenter.checkIfUserIsLoggedIn();
    }

    @Override
    public void navigateToLoginScreen(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void navigateToRegisterScreen(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }
}
