package com.example.slatielly.app.dress.dresses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.repository.FirestoreRepository;
import com.example.slatielly.view.dress.DressAdapter;
import com.example.slatielly.view.GridSpacingItemDecoration;


public class DressesFragment extends Fragment implements DressAdapter.DressListener, DressesContract.View {

    private RecyclerView recyclerView;
    private DressAdapter adapter;
    private DressesContract.Presenter presenter;
    private OnNavigationListener listener;

    public DressesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dresses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirestoreRepository<Dress> firestoreRepository = new FirestoreRepository<>(Dress.class, Dress.DOCUMENT_NAME);
        this.presenter = new DressesPresenter(this, firestoreRepository);

        this.recyclerView = view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        this.recyclerView.setLayoutManager(mLayoutManager);

        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.adapter = new DressAdapter(this.presenter.getRecyclerOptions(), this);
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void onClickDressListener(Dress dress) {
        this.listener.onNavigateToDress(dress.getId());
    }

    public void setNavigationListener(OnNavigationListener listener) {
        this.listener = listener;
    }

    public interface OnNavigationListener {
        void onNavigateToDress(String id);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.adapter.stopListening();
    }
}
