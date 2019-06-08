package com.example.slatielly.view.rentRequest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.slatielly.model.Rent;
import com.example.slatielly.R;

public class RentRequestViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView description;
    private TextView hirerName;
    private TextView startDate;
    private TextView endDate;
    private TextView totalPrice;
    private Rent rent;
    private RentRequestAdapter.RentRequestListener listener;


    public RentRequestViewHolder(@NonNull View itemView, RentRequestAdapter.RentRequestListener listener) {
        super(itemView);
        this.description = (TextView) itemView.findViewById(R.id.RRDressName);
        this.hirerName = (TextView) itemView.findViewById(R.id.RRHirerName);
        this.startDate = (TextView) itemView.findViewById(R.id.RRStartDate);
        this.endDate = (TextView) itemView.findViewById(R.id.RREndDate);
        this.totalPrice = (TextView) itemView.findViewById(R.id.RRTotalPrice);
        this.listener = listener;

        Button btnAccept = itemView.findViewById(R.id.RRbtnAccept);
        btnAccept.setOnClickListener(this);

        Button btnDecline = itemView.findViewById(R.id.RRbtnDecline);
        btnDecline.setOnClickListener(this);
    }

    public void bind(Rent rent){
        this.description.setText(rent.getDress().getDescription());
        this.hirerName.setText(rent.getUser().getName());
        this.startDate.setText(rent.getStartDate().toString());
        this.endDate.setText(rent.getEndDate().toString());
        this.totalPrice.setText(rent.getDress().getPrice().toString());
        this.rent = rent;
    }


    @Override
    public void onClick(View v) {
        listener.onClickRentRequestListener(this.rent);
    }
}
