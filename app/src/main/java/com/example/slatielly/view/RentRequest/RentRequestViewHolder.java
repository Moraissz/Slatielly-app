package com.example.slatielly.view.RentRequest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.slatielly.Model.Rent;
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
        itemView.setOnClickListener(this);
        this.listener = listener;
    }

    public void bind(Rent rent){
        this.description.setText(rent.getDress().getDescription());
        this.hirerName.setText(rent.getUser().getName());
        this.startDate.setText(rent.getStartDate());
        this.endDate.setText(rent.getEndDate());
        this.totalPrice.setText(rent.getDress().getPrice());
        this.rent = rent;
    }


    @Override
    public void onClick(View v) {
        listener.onClickRentRequestListener(this.rent);
    }
}
