/*package com.example.slatielly.app.calendar.CalendarDateFinish;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.slatielly.R;
import com.example.slatielly.app.calendar.CalendarDateStart.CalendarDateStartActivity;
import com.example.slatielly.app.calendar.CalendarDateStart.CalendarDateStartPresenter;
import com.example.slatielly.model.Rent;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class CalendarDateFinishActivity extends AppCompatActivity implements CalendarDateFinishContract.View
{
    private Calendar calendar;

    private List<Calendar> disabledays;

    private CalendarDateFinishContract.Presenter presenter;

    public static List<Rent> rents;

    private Timestamp dateStart;


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.calendar_date_finish);

        presenter = new CalendarDateFinishPresenter();

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

        disabledays = CalendarDateStartActivity.disabledays;
        calendarViewFinish.setDisabledDays(disabledays);


        dateStart = CalendarDateStartActivity.dateStart;
        rents = CalendarDateStartActivity.rents;


        calendarViewFinish.setOnDayClickListener(new OnDayClickListener()
        {
            @Override
            public void onDayClick(EventDay eventDay)
            {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                Timestamp dateFinish = formDate(clickedDayCalendar);


                if(dateFinish.before(dateStart))//verifica se a data de devolução é anterior a data de de inicio, se entrar aqui a data escolhida é invalida
                {

                }
                else if(!presenter.dateVerificationDisableDays(disabledays,dateFinish)) //verifica se a data escolhida não é uma data desabilitada, se entrar aqui a data escolhida é invalida
                {

                }
                else if(!presenter.dateVerificationRents( rents,dateStart,dateFinish ))//verifica se o período de dias entre a data de incício e a data de devolução não choca com nenhum outro período de aluguel. Se entrar aqui, o período escolhido é inválido
                {

                }
                else //pode fazer o aluguel
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
*/