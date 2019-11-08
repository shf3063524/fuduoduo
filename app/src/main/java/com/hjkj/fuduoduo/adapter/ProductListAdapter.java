package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.sort_fragment.ClassifiedSearchActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryCategoryDetailsData;
import com.hjkj.fuduoduo.entity.bean.ParentBean;
import com.hjkj.fuduoduo.entity.bean.SonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Created by shihongfei on 2019/9/26 17:01
 * Email：1511808259@qq.com
 */
public class ProductListAdapter extends BaseQuickAdapter<DoQueryCategoryDetailsData, BaseViewHolder> {

    public ProductListAdapter(int layoutResId, @Nullable List<DoQueryCategoryDetailsData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryCategoryDetailsData item) {
        ParentBean parent = item.getParent();
        // 标题
        helper.setText(R.id.m_tv_title,parent.getName());


        ArrayList<SonBean> son = item.getSon();
        initRecyclerView(helper, son);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView(BaseViewHolder helper, ArrayList<SonBean> dataList) {
        TypeDetailAdapter mAdapter = new TypeDetailAdapter(R.layout.item_type_detail, dataList);
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setLayoutManager(new GridLayoutManager(mContext, 3));
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // 分类搜索
                ClassifiedSearchActivity.openActivity(mContext,dataList.get(position).getId());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
}
