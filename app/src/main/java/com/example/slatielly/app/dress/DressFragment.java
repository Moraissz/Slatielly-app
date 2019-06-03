package com.example.slatielly.app.dress;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.slatielly.R;
import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Image;
import com.example.slatielly.model.repository.FirestoreRepository;

import java.util.ArrayList;

public class DressFragment extends Fragment implements DressContract.View {

    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private TextView textView_description_fragment_screen_slide_dress;
    private TextView textView_size_fragment_screen_slide_dress;
    private TextView textView_color_fragment_screen_slide_dress;
    private TextView textView_price_fragment_screen_slide_dress;
    private TextView textView_type_fragment_screen_slide_dress;
    private TextView textView_material_fragment_screen_slide_dress;
    private DressContract.Presenter presenter;

    public static DressFragment newInstance(String id) {
        DressFragment dressFragment = new DressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        dressFragment.setArguments(bundle);
        return dressFragment;
    }

    public DressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirestoreRepository<Dress> repository = new FirestoreRepository<>(Dress.class, "dresses");
        this.presenter = new DressPresenter(this, repository);

        this.setupViews(view);
        mPager = view.findViewById(R.id.pager_dress_fragment_screen_slide);

        if (this.getArguments() != null) {
            String id = this.getArguments().getString("id");
            this.presenter.loadDress(id);
        }
    }

    private void setupViews(View view) {
        textView_description_fragment_screen_slide_dress = view.findViewById(R.id.textView_description_fragment_screen_slide_dress);
        textView_size_fragment_screen_slide_dress = view.findViewById(R.id.textView_size_fragment_screen_slide_dress);
        textView_color_fragment_screen_slide_dress = view.findViewById(R.id.textView_color_fragment_screen_slide_dress);
        textView_price_fragment_screen_slide_dress = view.findViewById(R.id.textView_price_fragment_screen_slide_dress);
        textView_type_fragment_screen_slide_dress = view.findViewById(R.id.textView_type_fragment_screen_slide_dress);
        textView_material_fragment_screen_slide_dress = view.findViewById(R.id.textView_material_fragment_screen_slide_dress);
    }

    @Override
    public void setDressViews(Dress dress) {
        textView_description_fragment_screen_slide_dress.setText(dress.getDescription());
        textView_size_fragment_screen_slide_dress.setText(dress.getSize());
        textView_color_fragment_screen_slide_dress.setText(dress.getColor());
        textView_price_fragment_screen_slide_dress.setText(dress.getPrice().toString());
        textView_type_fragment_screen_slide_dress.setText(dress.getType());
        textView_material_fragment_screen_slide_dress.setText(dress.getMaterial());
    }

    @Override
    public void setScreenSlideAdapter(ArrayList<Image> images) {
        pagerAdapter = new ScreenSlideAdapter(this.getFragmentManager(), images);
        mPager.setAdapter(pagerAdapter);
    }
}
