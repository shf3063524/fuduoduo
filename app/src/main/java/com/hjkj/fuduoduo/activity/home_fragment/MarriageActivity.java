package com.hjkj.fuduoduo.activity.home_fragment;

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
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.adapter.BabyProductsAdapter;
import com.hjkj.fuduoduo.adapter.GoodGoodsAdapter;
import com.hjkj.fuduoduo.adapter.GiftBoxAdapter;
import com.hjkj.fuduoduo.adapter.PregnancyAdapter;
import com.hjkj.fuduoduo.adapter.RecommendeAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 婚孕关怀页面
 */
public class MarriageActivity extends BaseActivity {
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
    @BindView(R.id.m_recycler_view_one)
    RecyclerView mRecyclerVeiwOne;
    @BindView(R.id.m_recycler_view_two)
    RecyclerView mRecyclerVeiwTwo;
    @BindView(R.id.m_recycler_view_three)
    RecyclerView mRecyclerVeiwThree;
    @BindView(R.id.m_recycler_view_four)
    RecyclerView mRecyclerVeiwFour;
    @BindView(R.id.m_recycler_view_five)
    RecyclerView mRecyclerVeiwFive;
    @BindView(R.id.m_rl_one)
    RelativeLayout mRlOne;
    @BindView(R.id.m_rl_two)
    RelativeLayout mRlTwo;
    @BindView(R.id.m_rl_three)
    RelativeLayout mRlThree;
    @BindView(R.id.m_rl_four)
    RelativeLayout mRlFour;
    @BindColor(R.color.cl_e85d6f)
    int cl_e85d6f;
    @BindColor(R.color.cl_f07484)
    int cl_f07484;
    private ArrayList<TestBean> mRecommendedData;
    private RecommendeAdapter mRecommendeAdapter;

    private ArrayList<TestBean> mGoodGoodsData;
    private GoodGoodsAdapter mGoodGoodsAdapter;


    private ArrayList<TestBean> mGiftBoxData;
    private GiftBoxAdapter mGiftBoxAdapter;

    private ArrayList<TestBean> mBabyProductsData;
    private BabyProductsAdapter mBabyProductsAdapter;

    private ArrayList<TestBean> mPregnancyData;
    private PregnancyAdapter mPregnancyAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, MarriageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.actvity_marriage;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecommendedData = new ArrayList<>();
        mRecommendeAdapter = new RecommendeAdapter(R.layout.item_recommende, mRecommendedData);
        LinearLayoutManager layoutManagerHometown = new LinearLayoutManager(MarriageActivity.this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwOne.setLayoutManager(layoutManagerHometown);
        mRecyclerVeiwOne.setAdapter(mRecommendeAdapter);


        mGoodGoodsData = new ArrayList<>();
        mGoodGoodsAdapter = new GoodGoodsAdapter(R.layout.item_good_goods, mGoodGoodsData);
        LinearLayoutManager layoutManagerFruits = new LinearLayoutManager(MarriageActivity.this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwTwo.setLayoutManager(layoutManagerFruits);
        mRecyclerVeiwTwo.setAdapter(mGoodGoodsAdapter);


        mGiftBoxData = new ArrayList<>();
        mGiftBoxAdapter = new GiftBoxAdapter(R.layout.item_gift_box, mGiftBoxData);
        GridLayoutManager gridLayoutManagerProducts = new GridLayoutManager(MarriageActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwThree.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwThree.setLayoutManager(gridLayoutManagerProducts);
        mRecyclerVeiwThree.setAdapter(mGiftBoxAdapter);


        mBabyProductsData = new ArrayList<>();
        mBabyProductsAdapter = new BabyProductsAdapter(R.layout.item_baby_products, mBabyProductsData);
        GridLayoutManager gridLayoutManagerGoods = new GridLayoutManager(MarriageActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwFour.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwFour.setLayoutManager(gridLayoutManagerGoods);
        mRecyclerVeiwFour.setAdapter(mBabyProductsAdapter);

        mPregnancyData = new ArrayList<>();
        mPregnancyAdapter = new PregnancyAdapter(R.layout.item_pregnancy, mPregnancyData);
        GridLayoutManager gridLayoutManagerSemiFinished = new GridLayoutManager(MarriageActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwFive.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwFive.setLayoutManager(gridLayoutManagerSemiFinished);
        mRecyclerVeiwFive.setAdapter(mPregnancyAdapter);
    }

    @Override
    protected void actionView() {
        mRecommendedData.clear();
        for (int i = 0; i < 10; i++) {
            mRecommendedData.add(new TestBean("item" + i));
        }
        mRecommendeAdapter.notifyDataSetChanged();
        mRecommendeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(MarriageActivity.this);
            }
        });

        mGoodGoodsData.clear();
        for (int i = 0; i < 10; i++) {
            mGoodGoodsData.add(new TestBean("item" + i));
        }
        mGoodGoodsAdapter.notifyDataSetChanged();
        mGoodGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(MarriageActivity.this);
            }
        });


        mGiftBoxData.clear();
        for (int i = 0; i < 10; i++) {
            mGiftBoxData.add(new TestBean("item" + i));
        }
        mGiftBoxAdapter.notifyDataSetChanged();
        mGiftBoxAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(MarriageActivity.this);
            }
        });


        mBabyProductsData.clear();
        for (int i = 0; i < 10; i++) {
            mBabyProductsData.add(new TestBean("item" + i));
        }
        mBabyProductsAdapter.notifyDataSetChanged();
        mBabyProductsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(MarriageActivity.this);
            }
        });

        mPregnancyData.clear();
        for (int i = 0; i < 10; i++) {
            mPregnancyData.add(new TestBean("item" + i));
        }
        mPregnancyAdapter.notifyDataSetChanged();
        mPregnancyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(MarriageActivity.this);
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
            case R.id.m_layout_one: // 为你推荐
                mLayoutOne.setBackgroundColor(cl_e85d6f);
                mLayoutTwo.setBackgroundColor(cl_f07484);
                mLayoutThree.setBackgroundColor(cl_f07484);
                mLayoutFour.setBackgroundColor(cl_f07484);
                mLayoutFive.setBackgroundColor(cl_f07484);
                //滚动到顶部
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                break;
            case R.id.m_layout_two: // 精选好物
                mLayoutOne.setBackgroundColor(cl_f07484);
                mLayoutTwo.setBackgroundColor(cl_e85d6f);
                mLayoutThree.setBackgroundColor(cl_f07484);
                mLayoutFour.setBackgroundColor(cl_f07484);
                mLayoutFive.setBackgroundColor(cl_f07484);
                //精选好物
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //为你推荐高度+字体像素高度
                        int twoHeight = mTvOne.getHeight() + mRlOne.getHeight() + (int) DensityUtil.px2dp(15 + 16);
                        mScrollView.smoothScrollTo(0, twoHeight);
                    }
                });
                break;
            case R.id.m_layout_three: // 礼物礼盒
                mLayoutOne.setBackgroundColor(cl_f07484);
                mLayoutTwo.setBackgroundColor(cl_f07484);
                mLayoutThree.setBackgroundColor(cl_e85d6f);
                mLayoutFour.setBackgroundColor(cl_f07484);
                mLayoutFive.setBackgroundColor(cl_f07484);
                //滚动到礼物礼盒
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //为你推荐+精选好物+字体像素高度
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() + mRlTwo.getHeight() + (int) DensityUtil.px2dp(15 + 16 + 15 + 16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_four: // 母婴用品
                mLayoutOne.setBackgroundColor(cl_f07484);
                mLayoutTwo.setBackgroundColor(cl_f07484);
                mLayoutThree.setBackgroundColor(cl_f07484);
                mLayoutFour.setBackgroundColor(cl_e85d6f);
                mLayoutFive.setBackgroundColor(cl_f07484);
                //滚动到母婴用品
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        // 为你推荐+精选好物+礼物礼盒+字体像素高度
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() + mRlTwo.getHeight() + mTvThree.getHeight() + mRlThree.getHeight() + (int) DensityUtil.px2dp(15 + 16 + 15 + 16 + 15 + 16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_five: // 孕期补品
                mLayoutOne.setBackgroundColor(cl_f07484);
                mLayoutTwo.setBackgroundColor(cl_f07484);
                mLayoutThree.setBackgroundColor(cl_f07484);
                mLayoutFour.setBackgroundColor(cl_f07484);
                mLayoutFive.setBackgroundColor(cl_e85d6f);
                //滚动到孕期补品
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        // 为你推荐+精选好物+礼物礼盒+母婴用品+字体像素高度
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() +
                                mRlTwo.getHeight() + mTvThree.getHeight() + mRlThree.getHeight() +
                                mTvFour.getHeight() + mRlFour.getHeight() + (int) DensityUtil.px2dp(15 + 16 + 15 + 16 + 15 + 16 + 15 + 16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
        }
    }
}
