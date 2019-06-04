package com.example.slatielly.app.dress.comments;

public class CommentsPresenter implements CommentsContract.Presenter {
    private CommentsContract.View view;

    public CommentsPresenter(CommentsContract.View view) {
        this.view = view;
    }
}
