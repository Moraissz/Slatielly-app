package com.example.slatielly;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.model.Address;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.GridSpacingItemDecoration;
import com.example.slatielly.view.rent.RentAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;


public class RentsFragment extends Fragment implements RentAdapter.RentListener {
    private RecyclerView recyclerView;
    private RentAdapter adapter;

    public Calendar calendar = Calendar.getInstance();

    public RentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rents, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.recyclerView = view.findViewById(R.id.allRentsRecyclerView);

        FirestoreRepository<Rent> repository = new FirestoreRepository<>(Rent.class, Rent.DOCUMENT_NAME);
        this.adapter = new RentAdapter(this.getRecyclerOptions(repository), this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.adapter);
    }

    public FirestoreRecyclerOptions<Rent> getRecyclerOptions(FirestoreRepository<Rent> repository ) {
        return new FirestoreRecyclerOptions
                .Builder<Rent>()
                .setQuery(repository.getQuery(), Rent.class)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.adapter.stopListening();
    }

    @Override
    public void onClickRentListener(Rent rent) {

    }
}
