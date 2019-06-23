package com.example.slatielly.app.dress;

import com.example.slatielly.model.Dress;
import com.example.slatielly.model.User;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DressPresenter implements DressContract.Presenter, OnSuccessListener<Dress> {

    private DressContract.View view;
    private FirestoreRepository<Dress> repository;

    public DressPresenter(DressContract.View view, FirestoreRepository<Dress> repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadDress(String id) {
        this.repository
                .get(id)
                .addOnSuccessListener(this);
    }

    @Override
    public void onSuccess(final Dress dress)
    {
        final DressContract.View view2 = view;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                Boolean state = true;

                User currentUser = documentSnapshot.toObject(User.class);

                view2.setDressViews(dress,currentUser);
                view2.setScreenSlideAdapter(dress.getImages());
            }
        });
    }
}
