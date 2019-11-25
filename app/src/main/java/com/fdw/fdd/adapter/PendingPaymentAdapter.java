package com.fdw.fdd.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.bean.OrderDetailsBean;
import com.fdw.fdd.entity.bean.ShopBean;
import com.fdw.fdd.tool.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/8 19:48
 * Email：1511808259@qq.com
 */
public class PendingPaymentAdapter extends BaseQuickAdapter<DoQueryOrdersDetailsData, BaseViewHolder> {
    private boolean singleSelect = false;

    public PendingPaymentAdapter(int layoutResId, @Nullable List<DoQueryOrdersDetailsData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryOrdersDetailsData item) {
        ArrayList<OrderDetailsBean> mTypeData = new ArrayList<>();
        // 店铺名称
        ShopBean shop = item.getShop();
        helper.setText(R.id.m_tv_store, "  " + shop.getName() + " ");
        // 根据选中状态 更改UI
        final boolean check = item.isClickable();
        if (check) {
            helper.setImageResource(R.id.m_iv_no_check, R.drawable.ic_yes_check);
        } else {
            helper.setImageResource(R.id.m_iv_no_check, R.drawable.ic_no_check);
        }
        helper.getView(R.id.m_iv_no_check).setEnabled(true);
        helper.getView(R.id.m_iv_no_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!singleSelect) {
                    if (!check) {
                        item.setClickable(true);
                    } else {
                        item.setClickable(false);
                    }
                } else {
                    for (DoQueryOrdersDetailsData listData02 : mData) {
                        listData02.setClickable(false);
                    }
                    item.setClickable(true);
                }
                notifyDataSetChanged();
            }
        });

        //共几件商品
        helper.setText(R.id.m_tv_number, "共" + item.getOrderDetails().size() + "件商品");
        // 合计
        helper.setText(R.id.m_tv_total_price, DoubleUtil.double2Str(item.getOrder().getActualPrice()) + "积分");
        //商品相关
        ArrayList<OrderDetailsBean> orderDetails = item.getOrderDetails();
        mTypeData.addAll(orderDetails);
        initRecyclerView(helper, mTypeData);

        helper.addOnClickListener(R.id.m_cv_item);
        helper.addOnClickListener(R.id.m_layout_store);
        helper.addOnClickListener(R.id.m_tv_one);
        helper.addOnClickListener(R.id.m_tv_two);
        helper.addOnClickListener(R.id.m_tv_three);
    }

    /**
     * 设置单选
     */
    public void setSingleSelect(boolean singleSelect) {
        this.singleSelect = singleSelect;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView(BaseViewHolder helper, ArrayList<OrderDetailsBean> dataList) {
        OrderListAdapter mAdapter = new OrderListAdapter(R.layout.item_order_list, dataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setLayoutManager(layoutManager);
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setAdapter(mAdapter);
        // 屏蔽内嵌recyclerview的点击事件
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return helper.getView(R.id.m_cv_item).onTouchEvent(event);
            }
        });

        mAdapter.notifyDataSetChanged();
    }
}
