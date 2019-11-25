package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.entity.TestBean;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/10 11:35
 * Email：1511808259@qq.com
 */
public class RefundDetailsAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    public RefundDetailsAdapter(int layoutResId, @Nullable List<TestBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
    }
}

