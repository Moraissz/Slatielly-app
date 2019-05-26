package com.example.slatielly.view.Comment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slatielly.R;
import com.squareup.picasso.Picasso;

public class HolderComment extends RecyclerView.ViewHolder
{
    ImageView image_profile_image_comment_model;
    ImageView image_comment_comment_model;

    TextView TextView_name_comment_model;
    TextView TextView_comment_comment_view;
    TextView TextView_date_comment_model;

    Button button_like_comment_model;
    Button button_reply_comment_model;
    Button button_see_answers_comment_model;

    public HolderComment(@NonNull View itemView)
    {
        super( itemView );

        TextView_name_comment_model = (TextView) itemView.findViewById(R.id.TextView_name_comment_model);
        TextView_comment_comment_view = (TextView) itemView.findViewById(R.id.TextView_comment_comment_view);
        TextView_date_comment_model = (TextView) itemView.findViewById(R.id.TextView_date_comment_model);


        image_profile_image_comment_model = (ImageView)  itemView.findViewById(R.id.image_profile_image_comment_model);
        image_comment_comment_model = (ImageView)  itemView.findViewById(R.id.image_comment_comment_model);

        button_like_comment_model = (Button)  itemView.findViewById(R.id.button_like_comment_model);
        button_reply_comment_model = (Button)  itemView.findViewById(R.id.button_reply_comment_model);
        button_see_answers_comment_model = (Button)  itemView.findViewById(R.id.button_see_answers_comment_model);

    }

    public void bind()
    {

        image_comment_comment_model.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        button_like_comment_model.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        button_reply_comment_model.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        button_see_answers_comment_model.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }
}
