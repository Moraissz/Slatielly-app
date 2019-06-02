package com.example.slatielly.app.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.slatielly.model.Address;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.R;
import com.example.slatielly.app.auth.AuthActivity;
import com.example.slatielly.service.ValidationService;

public class ProfileFragment extends Fragment implements ProfileContract.View, View.OnClickListener {

    private TextInputEditText ptxtEmail;
    private TextInputEditText ptxtPhone;
    private TextInputEditText ptxtName;
    private TextInputEditText ptxtCep;
    private TextInputEditText ptxtCity;
    private TextInputEditText ptxtNeighborhood;
    private TextInputEditText ptxtStreet;
    private TextInputEditText ptxtNumber;
    private TextInputEditText ptxtComplement;
    private TextView txtErrorMessageEP;
    private TextView txtErrorMessageEA;
    private TextView txtSuccessMessageEA;
    private TextView txtSuccessMessageEP;
    private ProgressBar loadingBar;
    private ScrollView scrollView;
    private Button btnSubmitEditProfile;
    private Button btnSubmitEditAddress;
    private Button btnLogout;
    private boolean isSubmittingEP = false;
    private ProfileContract.Presenter presenter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ValidationService vsEditProfile = new ValidationService(this.getActivity());
        ValidationService vsEditAddress = new ValidationService(this.getActivity());
        FirestoreRepository<User> userRepository = new FirestoreRepository<>(User.class, "users");

        this.presenter = new ProfilePresenter(this, vsEditProfile, vsEditAddress, userRepository);

        this.setupViews(view);
        this.presenter.loadUser();
        this.presenter.createValidationSchema();
    }

    private void setupViews(View view) {
        this.ptxtEmail = view.findViewById(R.id.ptxtEmail);
        this.ptxtName = view.findViewById(R.id.ptxtName);
        this.ptxtPhone = view.findViewById(R.id.ptxtPhone);

        this.ptxtCep = view.findViewById(R.id.ptxtCep);
        this.ptxtCity = view.findViewById(R.id.ptxtCity);
        this.ptxtNeighborhood = view.findViewById(R.id.ptxtNeighborhood);
        this.ptxtStreet = view.findViewById(R.id.ptxtStreet);
        this.ptxtNumber = view.findViewById(R.id.ptxtNumber);
        this.ptxtComplement = view.findViewById(R.id.ptxtComplement);

        this.txtErrorMessageEA = view.findViewById(R.id.txtErrorMessageEA);
        this.txtErrorMessageEP = view.findViewById(R.id.txtErrorMessageEP);
        this.txtSuccessMessageEA = view.findViewById(R.id.txtSuccessMessageEA);
        this.txtSuccessMessageEP = view.findViewById(R.id.txtSuccessMessageEP);

        this.scrollView = view.findViewById(R.id.mainScrollView);
        this.loadingBar = view.findViewById(R.id.loadingBar);

        this.btnSubmitEditProfile = view.findViewById(R.id.btnSubmitEditProfile);
        this.btnSubmitEditProfile.setOnClickListener(this);
        this.btnSubmitEditAddress = view.findViewById(R.id.btnSubmitEditAddress);
        this.btnSubmitEditAddress.setOnClickListener(this);
        this.btnLogout = view.findViewById(R.id.btnLogout);
        this.btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == this.btnSubmitEditProfile) {
            String email = this.ptxtEmail.getText().toString();
            String phone = this.ptxtPhone.getText().toString();
            String name = this.ptxtName.getText().toString();

            this.presenter.editProfile(email, phone, name);
            return;
        }

        if (v == this.btnSubmitEditAddress) {
            int cep = Integer.parseInt(this.ptxtCep.getText().toString());
            String city = this.ptxtCity.getText().toString();
            String neighborhood = this.ptxtNeighborhood.getText().toString();
            String street = this.ptxtStreet.getText().toString();
            int number = Integer.parseInt(this.ptxtNumber.getText().toString());
            String complement = this.ptxtComplement.getText().toString();

            Address address = new Address(cep, city, neighborhood, street, number, complement);

            this.presenter.editAddress(address);
            return;
        }

        if (v == this.btnLogout) {
            this.presenter.logout();
            return;
        }
    }

    @Override
    public void setLoadingStatus(boolean isLoading) {
        if (isLoading) {
            this.scrollView.fullScroll(ScrollView.FOCUS_UP);
            this.loadingBar.setVisibility(ProgressBar.VISIBLE);
            this.btnSubmitEditProfile.setEnabled(false);
            this.btnSubmitEditAddress.setEnabled(false);

            return;
        }

        this.loadingBar.setVisibility(ProgressBar.INVISIBLE);
        this.btnSubmitEditProfile.setEnabled(true);
        this.btnSubmitEditAddress.setEnabled(true);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        if (this.isSubmittingEP) {
            this.txtErrorMessageEP.setText(errorMessage);
            this.txtErrorMessageEP.setVisibility(TextView.VISIBLE);
            return;
        }

        this.txtErrorMessageEA.setText(errorMessage);
        this.txtErrorMessageEA.setVisibility(TextView.VISIBLE);
    }

    @Override
    public void showUser(User user) {
        this.ptxtEmail.setText(user.getEmail());
        this.ptxtName.setText(user.getName());
        this.ptxtPhone.setText(user.getPhone());

        this.ptxtCep.setText(Integer.toString(user.getAddress().getCep()));
        this.ptxtCity.setText(user.getAddress().getCity());
        this.ptxtNeighborhood.setText(user.getAddress().getNeighborhood());
        this.ptxtStreet.setText(user.getAddress().getStreet());
        this.ptxtNumber.setText(Integer.toString(user.getAddress().getNumber()));
        this.ptxtComplement.setText(user.getAddress().getComplement());
    }

    @Override
    public void navigateToAuthScreen() {
        Intent intent = new Intent(this.getActivity(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    @Override
    public void setSuccessMessage() {
        if (this.isSubmittingEP) {
            this.txtSuccessMessageEP.setText(R.string.success_edit_profile);
            this.txtSuccessMessageEP.setVisibility(TextView.VISIBLE);
            return;
        }

        this.txtSuccessMessageEA.setText(R.string.success_edit_address);
        this.txtSuccessMessageEA.setVisibility(TextView.VISIBLE);
    }

    @Override
    public void setSubmittingEp(boolean isSubmittingEP) {
        this.isSubmittingEP = isSubmittingEP;
    }
}
