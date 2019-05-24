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

import com.example.slatielly.Model.Order;
import com.example.slatielly.view.Order.OrderAdapter;

import java.util.ArrayList;

public class ListOrdersFragment extends Fragment implements  OrderAdapter.OrderListener {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> orderArrayList;

    public ListOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.recyclerView = this.getActivity().findViewById(R.id.rvListOrders);
        this.orderArrayList = new ArrayList<>();

        for(int i = 0; i < 100; i++){
            this.orderArrayList.add(new Order("Astra"));
        }

        this.orderAdapter = new OrderAdapter(this.orderArrayList, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getActivity(),1);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.orderAdapter);
    }

    @Override
    public void onClickOrderListener(Order order) {

    }
}
