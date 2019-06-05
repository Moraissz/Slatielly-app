package com.example.slatielly.view.dress;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.slatielly.model.Dress;
import com.example.slatielly.R;
import com.example.slatielly.model.Image;

public class DressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView description;
    private TextView price;
    private TextView type;
    private ImageView dressImage;
    private Dress dress;
    private View context;
    private DressAdapter.DressListener listener;

    public DressViewHolder(@NonNull View itemView, DressAdapter.DressListener listener) {
        super(itemView);
        this.description = (TextView) itemView.findViewById(R.id.dressDescription);
        this.type = (TextView) itemView.findViewById(R.id.dressType);
        this.price = (TextView) itemView.findViewById(R.id.dressPrice);
        this.dressImage = (ImageView) itemView.findViewById(R.id.dressImage);
        this.listener = listener;
        this.context = itemView;
        itemView.setOnClickListener(this);
    }

    public void bind(Dress dress) {
        this.description.setText(dress.getDescription());
        this.type.setText(dress.getType());
        this.price.setText(dress.getPrice().toString());
        //Glide.with(this.context).load(dress.getImages().get(0).getdownloadLink()).into(dressImage);
        this.dress = dress;
    }

    @Override
    public void onClick(View v) {
        listener.onClickDressListener(this.dress);
    }
}
