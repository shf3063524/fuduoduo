package com.hjkj.fuduoduo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.home_fragment.HomeSearchActivity;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.entity.bean.BestSellingData;
import com.hjkj.fuduoduo.entity.bean.DoqueryassociationData;

import java.util.ArrayList;
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