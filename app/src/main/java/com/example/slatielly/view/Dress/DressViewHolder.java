package com.example.slatielly.view.Dress;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.slatielly.Model.Dress;
import com.example.slatielly.R;

public class DressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView description;
    private TextView price;
    private TextView type;
    private Dress dress;
    private DressAdapter.DressListener listener;

    public DressViewHolder(@NonNull View itemView, DressAdapter.DressListener listener) {
        super(itemView);
        this.description = (TextView) itemView.findViewById(R.id.dressDescription);
        this.type = (TextView) itemView.findViewById(R.id.dressType);
        this.price = (TextView) itemView.findViewById(R.id.dressPrice);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    public void bind(Dress dress){
        this.description.setText(dress.getDescription());
        this.type.setText(dress.getType());
        this.price.setText(dress.getPrice());
        this.dress = dress;
    }
    @Override
    public void onClick(View v) {
        listener.onClickDressListener(this.dress);

    }
}
