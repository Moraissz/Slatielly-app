package com.example.slatielly.view.comment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
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
import com.example.slatielly.app.dress.comments.CommentsContract;
import com.example.slatielly.app.dress.comments.CommentsFragment;
import com.example.slatielly.app.dress.comments.CommentsPresenter;
import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Like;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.LargePhoto;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private ImageView image_comment_comment_model;

    private TextView TextView_name_comment_model;
    private TextView TextView_comment_comment_view;
    private TextView TextView_date_comment_model;
    private TextView textView_Likes;

    private ImageView buttonImage_like_comment_model;
    private Button button_reply_comment_model;
    private Button button_see_answers_comment_model;

    private ConstraintLayout constraintLayout_Comment_View_Holder;

    private View context;

    private Comment comment;
    private String dressId;

    private CommentsPresenter presenter;

    private CommentsFragment view;

    private ProgressBar progressBar;

    public CommentViewHolder(@NonNull View itemView)
    {
        super(itemView);

        context = itemView;

        TextView_name_comment_model = (TextView) itemView.findViewById(R.id.TextView_name_comment_model);
        TextView_comment_comment_view = (TextView) itemView.findViewById(R.id.TextView_comment_comment_view);
        TextView_date_comment_model = (TextView) itemView.findViewById(R.id.TextView_date_comment_model);
        textView_Likes = (TextView) itemView.findViewById(R.id.textView_Likes);

        image_comment_comment_model = (ImageView) itemView.findViewById(R.id.image_comment_comment_model);

        buttonImage_like_comment_model = (ImageView) itemView.findViewById(R.id.buttonImage_like_comment_model);
        button_reply_comment_model = (Button) itemView.findViewById(R.id.button_reply_comment_model);
        button_see_answers_comment_model = (Button) itemView.findViewById(R.id.button_see_answers_comment_model);

        constraintLayout_Comment_View_Holder = (ConstraintLayout) itemView.findViewById(R.id.ConstraintLayout_Comment_View_Holder);

        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarComment);

        image_comment_comment_model.setOnClickListener(this);
        buttonImage_like_comment_model.setOnClickListener(this);
        button_reply_comment_model.setOnClickListener(this);
        button_see_answers_comment_model.setOnClickListener(this);

        this.presenter = new CommentsPresenter(this);

    }

    public void bind(Comment comment, String dressId, CommentsFragment view)
    {
        this.comment = comment;
        this.dressId = dressId;
        this.view = view;

        TextView_comment_comment_view.setText(comment.getDescription());
        textView_Likes.setText(String.valueOf(comment.getNumberLikes()));
        TextView_date_comment_model.setText(formDate(comment.getDate()));
        TextView_name_comment_model.setText(comment.getUser().getName());

        if(!(comment.getImage() == null))
        {
            progressBar.setVisibility(View.VISIBLE);

            Glide.with(context).load(comment.getImage().getdownloadLink()).into(image_comment_comment_model);

            image_comment_comment_model.setVisibility(ImageView.VISIBLE);

            constraintLayout_Comment_View_Holder.setVisibility(ConstraintLayout.VISIBLE);
        }
        else
        {
            image_comment_comment_model.setVisibility(ImageView.GONE);
            constraintLayout_Comment_View_Holder.setVisibility(ConstraintLayout.GONE);
            progressBar.setVisibility(View.GONE);
        }


        if(comment.getAnswers().size()>0)
        {
            button_see_answers_comment_model.setText("View Answers ("+comment.getAnswers().size()+")");
            button_see_answers_comment_model.setVisibility(Button.VISIBLE);
        }
        else
        {
            button_see_answers_comment_model.setVisibility(Button.GONE);
        }

        this.presenter.checkUserBind(this.comment);
    }

    @Override
    public void onClick(View v)
    {
        if (v == image_comment_comment_model)
        {
            Intent intent = new Intent(context.getContext(), LargePhoto.class);

            intent.putExtra(LargePhoto.KeyOption,"firebaseStorage");
            intent.putExtra(LargePhoto.KeyPhoto,comment.getImage().getaddressStorage());

            context.getContext().startActivity(intent);
        }

        if (v == buttonImage_like_comment_model)
        {
            this.presenter.checkUser(this.comment);
        }

        if (v == button_reply_comment_model)
        {
            view.getListener().onNavigateToNewAnswer(dressId,this.comment.getId());
        }

        if (v == button_see_answers_comment_model)
        {
            view.getListener().onNavigateToAnswers(dressId,comment.getId());
        }
    }

    public void addLike(User currentUser)
    {
        Like like = new Like();
        like.setUser(currentUser);
        this.comment.getLikes().add(like);
        this.comment.setNumberLikes(this.comment.getNumberLikes()+1);

        textView_Likes.setText(String.valueOf(this.comment.getNumberLikes()));

        buttonImage_like_comment_model.setImageResource(R.drawable.like_image2);

        this.presenter.updateCommentAddLike(this.comment,this.dressId,currentUser);
    }

    public void subtractLike(User currentUser)
    {
        this.comment.setNumberLikes(this.comment.getNumberLikes()-1);
        for(int i=0;i<this.comment.getLikes().size();i=i+1)
        {
            if(currentUser.getId().equals(comment.getLikes().get(i).getUser().getId()))
            {
                comment.getLikes().remove(comment.getLikes().get(i));
                break;
            }
        }

        textView_Likes.setText(String.valueOf(this.comment.getNumberLikes()));

        buttonImage_like_comment_model.setImageResource(R.drawable.like_image);

        this.presenter.updateCommentSubtractLike(this.comment,this.dressId,currentUser);
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

    public void setLike()
    {
        buttonImage_like_comment_model.setImageResource(R.drawable.like_image2);
    }

    public void setLike2()
    {
        buttonImage_like_comment_model.setImageResource(R.drawable.like_image);
    }
}
