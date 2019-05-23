package com.example.slatielly;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.Model.Dress;
import com.example.slatielly.view.Dress.DressAdapter;

import java.util.ArrayList;


public class AllDressesFragment extends Fragment implements DressAdapter.DressListener {

    private RecyclerView recyclerView;
    private DressAdapter adapter;
    private ArrayList<Dress> dressArrayList;

    public AllDressesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_dresses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.recyclerView = this.getView().findViewById(R.id.recycler_view);
        this.dressArrayList = new ArrayList<>();

        //Preencher array
        for (int i = 0; i < 20; i++) {
            Dress dress = new Dress(i, "Vestido para Casamento", "Tipo " + i, "R$ 50,00");
            this.dressArrayList.add(dress);
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        this.recyclerView.setLayoutManager(mLayoutManager);
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.adapter = new DressAdapter(this.dressArrayList, this);
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void onClickDressListener(Dress dress) {

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % this.spanCount; // item column

            if (this.includeEdge) {
                outRect.left = this.spacing - column * this.spacing / this.spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * this.spacing / this.spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < this.spanCount) { // top edge
                    outRect.top = this.spacing;
                }
                outRect.bottom = this.spacing; // item bottom
            } else {
                outRect.left = column * this.spacing / this.spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = this.spacing - (column + 1) * this.spacing / this.spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= this.spanCount) {
                    outRect.top = this.spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
