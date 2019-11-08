package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.ViewLogisticsAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 查看物流页面
 * Author：Created by shihongfei on 2019/10/10 21:50
 * Email：1511808259@qq.com
 */
public class ViewLogisticsActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    private ArrayList<TestBean> mData;
    private ViewLogisticsAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ViewLogisticsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_view_logistice;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ViewLogisticsAdapter(R.layout.item_view_logistics, mData);
        // mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewLogisticsActivity.this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        super.actionView();
        mData.clear();
        for (int i = 0; i < 10; i++) {
            mData.add(new TestBean("item" + i));
        }
        mAdapter.notifyDataSetChanged();

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogisticsInfoActivity.openActivity(ViewLogisticsActivity.this);
            }
        });
    }

    @OnClick({R.id.m_iv_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
        }
    }

}
