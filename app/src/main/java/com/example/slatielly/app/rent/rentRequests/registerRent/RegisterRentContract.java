package com.example.slatielly.app.rent.rentRequests.registerRent;

import com.example.slatielly.app.FormContract;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Rent;

public interface RegisterRentContract {
    interface View extends FormContract.View{
        void navigateToAllRents();
    }
    interface Presenter extends FormContract.Presenter{
        void createRent(Rent rent);


    }
}
