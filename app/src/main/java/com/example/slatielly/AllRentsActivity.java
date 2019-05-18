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

import com.example.slatielly.Model.Rent;
import com.example.slatielly.Model.Dress;
import com.example.slatielly.Model.Person;
import com.example.slatielly.view.Rent.RentAdapter;

import java.util.ArrayList;

public class AllRentsActivity extends AppCompatActivity implements RentAdapter.rentListener {
    private RecyclerView recyclerView;
    private RentAdapter adapter;
    private ArrayList<Rent> rentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        Toolbar toolbar = (Toolbar) findViewById(R.id.allBooksToolbar);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.allBooksRecyclerView);
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
        adapter = new RentAdapter(rentArrayList,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClickRentListener(Rent rent) {

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


}
