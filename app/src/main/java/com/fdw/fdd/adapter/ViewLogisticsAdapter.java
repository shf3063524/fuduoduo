package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoqueryorderexpressesData;
import com.fdw.fdd.entity.bean.SpecificationsBean;
import com.fdw.fdd.tool.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/10 22:01
 * Email：1511808259@qq.com
 */
public class ViewLogisticsAdapter extends BaseQuickAdapter<DoqueryorderexpressesData, BaseViewHolder> {
    public ViewLogisticsAdapter(int layoutResId, @Nullable List<DoqueryorderexpressesData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoqueryorderexpressesData item) {
        // 物流状态
        helper.setText(R.id.m_tv_freightState, item.getFreightState());
        // 快递名称
        helper.setText(R.id.m_tv_freightCompany, item.getFreight().getFreightCompany() + ":" + item.getFreight().getFreightCode());
        // 最后收货地址
        helper.setText(R.id.m_tv_address, item.getFreightData().getContext());
        // 商品图片
        ArrayList<SpecificationsBean> images = item.getImages();
        if (images != null && images.size() > 0) {
            GlideUtils.loadImage(mContext, item.getImages().get(0).getSpecificationImage(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        }
    }
}
