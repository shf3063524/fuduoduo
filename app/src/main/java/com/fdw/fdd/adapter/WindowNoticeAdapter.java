package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.TestBean;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;

public class WindowNoticeAdapter extends BaseItemDraggableAdapter<TestBean, BaseViewHolder> {
    /**
     * 当前处于打开状态的item
     */
    private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();
    public WindowNoticeAdapter(int layoutResId, @Nullable List<TestBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        BGASwipeItemLayout mSwipeItemLayout = (BGASwipeItemLayout) helper.getView(R.id.m_swipe_item_layout);
        // if (Integer.parseInt(item.getHasUse()) > 0) {
        //     mSwipeItemLayout.setSwipeAble(false);
        // } else {
        mSwipeItemLayout.setSwipeAble(true);
        // }
        mSwipeItemLayout.setDelegate(new BGASwipeItemLayout.BGASwipeItemLayoutDelegate() {
            @Override
            public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout) {
                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
            }
        });

        helper.addOnClickListener(R.id.m_iv_delete);
        helper.addOnClickListener(R.id.m_layout_item);
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (BGASwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }

    public boolean isExistOpenItem() {
        if (!mOpenedSil.isEmpty()) {
            return true;
        }
        return false;
    }
}

