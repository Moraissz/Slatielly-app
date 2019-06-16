package com.example.slatielly.app.calendar.CalendarDateFinish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.example.slatielly.R;
import com.example.slatielly.app.calendar.CalendarDateStart.CalendarDateStartFragment;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.repository.FirestoreRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarDateFinishFragment  extends Fragment implements CalendarDateFinishContract.View, View.OnClickListener, OnDayClickListener {

    private Calendar calendar;
    private List<Calendar> disabledays;
    private CalendarDateFinishContract.Presenter presenter;
    public static List<Rent> rents;
    private Timestamp dateStart;
    private CalendarDateFinishFragment.OnNavigateListener onNavigateListener;
    private View view;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = view;
        FirestoreRepository<Rent> repository = new FirestoreRepository<>(Rent.class, Rent.DOCUMENT_NAME);
        this.presenter = new CalendarDateFinishPresenter(this, repository);
        if (this.getArguments() != null) {
            String dressId = this.getArguments().getString("id");
            rents = this.presenter.loadRents(dressId);
        }




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface OnNavigateListener{

    }

    @Override
    public void onClick(View v) {

    }

    public void setOnNavigationListener(CalendarDateFinishFragment.OnNavigateListener onNavigationListener){
        this.onNavigateListener = onNavigationListener;
    }

    @Override
    public void onDayClick(EventDay eventDay) {
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

    public static CalendarDateFinishFragment newInstance(String id, Timestamp dateStart) {
        CalendarDateFinishFragment calendarDateFinishFragment = new CalendarDateFinishFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("dateStart", dateStart.toString());
        calendarDateFinishFragment.setArguments(bundle);

        return calendarDateFinishFragment;
    }



    @Override
    public Timestamp formDate(Calendar clickedDayCalendar) {
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
    public void continueProcess(ArrayList<Rent> rents) {
        CalendarView calendarViewFinish = (CalendarView) this.view.findViewById(R.id.MaterialCalendarView_calendar_date_finish);


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

        disabledays = CalendarDateStartFragment.disabledays;
        calendarViewFinish.setDisabledDays(disabledays);


        dateStart = CalendarDateStartFragment.dateStart;
        this.rents = CalendarDateStartFragment.rents;
    }
}
