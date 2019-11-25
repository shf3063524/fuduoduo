package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.BestSellingData;

import java.util.List;

public class HotListAdapter extends BaseQuickAdapter<BestSellingData, BaseViewHolder> {
    public HotListAdapter(int layoutResId, @Nullable List<BestSellingData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BestSellingData item) {
        helper.setText(R.id.m_tv_name, item.getName());
    }
}