package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnordersData;
import com.hjkj.fuduoduo.entity.bean.ReturnDetailsListBean;
import com.hjkj.fuduoduo.tool.GlideUtils;

import java.util.ArrayList;
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
        // 商品图片
        GlideUtils.loadImage(mContext, item.getReturnDetailsList().getSpecification().getSpecificationImage(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        // 商品内容
        helper.setText(R.id.m_tv_content, item.getReturnDetailsList().getCommodity().getName());
        // 商品数量
        helper.setText(R.id.m_tv_number, "x " + item.getReturnDetailsList().getReturnOrderDetails().getNumber());
        // 换成
        if (item.getReturnDetailsList().getExchange() != null) {
            helper.setText(R.id.m_tv_sale_name,"换成：" + item.getReturnDetailsList().getExchange().getCommoditySpecification());
        }
    }
}
