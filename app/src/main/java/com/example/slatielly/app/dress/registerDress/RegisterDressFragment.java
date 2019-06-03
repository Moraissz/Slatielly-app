package com.example.slatielly.app.dress.registerDress;

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

import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.service.ValidationService;


public class RegisterDressFragment extends Fragment implements RegisterDressContract.View, View.OnClickListener {

    private TextInputEditText ptxtMaterial;
    private TextInputEditText ptxtColor;
    private TextInputEditText ptxtSize;
    private TextInputEditText ptxtDescription;
    private TextInputEditText ptxtPriceDress;
    private TextInputEditText ptxtDaysOfWashing;
    private TextInputEditText ptxtDaysOfPrepare;
    private TextInputEditText ptxtTypeDress;
    private TextView txtErrorMessage;
    private ProgressBar loadingBar;
    private ScrollView svRegisterDress;
    private Button btnSaveChanges;
    private Button btnEditPhotos;
    private RegisterDressContract.Presenter presenter;
    private OnNavigationListener onNavigationListener;

    public RegisterDressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_dress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ValidationService validationService = new ValidationService(this.getActivity());
        FirestoreRepository<Dress> firestoreRepository = new FirestoreRepository<>(Dress.class, "dresses");
        this.presenter = new RegisterDressPresenter(this, validationService, firestoreRepository);

        this.setupViews(view);
        this.presenter.createValidationSchema();
    }

    private void setupViews(View view) {
        this.ptxtMaterial = view.findViewById(R.id.ptxtMaterial);
        this.ptxtColor = view.findViewById(R.id.ptxtColor);
        this.ptxtSize = view.findViewById(R.id.ptxtSize);
        this.ptxtDescription = view.findViewById(R.id.ptxtDescription);
        this.ptxtPriceDress = view.findViewById(R.id.ptxtPriceDress);
        this.ptxtDaysOfWashing = view.findViewById(R.id.ptxtDaysOfWashing);
        this.ptxtDaysOfPrepare = view.findViewById(R.id.ptxtDaysOfPrepare);
        this.ptxtTypeDress = view.findViewById(R.id.ptxtTypeDress);

        this.btnSaveChanges = view.findViewById(R.id.btnSaveChanges);
        this.btnEditPhotos = view.findViewById(R.id.btnEditPhotos);
        this.btnEditPhotos.setOnClickListener(this);
        this.btnSaveChanges.setOnClickListener(this);

        this.txtErrorMessage = view.findViewById(R.id.txtErrorMessage);
        this.loadingBar = view.findViewById(R.id.loadingBar);
        this.svRegisterDress = view.findViewById(R.id.svRegisterDress);
    }

    @Override
    public void setLoadingStatus(boolean isLoading) {
        if (isLoading) {
            this.svRegisterDress.fullScroll(ScrollView.FOCUS_UP);
            this.loadingBar.setVisibility(ProgressBar.VISIBLE);
            this.btnSaveChanges.setEnabled(false);
            this.btnEditPhotos.setEnabled(false);

            return;
        }

        this.loadingBar.setVisibility(ProgressBar.INVISIBLE);
        this.btnSaveChanges.setEnabled(true);
        this.btnEditPhotos.setEnabled(true);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.txtErrorMessage.setText(errorMessage);
        this.txtErrorMessage.setVisibility(TextView.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v == this.btnSaveChanges) {
            String material = this.ptxtMaterial.getText().toString();
            String color = this.ptxtColor.getText().toString();
            String size = this.ptxtSize.getText().toString();
            String description = this.ptxtDescription.getText().toString();
            Double price = Double.parseDouble(this.ptxtPriceDress.getText().toString());
            String type = this.ptxtTypeDress.getText().toString();
            int washingDays = Integer.parseInt(this.ptxtDaysOfWashing.getText().toString());
            int prepareDays = Integer.parseInt(this.ptxtDaysOfPrepare.getText().toString());

            Dress dress = new Dress(description, type, price, size, color, material,
                    washingDays, prepareDays);

            this.presenter.createDress(dress);
            return;
        }
    }

    @Override
    public void navigateToAllDresses() {
        this.onNavigationListener.navigateToAllDresses();
    }

    public void setOnNavigationListener(OnNavigationListener onNavigationListener) {
        this.onNavigationListener = onNavigationListener;
    }

    public interface OnNavigationListener {
        void navigateToAllDresses();
    }
}
