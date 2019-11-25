package com.fdw.fdd.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoFindCategoryData;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/9/26 17:00
 * Email：1511808259@qq.com
 */
public class ProductTypeAdapter extends BaseQuickAdapter<DoFindCategoryData, BaseViewHolder> {
    public ProductTypeAdapter(int layoutResId, @Nullable List<DoFindCategoryData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoFindCategoryData item) {
        // 类目名称
        helper.setText(R.id.m_tv_type, item.getNickName());

        // 判断改变属性
        if (item.isClickable()) {
            helper.setTextColor(R.id.m_tv_type, Color.parseColor("#FFFFFF"));
            helper.setBackgroundRes(R.id.m_layout_status, R.drawable.shape_frame11_cl_e51c23);
        } else {
            helper.setTextColor(R.id.m_tv_type, Color.parseColor("#666666"));
            helper.setBackgroundRes(R.id.m_layout_status, R.drawable.shape_frame11_cl_fff);
        }
    }
}