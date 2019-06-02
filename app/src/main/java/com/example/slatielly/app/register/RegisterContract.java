package com.example.slatielly.app.register;

import com.example.slatielly.model.User;
import com.example.slatielly.app.FormContract;

public interface RegisterContract {
    interface View extends FormContract.View {
        void navigateToMainScreen();
    }

    interface Presenter extends FormContract.Presenter {
        void registerUser(User user, String password);
    }
}
