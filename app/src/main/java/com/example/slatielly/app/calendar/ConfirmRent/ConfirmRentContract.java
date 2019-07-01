package com.example.slatielly.app.calendar.ConfirmRent;

import com.example.slatielly.model.Rent;

public interface ConfirmRentContract
{
    interface View
    {
        void navigateToDresses();
    }

    interface Presenter
    {
        void saveRent(Rent rent);
    }
}
