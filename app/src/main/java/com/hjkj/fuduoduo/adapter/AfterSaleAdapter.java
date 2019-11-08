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
        helper.setText(R.id.m_tv_store,"  " + item.getShop().getName() + " ");
        // 换货中、仅退款，退款成功
        helper.setText(R.id.m_tv_refunding,item.getRefunding());


        ArrayList<TestBean> mTypeData = new ArrayList<>();
        initRecyclerView(helper, mTypeData);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView(BaseViewHolder helper, ArrayList<TestBean> dataList) {
        AfterSaleListAdapter mAdapter = new AfterSaleListAdapter(R.layout.item_after_sale_list, dataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setLayoutManager(layoutManager);
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setAdapter(mAdapter);
        dataList.clear();
        for (int i = 0; i < 2; i++) {
            dataList.add(new TestBean("item" + i));
        }
        mAdapter.notifyDataSetChanged();
    }
}
