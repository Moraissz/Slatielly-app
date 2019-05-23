package com.example.slatielly.view.Rent;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.slatielly.Model.Rent;
import com.example.slatielly.R;

public class RentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView description;
    private TextView hirerName;
    private TextView status;
    private Rent rent;
    private RentAdapter.RentListener listener;

    public RentViewHolder(@NonNull View itemView, RentAdapter.RentListener listener) {
        super(itemView);
        this.description = (TextView) itemView.findViewById(R.id.rentDressDescription);
        this.hirerName = (TextView) itemView.findViewById(R.id.hirerName);
        this.status = (TextView) itemView.findViewById(R.id.rentStatus);
        itemView.setOnClickListener(this);
        this.listener = listener;
    }


    public void bind(Rent rent){
        this.description.setText(rent.getDress().getDescription());
        this.hirerName.setText(rent.getUser().getName());
        this.status.setText(rent.getStatus());
        if(rent.getStatus().equals("Pendente"))
            this.status.setTextColor(Color.parseColor("#f4d742"));
        else
            this.status.setTextColor(Color.parseColor("#1fe056"));
        this.rent = rent;
    }

    @Override
    public void onClick(View v) {
        listener.onClickRentListener(this.rent);

    }
}
