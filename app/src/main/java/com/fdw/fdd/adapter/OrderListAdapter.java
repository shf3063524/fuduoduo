package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.OrderDetailsBean;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.GlideUtils;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/3 21:18
 * Email：1511808259@qq.com
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderDetailsBean, BaseViewHolder> {
    public OrderListAdapter(int layoutResId, @Nullable List<OrderDetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailsBean item) {
        // 商品图片
        GlideUtils.loadImage(mContext, item.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        // 商品名称
        helper.setText(R.id.m_tv_content, item.getCommodity().getName());
        // 商品规格
        helper.setText(R.id.m_tv_specification, item.getSpecification().getCommoditySpecification());
        // 积分+数量
        helper.setText(R.id.m_tv_prce_num, DoubleUtil.double2Str(item.getOrderDetail().getPrice()) + " 积分" + "x" + item.getOrderDetail().getNumber());
        // 商品状态
        helper.setText(R.id.m_tv_refunding, item.getRefunding());
    }
}