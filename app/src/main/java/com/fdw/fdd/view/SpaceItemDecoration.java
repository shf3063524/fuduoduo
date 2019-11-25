package com.fdw.fdd.view;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置横向RecyclerView间距类
 * Author：Created by shihongfei on 2019/9/30 10:47
 * Email：1511808259@qq.com
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    private int spanCount; //列数
    private int spacing; //间隔
    private boolean includeEdge; //是否包含边缘

    public SpaceItemDecoration(int spanCount, int space, boolean includeEdge) {
        this.spacing = space;
        this.spanCount = spanCount;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = spacing;
        //获取当前Item的位置
        int position = parent.getChildAdapterPosition(view); // item position
        //判断奇偶位置，然后进行相应的赋值运算
        int column = position % spanCount; // item column
        if (column == 0){
            outRect.left = spacing;
            outRect.right = spacing /spanCount;
        }else if (column == 1){
            outRect.left = spacing /spanCount;
            outRect.right = spacing;
        }

    }
}
