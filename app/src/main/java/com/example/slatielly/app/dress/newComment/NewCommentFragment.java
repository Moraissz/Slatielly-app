package com.example.slatielly.app.dress.newComment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.slatielly.R;
import com.example.slatielly.view.LargePhoto;

import static android.app.Activity.RESULT_OK;

public class NewCommentFragment extends Fragment implements NewCommentContract.View, View.OnClickListener
{
    private NewCommentContract.Presenter presenter;
    private OnNavigationListener listener;
    private TextView text_view_new_comment;
    private Button btnImage_newComment;
    private  Button btnComment_newComment;
    private ImageView imageComment;
    private ScrollView scrollViewComment;
    private ProgressBar loadingBarComment;

    private View view;


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

        scrollViewComment = view.findViewById(R.id.scrollViewComment);
        loadingBarComment = view.findViewById(R.id.loadingBarComment);

        imageComment.setVisibility(ImageView.GONE);

        this.view = view;

        btnComment_newComment.setOnClickListener(this);
        btnImage_newComment.setOnClickListener(this);
        imageComment.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if (v == btnComment_newComment)
        {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService( Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow( view.getWindowToken(), 0);

            this.scrollViewComment.fullScroll(ScrollView.FOCUS_UP);
            this.loadingBarComment.setVisibility(ProgressBar.VISIBLE);

            btnImage_newComment.setEnabled(false);
            btnComment_newComment.setEnabled(false);
            imageComment.setEnabled(false);

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
        if(v == imageComment)
        {
            Intent intent = new Intent(getActivity(), LargePhoto.class);

            intent.putExtra(LargePhoto.KeyOption,"bitmap");
            byte[] bytes = this.presenter.getData();
            intent.putExtra(LargePhoto.KeyPhoto,bytes);

            startActivity(intent);
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

            Glide.with( view ).load(this.presenter.getImage()).into(imageComment);

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
