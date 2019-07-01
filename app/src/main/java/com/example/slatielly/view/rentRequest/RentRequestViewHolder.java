package com.example.slatielly.view.rentRequest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.slatielly.R;
import com.example.slatielly.model.Rent;

import java.text.DateFormat;
import java.text.NumberFormat;

public class RentRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView description;
    private TextView hirerName;
    private TextView startDate;
    private ImageView dressImage;
    private TextView endDate;
    private TextView totalPrice;
    private Button btnAccept;
    private Button btnDecline;
    private Rent rent;
    private View view;
    private RentRequestAdapter.RentRequestListener listener;


    public RentRequestViewHolder(@NonNull View itemView, RentRequestAdapter.RentRequestListener listener) {
        super(itemView);
        this.description = (TextView) itemView.findViewById(R.id.RRDressName);
        this.hirerName = (TextView) itemView.findViewById(R.id.RRHirerName);
        this.dressImage = (ImageView) itemView.findViewById(R.id.dressImage);
        this.startDate = (TextView) itemView.findViewById(R.id.RRStartDate);
        this.endDate = (TextView) itemView.findViewById(R.id.RREndDate);
        this.totalPrice = (TextView) itemView.findViewById(R.id.RRTotalPrice);
        this.listener = listener;
        this.view = itemView;
        this.btnAccept = itemView.findViewById(R.id.RRbtnAccept);
        this.btnAccept.setOnClickListener(this);

        this.btnDecline = itemView.findViewById(R.id.RRbtnDecline);
        this.btnDecline.setOnClickListener(this);
    }

    public void bind(Rent rent) {
        this.description.setText(rent.getDress().getDescription());
        this.hirerName.setText(rent.getUser().getName());
        this.startDate.setText(DateFormat.getDateInstance().format(rent.getStartDate()));
        this.endDate.setText(DateFormat.getDateInstance().format(rent.getEndDate()));
        NumberFormat format = NumberFormat.getCurrencyInstance();
        this.totalPrice.setText(format.format(rent.getPrice()));

        if (!rent.getDress().getImages().isEmpty()) {
            Glide.with(this.view).load(rent.getDress().getImages().get(0).getdownloadLink()).into(dressImage);
        }

        this.rent = rent;
    }


    @Override
    public void onClick(View v) {
        if (v == this.btnAccept) {
            this.listener.onClickRentRequestListener(this.rent, RentRequestAdapter.Actions.ACCEPT);
            return;
        }

        if (v == this.btnDecline) {
            this.listener.onClickRentRequestListener(this.rent, RentRequestAdapter.Actions.DECLINE);
            return;
        }
    }
}
