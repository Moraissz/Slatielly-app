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

import com.example.slatielly.model.Dress;
import com.example.slatielly.view.dress.DressAdapter;
import com.example.slatielly.view.GridSpacingItemDecoration;

import java.util.ArrayList;


public class DressesFragment extends Fragment implements DressAdapter.DressListener {

    private RecyclerView recyclerView;
    private DressAdapter adapter;
    private ArrayList<Dress> dressArrayList;

    public DressesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dresses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.recyclerView = view.findViewById(R.id.recycler_view);
        this.dressArrayList = new ArrayList<>();

        //Preencher array
        for (int i = 0; i < 20; i++) {
            Dress dress = new Dress("i", "Vestido para Casamento", "Tipo " + i, "R$ 50,00","38 a 40","Preto","Pano");
            this.dressArrayList.add(dress);
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        this.recyclerView.setLayoutManager(mLayoutManager);

        this.recyclerView.addItemDecoration(
                new GridSpacingItemDecoration(2,
                        GridSpacingItemDecoration.dpToPx(10, this.getResources()), true
                )
        );

        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.adapter = new DressAdapter(this.dressArrayList, this);
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void onClickDressListener(Dress dress) {

    }
}
