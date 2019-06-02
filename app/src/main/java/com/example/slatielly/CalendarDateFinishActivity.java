package com.example.slatielly;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.sql.Timestamp;
import java.util.Calendar;

public class CalendarDateFinishActivity extends AppCompatActivity
{
    public Calendar calendar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_date_finish);

        CalendarView calendarViewFinish = (CalendarView) findViewById(R.id.MaterialCalendarView_calendar_date_finish);


        calendar = Calendar.getInstance();

        calendarViewFinish.setMinimumDate(calendar);

        try //releases dates after a minimum date
        {
            calendarViewFinish.setDate(calendar);
        }
        catch (OutOfDateRangeException e)
        {
            e.printStackTrace();
        }

        calendarViewFinish.setOnDayClickListener(new OnDayClickListener()
        {
            @Override
            public void onDayClick(EventDay eventDay)
            {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                int day = clickedDayCalendar.get(Calendar.DAY_OF_MONTH);
                int month = clickedDayCalendar.get(Calendar.MONTH);
                int year = clickedDayCalendar.get(Calendar.YEAR);

                Calendar aux = Calendar.getInstance();

                aux.set(year,month,day,0,0,0);

                Timestamp dateFinish = new Timestamp(aux.getTimeInMillis());
                dateFinish.setNanos(0);
            }
        });
    }
}
