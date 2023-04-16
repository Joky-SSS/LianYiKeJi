package com.lianyi.app.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
  * @ClassName:      SpacesItemDecoration
  * @Description:     recycleview 垂直 间距
  * @Author:         Lee
  * @CreateDate:     2020/6/28 15:17
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildPosition(view) != -1) {
//            outRect.top = space;
            outRect.bottom = space;
            outRect.left = space;
            outRect.right = space;
        }

    }
}