package com.example.slatielly.view.comment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;

public class AdapterComment extends RecyclerView.Adapter<HolderComment>
{
    public AdapterComment()
    {

    }
    @NonNull
    @Override
    public HolderComment onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.comment_model,viewGroup,false);

        HolderComment holder_comentarios = new HolderComment(view);

        return holder_comentarios;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderComment holderComment, int i)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }
}
