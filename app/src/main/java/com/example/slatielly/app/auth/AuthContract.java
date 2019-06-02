package com.example.slatielly.app.auth;

public interface AuthContract {
    interface View {
        void navigateToMainScreen();
    }

    interface Presenter {
        void checkIfUserIsLoggedIn();
    }
}
