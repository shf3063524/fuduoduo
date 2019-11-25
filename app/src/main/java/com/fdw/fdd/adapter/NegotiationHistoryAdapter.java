package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoqueryconsultData;
import com.fdw.fdd.tool.GlideUtils;

import java.util.List;

public class NegotiationHistoryAdapter extends BaseQuickAdapter<DoqueryconsultData, BaseViewHolder> {
    public NegotiationHistoryAdapter(int layoutResId, @Nullable List<DoqueryconsultData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoqueryconsultData item) {
        // 头像
        if ("0".equals(item.getConsult().getType())) {
            GlideUtils.loadCircleHeadImage(mContext, item.getConsumer().getLogo(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_head));
            helper.setText(R.id.m_tv_name, item.getConsumer().getName());
        } else {
            GlideUtils.loadCircleHeadImage(mContext, item.getConsumer().getLogo(), R.mipmap.ic_my_logo, helper.getView(R.id.m_iv_head));
            helper.setText(R.id.m_tv_name, "系统");
        }
        // 时间
        helper.setText(R.id.m_tv_time,item.getConsult().getCreateTime());
        // 内容
        helper.setText(R.id.m_tv_content,item.getConsult().getDescription());
    }
}
