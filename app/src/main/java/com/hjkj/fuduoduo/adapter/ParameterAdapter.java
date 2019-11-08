package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.AttributesBean;

import java.util.List;

public class ParameterAdapter extends BaseQuickAdapter<AttributesBean, BaseViewHolder> {
    public ParameterAdapter(int layoutResId, @Nullable List<AttributesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttributesBean item) {
        // 标题
        helper.setText(R.id.m_tv_attribute, item.getAttribute() + ":");
        // 内容
        helper.setText(R.id.m_tv_value, item.getValue());
    }
}

