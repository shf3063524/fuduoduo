package com.fdw.fdd.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.FruitsAdapter;
import com.fdw.fdd.adapter.GoodsAdapter;
import com.fdw.fdd.adapter.HometownAdapter;
import com.fdw.fdd.adapter.ProductsAdapter;
import com.fdw.fdd.adapter.SemiFinishedAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.TestBean;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 精准扶贫页面
 * Author：Created by shihongfei on 2019/9/26 15:44
 * Email：1511808259@qq.com
 */
public class ReductionActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_layout_one)
    LinearLayout mLayoutOne;
    @BindView(R.id.m_layout_two)
    LinearLayout mLayoutTwo;
    @BindView(R.id.m_layout_three)
    LinearLayout mLayoutThree;
    @BindView(R.id.m_layout_four)
    LinearLayout mLayoutFour;
    @BindView(R.id.m_layout_five)
    LinearLayout mLayoutFive;
    @BindView(R.id.m_tv_one)
    TextView mTvOne;
    @BindView(R.id.m_tv_two)
    TextView mTvTwo;
    @BindView(R.id.m_tv_three)
    TextView mTvThree;
    @BindView(R.id.m_tv_four)
    TextView mTvFour;
    @BindView(R.id.m_tv_five)
    TextView mTvFive;
    @BindView(R.id.m_scrollview)
    ScrollView mScrollView;
    @BindView(R.id.m_recycler_view_hometown)
    RecyclerView mRecyclerVeiwHometown;
    @BindView(R.id.m_recycler_view_fruits)
    RecyclerView mRecyclerVeiwFruits;
    @BindView(R.id.m_recycler_view_products)
    RecyclerView mRecyclerVeiwProducts;
    @BindView(R.id.m_recycler_view_goods)
    RecyclerView mRecyclerVeiwGoods;
    @BindView(R.id.m_recycler_view_semi_finished)
    RecyclerView mRecyclerVeiwSemiFinished;
    @BindView(R.id.m_rl_one)
    RelativeLayout mRlOne;
    @BindView(R.id.m_rl_two)
    RelativeLayout mRlTwo;
    @BindView(R.id.m_rl_three)
    RelativeLayout mRlThree;
    @BindView(R.id.m_rl_four)
    RelativeLayout mRlFour;
    @BindColor(R.color.cl_bf161c)
    int cl_bf161c;
    @BindColor(R.color.cl_f8252c)
    int cl_f8252c;
    private int position = 1;
    private ArrayList<TestBean> mHometownData;
    private HometownAdapter mHonetownAdapter;

    private ArrayList<TestBean> mFruitsData;
    private FruitsAdapter mFruitsAdapter;


    private ArrayList<TestBean> mProductsData;
    private ProductsAdapter mProductsAdapter;

    private ArrayList<TestBean> mGoodsData;
    private GoodsAdapter mGoodsAdapter;

    private ArrayList<TestBean> mSemiFinishedData;
    private SemiFinishedAdapter mSemiFinishedAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ReductionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_reduction;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mHometownData = new ArrayList<>();
        mHonetownAdapter = new HometownAdapter(R.layout.item_hometown, mHometownData);
        LinearLayoutManager layoutManagerHometown = new LinearLayoutManager(ReductionActivity.this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwHometown.setLayoutManager(layoutManagerHometown);
        mRecyclerVeiwHometown.setAdapter(mHonetownAdapter);


        mFruitsData = new ArrayList<>();
        mFruitsAdapter = new FruitsAdapter(R.layout.item_fruits, mFruitsData);
        LinearLayoutManager layoutManagerFruits = new LinearLayoutManager(ReductionActivity.this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwFruits.setLayoutManager(layoutManagerFruits);
        mRecyclerVeiwFruits.setAdapter(mFruitsAdapter);


        mProductsData = new ArrayList<>();
        mProductsAdapter = new ProductsAdapter(R.layout.item_products, mFruitsData);
        GridLayoutManager gridLayoutManagerProducts = new GridLayoutManager(ReductionActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwProducts.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwProducts.setLayoutManager(gridLayoutManagerProducts);
        mRecyclerVeiwProducts.setAdapter(mProductsAdapter);


        mGoodsData = new ArrayList<>();
        mGoodsAdapter = new GoodsAdapter(R.layout.item_goods, mGoodsData);
        GridLayoutManager gridLayoutManagerGoods = new GridLayoutManager(ReductionActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwGoods.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwGoods.setLayoutManager(gridLayoutManagerGoods);
        mRecyclerVeiwGoods.setAdapter(mGoodsAdapter);

        mSemiFinishedData = new ArrayList<>();
        mSemiFinishedAdapter = new SemiFinishedAdapter(R.layout.item_semi_finished, mSemiFinishedData);
        GridLayoutManager gridLayoutManagerSemiFinished = new GridLayoutManager(ReductionActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwSemiFinished.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwSemiFinished.setLayoutManager(gridLayoutManagerSemiFinished);
        mRecyclerVeiwSemiFinished.setAdapter(mSemiFinishedAdapter);
    }

    @Override
    protected void actionView() {
        mHometownData.clear();
        for (int i = 0; i < 10; i++) {
            mHometownData.add(new TestBean("item" + i));
        }
        mHonetownAdapter.notifyDataSetChanged();
        mHonetownAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(ReductionActivity.this);
            }
        });


        mFruitsData.clear();
        for (int i = 0; i < 10; i++) {
            mFruitsData.add(new TestBean("item" + i));
        }
        mFruitsAdapter.notifyDataSetChanged();
        mFruitsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(ReductionActivity.this);
            }
        });

        mProductsData.clear();
        for (int i = 0; i < 10; i++) {
            mProductsData.add(new TestBean("item" + i));
        }
        mProductsAdapter.notifyDataSetChanged();
        mProductsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(ReductionActivity.this);
            }
        });


        mGoodsData.clear();
        for (int i = 0; i < 10; i++) {
            mGoodsData.add(new TestBean("item" + i));
        }
        mGoodsAdapter.notifyDataSetChanged();
        mGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(ReductionActivity.this);
            }
        });


        mSemiFinishedData.clear();
        for (int i = 0; i < 10; i++) {
            mSemiFinishedData.add(new TestBean("item" + i));
        }
        mSemiFinishedAdapter.notifyDataSetChanged();
        mSemiFinishedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(ReductionActivity.this);
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_layout_one, R.id.m_layout_two, R.id.m_layout_three, R.id.m_layout_four, R.id.m_layout_five})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_one: // 家乡特产
                mLayoutOne.setBackgroundColor(cl_bf161c);
                mLayoutTwo.setBackgroundColor(cl_f8252c);
                mLayoutThree.setBackgroundColor(cl_f8252c);
                mLayoutFour.setBackgroundColor(cl_f8252c);
                mLayoutFive.setBackgroundColor(cl_f8252c);
                //滚动到顶部
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                break;
            case R.id.m_layout_two: // 时令果蔬
                mLayoutOne.setBackgroundColor(cl_f8252c);
                mLayoutTwo.setBackgroundColor(cl_bf161c);
                mLayoutThree.setBackgroundColor(cl_f8252c);
                mLayoutFour.setBackgroundColor(cl_f8252c);
                mLayoutFive.setBackgroundColor(cl_f8252c);
                //滚动到时令果蔬
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //家乡特产高度
                        int twoHeight = mTvOne.getHeight() + mRlOne.getHeight() + (int) DensityUtil.px2dp(15+16);
                        mScrollView.smoothScrollTo(0, twoHeight);
                    }
                });
                break;
            case R.id.m_layout_three: // 农福产品
                mLayoutOne.setBackgroundColor(cl_f8252c);
                mLayoutTwo.setBackgroundColor(cl_f8252c);
                mLayoutThree.setBackgroundColor(cl_bf161c);
                mLayoutFour.setBackgroundColor(cl_f8252c);
                mLayoutFive.setBackgroundColor(cl_f8252c);
                //滚动到农福产品
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //家乡农福产品
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() + mRlTwo.getHeight()+ (int) DensityUtil.px2dp(15+16+15+16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_four: // 富农商品
                mLayoutOne.setBackgroundColor(cl_f8252c);
                mLayoutTwo.setBackgroundColor(cl_f8252c);
                mLayoutThree.setBackgroundColor(cl_f8252c);
                mLayoutFour.setBackgroundColor(cl_bf161c);
                mLayoutFive.setBackgroundColor(cl_f8252c);
                //滚动到富农商品
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() + mRlTwo.getHeight()+mTvThree.getHeight()+ mRlThree.getHeight()+ (int) DensityUtil.px2dp(15+16+15+16+15+16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_five: // 半加工品
                mLayoutOne.setBackgroundColor(cl_f8252c);
                mLayoutTwo.setBackgroundColor(cl_f8252c);
                mLayoutThree.setBackgroundColor(cl_f8252c);
                mLayoutFour.setBackgroundColor(cl_f8252c);
                mLayoutFive.setBackgroundColor(cl_bf161c);
                //滚动到富农商品
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() +
                                mRlTwo.getHeight()+mTvThree.getHeight()+ mRlThree.getHeight()+
                                mTvFour.getHeight()+ mRlFour.getHeight()+ (int) DensityUtil.px2dp(15+16+15+16+15+16+15+16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
        }
    }

    /**
     * @param position
     */
    private void switchText(int position) {
        switch (position) {
            case 1:// 家乡特产
                mTvOne.setText("# 家乡特产 #");
                break;
            case 2:// 时令果蔬
                mTvTwo.setText("# 时令果蔬 #");
                break;
            case 3: // 农福产品
                mTvThree.setText("# 农副产品 #");
                break;
            case 4: // 富农商品
                mTvFour.setText("# 富农商品 #");
                break;
            case 5: // 半加工品
                mTvFive.setText("# 半加工品 #");
                break;

        }
    }
}
