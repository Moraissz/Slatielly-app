package com.example.slatielly.app.login;

import com.example.slatielly.app.FormContract;

public interface LoginContract {
    interface View extends FormContract.View {
        void navigateToMainScreen();
    }

    interface Presenter extends FormContract.Presenter {
        void loginUser(String email, String password);
    }
}
