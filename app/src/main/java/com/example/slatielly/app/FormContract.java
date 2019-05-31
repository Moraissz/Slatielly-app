package com.example.slatielly.app;

public interface FormContract {
    interface View {
        void setLoadingStatus(boolean isLoading);

        void setErrorMessage(String errorMessage);
    }

    interface Presenter {
        void createValidationSchema();
    }
}
