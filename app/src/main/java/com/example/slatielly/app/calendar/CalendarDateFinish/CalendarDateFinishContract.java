package com.example.slatielly.app.calendar.CalendarDateFinish;

import com.example.slatielly.app.FormContract;
import com.example.slatielly.model.Rent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface CalendarDateFinishContract
{
    interface View
    {
        Timestamp formDate(Calendar clickedDayCalendar);
        void continueProcess(ArrayList<Rent> rents);

    }

    interface Presenter
    {
        boolean dateVerificationRents (List<Rent> rents,Timestamp dateStart,Timestamp dateFinish);
        boolean dateVerificationDisableDays (List<Calendar> disableDays,Timestamp dateFinish);
        ArrayList<Rent> loadRents(String dressId);
        List<Calendar> getDisableDays(List<Rent> rents);
    }
}
