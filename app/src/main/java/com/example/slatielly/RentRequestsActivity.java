package com.example.slatielly;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.example.slatielly.Model.Dress;
import com.example.slatielly.Model.Person;
import com.example.slatielly.Model.Rent;
import com.example.slatielly.view.RentRequest.RentRequestAdapter;

import java.util.ArrayList;

public class RentRequestsActivity extends AppCompatActivity  implements RentRequestAdapter.rentRequestListener {

    private RecyclerView recyclerView;
    private RentRequestAdapter adapter;
    private ArrayList<Rent> rentArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_requests);

        Toolbar toolbar = (Toolbar) findViewById(R.id.RentRequestToolbar);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.RRRecyclerView);
        rentArrayList = new ArrayList<>();
        //Preencher ArrayList
        for (int i = 0; i < 20;i++){
            Rent rent = new Rent(i,new Dress(i,"Vestido Doidao","Para doidos","R$ 70,00"),
                    new Person(i,"Lucas","Avenida dos doido","333333"),"30-02-2020","31-02-2021","Pendente");
            if(i > 10){
                rent.setStatus("Concluido");
            }
            rentArrayList.add(rent);
        }
        System.out.println(rentArrayList.get(0));
        adapter = new RentRequestAdapter(rentArrayList,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public void onClickRentRequestListener(Rent rent) {

    }
}
