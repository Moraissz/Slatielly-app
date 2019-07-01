package com.example.slatielly.app.calendar.CalendarDateStart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.slatielly.model.Rent;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.slatielly.R;
import com.example.slatielly.app.dress.comments.CommentsFragment;
import com.example.slatielly.app.dress.comments.CommentsPresenter;
import com.example.slatielly.app.dress.registerDress.RegisterDressContract;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarDateStartFragment extends Fragment implements CalendarDateStartContract.View , View.OnClickListener , OnDayClickListener{

    public Calendar calendar;

    public List<Calendar> disabledays ;

    public List<com.example.slatielly.model.Rent> rents; //Load all dresses

    private CalendarDateStartContract.Presenter presenter;

    private Timestamp dateToday;

    public Timestamp dateStart;

    private String dressId;

    private CalendarView calendarViewStart;

    private CalendarDateStartFragment.OnNavigateListener onNavigateListener;

    @Override
    public void onDayClick(EventDay eventDay) {
        Calendar clickedDayCalendar = eventDay.getCalendar();
        dateStart = formDate(clickedDayCalendar);

        if(dateStart.before(dateToday))//verifica se a data escolhida é anterior a data de hoje, se entrar aqui a data escolhida é invalida
        {
            Toast.makeText(this.getContext().getApplicationContext(), "A DATA DE INÍCIO NÃO PODE SER ANTERIOR A DATA DE HOJE!", Toast.LENGTH_SHORT).show();
        }
        else if(!presenter.dateVerificationDisableDays(disabledays,dateStart)) //verifica se a data escolhida não é uma data desabilitada, se entrar aqui a data escolhida é invalida
        {
            Toast.makeText(this.getContext().getApplicationContext(), "ESSA DATA NÃO PODE SER ESCOLHIDA!", Toast.LENGTH_SHORT).show();
        }
        else //pode prosseguir para escolher a data de devolução
        {
            if (this.getArguments() != null)
            {
                long startDateMiliseconds = this.dateStart.getTime();
                System.out.println("Date Start 1: " + startDateMiliseconds);
                System.out.println("BUG 6: " + this.dressId);
                this.onNavigateListener.onSelectFinishDateRent(this.dressId, startDateMiliseconds);
            }

        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.calendarViewStart = (CalendarView) view.findViewById(R.id.MaterialCalendarView_calendar_date_start);
        FirestoreRepository<Rent> repository = new FirestoreRepository<>(Rent.class, Rent.DOCUMENT_NAME);
        this.presenter = new CalendarDateStartPresenter(this, repository);
        if (this.getArguments() != null) {
            String dressId = this.getArguments().getString("id");
            //this.presenter.loadComments(dressId);
            //Buscar as datas
            this.dressId = this.getArguments().getString("id");

            calendar = Calendar.getInstance();
            calendarViewStart.setMinimumDate(calendar);
            try //releases dates after a minimum date
            {
                calendarViewStart.setDate(calendar);
            }
            catch (OutOfDateRangeException e)
            {
                e.printStackTrace();
            }
            rents = this.presenter.loadRents(this.dressId);
        }
    }


    public static CalendarDateStartFragment newInstance(String id) {
        CalendarDateStartFragment calendarDateStartFragment = new CalendarDateStartFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        calendarDateStartFragment.setArguments(bundle);

        return calendarDateStartFragment;
    }



    @Override
    public void onClick(View v) {

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
    public void setRents(ArrayList<Rent> rents) {

    }

    @Override
    public void addRent(Rent rent) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendar_date_start, container, false);
    }

    @Override
    public void continueProcess(ArrayList<Rent> rents){

        disabledays = this.presenter.getDisableDays(rents);

        calendar = Calendar.getInstance();
        calendarViewStart.setMinimumDate(calendar);

        try //releases dates after a minimum date
        {
            calendarViewStart.setDate(calendar);
        }
        catch (OutOfDateRangeException e)
        {
            e.printStackTrace();
        }

        calendarViewStart.setOnDayClickListener(this);

        disabledays = presenter.getDisableDays(rents);

        dateToday = formDate(calendar);

        calendarViewStart.setDisabledDays(disabledays);
    }

    public interface OnNavigateListener
    {
        void onSelectFinishDateRent(String dressId, long startDate);
        void onBackPressed();
        void enableViews(boolean enable);
    }

    public void setOnNavigationListener(CalendarDateStartFragment.OnNavigateListener onNavigationListener){
        this.onNavigateListener = onNavigationListener;
    }

    public CalendarDateStartFragment.OnNavigateListener getListener()
    {
        return onNavigateListener;
    }
}
