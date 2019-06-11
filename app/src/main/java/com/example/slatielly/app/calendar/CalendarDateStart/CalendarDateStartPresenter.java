package com.example.slatielly.app.calendar.CalendarDateStart;

import com.example.slatielly.app.dress.comments.CommentsContract;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

public class CalendarDateStartPresenter implements CalendarDateStartContract.Presenter, OnSuccessListener<Rent> {

    private CalendarDateStartContract.View view;
    private FirestoreRepository<Rent> repository;

    public CalendarDateStartPresenter (CalendarDateStartContract.View view, FirestoreRepository<Rent> repository) {
        this.view = view;
        this.repository = repository;
    }

    public void loadRents(String dressId){
        this.repository
                .get(dressId)
                .addOnSuccessListener(this);
    }

    @Override
    public List<Calendar> getDisableDays(List<Rent> rents) {
        List<Calendar> disabledays = new ArrayList<>();

        for (int i = 0; i < rents.size(); i = i + 1) {
            Calendar aux = Calendar.getInstance();
            aux.setTime(rents.get(i).getStartDate());
            disabledays.add(aux);

            aux = Calendar.getInstance();
            aux.setTime(rents.get(i).getEndDate());
            disabledays.add(aux);

            Date dataaux = rents.get(i).getStartDate();
            while (dataaux.before(rents.get(i).getEndDate()))
            {
                aux = Calendar.getInstance();
                aux.setTime( dataaux );

                int day = aux.get( Calendar.DAY_OF_MONTH );
                int month = aux.get( Calendar.MONTH );
                int year = aux.get( Calendar.YEAR );

                aux.set( year, month, day + 1, 0, 0, 0 );

                dataaux = new Timestamp(aux.getTimeInMillis());
                //dataaux.setNanos(0);

                aux = Calendar.getInstance();
                aux.setTime(dataaux);
                disabledays.add(aux);
            }
        }
        return disabledays;
    }

    @Override
    public boolean dateVerificationDisableDays(List<Calendar> disableDays, Timestamp dateStart)
    {
        boolean state = true;
        for(int i=0;i<disableDays.size();i=i+1)
        {
            Timestamp stampaux = new Timestamp(disableDays.get(i).getTimeInMillis());
            stampaux.setNanos(0);

            if(dateStart.compareTo(stampaux)==0)
            {
                state = false;
                break;
            }
        }

        return state;
    }



    @Override
    public void onSuccess(Rent rent) {
        this.view.addRent(rent);
    }
}


