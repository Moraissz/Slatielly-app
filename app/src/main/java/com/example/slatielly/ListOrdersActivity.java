package com.example.slatielly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.slatielly.Model.Order;
import com.example.slatielly.view.Order.OrderAdapter;

import java.util.ArrayList;

public class ListOrdersActivity extends AppCompatActivity implements  OrderAdapter.OrderListener{

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> orderArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders);

        recyclerView = findViewById(R.id.rvListOrders);
        orderArrayList = new ArrayList<>();

        for(int i = 0; i < 100; i++){
            orderArrayList.add(new Order("Astra"));
        }



        orderAdapter = new OrderAdapter(orderArrayList, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(orderAdapter);
    }

    @Override
    public void onClickOrderListener(Order order) {
        Toast.makeText(this, "Astra", Toast.LENGTH_SHORT).show();
    }
}
