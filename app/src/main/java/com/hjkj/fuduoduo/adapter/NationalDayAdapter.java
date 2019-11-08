package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.entity.TestBean;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/9/26 10:24
 * Email：1511808259@qq.com
 */
public class NationalDayAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    public NationalDayAdapter(int layoutResId, @Nullable List<TestBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {

    }
}

