package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/9 16:52
 * Email：1511808259@qq.com
 */
public class OrderDetails03Adapter extends BaseQuickAdapter<OrderDetailsBean, BaseViewHolder> {
    public OrderDetails03Adapter(int layoutResId, @Nullable List<OrderDetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailsBean item) {
        helper.addOnClickListener(R.id.m_tv_refund);

        // 选择商品图片
        GlideUtils.loadImage(mContext, item.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        //商品名称
        helper.setText(R.id.m_tv_content, item.getCommodity().getName());
        // 商品规格
        helper.setText(R.id.m_tv_specification, item.getSpecification().getCommoditySpecification());
        // 选择商品数量+价格
        helper.setText(R.id.m_tv_price_num, DoubleUtil.double2Str(item.getOrderDetail().getPrice()) + "积分 x" + item.getOrderDetail().getNumber());
        switch (item.getRefunding()) {
            case "等待商家处理换货申请":
                helper.setText(R.id.m_tv_refund, "售后处理中");
                break;
            case "":
                helper.setText(R.id.m_tv_refund, "退换");
                break;
        }
    }
}
