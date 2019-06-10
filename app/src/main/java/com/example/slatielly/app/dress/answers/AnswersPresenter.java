package com.example.slatielly.app.dress.answers;

import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class AnswersPresenter implements AnswersContract.Presenter, OnSuccessListener<Dress>
{
    private AnswersContract.View view;
    private FirestoreRepository<Dress> repository;
    private String commentId;

    public AnswersPresenter(AnswersContract.View view,  FirestoreRepository<Dress> repository)
    {
        this.view = view;
        this.repository = repository;
    }
    @Override
    public void loadAnswers(String dressId, String commentId)
    {
        this.commentId = commentId;

        this.repository.get(dressId).addOnSuccessListener(this);
    }

    @Override
    public void onSuccess(Dress dress)
    {
        ArrayList<Comment> comments = dress.getComments();

        for(int i=0;i<comments.size();i=i+1)
        {
            if(commentId.equals(comments.get(i).getId()))
            {
                view.setAnswers(comments.get(i).getAnswers());
                break;
            }
        }

    }
}
