package com.example.slatielly.login;

public interface LoginContract {
    interface View {
        void setLoadingStatus(boolean isLoading);

        void setErrorMessage(String errorMessage);

        void navigateToMainScreen();
    }

    interface Presenter {
        void createValidationSchema();

        void loginUser(String email, String password);
    }
}
