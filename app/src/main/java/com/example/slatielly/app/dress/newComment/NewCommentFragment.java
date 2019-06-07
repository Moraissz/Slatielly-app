package com.example.slatielly.app.dress.newComment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Image;
import com.example.slatielly.model.repository.FirestoreRepository;

public class NewCommentFragment extends Fragment implements NewCommentContract.View
{
    private NewCommentContract.Presenter presenter;

    private TextView text_view_new_comment;
    private Button btnImage_newComment;
    private  Button btnComment_newComment;
    private ImageView imageComment;


    private String dressId;

    public static NewCommentFragment newInstance(String id) {
        NewCommentFragment commentsFragment = new NewCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        commentsFragment.setArguments(bundle);

        return commentsFragment;
    }

    public NewCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate( R.layout.new_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setupViews(view);

        FirestoreRepository<Dress> repository = new FirestoreRepository<>(Dress.class, Dress.DOCUMENT_NAME);
        this.presenter = new NewCommentPresenter( this,repository);

        if (this.getArguments() != null)
        {
            this.dressId = this.getArguments().getString("id");
        }
    }

    private void setupViews(View view)
    {
        text_view_new_comment = view.findViewById(R.id.text_view_new_comment);
        btnImage_newComment = view.findViewById(R.id.btnImage_newComment);
        btnComment_newComment = view.findViewById(R.id.btnComment_newComment);
        imageComment = view.findViewById(R.id.imageComment);


    }

    public void getComment()
    {

    }
}
