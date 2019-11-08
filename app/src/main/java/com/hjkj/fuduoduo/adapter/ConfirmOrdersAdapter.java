package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.bean.CommoditiesBeans;
import com.hjkj.fuduoduo.entity.bean.ShopBean;
import com.hjkj.fuduoduo.entity.bean.SpecBean;
import com.hjkj.fuduoduo.entity.bean.SpecificationBeans;
import com.hjkj.fuduoduo.tool.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrdersAdapter extends BaseQuickAdapter<CommoditiesBeans, BaseViewHolder> {
    public ConfirmOrdersAdapter(int layoutResId, @Nullable List<CommoditiesBeans> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommoditiesBeans item) {
        ArrayList<SpecBean> typeData = new ArrayList<>();
        // 店铺名称
        ShopBean shop = item.getShop();
        helper.setText(R.id.m_tv_store, "  " + shop.getName() + " ");
        // 运费
        helper.setText(R.id.m_tv_freight,item.getFreightPrice() + "积分");
        // 共几件
        ArrayList<SpecBean> specifications = item.getSpecifications();
        typeData.addAll(specifications);
        helper.setText(R.id.m_tv_number, "共" + specifications.size() + "件");

        // 小计
        ArrayList<Double> subtotal = new ArrayList<>();
        double d = 0.00;
        for (SpecBean specification : specifications) {
            SpecificationBeans specification1 = specification.getSpecification();
            String number = specification.getNumber();
            String price = specification1.getSalePrice();
            Double mul = DoubleUtil.mul(Double.parseDouble(number), Double.parseDouble(price));
            subtotal.add(mul);
        }
        for (int i = 0; i < subtotal.size(); i++) {
            d = subtotal.get(i) + d;
        }
        helper.setText(R.id.m_tv_integral, DoubleUtil.double2Str(String.valueOf(d)));



        initRecyclerView(helper, typeData);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView(BaseViewHolder helper, ArrayList<SpecBean> dataList) {
        ConfirmOrderListAdapter mAdapter = new ConfirmOrderListAdapter(R.layout.item_confirm_orders_list, dataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setLayoutManager(layoutManager);
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }
}



