package com.example.slatielly.view.rentRequest;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.model.Rent;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RentRequestAdapter extends FirestoreRecyclerAdapter<Rent, RentRequestViewHolder> {
    private RentRequestListener listener;

    public RentRequestAdapter(FirestoreRecyclerOptions<Rent> options, RentRequestListener listener) {
        super(options);
        this.listener = listener;
    }

    @NonNull
    @Override
    public RentRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.request_rent_card_viewholder, viewGroup, false);
        return new RentRequestViewHolder(view, this.listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull RentRequestViewHolder holder, int position, @NonNull Rent model) {
        holder.bind(model);
    }

    public enum Actions {
        ACCEPT,
        DECLINE
    }

    public interface RentRequestListener {
        void onClickRentRequestListener(Rent rent, Actions action);
    }
}
