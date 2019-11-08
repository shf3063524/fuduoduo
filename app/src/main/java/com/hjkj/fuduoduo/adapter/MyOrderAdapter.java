package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryOrdersDetailsData;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
import com.hjkj.fuduoduo.entity.bean.ShopBean;
import com.hjkj.fuduoduo.tool.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/3 17:26
 * Email：1511808259@qq.com
 */
public class MyOrderAdapter extends BaseQuickAdapter<DoQueryOrdersDetailsData, BaseViewHolder> {
    public MyOrderAdapter(int layoutResId, @Nullable List<DoQueryOrdersDetailsData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryOrdersDetailsData item) {
        ArrayList<OrderDetailsBean> mTypeData = new ArrayList<>();
        // 店铺名称
        ShopBean shop = item.getShop();
        helper.setText(R.id.m_tv_store, "  " + shop.getName() + " ");
        // 商品状态
        switch (item.getOrder().getSaleState()) {
            case "1":
                helper.setText(R.id.m_tv_shopping_status, "等待买家付款");
                helper.setGone(R.id.m_tv_one,true);
                helper.setGone(R.id.m_tv_two,true);
                helper.setGone(R.id.m_tv_three,true);
                helper.setGone(R.id.m_tv_four,false);
                helper.setGone(R.id.m_tv_five,false);
                helper.setGone(R.id.m_tv_six,false);
                helper.setGone(R.id.m_tv_seven,false);
                helper.setGone(R.id.m_tv_eight,false);
                break;
            case "2":
                helper.setText(R.id.m_tv_shopping_status, "等待卖家发货");
                helper.setGone(R.id.m_tv_one,false);
                helper.setGone(R.id.m_tv_two,false);
                helper.setGone(R.id.m_tv_three,false);
                helper.setGone(R.id.m_tv_four,true);
                helper.setGone(R.id.m_tv_five,false);
                helper.setGone(R.id.m_tv_six,false);
                helper.setGone(R.id.m_tv_seven,false);
                helper.setGone(R.id.m_tv_eight,false);
                break;
            case "3":
                helper.setText(R.id.m_tv_shopping_status, "卖家已发货");
                helper.setGone(R.id.m_tv_one,false);
                helper.setGone(R.id.m_tv_two,false);
                helper.setGone(R.id.m_tv_three,false);
                helper.setGone(R.id.m_tv_four,false);
                helper.setGone(R.id.m_tv_five,true);
                helper.setGone(R.id.m_tv_six,true);
                helper.setGone(R.id.m_tv_seven,false);
                helper.setGone(R.id.m_tv_eight,false);
                break;
            case "4":
                helper.setText(R.id.m_tv_shopping_status, "交易完成");
                helper.setGone(R.id.m_tv_one,false);
                helper.setGone(R.id.m_tv_two,false);
                helper.setGone(R.id.m_tv_three,false);
                helper.setGone(R.id.m_tv_four,false);
                helper.setGone(R.id.m_tv_five,false);
                helper.setGone(R.id.m_tv_six,false);
                helper.setGone(R.id.m_tv_seven,true);
                helper.setGone(R.id.m_tv_eight,true);
                break;
        }
        // 退款中，待处理等等
        helper.setText(R.id.m_tv_refunding,item.getRefunding());
        //共几件商品
        helper.setText(R.id.m_tv_number, "共" + item.getOrderDetails().size() + "件商品");
        // 合计
        helper.setText(R.id.m_tv_total_price, DoubleUtil.double2Str(item.getOrder().getActualPrice()) + "积分");

        helper.addOnClickListener(R.id.m_cv_item);
        helper.addOnClickListener(R.id.m_layout_store);
        helper.addOnClickListener(R.id.m_tv_one);
        helper.addOnClickListener(R.id.m_tv_two);
        helper.addOnClickListener(R.id.m_tv_three);
        helper.addOnClickListener(R.id.m_tv_four);
        helper.addOnClickListener(R.id.m_tv_five);
        helper.addOnClickListener(R.id.m_tv_six);
        helper.addOnClickListener(R.id.m_tv_seven);
        helper.addOnClickListener(R.id.m_tv_eight);

        //商品相关
        ArrayList<OrderDetailsBean> orderDetails = item.getOrderDetails();
        mTypeData.addAll(orderDetails);
        initRecyclerView(helper, mTypeData);
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

