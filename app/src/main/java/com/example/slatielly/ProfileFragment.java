package com.example.slatielly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.slatielly.Model.Address;
import com.example.slatielly.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment implements View.OnClickListener, OnSuccessListener<Void>, OnFailureListener, OnCompleteListener<Void> {

    private static final String USER_PARAM = "USER_PARAM";
    private User user;
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
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private AwesomeValidation avEditProfile;
    private AwesomeValidation avEditAddress;
    private boolean isSubmittingEP = false;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_PARAM, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.user = getArguments().getParcelable(USER_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setupViews(view);

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

        this.createValidationSchema();
    }

    private void setupViews(View view) {
        this.ptxtEmail = view.findViewById(R.id.ptxtEmail);
        this.ptxtEmail.setText(this.user.getEmail());

        this.ptxtName = view.findViewById(R.id.ptxtName);
        this.ptxtName.setText(this.user.getName());

        this.ptxtPhone = view.findViewById(R.id.ptxtPhone);
        this.ptxtPhone.setText(this.user.getPhone());

        this.ptxtCep = view.findViewById(R.id.ptxtCep);
        this.ptxtCep.setText(Integer.toString(this.user.getAddress().getCep()));

        this.ptxtCity = view.findViewById(R.id.ptxtCity);
        this.ptxtCity.setText(this.user.getAddress().getCity());

        this.ptxtNeighborhood = view.findViewById(R.id.ptxtNeighborhood);
        this.ptxtNeighborhood.setText(this.user.getAddress().getNeighborhood());

        this.ptxtStreet = view.findViewById(R.id.ptxtStreet);
        this.ptxtStreet.setText(this.user.getAddress().getStreet());

        this.ptxtNumber = view.findViewById(R.id.ptxtNumber);
        this.ptxtNumber.setText(Integer.toString(this.user.getAddress().getNumber()));

        this.ptxtComplement = view.findViewById(R.id.ptxtComplement);
        this.ptxtComplement.setText(this.user.getAddress().getComplement());

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

    private void createValidationSchema() {
        this.avEditProfile = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);

        this.avEditProfile.addValidation(this.getActivity(), R.id.tilName, "^(?=\\s*\\S).*$", R.string.err_name);
        this.avEditProfile.addValidation(this.getActivity(), R.id.tilEmail, Patterns.EMAIL_ADDRESS, R.string.err_email);
        this.avEditProfile.addValidation(this.getActivity(), R.id.tilPhone, Patterns.PHONE, R.string.err_phone);

        this.avEditAddress = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        this.avEditAddress.addValidation(this.getActivity(), R.id.tilCep, "\\d{8}", R.string.err_cep);
        this.avEditAddress.addValidation(this.getActivity(), R.id.tilCity, "^(?=\\s*\\S).*$", R.string.err_city);
        this.avEditAddress.addValidation(this.getActivity(), R.id.tilNeighborhood, "^(?=\\s*\\S).*$", R.string.err_neighborhood);
        this.avEditAddress.addValidation(this.getActivity(), R.id.tilStreet, "^(?=\\s*\\S).*$", R.string.err_street);
        this.avEditAddress.addValidation(this.getActivity(), R.id.tilNumber, "^(?=\\s*\\S).*$", R.string.err_number);
    }

    private void onClickEditProfile() {
        if (this.avEditProfile.validate()) {
            this.isSubmittingEP = true;
            this.submit();

            String email = this.ptxtEmail.getText().toString();
            String phone = this.ptxtPhone.getText().toString();
            String name = this.ptxtName.getText().toString();

            user.setEmail(email);
            user.setName(name);
            user.setPhone(phone);

            FirebaseUser firebaseUser = this.firebaseAuth.getCurrentUser();
            firebaseUser.updateEmail(email)
                    .addOnCompleteListener(this);
        }
    }

    private void onClickEditAddress() {
        Log.d("edit", "edit address");
        if (this.avEditAddress.validate()) {
            this.isSubmittingEP = false;
            this.submit();

            int cep = Integer.parseInt(this.ptxtCep.getText().toString());
            String city = this.ptxtCity.getText().toString();
            String neighborhood = this.ptxtNeighborhood.getText().toString();
            String street = this.ptxtStreet.getText().toString();
            int number = Integer.parseInt(this.ptxtNumber.getText().toString());
            String complement = this.ptxtComplement.getText().toString();

            Address address = new Address(cep, city, neighborhood, street, number, complement);
            this.user.setAddress(address);

            this.db.collection("users")
                    .document(this.user.getId())
                    .set(this.user)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);
        }
    }

    private void logout() {
        this.firebaseAuth.signOut();

        Intent intent = new Intent(this.getActivity(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == this.btnSubmitEditProfile) {
            this.onClickEditProfile();
            return;
        }

        if (v == this.btnSubmitEditAddress) {
            this.onClickEditAddress();
            return;
        }

        if (v == this.btnLogout) {
            this.logout();
            return;
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        this.handleError(e);
    }
    
    @Override
    public void onSuccess(Void aVoid) {
        this.stopSubmit();

        if (this.isSubmittingEP) {
            this.txtSuccessMessageEP.setText(R.string.success_edit_profile);
            this.txtSuccessMessageEP.setVisibility(TextView.VISIBLE);
            return;
        }

        this.txtSuccessMessageEA.setText(R.string.success_edit_address);
        this.txtSuccessMessageEA.setVisibility(TextView.VISIBLE);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            this.db.collection("users")
                    .document(this.user.getId())
                    .set(this.user)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);
            return;
        }

        this.handleError(task.getException());
    }

    private void handleError(Exception e) {
        this.stopSubmit();
        String error = e.getLocalizedMessage();

        if (this.isSubmittingEP) {
            this.txtErrorMessageEP.setText(error);
            this.txtErrorMessageEP.setVisibility(TextView.VISIBLE);
            return;
        }

        this.txtErrorMessageEA.setText(error);
        this.txtErrorMessageEA.setVisibility(TextView.VISIBLE);
    }

    private void stopSubmit() {
        this.loadingBar.setVisibility(ProgressBar.INVISIBLE);
        this.btnSubmitEditProfile.setEnabled(true);
        this.btnSubmitEditAddress.setEnabled(true);
    }

    private void submit() {
        this.scrollView.fullScroll(ScrollView.FOCUS_UP);
        this.loadingBar.setVisibility(ProgressBar.VISIBLE);
        this.btnSubmitEditProfile.setEnabled(false);
        this.btnSubmitEditAddress.setEnabled(false);
    }
}
