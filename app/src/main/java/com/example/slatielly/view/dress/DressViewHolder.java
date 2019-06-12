package com.example.slatielly.view.dress;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.slatielly.model.Dress;
import com.example.slatielly.R;

import java.text.NumberFormat;

public class DressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView description;
    private TextView price;
    private TextView type;
    private TextView size;
    private TextView material;
    private ImageView dressImage;
    private Dress dress;
    private View view;
    private DressAdapter.DressListener listener;
    private ProgressBar progressBar;

    public DressViewHolder(@NonNull View itemView, DressAdapter.DressListener listener)
    {
        super(itemView);
        this.description = (TextView) itemView.findViewById(R.id.dressDescription);
        this.type = (TextView) itemView.findViewById(R.id.dressType);
        this.price = (TextView) itemView.findViewById(R.id.dressPrice);
        this.dressImage = (ImageView) itemView.findViewById(R.id.dressImage);
        this.size = (TextView) itemView.findViewById(R.id.dressSize);
        this.material = (TextView) itemView.findViewById(R.id.dressMaterial);
        this.listener = listener;
        this.view = itemView;
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarDresses);
        itemView.setOnClickListener(this);
    }

    public void bind(Dress dress)
    {
        this.description.setText(dress.getDescription());
        this.type.setText(dress.getType());
        NumberFormat format = NumberFormat.getCurrencyInstance();
        this.price.setText(format.format(dress.getPrice()));
        this.size.setText(dress.getSize());
        this.material.setText(dress.getMaterial());

        if(!dress.getImages().isEmpty())
        {
            Glide.with(this.view ).load(dress.getImages().get(0).getdownloadLink()).into(dressImage);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
        }

        this.dress = dress;
    }

    @Override
    public void onClick(View v) {
        listener.onClickDressListener(this.dress);
    }
}
