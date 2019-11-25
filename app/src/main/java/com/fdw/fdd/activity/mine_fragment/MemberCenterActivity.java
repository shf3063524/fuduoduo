package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.ExchangeGiftsAdapter;
import com.fdw.fdd.adapter.MemberCenterAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.TestBean;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员中心-页面
 */
public class MemberCenterActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_recycler_member_center)
    RecyclerView mRecyclerMemberCenter;
    @BindView(R.id.m_recycler_exchange_gifts)
    RecyclerView mRecyclerExchangeGifts;
    private ExchangeGiftsAdapter mExchangeGiftsAdapter;
    private ArrayList<TestBean> mExchangeGiftsData;

    private MemberCenterAdapter mMemberCenterAdapter;
    private ArrayList<TestBean> mMemberCenterData;
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, MemberCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_member_center;
    }

    @Override
    protected void initViews() {
        mExchangeGiftsData = new ArrayList<>();
        mExchangeGiftsAdapter = new ExchangeGiftsAdapter(R.layout.item_exchange_gifts, mExchangeGiftsData);
        GridLayoutManager gridLayoutExchangeGifts = new GridLayoutManager(MemberCenterActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerExchangeGifts.addItemDecoration(new GridSpacingItemDecoration(3, 8, false));
        mRecyclerExchangeGifts.setLayoutManager(gridLayoutExchangeGifts);
        mRecyclerExchangeGifts.setAdapter(mExchangeGiftsAdapter);

        mMemberCenterData = new ArrayList<>();
        mMemberCenterAdapter = new MemberCenterAdapter(R.layout.item_exchange_gifts, mMemberCenterData);
        GridLayoutManager gridLayoutMemberCenter = new GridLayoutManager(MemberCenterActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerMemberCenter.addItemDecoration(new GridSpacingItemDecoration(3, 8, false));
        mRecyclerMemberCenter.setLayoutManager(gridLayoutMemberCenter);
        mRecyclerMemberCenter.setAdapter(mMemberCenterAdapter);
    }

    @Override
    protected void actionView() {
        mExchangeGiftsData.clear();
        for (int i = 0; i < 10; i++) {
            mExchangeGiftsData.add(new TestBean("item" + i));
        }
        mExchangeGiftsAdapter.notifyDataSetChanged();
        mExchangeGiftsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(MemberCenterActivity.this);
            }
        });

        mMemberCenterData.clear();
        for (int i = 0; i < 10; i++) {
            mMemberCenterData.add(new TestBean("item" + i));
        }
        mMemberCenterAdapter.notifyDataSetChanged();
        mMemberCenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(MemberCenterActivity.this);
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
