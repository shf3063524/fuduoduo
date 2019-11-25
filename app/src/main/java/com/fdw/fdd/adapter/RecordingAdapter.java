package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoQueryTransactionRecordData;
import com.fdw.fdd.tool.DoubleUtil;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/9/29 14:21
 * Email：1511808259@qq.com
 */
public class RecordingAdapter extends BaseQuickAdapter<DoQueryTransactionRecordData, BaseViewHolder> {
    public RecordingAdapter(int layoutResId, @Nullable List<DoQueryTransactionRecordData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryTransactionRecordData item) {
        // 0花&&1充
        if (item.getType().equals("1")) {
            helper.setImageResource(R.id.m_iv_flower, R.mipmap.ic_full);
            helper.setText(R.id.m_tv_integral, "+" + DoubleUtil.double2Str(item.getPrice()));
        } else {
            helper.setImageResource(R.id.m_iv_flower, R.mipmap.ic_flower);
            helper.setText(R.id.m_tv_integral, "-" + DoubleUtil.double2Str(item.getPrice()));
        }
        // 内容
        helper.setText(R.id.m_tv_pay_name, item.getDescription());
        //时间
        helper.setText(R.id.m_tv_time, item.getDealTime());
    }
}

