package com.example.slatielly.app.dress.newComment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.slatielly.R;

import static android.app.Activity.RESULT_OK;

public class NewCommentFragment extends Fragment implements NewCommentContract.View, View.OnClickListener
{
    private NewCommentContract.Presenter presenter;
    private OnNavigationListener listener;
    private TextView text_view_new_comment;
    private Button btnImage_newComment;
    private  Button btnComment_newComment;
    private ImageView imageComment;

    private View context;


    private String dressId;

    public static NewCommentFragment newInstance(String id) {
        NewCommentFragment commentsFragment = new NewCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        commentsFragment.setArguments(bundle);

        return commentsFragment;
    }

    public NewCommentFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate( R.layout.new_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.setupViews(view);

        this.presenter = new NewCommentPresenter(this);

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

        imageComment.setVisibility(ImageView.GONE);

        context = view;

        btnComment_newComment.setOnClickListener(this);
        btnImage_newComment.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if (v == btnComment_newComment)
        {
            this.presenter.saveComment(text_view_new_comment.getText().toString(),dressId);
        }
        if (v == btnImage_newComment)
        {
            if(this.presenter.getImage() == null)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
            else
            {
                this.presenter.setImage(null);
                imageComment.setVisibility(ImageView.GONE);
                btnImage_newComment.setText(R.string.mais_image);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1)
        {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            this.presenter.saveImage(picturePath);

            Glide.with(context).load(this.presenter.getImage()).into(imageComment);

            imageComment.setVisibility(ImageView.VISIBLE);

            btnImage_newComment.setText(R.string.menos_Imagem);
        }
    }

    public void setOnNavigationListener(OnNavigationListener listener)
    {
        this.listener = listener;
    }

    public void navigateToComments()
    {
        this.listener.onBackPressed();
    }

    public interface OnNavigationListener
    {
        void onBackPressed();
    }
}
