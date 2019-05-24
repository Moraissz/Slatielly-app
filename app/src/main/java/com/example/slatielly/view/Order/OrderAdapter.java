package com.example.slatielly.view.Order;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.Model.Order;
import com.example.slatielly.R;

import java.util.ArrayList;



public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private ArrayList<Order> orderArrayList;
    private OrderListener listener;

    public OrderAdapter(ArrayList<Order> orderArrayList, OrderListener listener) {
        this.orderArrayList = orderArrayList;
        this.listener = listener;
    }

    public interface OrderListener{
        void onClickOrderListener(Order order);
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.order_resume_view_holder,viewGroup,false);
        OrderViewHolder dressViewHolder = new OrderViewHolder(view,this.listener);
        return dressViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {
        orderViewHolder.bind(orderArrayList.get(i));

    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }
}
