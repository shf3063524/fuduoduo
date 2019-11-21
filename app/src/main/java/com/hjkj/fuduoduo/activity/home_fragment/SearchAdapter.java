package com.hjkj.fuduoduo.activity.home_fragment;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<DoFindMaybeYouLikeData, BaseViewHolder> {
    public SearchAdapter(int layoutResId, @Nullable List<DoFindMaybeYouLikeData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoFindMaybeYouLikeData item) {
        helper.setText(R.id.m_tv_name, item.getCommodity().getName());
    }
}