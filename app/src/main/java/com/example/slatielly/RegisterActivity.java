package com.example.slatielly;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.slatielly.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

// TODO: LIDAR COM OS ERROS AOS REGISTRAR USUARIO
public class RegisterActivity extends AppCompatActivity implements OnCompleteListener<AuthResult>, OnSuccessListener<Void> {
    private TextInputEditText ptxtEmail;
    private TextInputEditText ptxtPassword;
    private TextInputEditText ptxtPhone;
    private TextInputEditText ptxtName;
    private ProgressBar loadingBar;
    private Button btnSubmit;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.setupViews();

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

        this.createValidationSchema();
    }

    private void setupViews() {
        this.ptxtEmail = this.findViewById(R.id.ptxtEmail);
        this.ptxtPassword = this.findViewById(R.id.ptxtPassword);
        this.ptxtName = this.findViewById(R.id.ptxtName);
        this.ptxtPhone = this.findViewById(R.id.ptxtPhone);

        this.loadingBar = this.findViewById(R.id.loadingBar);
        this.btnSubmit = this.findViewById(R.id.btnSubmit);

    }

    private void createValidationSchema() {
        this.awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);

        this.awesomeValidation.addValidation(this, R.id.tilName, "/^$|s+/", R.string.err_password);
        this.awesomeValidation.addValidation(this, R.id.tilEmail, Patterns.EMAIL_ADDRESS, R.string.err_email);
        this.awesomeValidation.addValidation(this, R.id.tilPhone, Patterns.PHONE, R.string.err_password);
        this.awesomeValidation.addValidation(this, R.id.tilPassword, "/^$|s+/", R.string.err_password);
    }

    public void onClickRegister(View v) {
        if (this.awesomeValidation.validate()) {
            this.loadingBar.setVisibility(ProgressBar.VISIBLE);
            this.btnSubmit.setEnabled(false);

            String email = this.ptxtEmail.getText().toString();
            String password = this.ptxtPassword.getText().toString();

            this.firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            FirebaseUser firebaseUser = this.firebaseAuth.getCurrentUser();
            String id = firebaseUser.getUid();
            String email = firebaseUser.getEmail();
            String phone = this.ptxtPhone.getText().toString();
            String name = this.ptxtName.getText().toString();

            User user = new User(id, name, email, phone);
            this.db.collection("users")
                    .document(id)
                    .set(user.toHashMap())
                    .addOnSuccessListener(this);

            return;
        }
    }

    @Override
    public void onSuccess(Void aVoid) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }
}
