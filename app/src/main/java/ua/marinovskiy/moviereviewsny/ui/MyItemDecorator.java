package ua.marinovskiy.moviereviewsny.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Alex on 06.03.2016.
 */
public class MyItemDecorator extends RecyclerView.ItemDecoration {

    private int mSpace;
    private int mGridSize;

    public MyItemDecorator(int space, int gridSize) {
        mSpace = space;
        mGridSize = gridSize;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        int itemSize = parent.getChildAt(0).getWidth();
        int padding = (parent.getWidth() - (itemSize * 3)) / 4;
        //int myPadding = view.getContext().getResources().getDimensionPixelSize(padding);
        String s = "mylogtag";
        Log.i(s, "getItemOffsets: itemSize" + itemSize);
        Log.i(s, "getItemOffsets: parentSize" + parent.getWidth());
        Log.i(s, "getItemOffsets: padding" + padding);
        if (itemPosition % mGridSize == 0) {
            outRect.left = mSpace;
            outRect.right = mSpace / 2;
        } else if (itemPosition % mGridSize == 1) {
            outRect.left = mSpace / 2;
            outRect.right = mSpace / 2;
        } else if (itemPosition % mGridSize == 2) {
            outRect.left = mSpace / 2;
            outRect.right = padding;
        }

//        outRect.left = mSpace;
//        outRect.right = mSpace;
//        outRect.bottom = mSpace;

//        // Add top margin only for the first item to avoid double space between items
//        if (parent.getChildLayoutPosition(view) == 0) {
//            outRect.top = mSpace;
//        } else {
//            outRect.top = 0;
//        }
    }
}
