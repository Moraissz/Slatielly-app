package com.example.slatielly;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.Model.Address;
import com.example.slatielly.Model.Dress;
import com.example.slatielly.Model.Rent;
import com.example.slatielly.Model.User;
import com.example.slatielly.view.GridSpacingItemDecoration;
import com.example.slatielly.view.RentRequest.RentRequestAdapter;

import java.util.ArrayList;

public class RentRequestsFragment extends Fragment implements RentRequestAdapter.RentRequestListener {

    private RecyclerView recyclerView;
    private RentRequestAdapter adapter;
    private ArrayList<Rent> rentArrayList;

    public RentRequestsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rent_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.recyclerView = view.findViewById(R.id.RRRecyclerView);
        this.rentArrayList = new ArrayList<>();
        //Preencher ArrayList
        for (int i = 0; i < 20; i++) {
            Rent rent = new Rent(i, new Dress(i, "Vestido Doidao", "Para doidos", "R$ 70,00","38 a 40","Preto","Pano"),
                    new User(""+i, "Lucas", "Avenida dos doido", "333333", new Address()), "30-02-2020", "31-02-2021", "Pendente");
            if (i > 10) {
                rent.setStatus("Concluido");
            }
            this.rentArrayList.add(rent);
        }

        this.adapter = new RentRequestAdapter(this.rentArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 1);
        this.recyclerView.setLayoutManager(mLayoutManager);

        this.recyclerView.addItemDecoration(
                new GridSpacingItemDecoration(2,
                        GridSpacingItemDecoration.dpToPx(10, this.getResources()), true
                )
        );

        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickRentRequestListener(Rent rent) {

    }
}
