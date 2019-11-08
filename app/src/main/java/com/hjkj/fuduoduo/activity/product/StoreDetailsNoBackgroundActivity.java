package com.hjkj.fuduoduo.activity.product;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.StoreDetailsAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 店铺详情-无背景页面
 */
public class StoreDetailsNoBackgroundActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_collect)
    ImageView mIvCollect;
    @BindView(R.id.m_tv_complex)
    TextView mTvComplex;
    @BindView(R.id.m_tv_volume)
    TextView mTvVolume;
    @BindView(R.id.m_tv_price)
    TextView mTvPrice;
    @BindView(R.id.m_iv_price)
    ImageView mIvPrice;
    @BindView(R.id.m_layout_price)
    LinearLayout mLayoutPrice;
    @BindColor(R.color.cl_fff)
    int cl_fff;
    @BindColor(R.color.cl_666)
    int cl_666;
    /**
     * 收藏-按钮点击判断标识
     */
    private boolean checkCollect = false;
    /**
     * 价格-上下标切换标识
     */
    private boolean check = false;

    private ArrayList<TestBean> mData;
    private StoreDetailsAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, StoreDetailsNoBackgroundActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_store_details_no_background;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucent(StoreDetailsNoBackgroundActivity.this, 0);
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
//        mData = new ArrayList<>();
//        mAdapter = new StoreDetailsAdapter(R.layout.item_store_details, mData);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(StoreDetailsNoBackgroundActivity.this, 2));
//        mRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 10, true));
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mData.clear();
        for (int i = 0; i < 20; i++) {
            mData.add(new TestBean("item" + i));
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_iv_collect, R.id.m_tv_complex, R.id.m_tv_volume, R.id.m_tv_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_iv_collect: // 收藏
                if (checkCollect) {
                    checkCollect = false;
                    mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collection_black));
                } else {
                    checkCollect = true;
                    mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_red_collection));
                }
                break;
            case R.id.m_tv_complex: // 综合
                mTvComplex.setTextColor(cl_fff);
                mTvVolume.setTextColor(cl_666);
                mTvPrice.setTextColor(cl_666);
                mTvComplex.setBackground(getResources().getDrawable(R.drawable.ic_select_red));
                mTvVolume.setBackgroundColor(getResources().getColor(R.color.transparent));
                mLayoutPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
                mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_price_sort));
                break;
            case R.id.m_tv_volume: // 销量
                mTvVolume.setTextColor(cl_fff);
                mTvComplex.setTextColor(cl_666);
                mTvPrice.setTextColor(cl_666);
                mTvComplex.setBackgroundColor(getResources().getColor(R.color.transparent));
                mTvVolume.setBackground(getResources().getDrawable(R.drawable.ic_select_red));
                mLayoutPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
                mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_price_sort));
                break;
            case R.id.m_tv_price: // 价格
                if (check) {
                    check = false;
                    mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_from_high_to_low));
                } else {
                    check = true;
                    mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_from_low_to_high));
                }
                mTvVolume.setTextColor(cl_666);
                mTvComplex.setTextColor(cl_666);
                mTvPrice.setTextColor(cl_fff);
                mTvComplex.setBackgroundColor(getResources().getColor(R.color.transparent));
                mTvVolume.setBackgroundColor(getResources().getColor(R.color.transparent));
                mLayoutPrice.setBackground(getResources().getDrawable(R.drawable.ic_select_red));
                break;
        }
    }
}
