package com.example.slatielly.view.ansher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.slatielly.R;
import com.example.slatielly.app.dress.answers.AnswersPresenter;
import com.example.slatielly.model.Answer;
import com.example.slatielly.model.Comment;

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

    private AnswersPresenter presenter;


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

        image_ansher_ansher_view_holder.setOnClickListener(this);
        buttonImage_like_ansher_view_holder.setOnClickListener(this);
        button_reply_ansher_view_holder .setOnClickListener(this);
    }

    public void bind(Answer answer, String dressId, String commentId)
    {
        this.answer = answer;
        this.dressId = dressId;
        this.commentId = commentId;

        TextView_ansher_ansher_view_holder.setText(answer.getDescription());
        textView_Likes_ansher_view_holder.setText(String.valueOf(answer.getNumberLikes()));
        TextView_date_ansher_view_holder.setText(formDate(answer.getDate()));
        TextView_name_ansher_view_holder.setText(answer.getUser().getName());

        if(!(answer.getImage() == null))
        {
            Glide.with(context).load(answer.getImage().getdownloadLink()).into(image_ansher_ansher_view_holder);

            image_ansher_ansher_view_holder.setVisibility(ImageView.VISIBLE);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v ==  image_ansher_ansher_view_holder)
        {

        }

        if (v == buttonImage_like_ansher_view_holder)
        {

        }

        if (v == button_reply_ansher_view_holder)
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

    public void setLike()
    {
        buttonImage_like_ansher_view_holder.setImageResource(R.drawable.like_image2);
    }

    public void setLike2()
    {
        buttonImage_like_ansher_view_holder.setImageResource(R.drawable.like_image);
    }

}
