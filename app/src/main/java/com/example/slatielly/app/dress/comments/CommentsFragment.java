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
import android.widget.Button;

import com.example.slatielly.R;
import com.example.slatielly.app.dress.DressFragment;
import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.comment.CommentAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CommentsFragment extends Fragment implements CommentsContract.View, View.OnClickListener{

    private RecyclerView rvComments;
    private CommentsContract.Presenter presenter;

    private CommentsFragment.OnNavigationListener listener;

    private Button btnComment;

    private String dressId;

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

        FirestoreRepository<Dress> repository = new FirestoreRepository<>(Dress.class, Dress.DOCUMENT_NAME);
        this.presenter = new CommentsPresenter(this, repository);

       if (this.getArguments() != null) {
           dressId = this.getArguments().getString("id");
           this.presenter.loadComments(dressId);
       }
    }

    private void setupViews(View view)
    {
        rvComments = view.findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvComments.setHasFixedSize(true);

        btnComment = (Button) view.findViewById(R.id.btnComment);
        btnComment.setOnClickListener(this);
    }

    @Override
    public void setComments(ArrayList<Comment> comments) {
        CommentAdapter commentAdapter = new CommentAdapter(comments,dressId);
        rvComments.setAdapter(commentAdapter);
    }

    @Override
    public void onClick(View v)
    {
        if (v == btnComment)
        {
            if (this.getArguments() != null)
            {
                String id = this.getArguments().getString("id");
                this.listener.onNavigateToNewComment(id);
                return;
            }

            return;

        }
    }

    public interface OnNavigationListener
    {
        void onNavigateToNewComment(String dressId);
    }

    public void setOnNavigationListener(CommentsFragment.OnNavigationListener listener)
    {
        this.listener = listener;
    }
}
