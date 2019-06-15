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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

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
        Comment comment = new Comment();
        comment.setId(commentId);

        int i = dress.getComments().indexOf(comment);
        view.setAnswers(dress.getComments().get(i).getAnswers());
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

                User currentUser = documentSnapshot.toObject(User.class);

                Set<Like> likes = new HashSet<>(answer.getLikes());
                Like like = new Like();
                like.setUser(currentUser);

                if(likes.contains(like))
                {
                    viewHolder.subtractLike(currentUser);
                }
                else
                {
                    viewHolder.addLike(currentUser);
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

                User currentUser = documentSnapshot.toObject(User.class);

                Set<Like> likes = new HashSet<>(answer.getLikes());
                Like like = new Like();
                like.setUser(currentUser);

                if(likes.contains(like))
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

    public void updateAnswerAddLike(final Answer answer, final String commentId, final String dressId, final User currentUser)
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

                Comment comment = new Comment();
                comment.setId(commentId);

                int i = dress.getComments().indexOf(comment);

                int j = dress.getComments().get(i).getAnswers().indexOf(answer);

                dress.getComments().get(i).getAnswers().get(j).getLikes().add(like);
                dress.getComments().get(i).getAnswers().get(j).setNumberLikes(dress.getComments().get(i).getAnswers().get(j).getNumberLikes()+1);

                db.collection( "dresses" ).document(dress.getId()).update("comments",dress.getComments());
            }
        });
    }

    public void updateCommentSubtractLike(final Answer answer, final String commentId, final String dressId, final User currentUser)
    {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "dresses" ).document(dressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                final Dress dress = documentSnapshot.toObject(Dress.class);

                Comment comment = new Comment();
                comment.setId(commentId);

                int i = dress.getComments().indexOf(comment);

                int j = dress.getComments().get(i).getAnswers().indexOf(answer);

                Like like = new Like();
                like.setUser(currentUser);

                int k = dress.getComments().get(i).getAnswers().get(j).getLikes().indexOf(like);

                dress.getComments().get(i).getAnswers().get(j).setNumberLikes(dress.getComments().get(i).getAnswers().get(j).getNumberLikes()-1);
                dress.getComments().get(i).getAnswers().get(j).getLikes().remove(dress.getComments().get(i).getAnswers().get(j).getLikes().get(k));

                db.collection( "dresses" ).document(dress.getId()).update("comments",dress.getComments());
            }
        });
    }
}
