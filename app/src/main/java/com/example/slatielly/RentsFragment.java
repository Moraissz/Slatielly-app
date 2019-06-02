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
import com.example.slatielly.view.Rent.RentAdapter;

import java.util.ArrayList;


public class RentsFragment extends Fragment implements RentAdapter.RentListener {
    private RecyclerView recyclerView;
    private RentAdapter adapter;
    private ArrayList<Rent> rentArrayList;

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
        this.rentArrayList = new ArrayList<>();

        Rent rent;
        //Preencher ArrayList
        for (int i = 0; i < 20;i++){
            rent = new Rent("i", new Dress("i", "Vestido Doidao", "Para doidos", "R$ 70,00","38 a 40","Preto","Pano"),
                    new User(""+i, "Lucas", "Avenida dos doido", "333333", new Address()), "30-02-2020", "31-02-2021", "Pendente");

            if(i > 10){
                rent.setStatus("Concluido");
            }

            this.rentArrayList.add(rent);
        }
        System.out.println(rentArrayList.get(0));
        this.adapter = new RentAdapter(rentArrayList,this);

        this.recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));

        this.recyclerView.addItemDecoration(
                new GridSpacingItemDecoration(2,
                        GridSpacingItemDecoration.dpToPx(10, this.getResources()), true
                )
        );

        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this. recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void onClickRentListener(Rent rent) {

    }
}
