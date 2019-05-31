package com.example.slatielly.app.register;

import com.example.slatielly.Model.User;

public interface RegisterContract {
    interface View {
        void setLoadingStatus(boolean isLoading);

        void setErrorMessage(String errorMessage);

        void navigateToMainScreen();
    }

    interface Presenter {
        void createValidationSchema();

        void registerUser(User user, String password);
    }
}
