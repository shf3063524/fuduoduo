package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;

import java.util.List;

public class NoReviewAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    public NoReviewAdapter(int layoutResId, @Nullable List<TestBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {

    }
}

