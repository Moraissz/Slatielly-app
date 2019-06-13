package com.example.slatielly.view.ansher;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.slatielly.R;
import com.example.slatielly.app.dress.answers.AnswersFragment;
import com.example.slatielly.app.dress.answers.AnswersPresenter;
import com.example.slatielly.model.Answer;
import com.example.slatielly.model.Like;
import com.example.slatielly.model.User;

import java.util.Calendar;
import java.util.Date;

public class AnswerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private ImageView image_profile_image_ansher_view_holder;
    private ImageView image_ansher_ansher_view_holder;

    private TextView TextView_name_ansher_view_holder;
    private TextView TextView_ansher_ansher_view_holder;
    private TextView TextView_date_ansher_view_holder;
    private TextView textView_Likes_ansher_view_holder;

    private ImageView buttonImage_like_ansher_view_holder;
    private Button button_reply_ansher_view_holder;

    private View context;

    private Answer answer;
    private String dressId;
    private String commentId;
    private AnswersFragment answersFragment;

    private AnswersPresenter presenter;

    private ProgressBar progressBar;

    private ConstraintLayout constraintLayout_Answer_View_Holder;


    public AnswerViewHolder(@NonNull View itemView)
    {
        super(itemView);

        context = itemView;

        TextView_name_ansher_view_holder = (TextView) itemView.findViewById( R.id.TextView_name_ansher_view_holder);
        TextView_ansher_ansher_view_holder = (TextView) itemView.findViewById(R.id.TextView_ansher_ansher_view_holder);
        TextView_date_ansher_view_holder = (TextView) itemView.findViewById(R.id.TextView_date_ansher_view_holder);
        textView_Likes_ansher_view_holder = (TextView) itemView.findViewById(R.id.textView_Likes_ansher_view_holder);

        image_profile_image_ansher_view_holder = (ImageView) itemView.findViewById(R.id.image_profile_image_ansher_view_holder);
        image_ansher_ansher_view_holder = (ImageView) itemView.findViewById(R.id.image_ansher_ansher_view_holder);

        buttonImage_like_ansher_view_holder = (ImageView) itemView.findViewById(R.id.buttonImage_like_ansher_view_holder);
        button_reply_ansher_view_holder = (Button) itemView.findViewById(R.id.button_reply_ansher_view_holder);

        constraintLayout_Answer_View_Holder = (ConstraintLayout) itemView.findViewById(R.id.ConstraintLayout_Ansher_View_Holder);

        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarAnswer);

        image_ansher_ansher_view_holder.setOnClickListener(this);
        buttonImage_like_ansher_view_holder.setOnClickListener(this);
        button_reply_ansher_view_holder .setOnClickListener(this);

        this.presenter = new AnswersPresenter(this);
    }

    public void bind(Answer answer, String dressId, String commentId, AnswersFragment answersFragment)
    {
        this.answer = answer;
        this.dressId = dressId;
        this.commentId = commentId;
        this.answersFragment =answersFragment;

        TextView_ansher_ansher_view_holder.setText(answer.getDescription());
        textView_Likes_ansher_view_holder.setText(String.valueOf(answer.getNumberLikes()));
        TextView_date_ansher_view_holder.setText(formDate(answer.getDate()));
        TextView_name_ansher_view_holder.setText(answer.getUser().getName());

        if(!(answer.getImage() == null))
        {
            progressBar.setVisibility(View.VISIBLE);

            Glide.with(context).load(answer.getImage().getdownloadLink()).into(image_ansher_ansher_view_holder);

            image_ansher_ansher_view_holder.setVisibility(ImageView.VISIBLE);

            constraintLayout_Answer_View_Holder.setVisibility(ConstraintLayout.VISIBLE);
        }
        else
        {
            image_ansher_ansher_view_holder.setVisibility(ImageView.GONE);
            constraintLayout_Answer_View_Holder.setVisibility(ConstraintLayout.GONE);
            progressBar.setVisibility(View.GONE);
        }

        this.presenter.checkUserBind(this.answer);

    }

    @Override
    public void onClick(View v)
    {
        if (v ==  image_ansher_ansher_view_holder)
        {

        }

        if (v == buttonImage_like_ansher_view_holder)
        {
            this.presenter.checkUser(this.answer);
        }

        if (v == button_reply_ansher_view_holder)
        {
            this.answersFragment.getListener().onNavigateToNewAnswer(this.dressId,this.commentId);
        }

    }

    public void addLike(User currentUser)
    {
        Like like = new Like();
        like.setUser(currentUser);
        this.answer.getLikes().add(like);
        this.answer.setNumberLikes(this.answer.getNumberLikes()+1);

        textView_Likes_ansher_view_holder.setText(String.valueOf(this.answer.getNumberLikes()));

        buttonImage_like_ansher_view_holder.setImageResource(R.drawable.like_image2);

        this.presenter.updateAnswerAddLike(this.answer.getId(),this.commentId,this.dressId,currentUser);
    }

    public void subtractLike(User currentUser)
    {
        this.answer.setNumberLikes(this.answer.getNumberLikes()-1);
        for(int i=0;i<this.answer.getLikes().size();i=i+1)
        {
            if(currentUser.getId().equals(answer.getLikes().get(i).getUser().getId()))
            {
                answer.getLikes().remove(answer.getLikes().get(i));
                break;
            }
        }

        textView_Likes_ansher_view_holder.setText(String.valueOf(this.answer.getNumberLikes()));

        buttonImage_like_ansher_view_holder.setImageResource(R.drawable.like_image);

        this.presenter.updateCommentSubtractLike(this.answer.getId(),this.commentId,this.dressId,currentUser);
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
        buttonImage_like_ansher_view_holder.setImageResource(R.drawable.like_image2);
    }

    public void setLike2()
    {
        buttonImage_like_ansher_view_holder.setImageResource(R.drawable.like_image);
    }

}
