package com.example.slatielly;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarDateStartActivity extends AppCompatActivity
{
    public Calendar calendar;

    public static List<Calendar> disabledays;

    public Intent intent;

    public static Date dateStartChose;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_date_start);

        CalendarView calendarViewStart = (CalendarView) findViewById(R.id.MaterialCalendarView_calendar_date_start);


        calendar = Calendar.getInstance();
        disabledays = new ArrayList<>();
        intent = new Intent(this,CalendarDateStartActivity.class);

        calendarViewStart.setMinimumDate(calendar);

        try //releases dates after a minimum date
        {
            calendarViewStart.setDate(calendar);
        }
        catch (OutOfDateRangeException e)
        {
            e.printStackTrace();
        }

        calendarViewStart.setDisabledDays(disabledays);

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
