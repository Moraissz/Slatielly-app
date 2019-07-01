package com.example.slatielly.app.calendar.ConfirmRent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.repository.FirestoreRepository;

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
    private TextView RenStartDate;
    private TextView RentEndDate;
    private TextView txtRentPrice;

    @SuppressLint("ValidFragment")
    public ConfirmRentFragment(Rent rent , Dress dress)
    {
        this.rent = rent;
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

        btnRentConfirm.setOnClickListener(this);
        btnRentCancel.setOnClickListener(this);

        String DateStart = formDate(rent.getStartDate());
        String DateEnd = formDate(rent.getEndDate());

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
            onNavigateListener.onNavigateToAllDresses2();

            Toast.makeText(this.getContext().getApplicationContext(), "RENT CANCELED!", Toast.LENGTH_SHORT).show();
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
