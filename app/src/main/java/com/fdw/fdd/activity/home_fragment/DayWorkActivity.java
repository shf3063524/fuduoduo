package com.fdw.fdd.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.NationalDayAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.TestBean;
import com.fdw.fdd.tool.StatusBarUtil;
import com.fdw.fdd.view.ObservableScrollView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 日用洗护页面
 * Author：Created by shihongfei on 2019/9/26 14:08
 * Email：1511808259@qq.com
 */
public class DayWorkActivity extends BaseActivity implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_search_black)
    ImageView mIvSearchBlack;
    @BindView(R.id.iv_shopping_cart)
    ImageView mIvShoppingCart;
    @BindView(R.id.divide_line)
    View dividerView;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.head_layout)
    LinearLayout headLayout;
    @BindView(R.id.iv_header)
    ImageView headerIv;
    @BindView(R.id.m_cv_one)
    CardView mCvOne;
    @BindView(R.id.m_cv_two)
    CardView mCvTwo;
    private int imageHeight; //图片高度

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, DayWorkActivity.class);
        context.startActivity(intent);
    }

    private ArrayList<TestBean> mData;
    private NationalDayAdapter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_day_work;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, headLayout);
    }

    @Override
    protected void initViews() {
        initListener();
        initRecyclerView();
    }

    private void initRecyclerView() {
//        mData = new ArrayList<>();
//        mAdapter = new NationalDayAdapter(R.layout.item_national_day, mData);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(DayWorkActivity.this, 2, GridLayoutManager.VERTICAL, false) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//        mRecyclerView.setLayoutManager(gridLayoutManager);
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
//        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver treeObserver = headerIv.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headerIv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imageHeight = headerIv.getHeight();
                scrollView.setScrollViewListener(DayWorkActivity.this);

            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            //设置渐变的头部的背景颜色
            headLayout.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            dividerView.setVisibility(View.GONE);
        } else if (y > 0 && y <= imageHeight) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / imageHeight;
            int alpha = (int) (scale * 255);
            headLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            // mIvBack.getBackground().setAlpha(255 - alpha);
            // mIvShoppingCart.getBackground().setAlpha(255 - alpha);
            // mIvShare.getBackground().setAlpha(255 - alpha);
            if (oldy < y) {
                // 手指向上滑动，屏幕内容下滑
                mIvBack.setImageResource(R.mipmap.ic_return_white);
                mIvSearchBlack.setImageResource(R.mipmap.ic_search_white);
                mIvShoppingCart.setImageResource(R.mipmap.ic_shopping_white);
            } else if (oldy > y) {
                // 手指向下滑动，屏幕内容上滑
                mIvBack.setImageResource(R.mipmap.ic_return_black);
                mIvSearchBlack.setImageResource(R.mipmap.ic_search_black);
                mIvShoppingCart.setImageResource(R.mipmap.ic_shopping_black);
            }

        } else {
            //滑动到banner下面设置普通颜色
            headLayout.setBackgroundColor(Color.WHITE);
            dividerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void actionView() {
        mData.clear();
        for (int i = 0; i < 10; i++) {
            mData.add(new TestBean("item" + i));
        }
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(DayWorkActivity.this);
            }
        });
    }

    @OnClick({R.id.iv_back,R.id.m_cv_one,R.id.m_cv_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_cv_one: // 条目一
                ProductDetailsActivity.openActivity(DayWorkActivity.this);
                break;
            case R.id.m_cv_two:// 条目二
                ProductDetailsActivity.openActivity(DayWorkActivity.this);
                break;
        }
    }
}
