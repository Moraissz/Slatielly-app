package com.example.slatielly.app.calendar.CalendarDateFinish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.example.slatielly.R;
import com.example.slatielly.app.calendar.CalendarDateStart.CalendarDateStartFragment;
import com.example.slatielly.app.dress.DressContract;
import com.example.slatielly.app.dress.DressFragment;
import com.example.slatielly.app.dress.DressPresenter;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarDateFinishFragment  extends Fragment implements CalendarDateFinishContract.View, View.OnClickListener, OnDayClickListener {

    private Calendar calendar;
    private List<Calendar> disabledays;
    private CalendarDateFinishContract.Presenter presenter;
    private List<Rent> rents;
    private Timestamp dateStart;
    private CalendarDateFinishFragment.OnNavigateListener onNavigateListener;
    private View view;
    private CalendarView calendarViewFinish;
    private Timestamp dateFinish;
    private Dress dress;
    private User user;
    private String dressId;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        onNavigateListener.enableViews(true);
        this.calendarViewFinish = (CalendarView) view.findViewById(R.id.MaterialCalendarView_calendar_date_finish);
        FirestoreRepository<Rent> repository = new FirestoreRepository<>(Rent.class, Rent.DOCUMENT_NAME);
        FirestoreRepository<User> repositoryUser = new FirestoreRepository<>(User.class, User.DOCUMENT_NAME);
        this.presenter = new CalendarDateFinishPresenter(this, repository, repositoryUser);
        if (this.getArguments() != null)
        {
            this.dressId = this.getArguments().getString("id");
            this.dateStart = new Timestamp(this.getArguments().getLong("dateStart"));

            this.calendar = Calendar.getInstance();
            try //releases dates after a minimum date
            {
                this.calendar.setTime(dateStart);
                calendarViewFinish.setDate(this.calendar);
                this.calendarViewFinish.setMinimumDate(this.calendar);

            }
            catch (OutOfDateRangeException e)
            {
                e.printStackTrace();
            }

            this.presenter.loadRents(this.dressId);
        }

        calendarViewFinish.setOnDayClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendar_date_finish, container, false);

    }

    public interface OnNavigateListener{
        void onNavigateToAllDresses();
        void enableViews(boolean enable);
        void onNavigateToConfirmRent(Rent rent, Dress dress);
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

        this.dateFinish = formDate(clickedDayCalendar);


        if(dateFinish.before(dateStart))//verifica se a data de devolução é anterior a data de de inicio, se entrar aqui a data escolhida é invalida
        {
            Toast.makeText(this.getContext().getApplicationContext(), "A DATA DE DEVOLUÇÃO NÃO PODE SER ANTERIOR A DATA DE INÍCIO!", Toast.LENGTH_SHORT).show();

        }
        else if(!presenter.dateVerificationDisableDays(disabledays,dateFinish)) //verifica se a data escolhida não é uma data desabilitada, se entrar aqui a data escolhida é invalida
        {
            Toast.makeText(this.getContext().getApplicationContext(), "ESSA DATA NÃO PODE SER ESCOLHIDA!", Toast.LENGTH_SHORT).show();

        }
        else if(!presenter.dateVerificationRents(rents,dateStart,dateFinish ))//verifica se o período de dias entre a data de incício e a data de devolução não choca com nenhum outro período de aluguel. Se entrar aqui, o período escolhido é inválido
        {
            Toast.makeText(this.getContext().getApplicationContext(), "O PERÍODO ESCOLHIDO É INVÁLIDO!", Toast.LENGTH_SHORT).show();
        }
        else //pode fazer o aluguel
        {
            FirestoreRepository<Dress> repository = new FirestoreRepository<>(Dress.class, Dress.DOCUMENT_NAME);
            if (this.getArguments() != null) {
                //Inserir
                this.presenter.getDress(this.dressId);
            }
        }
    }

    public static CalendarDateFinishFragment newInstance(String id, long dateStart) {
        CalendarDateFinishFragment calendarDateFinishFragment = new CalendarDateFinishFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putLong("dateStart", dateStart);

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
    public void continueProcess(ArrayList<Rent> rents)
    {
        this.rents = rents;
        this.disabledays = this.presenter.getDisableDays(rents);
        this.calendar = Calendar.getInstance();
        calendarViewFinish.setDisabledDays(disabledays);

        try //releases dates after a minimum date
        {
            this.calendar.setTime(dateStart);
            calendarViewFinish.setDate(this.calendar);
            this.calendarViewFinish.setMinimumDate(this.calendar);

        }
        catch (OutOfDateRangeException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void getUserDb(User user) {
        //TODO Pegar o usuário do objeto
        this.user = user;
        this.insertRent();
    }

    @Override
    public void getDressDb(Dress dress) {
        //TODO pegar o dress do objeto
        this.dress = dress;
        this.presenter.loadUser();
    }

    public void insertRent()
    {
        Rent rent = new Rent();
        rent.setDress(this.dress);
        rent.setUser(this.user);
        rent.setStartDate(this.dateStart);
        rent.setEndDate(this.dateFinish);
        rent.setStatus(Rent.PENDENT);
        rent.setTimestamp(new Date());

        long days = ((dateFinish.getTime() - dateStart.getTime())/(24*60*60*1000))+1;
        rent.setPrice(days*dress.getPrice());

        onNavigateListener.onNavigateToConfirmRent(rent, dress);
    }
}
