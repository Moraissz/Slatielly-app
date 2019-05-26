package com.example.slatielly;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.Calendar;
import java.util.Date;

public class CalendarDateStartActivity extends AppCompatActivity
{
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_date_start);

        CalendarView calendarViewStart = (CalendarView) findViewById(R.id.MaterialCalendarView_calendar_date_start);


        Calendar calendar = Calendar.getInstance();

        calendarViewStart.setMinimumDate(calendar);

        try //releases dates after a minimum date
        {
            calendarViewStart.setDate(calendar);
        }
        catch (OutOfDateRangeException e)
        {
            e.printStackTrace();
        }




        calendarViewStart.setOnDayClickListener(new OnDayClickListener()
        {
            @Override
            public void onDayClick(EventDay eventDay)
            {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                Date dateStart = clickedDayCalendar.getTime();
            }
        });

    }
}
