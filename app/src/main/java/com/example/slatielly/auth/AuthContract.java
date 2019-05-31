package com.example.slatielly.auth;

public interface AuthContract {
    interface View {
        void navigateToLoginScreen(android.view.View v);

        void navigateToRegisterScreen(android.view.View v);

        void navigateToMainScreen();
    }

    interface Presenter {
        void checkIfUserIsLoggedIn();
    }
}
