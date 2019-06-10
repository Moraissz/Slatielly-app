package com.example.slatielly.app.dress.answers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.slatielly.MainActivity;
import com.example.slatielly.R;
import com.example.slatielly.app.dress.comments.CommentsFragment;
import com.example.slatielly.model.Answer;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.ansher.AnswerAdapter;

import java.util.ArrayList;

public class AnswersFragment extends Fragment implements AnswersContract.View, View.OnClickListener
{
    private RecyclerView rvAnshers;
    private AnswersContract.Presenter presenter;

    private AnswersFragment.OnNavigationListener listener;

    private Button btnAnsher;

    private String dressId;
    private String commentId;

    public static AnswersFragment newInstance(String dressId, String commentId)
    {
        AnswersFragment answersFragment = new AnswersFragment();

        Bundle bundle = new Bundle();
        bundle.putString("dressId", dressId);
        bundle.putString("commentId", commentId);
        answersFragment.setArguments(bundle);

        return answersFragment;
    }

    public AnswersFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate( R.layout.fragment_answers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.setupViews(view);

        FirestoreRepository<Dress> repository = new FirestoreRepository<>(Dress.class, Dress.DOCUMENT_NAME);
        this.presenter = new AnswersPresenter(this, repository);

        if (this.getArguments() != null)
        {
            dressId = this.getArguments().getString("dressId");
            commentId = this.getArguments().getString("commentId");
            this.presenter.loadAnswers(dressId,commentId);
        }
    }

    private void setupViews(View view)
    {
        rvAnshers = view.findViewById(R.id.rvAnshers);
        rvAnshers.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvAnshers.setHasFixedSize(true);

        btnAnsher = (Button) view.findViewById(R.id.btnAnsher);
        btnAnsher.setOnClickListener(this);
    }

    @Override
    public void setAnswers(ArrayList<Answer> answers)
    {
        AnswerAdapter answerAdapter = new AnswerAdapter(answers,dressId,commentId);
        rvAnshers.setAdapter(answerAdapter);
    }

    @Override
    public void onClick(View v)
    {
        if (v == btnAnsher)
        {
            if (this.getArguments() != null)
            {
                return;
            }

            return;
        }

    }

    public interface OnNavigationListener
    {
        void onNavigateToNewAnswer(String dressId, String commentId);
    }

    public void setOnNavigationListener(AnswersFragment.OnNavigationListener listener)
    {
        this.listener = listener;
    }
}
