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
 * Author：Created by shihongfei on 2019/10/9 13:40
 * Email：1511808259@qq.com
 */
public class OrderDetailsAdapter extends BaseQuickAdapter<OrderDetailsBean, BaseViewHolder> {
    public OrderDetailsAdapter(int layoutResId, @Nullable List<OrderDetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailsBean item) {
        // 选择商品图片
        GlideUtils.loadImage(mContext, item.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        //商品名称
        helper.setText(R.id.m_tv_content, item.getCommodity().getName());
        // 商品规格
        helper.setText(R.id.m_tv_specification, item.getSpecification().getCommoditySpecification());
        // 选择商品数量+价格
        helper.setText(R.id.m_tv_price_num, DoubleUtil.double2Str(item.getOrderDetail().getPrice()) + "积分 x" + item.getOrderDetail().getNumber());
    }
}
