package com.example.slatielly.app.calendar.CalendarDateStart;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.slatielly.R;
import com.example.slatielly.model.Rent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarDateStartActivity extends AppCompatActivity implements CalendarDateStartContract.View
{
    public Calendar calendar;

    public static List<Calendar> disabledays;

    public List<Rent> rents;

    public Intent intent;

    private CalendarDateStartContract.Presenter presenter;

    public Timestamp dateToday;


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.calendar_date_start);
        intent = new Intent(this,CalendarDateStartActivity.class);

        presenter = new CalendarDateStartPresenter();

        dateToday = new Timestamp(calendar.getTimeInMillis());
        dateToday.setNanos(0);

        CalendarView calendarViewStart = (CalendarView) findViewById(R.id.MaterialCalendarView_calendar_date_start);




        calendar = Calendar.getInstance();
        calendarViewStart.setMinimumDate(calendar);


        disabledays = presenter.getDisableDays(rents);



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

                Timestamp dateStart = formDate(clickedDayCalendar);

                if(dateStart.before(dateToday))
                {

                }
                else if(presenter.dateVerificationDisableDays(disabledays,dateStart))
                {

                }
                else
                {

                }
            }
        });

    }

    @Override
    public Timestamp formDate(Calendar clickedDayCalendar)
    {
        int day = clickedDayCalendar.get(Calendar.DAY_OF_MONTH);
        int month = clickedDayCalendar.get(Calendar.MONTH);
        int year = clickedDayCalendar.get(Calendar.YEAR);

        Calendar aux = Calendar.getInstance();
        aux.set(year,month,day,0,0,0);

        Timestamp dateStart = new Timestamp(aux.getTimeInMillis());
        dateStart.setNanos(0);
        return dateStart;
    }

    @Override
    public void setLoadingStatus(boolean isLoading)
    {

    }

    @Override
    public void setErrorMessage(String errorMessage)
    {

    }
}
