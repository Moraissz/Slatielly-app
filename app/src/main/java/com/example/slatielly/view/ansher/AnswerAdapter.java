package com.example.slatielly.view.ansher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.app.dress.answers.AnswersFragment;
import com.example.slatielly.model.Answer;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerViewHolder>
{
    private String dressId;
    private ArrayList<Answer> answers;
    private String commentId;
    private AnswersFragment answersFragment;

    public AnswerAdapter(ArrayList<Answer> answers, String dressId, String commentId, AnswersFragment answersFragment)
    {
        this.answers = answers;
        this.dressId = dressId;
        this.commentId = commentId;
        this.answersFragment = answersFragment;
    }
    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate( R.layout.answer_view_holder, viewGroup, false);

        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder answerViewHolder, int i)
    {
        answerViewHolder.bind(this.answers.get(i),this.dressId,this.commentId,this.answersFragment);
    }

    @Override
    public int getItemCount()
    {
        return this.answers.size();
    }
}
