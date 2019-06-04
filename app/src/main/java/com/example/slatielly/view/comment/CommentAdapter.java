package com.example.slatielly.view.comment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder>
{
    public CommentAdapter()
    {

    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.comment_model,viewGroup,false);

        CommentViewHolder holder_comentarios = new CommentViewHolder(view);

        return holder_comentarios;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }
}
