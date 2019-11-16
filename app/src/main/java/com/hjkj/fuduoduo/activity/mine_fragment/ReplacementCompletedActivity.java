package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 换货详情-换货完成
 */
public class ReplacementCompletedActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_tv_reason_for_return)
    TextView mTvReasonForReturn;
    @BindView(R.id.m_tv_refund_amount)
    TextView mTvRefundAmount;
    @BindView(R.id.m_tv_application_time)
    TextView mTvApplicationTime;
    @BindView(R.id.m_tv_refund_number)
    TextView mTvRefundNumber;
    @BindView(R.id.m_tv_remaining_time)
    TextView mTvRemainingTime;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;

    private ArrayList<TestBean> mData;
    private ShoppingFragmentAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ReplacementCompletedActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_replacement_completed;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(ReplacementCompletedActivity.this, cl_e51C23, 1);
        initRecyclerView();
    }

    private void initRecyclerView() {

        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(ReplacementCompletedActivity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {

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
