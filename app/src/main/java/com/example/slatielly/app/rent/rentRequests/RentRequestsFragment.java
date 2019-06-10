package com.example.slatielly.app.rent.rentRequests;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.rentRequest.RentRequestAdapter;


public class RentRequestsFragment extends Fragment implements RentRequestAdapter.RentRequestListener,
        RentRequestsContract.View {

    private RecyclerView recyclerView;
    private RentRequestAdapter adapter;
    private RentRequestsContract.Presenter presenter;

    public static RentRequestsFragment newInstance() {
        return new RentRequestsFragment();
    }

    public RentRequestsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rent_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirestoreRepository<Rent> repository = new FirestoreRepository<>(Rent.class, Rent.DOCUMENT_NAME);
        this.presenter = new RentRequestsPresenter(this, repository);

        this.recyclerView = view.findViewById(R.id.RRRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        this.recyclerView.setLayoutManager(mLayoutManager);

        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.adapter = new RentRequestAdapter(this.presenter.getRecyclerOptions(), this);
        this.recyclerView.setAdapter(this.adapter);
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
    public void onClickRentRequestListener(Rent rent, RentRequestAdapter.Actions action) {
        switch (action) {
            case ACCEPT: {
                this.presenter.acceptRentRequest(rent);
                break;
            }

            case DECLINE: {
                this.presenter.declineRentRequest(rent);
                break;
            }
        }
    }
}
