package com.example.slatielly.auth;

public interface AuthContract {
    interface View {
        void navigateToMainScreen();
    }

    interface Presenter {
        void checkIfUserIsLoggedIn();
    }
}
