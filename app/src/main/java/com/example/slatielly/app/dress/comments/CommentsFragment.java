package com.example.slatielly.app.dress.comments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.comment.CommentAdapter;

import java.util.ArrayList;

public class CommentsFragment extends Fragment implements CommentsContract.View {

    private RecyclerView rvComments;
    private CommentsContract.Presenter presenter;

    public static CommentsFragment newInstance(String id) {
        CommentsFragment commentsFragment = new CommentsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        commentsFragment.setArguments(bundle);

        return commentsFragment;
    }

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setupViews(view);

        FirestoreRepository<Dress> repository = new FirestoreRepository<>(Dress.class, "dresses");
        this.presenter = new CommentsPresenter(this, repository);

       if (this.getArguments() != null) {
           String dressId = this.getArguments().getString("id");
           this.presenter.loadComments(dressId);
       }
    }

    private void setupViews(View view) {
        rvComments = view.findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvComments.setHasFixedSize(true);
    }

    @Override
    public void setComments(ArrayList<Comment> comments) {
        CommentAdapter commentAdapter = new CommentAdapter(comments);
        rvComments.setAdapter(commentAdapter);
    }
}
