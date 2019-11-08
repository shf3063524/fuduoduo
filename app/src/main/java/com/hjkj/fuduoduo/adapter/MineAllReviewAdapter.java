package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MineAllReviewAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    public MineAllReviewAdapter(int layoutResId, @Nullable List<TestBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        ArrayList<TestBean> mTypeData = new ArrayList<>();
        initRecyclerView(helper, mTypeData);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView(BaseViewHolder helper, ArrayList<TestBean> dataList) {
        AllReviewListAdapter mAdapter = new AllReviewListAdapter(R.layout.item_all_review, dataList);
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setLayoutManager(new GridLayoutManager(mContext, 3));
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).addItemDecoration(new SpaceItemDecoration(3, 10, true));
        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setAdapter(mAdapter);
        dataList.clear();
        for (int i = 0; i < 3; i++) {
            dataList.add(new TestBean("item" + i));
        }
        mAdapter.notifyDataSetChanged();

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                // 分类搜索
//                ClassifiedSearchActivity.openActivity(mContext);
            }
        });
    }
}



