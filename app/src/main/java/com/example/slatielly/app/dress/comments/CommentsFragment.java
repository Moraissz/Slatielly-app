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
import com.example.slatielly.view.comment.CommentAdapter;

public class CommentsFragment extends Fragment implements CommentsContract.View {

    private RecyclerView rvComments;
    private CommentAdapter commentAdapter;
    private CommentsContract.Presenter presenter;

    public static CommentsFragment newInstance() {
        return new CommentsFragment();
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

        this.presenter = new CommentsPresenter(this);

        rvComments = view.findViewById(R.id.rvComments);
        commentAdapter = new CommentAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvComments.setHasFixedSize(true);
        rvComments.setAdapter(commentAdapter);
    }
}
