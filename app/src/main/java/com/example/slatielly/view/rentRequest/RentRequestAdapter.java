package com.example.slatielly.view.rentRequest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.model.Rent;
import com.example.slatielly.R;

import java.util.ArrayList;

public class RentRequestAdapter extends RecyclerView.Adapter<RentRequestViewHolder>  {

    private ArrayList<Rent> rentRequestArrayList;
    private RentRequestListener listener;

    public RentRequestAdapter(ArrayList<Rent> rentArrayList, RentRequestListener listener) {
        this.rentRequestArrayList = rentArrayList;
        this.listener = listener;
    }

    public interface RentRequestListener {
        void onClickRentRequestListener(Rent rent);
    }

    @NonNull
    @Override
    public RentRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.request_rent_card_viewholder,viewGroup,false);
        RentRequestViewHolder rentRequestViewHolder = new RentRequestViewHolder(view,this.listener);
        return rentRequestViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RentRequestViewHolder rentRequestViewHolder, int i) {
        rentRequestViewHolder.bind(rentRequestArrayList.get(i));
    }


    @Override
    public int getItemCount() {
        return rentRequestArrayList.size();
    }
}
