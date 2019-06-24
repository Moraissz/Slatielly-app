package com.example.slatielly.view.rent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.model.Rent;
import com.example.slatielly.R;
import com.example.slatielly.view.rentRequest.RentRequestViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class RentAdapter extends FirestoreRecyclerAdapter<Rent, RentViewHolder> {
    private RentListener listener;

    public RentAdapter(FirestoreRecyclerOptions<Rent> options, RentListener listener) {
        super(options);
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
    protected void onBindViewHolder(@NonNull RentViewHolder holder, int position, @NonNull Rent model) {
        holder.bind(model);
    }
}
