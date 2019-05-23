package com.example.slatielly.view;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    public static int dpToPx(int dp, Resources r) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

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
