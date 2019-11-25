package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoQueryTransactionRecordData;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/9/28 21:51
 * Email：1511808259@qq.com
 */
public class FudouBanlanceAdapter extends BaseQuickAdapter<DoQueryTransactionRecordData, BaseViewHolder> {
    public FudouBanlanceAdapter(int layoutResId, @Nullable List<DoQueryTransactionRecordData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryTransactionRecordData item) {
        // 内容
        helper.setText(R.id.m_tv_pay_name, item.getDescription());
        //时间
        helper.setText(R.id.m_tv_time, item.getDealTime());
        // 0花&&1充
        if (item.getType().equals("1")) {
            helper.setText(R.id.m_tv_integral, "+" + item.getPrice());
        } else {
            helper.setText(R.id.m_tv_integral, "-" + item.getPrice());
        }
    }
}


