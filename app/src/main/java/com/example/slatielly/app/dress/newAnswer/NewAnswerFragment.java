package com.example.slatielly.app.dress.newAnswer;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.slatielly.R;

import static android.app.Activity.RESULT_OK;


public class NewAnswerFragment extends Fragment implements NewAswerContract.View, View.OnClickListener
{
    private NewAswerContract.Presenter presenter;
    private OnNavigationListener listener;

    private TextView text_view_new_answer;
    private Button btnImage_newAswer;
    private  Button btnanswer_newAnswer;
    private ImageView imageAswer;

    private View view;

    private String dressId;
    private String commentId;


    public static NewAnswerFragment newInstance(String dressId, String commentId)
    {
        NewAnswerFragment newAnswerFragment = new NewAnswerFragment();

        Bundle bundle = new Bundle();
        bundle.putString("dressId", dressId);
        bundle.putString("commentId", commentId);
        newAnswerFragment.setArguments(bundle);

        return newAnswerFragment;
    }

    public NewAnswerFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate( R.layout.new_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.setupViews(view);

        this.presenter = new NewAnswerPresenter(this);

        if (this.getArguments() != null)
        {
            dressId = this.getArguments().getString("dressId");
            commentId = this.getArguments().getString("commentId");
        }
    }

    private void setupViews(View view)
    {
        text_view_new_answer = view.findViewById(R.id.text_view_new_answer);
        btnImage_newAswer = view.findViewById(R.id.btnImage_newAswer);
        btnanswer_newAnswer = view.findViewById(R.id.btnanswer_newAnswer);
        imageAswer = view.findViewById(R.id.imageAswer);

        imageAswer.setVisibility(ImageView.GONE);

        this.view = view;

        btnanswer_newAnswer.setOnClickListener(this);
        btnImage_newAswer .setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if (v == btnanswer_newAnswer)
        {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService( Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow( view.getWindowToken(), 0);

            this.presenter.saveComment(text_view_new_answer.getText().toString(),dressId,commentId);
        }
        if (v ==  btnImage_newAswer)
        {
            if(this.presenter.getImage() == null)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
            else
            {
                this.presenter.setImage(null);
                imageAswer.setVisibility(ImageView.GONE);
                btnImage_newAswer.setText(R.string.mais_image);
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

            Glide.with( view ).load(this.presenter.getImage()).into(imageAswer);

            imageAswer.setVisibility(ImageView.VISIBLE);

            btnImage_newAswer.setText(R.string.menos_Imagem);
        }
    }

    public void navigateToAnswers()
    {
        listener.onBackPressed();
    }

    public void setOnNavigationListener(OnNavigationListener listener)
    {
        this.listener = listener;
    }

    public interface OnNavigationListener
    {
        void onBackPressed();
    }
}
