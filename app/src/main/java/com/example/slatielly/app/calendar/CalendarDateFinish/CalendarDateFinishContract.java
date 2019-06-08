package com.example.slatielly.app.calendar.CalendarDateFinish;

import com.example.slatielly.app.FormContract;
import com.example.slatielly.model.Rent;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public interface CalendarDateFinishContract
{
    interface View
    {
        Timestamp formDate(Calendar clickedDayCalendar);
    }

    interface Presenter
    {
        boolean dateVerificationRents (List<Rent> rents,Timestamp dateStart,Timestamp dateFinish);
        boolean dateVerificationDisableDays (List<Calendar> disableDays,Timestamp dateFinish);
    }
}
