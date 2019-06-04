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

    public static List<Rent> rents; //AQUI DEVE CHEGAR TODOS OS ALUGUÉIS DO VESTIDO CORRESPONDENTE

    private CalendarDateStartContract.Presenter presenter;

    private Timestamp dateToday;

    public static Timestamp dateStart;


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.calendar_date_start);

        presenter = new CalendarDateStartPresenter();


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


        dateToday = formDate(calendar);


        calendarViewStart.setDisabledDays(disabledays);


        calendarViewStart.setOnDayClickListener(new OnDayClickListener()
        {
            @Override
            public void onDayClick(EventDay eventDay)
            {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                dateStart = formDate(clickedDayCalendar);

                if(dateStart.before(dateToday))//verifica se a data escolhida é anterior a data de hoje, se entrar aqui a data escolhida é invalida
                {

                }
                else if(!presenter.dateVerificationDisableDays(disabledays,dateStart)) //verifica se a data escolhida não é uma data desabilitada, se entrar aqui a data escolhida é invalida
                {

                }
                else //pode prosseguir para escolher a data de devolução
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
}
