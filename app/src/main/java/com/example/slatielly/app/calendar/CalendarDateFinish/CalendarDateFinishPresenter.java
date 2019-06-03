package com.example.slatielly.app.calendar.CalendarDateFinish;

import com.example.slatielly.model.Rent;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class CalendarDateFinishPresenter implements CalendarDateFinishContract.Presenter
{
    public boolean dateVerificationRents (List<Rent> rents,Timestamp dateStart, Timestamp datefinish)
    {
        boolean state = false;
        for(int i=0;i<rents.size();i=i+1)
        {
            Timestamp comparation1 = rents.get(i).getStartDate();
            Timestamp comparation2 = rents.get(i).getEndDate();
            if(dateStart.before(comparation1) && datefinish.before(comparation1))
            {
                state =  true;
            }
            else if(dateStart.after(comparation2) && datefinish.after(comparation2))
            {
                state = true;
            }
            else
            {
                state=false;
            }

            if(!state)
            {
                state = false;
                break;
            }
        }

        return state;
    }

    @Override
    public boolean dateVerificationDisableDays(List<Calendar> disableDays, Timestamp dateFinish)
    {
        boolean state = true;
        for(int i=0;i<disableDays.size();i=i+1)
        {
            Timestamp stampaux = new Timestamp(disableDays.get(i).getTimeInMillis());
            stampaux.setNanos(0);

            if(dateFinish.compareTo(stampaux)==0)
            {
                state = false;
                break;
            }
        }

        return state;
    }
}
