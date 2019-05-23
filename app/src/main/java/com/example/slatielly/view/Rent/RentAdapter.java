package com.example.slatielly.view.Rent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.Model.Rent;
import com.example.slatielly.R;

import java.util.ArrayList;

public class RentAdapter extends RecyclerView.Adapter<RentViewHolder> {

    private ArrayList<Rent> rentArrayList;
    private RentListener listener;

    public RentAdapter(ArrayList<Rent> rentArrayList, RentListener listener) {
        this.rentArrayList = rentArrayList;
        this.listener = listener;
    }

    public interface RentListener{
        void onClickRentListener(Rent rent);
    }

    @NonNull
    @Override
    public RentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.rent_card_viewholder,viewGroup,false);
        RentViewHolder rentViewHolder = new RentViewHolder(view, this.listener);
        return rentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RentViewHolder bookViewHolder, int i) {
        bookViewHolder.bind(rentArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return rentArrayList.size();
    }
}
