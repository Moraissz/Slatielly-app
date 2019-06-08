package com.example.slatielly.app.auth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.slatielly.app.login.LoginActivity;
import com.example.slatielly.MainActivity;
import com.example.slatielly.R;
import com.example.slatielly.app.register.RegisterActivity;

public class AuthActivity extends AppCompatActivity implements AuthContract.View {

    private AuthContract.Presenter presenter;

    private static final int PERMISSAO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.READ_EXTERNAL_STORAGE ))
            {
            }
            else
            {
                ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_REQUEST );
            }
        }

        this.presenter = new AuthPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.presenter.checkIfUserIsLoggedIn();
    }

    public void navigateToLoginScreen(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
    }

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
