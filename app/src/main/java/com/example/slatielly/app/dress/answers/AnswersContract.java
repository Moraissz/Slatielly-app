package com.example.slatielly.app.dress.answers;

import com.example.slatielly.model.Answer;

import java.util.ArrayList;

public interface AnswersContract
{
    interface View {
        void setAnswers(ArrayList<Answer> answers);
    }

    interface Presenter {
        void loadAnswers(String dressId, String commentId);
    }
}
