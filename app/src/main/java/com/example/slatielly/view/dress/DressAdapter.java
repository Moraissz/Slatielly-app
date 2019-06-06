package com.example.slatielly.view.dress;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DressAdapter extends FirestoreRecyclerAdapter<Dress, DressViewHolder> {
    private DressListener listener;

    public DressAdapter(FirestoreRecyclerOptions<Dress> options, DressListener listener) {
        super(options);
        this.listener = listener;
    }

    @NonNull
    @Override
    public DressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.dresses_card_viewholder, viewGroup, false);

        return new DressViewHolder(view, this.listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull DressViewHolder holder, int position, @NonNull Dress model) {
        holder.bind(model);
    }

    public interface DressListener {
        void onClickDressListener(Dress dress);
    }
}
