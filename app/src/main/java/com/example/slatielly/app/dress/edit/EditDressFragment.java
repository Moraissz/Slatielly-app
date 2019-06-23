package com.example.slatielly.app.dress.edit;

import android.graphics.Bitmap;
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


import com.example.slatielly.R;
import com.example.slatielly.app.dress.registerDress.RegisterDressContract;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;

import java.text.NumberFormat;

public class EditDressFragment extends Fragment implements EditDressContract.View, View.OnClickListener
{
    private TextInputEditText ptxtMaterialEdit;
    private TextInputEditText ptxtColorEdit;
    private TextInputEditText ptxtSizeEdit;
    private TextInputEditText ptxtDescriptionEdit;
    private TextInputEditText ptxtPriceDressEdit;
    private TextInputEditText ptxtDaysOfWashingEdit;
    private TextInputEditText ptxtDaysOfPrepareEdit;
    private TextInputEditText ptxtTypeDressEdit;
    private ProgressBar loadingBarEdit;
    private ScrollView svEditDress;
    private Button btnSaveChangesEdit;
    private Button btnEditPhotosEdit;
    private OnNavigationListener onNavigationListener;

    private EditDressContract.Presenter presenter;

    private String dressId;
    private Dress dress;

    private boolean first;

    public static EditDressFragment newInstance(String id)
    {
        EditDressFragment editDressFragment = new EditDressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        editDressFragment.setArguments(bundle);
        return editDressFragment;
    }

    public  EditDressFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FirestoreRepository<Dress> repository = new FirestoreRepository<>(Dress.class, Dress.DOCUMENT_NAME);
        this.presenter = new EditDressPresenter(this,repository);
        this.first = true;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate( R.layout.fragment_edit_dress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        this.onNavigationListener.enableViews(true);

        this.setupViews(view);

        if (this.getArguments() != null)
        {
            this.dressId = this.getArguments().getString("id");
            this.presenter.loadDress(dressId);
        }
    }

    private void setupViews(View view)
    {
        this.ptxtMaterialEdit = view.findViewById(R.id.ptxtMaterialEdit);
        this.ptxtColorEdit = view.findViewById(R.id.ptxtColorEdit);
        this.ptxtSizeEdit = view.findViewById(R.id.ptxtSizeEdit);
        this.ptxtDescriptionEdit = view.findViewById(R.id.ptxtDescriptionEdit);
        this.ptxtPriceDressEdit = view.findViewById(R.id.ptxtPriceDressEdit);
        this.ptxtDaysOfWashingEdit = view.findViewById(R.id.ptxtDaysOfWashingEdit);
        this.ptxtDaysOfPrepareEdit = view.findViewById(R.id.ptxtDaysOfPrepareEdit);
        this.ptxtTypeDressEdit = view.findViewById(R.id.ptxtTypeDressEdit);

        this.btnSaveChangesEdit = view.findViewById(R.id.btnSaveChangesEdit);
        this.btnEditPhotosEdit = view.findViewById(R.id.btnEditPhotosEdit);

        this.btnEditPhotosEdit.setOnClickListener(this);
        this.btnSaveChangesEdit.setOnClickListener(this);

        this.loadingBarEdit = view.findViewById(R.id.loadingBarEditDress);
        this.svEditDress = view.findViewById(R.id.svEditDress);
    }

    @Override
    public void setDressView(Dress dress)
    {
        this.dress = dress;

        presenter.setDress(dress);

        if(first)
        {
            ptxtDescriptionEdit.setText(dress.getDescription());
            ptxtSizeEdit.setText(dress.getSize());
            ptxtColorEdit.setText(dress.getColor());
            ptxtPriceDressEdit.setText(String.valueOf(dress.getPrice()));
            ptxtTypeDressEdit.setText(dress.getType());
            ptxtMaterialEdit.setText(dress.getMaterial());
            ptxtDaysOfWashingEdit.setText(String.valueOf(dress.getWashingDays()));
            ptxtDaysOfPrepareEdit.setText(String.valueOf(dress.getPrepareDays()));

            first = false;
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v == btnEditPhotosEdit)
        {
            this.onNavigationListener.onNavigateToEditPhotos(null,presenter);
        }

        if(v == btnSaveChangesEdit)
        {
            this.svEditDress.fullScroll(ScrollView.FOCUS_UP);
            this.loadingBarEdit.setVisibility(ProgressBar.VISIBLE);

            btnEditPhotosEdit.setEnabled(false);
            btnSaveChangesEdit.setEnabled(false);

            String material = this.ptxtMaterialEdit.getText().toString();
            String color = this.ptxtColorEdit.getText().toString();
            String size = this.ptxtSizeEdit.getText().toString();
            String description = this.ptxtDescriptionEdit.getText().toString();
            Double price = Double.parseDouble(this.ptxtPriceDressEdit.getText().toString());
            String type = this.ptxtTypeDressEdit.getText().toString();
            int washingDays = Integer.parseInt(this.ptxtDaysOfWashingEdit.getText().toString());
            int prepareDays = Integer.parseInt(this.ptxtDaysOfPrepareEdit.getText().toString());

            dress.setMaterial(material);
            dress.setColor(color);
            dress.setSize(size);
            dress.setDescription(description);
            dress.setPrice(price);
            dress.setType(type);
            dress.setWashingDays(washingDays);
            dress.setPrepareDays(prepareDays);

            presenter.updateDress(dress);
        }
    }

    public interface OnNavigationListener
    {
        void enableViews(boolean enable);
        void onNavigateToEditPhotos(RegisterDressContract.Presenter presenterRegister, EditDressContract.Presenter presenterEdit);
        void onNavigateToAllDresses();
    }

    public void setOnNavigationListener(OnNavigationListener onNavigationListener)
    {
        this.onNavigationListener = onNavigationListener;
    }

    public void navigateToAllDress()
    {
        this.onNavigationListener.onNavigateToAllDresses();
    }
}
