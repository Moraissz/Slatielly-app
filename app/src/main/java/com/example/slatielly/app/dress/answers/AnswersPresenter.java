package com.example.slatielly.app.dress.answers;

import com.example.slatielly.model.Answer;
import com.example.slatielly.model.Comment;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Like;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.ansher.AnswerViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class AnswersPresenter implements AnswersContract.Presenter, OnSuccessListener<Dress>
{
    private AnswersContract.View view;
    private FirestoreRepository<Dress> repository;
    private String commentId;

    private AnswerViewHolder viewHolder;

    public AnswersPresenter(AnswersContract.View view,  FirestoreRepository<Dress> repository)
    {
        this.view = view;
        this.repository = repository;
    }

    public AnswersPresenter(AnswerViewHolder answerViewHolder)
    {
        this.viewHolder = answerViewHolder;
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

    public void checkUser(final Answer answer)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                Boolean state = true;

                User currentUser = documentSnapshot.toObject(User.class);

                for(int i=0;i<answer.getLikes().size();i=i+1)
                {
                    if(currentUser.getId().equals(answer.getLikes().get(i).getUser().getId()))
                    {
                        state = false;
                        break;
                    }
                }

                if(state)
                {
                    viewHolder.addLike(currentUser);
                }
                else
                {
                    viewHolder.subtractLike(currentUser);
                }
            }
        });
    }

    public void checkUserBind(final Answer answer)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                Boolean state = true;

                User currentUser = documentSnapshot.toObject(User.class);

                for(int i=0;i<answer.getLikes().size();i=i+1)
                {
                    if(currentUser.getId().equals(answer.getLikes().get(i).getUser().getId()))
                    {
                        state = false;
                        break;
                    }
                }

                if(!state)
                {
                    viewHolder.setLike();
                }
                else
                {
                    viewHolder.setLike2();
                }
            }
        });
    }

    public void updateAnswerAddLike(final String answerId, final String commentId, final String dressId, final User currentUser)
    {
        final Like like = new Like();
        like.setUser(currentUser);

        Calendar aux = Calendar.getInstance();
        like.setDateLike(new Timestamp(aux.getTimeInMillis()));

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "dresses" ).document(dressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                final Dress dress = documentSnapshot.toObject(Dress.class);

                ArrayList<Comment> comments = dress.getComments();

                for(int i=0;i<comments.size();i=i+1)
                {
                    if(commentId.equals(comments.get(i).getId()))
                    {

                        for(int j=0;j<comments.get(i).getAnswers().size();j=j+1)
                        {
                            if(answerId.equals(comments.get(i).getAnswers().get(j).getId()))
                            {
                                comments.get(i).getAnswers().get(j).getLikes().add(like);
                                comments.get(i).getAnswers().get(j).setNumberLikes(comments.get(i).getAnswers().get(j).getNumberLikes()+1);
                                break;
                            }
                        }
                        break;
                    }
                }
                db.collection( "dresses" ).document(dress.getId()).update("comments",comments);
            }
        });
    }

    public void updateCommentSubtractLike(final String answerId, final String commentId, final String dressId, final User currentUser)
    {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "dresses" ).document(dressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                final Dress dress = documentSnapshot.toObject(Dress.class);

                ArrayList<Comment> comments = dress.getComments();

                for(int i=0;i<comments.size();i=i+1)
                {
                    if(commentId.equals(comments.get(i).getId()))
                    {
                        for(int j=0;j<comments.get(i).getAnswers().size();j=j+1)
                        {
                            if(answerId.equals(comments.get(i).getAnswers().get(j).getId()))
                            {
                                comments.get(i).getAnswers().get(j).setNumberLikes(comments.get(i).getAnswers().get(j).getNumberLikes()-1);
                                for(int k=0;j<comments.get(i).getAnswers().get(j).getLikes().size();k=k+1)
                                {
                                    if(currentUser.getId().equals(comments.get(i).getAnswers().get(j).getLikes().get(k).getUser().getId()))
                                    {
                                        comments.get(i).getAnswers().get(j).getLikes().remove(comments.get(i).getAnswers().get(j).getLikes().get(k));
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                db.collection( "dresses" ).document(dress.getId()).update("comments",comments);
            }
        });
    }
}
