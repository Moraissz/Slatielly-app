package com.example.slatielly.app.dress.dresses;

import com.example.slatielly.model.Dress;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface DressesContract {
    interface View {}

    interface Presenter {
        FirestoreRecyclerOptions<Dress> getRecyclerOptions();
    }
}
