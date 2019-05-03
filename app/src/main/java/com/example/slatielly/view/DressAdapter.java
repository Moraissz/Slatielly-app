package com.example.slatielly.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.Model.Dress;
import com.example.slatielly.R;

import java.util.ArrayList;

public class DressAdapter extends RecyclerView.Adapter<DressViewHolder> {
    private ArrayList<Dress> dressArrayList;
    private DressListener listener;

    public DressAdapter(ArrayList<Dress> dressArrayList, DressListener listener) {
        this.dressArrayList = dressArrayList;
        this.listener = listener;
    }

    public interface DressListener{
        void onClickDressListener(Dress dress);
    }
    @NonNull
    @Override
    public DressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.dresses_card_viewholder,viewGroup,false);
        DressViewHolder dressViewHolder = new DressViewHolder(view,this.listener);
        return dressViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DressViewHolder dressViewHolder, int i) {
        dressViewHolder.bind(dressArrayList.get(i));

    }

    @Override
    public int getItemCount() {
        return dressArrayList.size();
    }
}
