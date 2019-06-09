package com.example.slatielly.view.comment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.app.dress.comments.CommentsFragment;
import com.example.slatielly.model.Comment;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private ArrayList<Comment> comments;
    private String dressId;
    private CommentsFragment.OnNavigationListener listener;

    public CommentAdapter(ArrayList<Comment> comments, String dressId, CommentsFragment.OnNavigationListener listener) {
        this.comments = comments;
        this.dressId = dressId;
        this.listener = listener;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.comment_view_holder, viewGroup, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.bind(this.comments.get(i),dressId,listener);
    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }
}
