package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoqueryreturnordersData;
import com.fdw.fdd.tool.GlideUtils;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/8 20:37
 * Email：1511808259@qq.com
 */
public class AfterSaleAdapter extends BaseQuickAdapter<DoqueryreturnordersData, BaseViewHolder> {
    public AfterSaleAdapter(int layoutResId, @Nullable List<DoqueryreturnordersData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoqueryreturnordersData item) {
        helper.addOnClickListener(R.id.m_cv_item);
        // 店铺名称
        helper.setText(R.id.m_tv_store, "  " + item.getShop().getName() + " ");
        // 换货中、仅退款，退款成功
        helper.setText(R.id.m_tv_refunding, item.getRefunding());

        GlideUtils.loadImage(mContext, item.getReturnDetailsList().getSpecification().getSpecificationImage(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));

        // 商品内容
        helper.setText(R.id.m_tv_content, item.getReturnDetailsList().getCommodity().getName());
        // 商品数量
        helper.setText(R.id.m_tv_number, "x " + item.getReturnDetailsList().getReturnOrderDetails().getNumber());
        // 换成
        if (item.getReturnDetailsList().getExchange() != null) {
            helper.setText(R.id.m_tv_sale_name, "换成：" + item.getReturnDetailsList().getExchange().getCommoditySpecification());
        }
    }
}
