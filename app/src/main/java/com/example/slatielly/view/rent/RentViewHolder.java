package com.example.slatielly.view.rent;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.slatielly.model.Rent;
import com.example.slatielly.R;

import info.androidhive.fontawesome.FontTextView;

public class RentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView description;
    private TextView hirerName;
    private TextView totalPrice;
    private TextView startDate;
    private TextView endDate;
    private ImageView dressImage;
    private TextView status;
    private Context context;
    private FontTextView statusIcon;
    private TextView drawableBorder;
    private Rent rent;
    private RentAdapter.RentListener listener;
    private View view;

    public RentViewHolder(@NonNull View itemView, RentAdapter.RentListener listener) {
        super(itemView);
        this.description = (TextView) itemView.findViewById(R.id.rentDressDescription);
        this.hirerName = (TextView) itemView.findViewById(R.id.hirerName);
        this.totalPrice = (TextView) itemView.findViewById(R.id.totalPrice);
        this.startDate = (TextView) itemView.findViewById(R.id.rentStartDate);
        this.endDate = (TextView) itemView.findViewById(R.id.rentEndDate);
        this.status = (TextView) itemView.findViewById(R.id.rentStatus);
        this.dressImage = (ImageView) itemView.findViewById(R.id.dressImage);
        this.context = itemView.getContext();
        this.statusIcon = (FontTextView) itemView.findViewById(R.id.rentStatusIcon);
        this.drawableBorder = (TextView) itemView.findViewById(R.id.drawableBorder);
        this.view = itemView;

        itemView.setOnClickListener(this);
        this.listener = listener;
    }


    public void bind(Rent rent) {
        this.description.setText(rent.getDress().getDescription());
        this.hirerName.setText(rent.getUser().getName());
        this.status.setText(rent.getStatus());

        if (!rent.getDress().getImages().isEmpty())
        {
            Glide.with(this.view).load(rent.getDress().getImages().get(0).getdownloadLink()).into(dressImage);
        }

        if (rent.getStatus().equals(Rent.PENDENT)) {
            this.status.setTextColor(ContextCompat.getColor(context, R.color.colorYellow400));
            this.statusIcon.setTextColor(ContextCompat.getColor(context, R.color.colorYellow400));
            statusIcon.setText(R.string.fa_hourglass_half_solid);
            drawableBorder.setBackgroundResource(R.drawable.border_left_yellow);
        } else {
            this.status.setTextColor(ContextCompat.getColor(context, R.color.colorGreen500));
            this.statusIcon.setTextColor(ContextCompat.getColor(context, R.color.colorGreen500));
            statusIcon.setText(R.string.fa_flag_checkered_solid);
            drawableBorder.setBackgroundResource(R.drawable.border_left_green);
        }
        this.rent = rent;
    }

    @Override
    public void onClick(View v) {
        listener.onClickRentListener(this.rent);
    }
}
