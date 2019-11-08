package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryCollectionsStoreData;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;

/**
 * Author：Created by shihongfei on 2019/10/6 10:07
 * Email：1511808259@qq.com
 */
public class MyCollectionStoreAdapter extends BaseItemDraggableAdapter<DoQueryCollectionsStoreData, BaseViewHolder> {
    /**
     * 当前处于打开状态的item
     */
    private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();
    public MyCollectionStoreAdapter(int layoutResId, @Nullable List<DoQueryCollectionsStoreData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryCollectionsStoreData item) {
        BGASwipeItemLayout mSwipeItemLayout = (BGASwipeItemLayout) helper.getView(R.id.m_swipe_item_layout);
        // 商店名称
        helper.setText(R.id.m_tv_store,item.getName());
        // 商店logo



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
        helper.addOnClickListener(R.id.m_cv_store);
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