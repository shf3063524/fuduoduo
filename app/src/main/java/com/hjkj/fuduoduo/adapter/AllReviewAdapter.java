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
import com.hjkj.fuduoduo.entity.bean.ConsumerBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryCommodityDetailsData;
import com.hjkj.fuduoduo.entity.bean.Evaluation;
import com.hjkj.fuduoduo.entity.bean.EvaluationBeans;
import com.hjkj.fuduoduo.entity.bean.SpecificationBeans;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class AllReviewAdapter extends BaseQuickAdapter<EvaluationBeans, BaseViewHolder> {
    public AllReviewAdapter(int layoutResId, @Nullable List<EvaluationBeans> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluationBeans item) {
        ConsumerBean consumer = item.getConsumer();
        // 头像
        GlideUtils.loadCircleHeadImage(mContext, consumer.getLogo(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_head));
        // 电话
        String phoneNumber = consumer.getPhoneNumber();
        String phonenum = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11);
        helper.setText(R.id.m_tv_phone, phonenum);

        // 时间
        Evaluation evaluation = item.getEvaluation();
        helper.setText(R.id.m_tv_time, evaluation.getCreateTime());
        //评价内容
        helper.setText(R.id.m_tv_content, evaluation.getEvaluationContent());

        // 规格
        SpecificationBeans specification = item.getSpecification();
        helper.setText(R.id.m_tv_specification, specification.getCommoditySpecification());


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


