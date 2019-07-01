package com.example.slatielly.app.calendar.CalendarDateFinish;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.slatielly.app.calendar.CalendarDateStart.CalendarDateStartContract;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Rent;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarDateFinishPresenter implements CalendarDateFinishContract.Presenter{

    private CalendarDateFinishContract.View view;
    private FirestoreRepository<Rent> repository;
    private FirestoreRepository<Dress> repositoryDress;
    private FirestoreRepository<User> userRepository;
    private User user;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CalendarDateFinishPresenter(CalendarDateFinishContract.View view, FirestoreRepository<Rent> rentFirestoreRepository, FirestoreRepository<User> userFirestoreRepository) {
        this.view = view;
        this.repository = rentFirestoreRepository;
        this.userRepository = userFirestoreRepository;

    }

    public boolean dateVerificationRents(List<Rent> rents, Timestamp dateStart, Timestamp datefinish) {
        boolean state = true;
        for (int i = 0; i < rents.size(); i = i + 1)
        {
            if(!rents.get(i).getStatus().equals(Rent.DECLINED))
            {
                Timestamp comparation1 = new Timestamp(rents.get(i).getStartDate().getTime());
                Timestamp comparation2 = new Timestamp(rents.get(i).getEndDate().getTime());
                if (dateStart.before(comparation1) && datefinish.before(comparation1)) {
                    state = true;
                } else if (dateStart.after(comparation2) && datefinish.after(comparation2)) {
                    state = true;
                } else {
                    state = false;
                }

                if (!state)
                {
                    break;
                }
            }
        }

        return state;
    }

    @Override
    public void loadUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.userRepository
            .get(firebaseUser.getUid())
            .addOnCompleteListener(new OnCompleteListener<User>() {
                @Override
                public void onComplete(@NonNull Task<User> task) {
                    if (task.isSuccessful()) {
                        user = task.getResult();
                        view.getUserDb(user);
                    }
                }
            });
    }

    @Override
    public void getDress(final String dressId) {
        System.out.println("\n\nID: " + dressId);
        db.collection("dresses").document(dressId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Dress dress = document.toObject(Dress.class);
                        System.out.println("BUG 1: " + dressId);
                        view.getDressDb(dress);

                    } else {

                    }
                } else {

                }
            }
        });
    }


    @Override
    public boolean dateVerificationDisableDays(List<Calendar> disableDays, Timestamp dateFinish) {
        boolean state = true;
        for (int i = 0; i < disableDays.size(); i = i + 1) {
            Timestamp stampaux = new Timestamp(disableDays.get(i).getTimeInMillis());
            stampaux.setNanos(0);

            if (dateFinish.compareTo(stampaux) == 0) {
                state = false;
                break;
            }
        }

        return state;
    }

    @Override
    public void loadRents(String dressId) {
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
    }

    @Override
    public List<Calendar> getDisableDays(List<Rent> rents)
    {
        List<Calendar> disabledays = new ArrayList<>();

        for (int i = 0; i < rents.size(); i = i + 1)
        {
            if(!rents.get(i).getStatus().equals(Rent.DECLINED))
            {
                Calendar aux = Calendar.getInstance();
                aux.setTime(rents.get(i).getStartDate());
                disabledays.add(aux);

                aux = Calendar.getInstance();
                aux.setTime(rents.get(i).getEndDate());
                disabledays.add(aux);

                Date dataaux;

                dataaux = rents.get(i).getStartDate();

                for(int j=0;j<rents.get(i).getDress().getPrepareDays();j=j+1)
                {
                    aux = Calendar.getInstance();
                    aux.setTime(dataaux);

                    int day = aux.get( Calendar.DAY_OF_MONTH );
                    int month = aux.get( Calendar.MONTH );
                    int year = aux.get( Calendar.YEAR );

                    aux.set( year, month, day - 1, 0, 0, 0 );

                    dataaux = new Timestamp(aux.getTimeInMillis());

                    aux = Calendar.getInstance();
                    aux.setTime(dataaux);
                    disabledays.add(aux);
                }

                dataaux = rents.get(i).getStartDate();
                while (dataaux.before(rents.get(i).getEndDate()))
                {
                    aux = Calendar.getInstance();
                    aux.setTime(dataaux);

                    int day = aux.get(Calendar.DAY_OF_MONTH);
                    int month = aux.get(Calendar.MONTH);
                    int year = aux.get(Calendar.YEAR);

                    aux.set(year, month, day + 1, 0, 0, 0);

                    dataaux = new Timestamp(aux.getTimeInMillis());

                    aux = Calendar.getInstance();
                    aux.setTime(dataaux);
                    disabledays.add(aux);
                }

                dataaux = rents.get(i).getEndDate();
                for(int j=0;j<rents.get(i).getDress().getWashingDays();j=j+1)
                {
                    aux = Calendar.getInstance();
                    aux.setTime(dataaux);

                    int day = aux.get( Calendar.DAY_OF_MONTH );
                    int month = aux.get( Calendar.MONTH );
                    int year = aux.get( Calendar.YEAR );

                    aux.set( year, month, day + 1, 0, 0, 0 );

                    dataaux = new Timestamp(aux.getTimeInMillis());

                    aux = Calendar.getInstance();
                    aux.setTime(dataaux);
                    disabledays.add(aux);
                }
            }
        }
        return disabledays;
    }
}


