package com.example.slatielly.view.comment;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.slatielly.R;
import com.example.slatielly.model.Comment;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView image_profile_image_comment_model;
    private ImageView image_comment_comment_model;

    private TextView TextView_name_comment_model;
    private TextView TextView_comment_comment_view;
    private TextView TextView_date_comment_model;
    private TextView textView_Likes;

    private ImageView buttonImage_like_comment_model;
    private Button button_reply_comment_model;
    private Button button_see_answers_comment_model;

    private View context;

    public CommentViewHolder(@NonNull View itemView)
    {
        super(itemView);

        context = itemView;

        TextView_name_comment_model = (TextView) itemView.findViewById(R.id.TextView_name_comment_model);
        TextView_comment_comment_view = (TextView) itemView.findViewById(R.id.TextView_comment_comment_view);
        TextView_date_comment_model = (TextView) itemView.findViewById(R.id.TextView_date_comment_model);
        textView_Likes = (TextView) itemView.findViewById(R.id.textView_Likes);

        image_profile_image_comment_model = (ImageView) itemView.findViewById(R.id.image_profile_image_comment_model);
        image_comment_comment_model = (ImageView) itemView.findViewById(R.id.image_comment_comment_model);

        buttonImage_like_comment_model = (ImageView) itemView.findViewById(R.id.buttonImage_like_comment_model);
        button_reply_comment_model = (Button) itemView.findViewById(R.id.button_reply_comment_model);
        button_see_answers_comment_model = (Button) itemView.findViewById(R.id.button_see_answers_comment_model);

        image_comment_comment_model.setOnClickListener(this);
        buttonImage_like_comment_model.setOnClickListener(this);
        button_reply_comment_model.setOnClickListener(this);
        button_see_answers_comment_model.setOnClickListener(this);
    }

    public void bind(Comment comment)
    {

        TextView_comment_comment_view.setText(comment.getDescription());
        textView_Likes.setText(String.valueOf(comment.getNumberLikes()));
        TextView_date_comment_model.setText(formDate(comment.getDate()));
        TextView_name_comment_model.setText(comment.getUser().getName());

        if(!(comment.getImage() == null))
        {

            Glide.with(context).load(comment.getImage().getdownloadLink()).into(image_comment_comment_model);

            image_comment_comment_model.setVisibility(ImageView.VISIBLE);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v == image_comment_comment_model)
        {

        }

        if (v == buttonImage_like_comment_model)
        {

        }

        if (v == button_reply_comment_model)
        {

        }

        if (v == button_see_answers_comment_model)
        {

        }
    }

    public String formDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        String dateString = day+"/"+(month+1)+"/"+year;

        return dateString;
    }
}
