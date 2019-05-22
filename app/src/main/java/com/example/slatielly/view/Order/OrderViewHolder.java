package com.example.slatielly.view.Order;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.slatielly.Model.Order;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView description;
    private TextView price;
    private TextView type;
    // TextInputEditText
    private Order order;
    private OrderAdapter.OrderListener listener;

    public OrderViewHolder(@NonNull View itemView, OrderAdapter.OrderListener listener) {
        super(itemView);
        /*
        this.description = (TextView) itemView.findViewById(R.id);
        this.type = (TextView) itemView.findViewById(R.id.orderType);
        this.price = (TextView) itemView.findViewById(R.id.orderPrice);
        this.listener = listener;
        */
        itemView.setOnClickListener(this);
    }

    public void bind(Order order){
        /*
        this.description.setText(order.getDescription());
        this.type.setText(order.getType());
        this.price.setText(order.getPrice());
        */
        this.order = order;

    }
    @Override
    public void onClick(View v) {
        listener.onClickOrderListener(this.order);

    }
}

