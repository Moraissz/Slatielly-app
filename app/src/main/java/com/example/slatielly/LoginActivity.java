package com.example.slatielly;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {
    private TextInputEditText ptxtEmail;
    private TextInputEditText ptxtPassword;
    private TextView txtErrorMessage;
    private ProgressBar loadingBar;
    private Button btnSubmit;
    private FirebaseAuth firebaseAuth;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setupViews();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.createValidationSchema();
    }

    private void setupViews() {
        this.ptxtEmail = this.findViewById(R.id.ptxtEmail);
        this.ptxtPassword = this.findViewById(R.id.ptxtPassword);
        this.txtErrorMessage = this.findViewById(R.id.txtErrorMessage);

        this.loadingBar = this.findViewById(R.id.loadingBar);
        this.btnSubmit = this.findViewById(R.id.btnSubmit);
    }

    private void createValidationSchema() {
        this.awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);

        this.awesomeValidation.addValidation(this, R.id.tilEmail, Patterns.EMAIL_ADDRESS, R.string.err_email);
        this.awesomeValidation.addValidation(this, R.id.tilPassword, "^(?=\\s*\\S).*$", R.string.err_password);
    }

    public void onClickLogin(View v) {
        if (this.awesomeValidation.validate()) {
            this.loadingBar.setVisibility(ProgressBar.VISIBLE);
            this.btnSubmit.setEnabled(false);

            String email = this.ptxtEmail.getText().toString();
            String password = this.ptxtPassword.getText().toString();

            this.firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);

            return;
        }

        this.loadingBar.setVisibility(ProgressBar.INVISIBLE);
        this.btnSubmit.setEnabled(true);

        String error = task.getException().getLocalizedMessage();
        this.txtErrorMessage.setText(error);
        this.txtErrorMessage.setVisibility(TextView.VISIBLE);
    }
}
