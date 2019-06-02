package com.example.slatielly.app.profile;

import com.example.slatielly.model.Address;
import com.example.slatielly.model.User;
import com.example.slatielly.app.FormContract;

public interface ProfileContract {
    interface View extends FormContract.View {
        void showUser(User user);
        void navigateToAuthScreen();
        void setSuccessMessage();
        void setSubmittingEp(boolean isSubmittingEP);
    }

    interface Presenter extends FormContract.Presenter  {
        void loadUser();
        void logout();
        void editProfile(String email, String phone, String name);
        void editAddress(Address address);
    }
}
