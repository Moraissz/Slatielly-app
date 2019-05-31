package com.example.slatielly.app.register;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.slatielly.MainActivity;
import com.example.slatielly.Model.Address;
import com.example.slatielly.Model.User;
import com.example.slatielly.Model.repository.FirestoreRepository;
import com.example.slatielly.R;
import com.example.slatielly.service.ValidationService;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    private TextInputEditText ptxtEmail;
    private TextInputEditText ptxtPassword;
    private TextInputEditText ptxtPhone;
    private TextInputEditText ptxtName;
    private TextInputEditText ptxtCep;
    private TextInputEditText ptxtCity;
    private TextInputEditText ptxtNeighborhood;
    private TextInputEditText ptxtStreet;
    private TextInputEditText ptxtNumber;
    private TextInputEditText ptxtComplement;
    private TextView txtErrorMessage;
    private ProgressBar loadingBar;
    private ScrollView scrollView;
    private Button btnSubmit;
    private RegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ValidationService validationService = new ValidationService(this);
        FirestoreRepository<User> userRepository = new FirestoreRepository<>(User.class, "users");
        this.presenter = new RegisterPresenter(this, validationService, userRepository);

        this.setupViews();
        this.presenter.createValidationSchema();
    }

    private void setupViews() {
        this.ptxtEmail = this.findViewById(R.id.ptxtEmail);
        this.ptxtPassword = this.findViewById(R.id.ptxtPassword);
        this.ptxtName = this.findViewById(R.id.ptxtName);
        this.ptxtPhone = this.findViewById(R.id.ptxtPhone);
        this.ptxtCep = this.findViewById(R.id.ptxtCep);
        this.ptxtCity = this.findViewById(R.id.ptxtCity);
        this.ptxtNeighborhood = this.findViewById(R.id.ptxtNeighborhood);
        this.ptxtStreet = this.findViewById(R.id.ptxtStreet);
        this.ptxtNumber = this.findViewById(R.id.ptxtNumber);
        this.ptxtComplement = this.findViewById(R.id.ptxtComplement);

        this.txtErrorMessage = this.findViewById(R.id.txtErrorMessage);

        this.scrollView = this.findViewById(R.id.mainScrollView);
        this.loadingBar = this.findViewById(R.id.loadingBar);
        this.btnSubmit = this.findViewById(R.id.btnSubmit);

    }

    public void onClickRegister(View v) {
        String email = this.ptxtEmail.getText().toString();
        String password = this.ptxtPassword.getText().toString();
        String phone = this.ptxtPhone.getText().toString();
        String name = this.ptxtName.getText().toString();

        int cep = Integer.parseInt(this.ptxtCep.getText().toString());
        String city = this.ptxtCity.getText().toString();
        String neighborhood = this.ptxtNeighborhood.getText().toString();
        String street = this.ptxtStreet.getText().toString();
        int number = Integer.parseInt(this.ptxtNumber.getText().toString());
        String complement = this.ptxtComplement.getText().toString();

        Address address = new Address(cep, city, neighborhood, street, number, complement);
        User user = new User(name, email, phone, address);

        this.presenter.registerUser(user, password);
    }

    @Override
    public void setLoadingStatus(boolean isLoading) {
        if (isLoading) {
            this.scrollView.fullScroll(ScrollView.FOCUS_UP);
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
