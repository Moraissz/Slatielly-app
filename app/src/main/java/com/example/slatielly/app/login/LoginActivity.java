package com.example.slatielly.app.login;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.slatielly.MainActivity;
import com.example.slatielly.R;
import com.example.slatielly.service.ValidationService;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private TextInputEditText ptxtEmail;
    private TextInputEditText ptxtPassword;
    private TextView txtErrorMessage;
    private ProgressBar loadingBar;
    private Button btnSubmit;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ValidationService validationService = new ValidationService(this);
        this.presenter = new LoginPresenter(this, validationService);

        this.setupViews();
        this.presenter.createValidationSchema();
    }

    private void setupViews() {
        this.ptxtEmail = this.findViewById(R.id.ptxtEmail);
        this.ptxtPassword = this.findViewById(R.id.ptxtPassword);
        this.txtErrorMessage = this.findViewById(R.id.txtErrorMessage);

        this.loadingBar = this.findViewById(R.id.loadingBar);
        this.btnSubmit = this.findViewById(R.id.btnSubmit);
    }

    public void onClickLogin(View v) {
        String email = this.ptxtEmail.getText().toString();
        String password = this.ptxtPassword.getText().toString();

        this.presenter.loginUser(email, password);
    }

    @Override
    public void setLoadingStatus(boolean isLoading) {
        if (isLoading) {
            this.loadingBar.setVisibility(ProgressBar.VISIBLE);
            this.btnSubmit.setEnabled(false);
            return;
        }

        this.loadingBar.setVisibility(ProgressBar.INVISIBLE);
        this.btnSubmit.setEnabled(true);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.txtErrorMessage.setText(errorMessage);
        this.txtErrorMessage.setVisibility(TextView.VISIBLE);
    }

    @Override
    public void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }
}
