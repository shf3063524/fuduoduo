package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.entity.TestBean;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/10 22:01
 * Email：1511808259@qq.com
 */
public class ViewLogisticsAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    public ViewLogisticsAdapter(int layoutResId, @Nullable List<TestBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
    }
}
