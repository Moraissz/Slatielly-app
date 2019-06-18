package com.example.slatielly.app.calendar.CalendarDateStart;

import android.support.annotation.NonNull;

import com.example.slatielly.app.dress.comments.CommentsContract;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

public class CalendarDateStartPresenter implements CalendarDateStartContract.Presenter, OnSuccessListener<Rent> {

    private CalendarDateStartContract.View view;
    private FirestoreRepository<Rent> repository;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CalendarDateStartPresenter (CalendarDateStartContract.View view, FirestoreRepository<Rent> repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public ArrayList<Rent> loadRents(String dressId){
        /*
        this.repository
                .get(dressId)
                .addOnSuccessListener(this);
        */
        final ArrayList<Rent> rents = new ArrayList<>();

        db.collection("rents")
                .whereEqualTo("dress.id", dressId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println(task.getResult().toString());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Rent rent = document.toObject(Rent.class);
                                rents.add(rent);
                            }
                            view.continueProcess(rents);
                        } else {
                        }
                    }
                });
        return rents;
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


