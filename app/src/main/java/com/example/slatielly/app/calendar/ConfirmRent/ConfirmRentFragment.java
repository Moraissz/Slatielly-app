package com.example.slatielly.app.calendar.ConfirmRent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.LargePhoto;

import java.util.Calendar;
import java.util.Date;

@SuppressLint("ValidFragment")
public class ConfirmRentFragment extends Fragment implements ConfirmRentContract.View, View.OnClickListener
{
    private ConfirmRentContract.Presenter presenter;

    private ConfirmRentFragment.OnNavigateListener onNavigateListener;

    private Rent rent;
    private Dress dress;

    private Button btnRentConfirm;
    private Button btnRentCancel;

    private ImageView imageDress_ConfirmRent;

    private TextView txtDescriptionConfirRent;
    private TextView txtSizeConfirmRent;
    private TextView txtColorConfirmRent;
    private TextView RenStartDate;
    private TextView RentEndDate;
    private TextView txtRentPrice;

    @SuppressLint("ValidFragment")
    public ConfirmRentFragment(Rent rent , Dress dress)
    {
        this.rent = rent;
        this.dress = dress;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate( R.layout.fragment_confirm_rent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        onNavigateListener.enableViews(true);
        FirestoreRepository<Rent> repository = new FirestoreRepository<>(Rent.class, Rent.DOCUMENT_NAME);

        this.presenter = new ConfirmRentPresenter(this, repository);

        btnRentConfirm = view.findViewById(R.id.btnRentConfirm);
        btnRentCancel = view.findViewById(R.id.btnRentCancel);
        RenStartDate = view.findViewById(R.id.RenStartDate);
        RentEndDate = view.findViewById(R.id.RentEndDate);
        txtRentPrice = view.findViewById(R.id.txtRentPrice);
        txtDescriptionConfirRent = view.findViewById(R.id.txtDescriptionConfirRent);
        txtSizeConfirmRent = view.findViewById(R.id.txtSizeConfirmRent);
        txtColorConfirmRent = view.findViewById(R.id.txtColorConfirmRent);
        imageDress_ConfirmRent = view.findViewById(R.id.imageDress_ConfirmRent);

        btnRentConfirm.setOnClickListener(this);
        btnRentCancel.setOnClickListener(this);
        imageDress_ConfirmRent.setOnClickListener(this);

        String DateStart = formDate(rent.getStartDate());
        String DateEnd = formDate(rent.getEndDate());


        Glide.with(this).load(dress.getImages().get(0).getdownloadLink()).into(imageDress_ConfirmRent);
        txtDescriptionConfirRent.setText(dress.getDescription());
        txtSizeConfirmRent.setText(dress.getSize());
        txtColorConfirmRent.setText(dress.getColor());
        RenStartDate.setText(DateStart);
        RentEndDate.setText(DateEnd);
        txtRentPrice.setText(String.valueOf(rent.getPrice()));
    }

    @Override
    public void onClick(View v)
    {
        if(v == btnRentConfirm)
        {
            presenter.saveRent(rent);
        }

        if(v == btnRentCancel)
        {
            Toast.makeText(this.getContext().getApplicationContext(), "RENT CANCELED!", Toast.LENGTH_SHORT).show();

            onNavigateListener.onNavigateToAllDresses2();
        }
        if(v == imageDress_ConfirmRent)
        {
            Intent intent = new Intent(this.getContext(), LargePhoto.class);

            intent.putExtra(LargePhoto.KeyOption,"firebaseStorage");
            intent.putExtra(LargePhoto.KeyPhoto,this.dress.getImages().get(0).getaddressStorage());

            startActivity(intent);
        }
    }

    public String formDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        String dateString = day+"/"+(month+1)+"/"+year;

        return dateString;
    }

    public interface OnNavigateListener
    {
        void onNavigateToAllDresses2();
        void enableViews(boolean enable);
    }

    @Override
    public void navigateToDresses()
    {
        Toast.makeText(this.getContext().getApplicationContext(), "RENT CONFIRMED!", Toast.LENGTH_SHORT).show();
        this.onNavigateListener.onNavigateToAllDresses2();
    }

    public void setOnNavigationListener(ConfirmRentFragment.OnNavigateListener onNavigationListener)
    {
        this.onNavigateListener = onNavigationListener;
    }
}
