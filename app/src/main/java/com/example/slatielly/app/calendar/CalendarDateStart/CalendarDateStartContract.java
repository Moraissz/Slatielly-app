package com.example.slatielly.app.calendar.CalendarDateStart;

import com.example.slatielly.app.FormContract;
import com.example.slatielly.model.Rent;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public interface CalendarDateStartContract
{
    interface View
    {
        Timestamp formDate(Calendar clickedDayCalendar);

    }

    interface Presenter
    {
        List<Calendar> getDisableDays(List<Rent> rents);
        boolean dateVerificationDisableDays (List<Calendar> disableDays,Timestamp dateStart);
    }
}
