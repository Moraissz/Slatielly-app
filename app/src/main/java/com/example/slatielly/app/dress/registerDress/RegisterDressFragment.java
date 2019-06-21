package com.example.slatielly.app.dress.registerDress;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.slatielly.app.dress.DressContract;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.service.ValidationService;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


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
    private Button btnTakePhotos;
    private RegisterDressContract.Presenter presenter;
    private OnNavigationListener onNavigationListener;

    public RegisterDressFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ValidationService validationService = new ValidationService(this.getActivity());
        FirestoreRepository<Dress> firestoreRepository = new FirestoreRepository<>(Dress.class, Dress.DOCUMENT_NAME);
        this.presenter = new RegisterDressPresenter(this, validationService, firestoreRepository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_register_dress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if (this.onNavigationListener != null)
        {
            this.onNavigationListener.enableViews(true);
        }

        this.setupViews(view);
        this.presenter.createValidationSchema();
    }

    private void setupViews(View view)
    {
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
        this.btnTakePhotos = view.findViewById(R.id.btnTakePhotos);
        this.btnEditPhotos.setOnClickListener(this);
        this.btnSaveChanges.setOnClickListener(this);
        this.btnTakePhotos.setOnClickListener(this);

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
        if (v == this.btnSaveChanges)
        {
            String material = this.ptxtMaterial.getText().toString();
            String color = this.ptxtColor.getText().toString();
            String size = this.ptxtSize.getText().toString();
            String description = this.ptxtDescription.getText().toString();
            Double price = Double.parseDouble(this.ptxtPriceDress.getText().toString());
            String type = this.ptxtTypeDress.getText().toString();
            int washingDays = Integer.parseInt(this.ptxtDaysOfWashing.getText().toString());
            int prepareDays = Integer.parseInt(this.ptxtDaysOfPrepare.getText().toString());

            Dress dress = new Dress(description, type, price, size, color, material, washingDays, prepareDays);

            this.presenter.createDress(dress);
            return;
        }

        if (v == this.btnTakePhotos) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, 1);
        }

        if (v == btnEditPhotos)
        {
            this.navigateToEditPhotos();
        }
    }

    public void navigateToEditPhotos()
    {
        this.onNavigationListener.onNavigateToEditPhotos(presenter,null);
    }

    @Override
    public void navigateToAllDresses() {
        this.onNavigationListener.onNavigateToAllDresses();
    }

    public void setOnNavigationListener(OnNavigationListener onNavigationListener) {
        this.onNavigationListener = onNavigationListener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1)
        {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            this.presenter.saveImage(picturePath);
        }
    }

    public interface OnNavigationListener
    {
        void onNavigateToAllDresses();
        void onNavigateToEditPhotos(RegisterDressContract.Presenter presenterRegister, DressContract.Presenter presenterDress);
        void enableViews(boolean enable);
    }
}
