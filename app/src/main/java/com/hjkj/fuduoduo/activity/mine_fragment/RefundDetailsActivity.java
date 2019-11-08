package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.RefundDetailsAdapter;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 退款详情页面
 * Author：Created by shihongfei on 2019/10/10 11:16
 * Email：1511808259@qq.com
 */
public class RefundDetailsActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private ArrayList<TestBean> mOrderDetailsData;
    private RefundDetailsAdapter mOrderDetailsAdapter;

    private ArrayList<TestBean> mData;
    private ShoppingFragmentAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RefundDetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_refund_details;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(RefundDetailsActivity.this, cl_e51C23, 1);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mOrderDetailsData = new ArrayList<>();
        mOrderDetailsAdapter = new RefundDetailsAdapter(R.layout.item_refund_details, mOrderDetailsData);
        // mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(RefundDetailsActivity.this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mOrderDetailsAdapter);

        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(RefundDetailsActivity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mOrderDetailsData.clear();
        for (int i = 0; i < 3; i++) {
            mOrderDetailsData.add(new TestBean("item" + i));
        }
        mOrderDetailsAdapter.notifyDataSetChanged();

        mData.clear();
        for (int i = 0; i < 10; i++) {
            mData.add(new TestBean("item" + i));
        }
        myScrollView.smoothScrollTo(0, 20);
        mAdapter.notifyDataSetChanged();
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
