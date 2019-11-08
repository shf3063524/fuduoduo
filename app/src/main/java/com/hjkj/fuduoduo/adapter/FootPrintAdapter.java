package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.CommoditiesBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryCollectionsShopData;
import com.hjkj.fuduoduo.entity.bean.DoQueryFootprintData;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/7 22:07
 * Email：1511808259@qq.com
 */
public class FootPrintAdapter extends BaseQuickAdapter<DoQueryFootprintData, BaseViewHolder> {
    private boolean isShow = false;
    private boolean isAllCheck = false;

    public FootPrintAdapter(int layoutResId, @Nullable List<DoQueryFootprintData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryFootprintData item) {
        // 日期
        helper.setText(R.id.m_tv_date, item.getFootprint());
        ArrayList<CommoditiesBean> mTypeData = new ArrayList<>();
        mTypeData.clear();
        mTypeData.addAll(item.getCommodities());
        // 按钮显示与不显示状态
        if (isShow) {
            helper.setGone(R.id.m_iv_date, true);
        } else {
            helper.setGone(R.id.m_iv_date, false);
        }
        initRecyclerView(helper, mTypeData, isShow);
    }

    /**
     * 按钮显示与不显示
     */
    public void setVisibleCheckBox(boolean isShow) {
        this.isShow = isShow;
        notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView(BaseViewHolder helper, ArrayList<CommoditiesBean> dataList, boolean isShow) {
        FootPrintTypelAdapter mAdapter = new FootPrintTypelAdapter(R.layout.item_foot_print_type, dataList, isShow);
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setLayoutManager(new GridLayoutManager(mContext, 3));
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setAdapter(mAdapter);

        ArrayList<CommoditiesBean> tempList = mAdapter.getCheckIdLists();
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < tempList.size(); i++) {
            CommoditiesBean doQueryCollectionsShopData = tempList.get(i);
            if (i != tempList.size() - 1) {
                ids.append(doQueryCollectionsShopData.getId()).append(",");
            } else {
                ids.append(doQueryCollectionsShopData.getId());
            }
        }
        String memberIds = ids.toString().trim();


        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_iv_shopping:
                        ProductDetailsActivity.openActivity(mContext,dataList.get(position).getId());
                        break;
                }
            }
        });
        helper.getView(R.id.m_iv_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 按钮选中与未选中状态
                if (isAllCheck) {
                    isAllCheck = false;
                    helper.setImageResource(R.id.m_iv_date, R.drawable.ic_no_check);
                } else {
                    isAllCheck = true;
                    helper.setImageResource(R.id.m_iv_date, R.drawable.ic_yes_check);
                }
                // 修改数据源
                for (CommoditiesBean data : dataList) {
                    data.setClickable(isAllCheck);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.notifyDataSetChanged();
    }
}

