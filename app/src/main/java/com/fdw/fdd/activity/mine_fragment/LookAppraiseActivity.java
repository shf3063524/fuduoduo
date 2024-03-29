package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fdw.fdd.R;
import com.fdw.fdd.adapter.MineAllReviewAdapter;
import com.fdw.fdd.adapter.NoReviewAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.TestBean;
import com.fdw.fdd.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的评价页面
 */
public class LookAppraiseActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_recycler_view_no_appraise)
    RecyclerView mRecyclerViewNoAppraise;
    @BindView(R.id.m_recycler_view_all_appraise)
    RecyclerView mRecyclerViewAllAppraise;
    @BindView(R.id.m_iv_pop_ups)
    ImageView mIvPopUps;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayoutSetReturn;
    private ArrayList<TestBean> mNoReviewData;
    private NoReviewAdapter mNoReviewAdapter;

    private ArrayList<TestBean> mAllReviewData;
    private MineAllReviewAdapter mAllReviewAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LookAppraiseActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_look_appraise;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        initQQPop();
    }

    private void initRecyclerView() {
        mNoReviewData = new ArrayList<>();
        mNoReviewAdapter = new NoReviewAdapter(R.layout.item_no_review, mNoReviewData);
        GridLayoutManager layoutManagerNoReview = new GridLayoutManager(LookAppraiseActivity.this, 5, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerViewNoAppraise.addItemDecoration(new SpaceItemDecoration(5, 6,false));
        mRecyclerViewNoAppraise.setLayoutManager(layoutManagerNoReview);
        mRecyclerViewNoAppraise.setAdapter(mNoReviewAdapter);

        mAllReviewData = new ArrayList<>();
        mAllReviewAdapter = new MineAllReviewAdapter(R.layout.item_all_reviview, mAllReviewData);
        LinearLayoutManager layoutManagerAllReview = new LinearLayoutManager(LookAppraiseActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerViewAllAppraise.setLayoutManager(layoutManagerAllReview);
        mRecyclerViewAllAppraise.setAdapter(mAllReviewAdapter);

    }

    @Override
    protected void actionView() {
        mIvPopUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int height = mLayoutSetReturn.getHeight();
                showQQPop(view, height);
            }
        });

        mNoReviewData.clear();
        for (int i = 0; i < 5; i++) {
            mNoReviewData.add(new TestBean("item" + i));
        }
        mNoReviewAdapter.notifyDataSetChanged();

        mAllReviewData.clear();
        for (int i = 0; i < 10; i++) {
            mAllReviewData.add(new TestBean("item" + i));
        }
        mAllReviewAdapter.notifyDataSetChanged();
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
