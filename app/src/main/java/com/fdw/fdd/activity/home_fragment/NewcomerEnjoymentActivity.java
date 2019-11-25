package com.fdw.fdd.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.BeautyWearAdapter;
import com.fdw.fdd.adapter.HomeDailyAdapter;
import com.fdw.fdd.adapter.KitchenAppliancesAdapter;
import com.fdw.fdd.adapter.RecommendedToYouAdapter;
import com.fdw.fdd.adapter.ValueExplosionAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.TestBean;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新人专享福利页面
 */
public class NewcomerEnjoymentActivity extends BaseActivity {
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
    @BindView(R.id.m_layout_tv_one)
    RelativeLayout mLayoutTvOne;
    @BindView(R.id.m_layout_tv_two)
    RelativeLayout mLayoutTvTwo;
    @BindView(R.id.m_layout_tv_three)
    RelativeLayout mLayoutTvThree;
    @BindView(R.id.m_layout_tv_four)
    RelativeLayout mLayoutTvFour;
    @BindView(R.id.m_layout_tv_five)
    RelativeLayout mLayoutTvFive;

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
    @BindColor(R.color.cl_9754d2)
    int cl_9754d2;
    @BindColor(R.color.cl_b064f2)
    int cl_b064f2;
    private ArrayList<TestBean> mRecommendedToYouData;
    private RecommendedToYouAdapter mRecommendedToYouAdapter;

    private ArrayList<TestBean> mValueExplosionData;
    private ValueExplosionAdapter mValueExplosionAdapter;


    private ArrayList<TestBean> mBeautyWearData;
    private BeautyWearAdapter mBeautyWearAdapter;

    private ArrayList<TestBean> mHomeDailyData;
    private HomeDailyAdapter mHomeDailyAdapter;

    private ArrayList<TestBean> mKitchenAppliancesData;
    private KitchenAppliancesAdapter mKitchenAppliancesAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, NewcomerEnjoymentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_newcomer_enjoyment;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecommendedToYouData = new ArrayList<>();
        mRecommendedToYouAdapter = new RecommendedToYouAdapter(R.layout.item_newcomer, mRecommendedToYouData);
        GridLayoutManager gridLayoutManagerRecommendedToYou = new GridLayoutManager(NewcomerEnjoymentActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwOne.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwOne.setLayoutManager(gridLayoutManagerRecommendedToYou);
        mRecyclerVeiwOne.setAdapter(mRecommendedToYouAdapter);


        mValueExplosionData = new ArrayList<>();
        mValueExplosionAdapter = new ValueExplosionAdapter(R.layout.item_newcomer, mValueExplosionData);
        GridLayoutManager gridLayoutManagerValueExplosion = new GridLayoutManager(NewcomerEnjoymentActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwTwo.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwTwo.setLayoutManager(gridLayoutManagerValueExplosion);
        mRecyclerVeiwTwo.setAdapter(mValueExplosionAdapter);


        mBeautyWearData = new ArrayList<>();
        mBeautyWearAdapter = new BeautyWearAdapter(R.layout.item_newcomer, mBeautyWearData);
        GridLayoutManager gridLayoutManagerProducts = new GridLayoutManager(NewcomerEnjoymentActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwThree.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwThree.setLayoutManager(gridLayoutManagerProducts);
        mRecyclerVeiwThree.setAdapter(mBeautyWearAdapter);


        mHomeDailyData = new ArrayList<>();
        mHomeDailyAdapter = new HomeDailyAdapter(R.layout.item_newcomer, mHomeDailyData);
        GridLayoutManager gridLayoutManagerGoods = new GridLayoutManager(NewcomerEnjoymentActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwFour.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwFour.setLayoutManager(gridLayoutManagerGoods);
        mRecyclerVeiwFour.setAdapter(mHomeDailyAdapter);

        mKitchenAppliancesData = new ArrayList<>();
        mKitchenAppliancesAdapter = new KitchenAppliancesAdapter(R.layout.item_newcomer, mKitchenAppliancesData);
        GridLayoutManager gridLayoutManagerSemiFinished = new GridLayoutManager(NewcomerEnjoymentActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwFive.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwFive.setLayoutManager(gridLayoutManagerSemiFinished);
        mRecyclerVeiwFive.setAdapter(mKitchenAppliancesAdapter);
    }

    @Override
    protected void actionView() {
        mRecommendedToYouData.clear();
        for (int i = 0; i < 10; i++) {
            mRecommendedToYouData.add(new TestBean("item" + i));
        }
        mRecommendedToYouAdapter.notifyDataSetChanged();
        mRecommendedToYouAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(NewcomerEnjoymentActivity.this);
            }
        });

        mValueExplosionData.clear();
        for (int i = 0; i < 10; i++) {
            mValueExplosionData.add(new TestBean("item" + i));
        }
        mValueExplosionAdapter.notifyDataSetChanged();
        mValueExplosionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(NewcomerEnjoymentActivity.this);
            }
        });

        mBeautyWearData.clear();
        for (int i = 0; i < 10; i++) {
            mBeautyWearData.add(new TestBean("item" + i));
        }
        mBeautyWearAdapter.notifyDataSetChanged();
        mBeautyWearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(NewcomerEnjoymentActivity.this);
            }
        });

        mHomeDailyData.clear();
        for (int i = 0; i < 10; i++) {
            mHomeDailyData.add(new TestBean("item" + i));
        }
        mHomeDailyAdapter.notifyDataSetChanged();
        mHomeDailyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(NewcomerEnjoymentActivity.this);
            }
        });
        mKitchenAppliancesData.clear();
        for (int i = 0; i < 10; i++) {
            mKitchenAppliancesData.add(new TestBean("item" + i));
        }
        mKitchenAppliancesAdapter.notifyDataSetChanged();
        mKitchenAppliancesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(NewcomerEnjoymentActivity.this);
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
                mLayoutOne.setBackgroundColor(cl_9754d2);
                mLayoutTwo.setBackgroundColor(cl_b064f2);
                mLayoutThree.setBackgroundColor(cl_b064f2);
                mLayoutFour.setBackgroundColor(cl_b064f2);
                mLayoutFive.setBackgroundColor(cl_b064f2);
                //滚动到顶部
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                break;
            case R.id.m_layout_two: // 超值爆款
                mLayoutOne.setBackgroundColor(cl_b064f2);
                mLayoutTwo.setBackgroundColor(cl_9754d2);
                mLayoutThree.setBackgroundColor(cl_b064f2);
                mLayoutFour.setBackgroundColor(cl_b064f2);
                mLayoutFive.setBackgroundColor(cl_b064f2);
                //超值爆款
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //为你推荐高度+字体像素高度
                        int twoHeight = mLayoutTvOne.getHeight() + mRlOne.getHeight();
                        mScrollView.smoothScrollTo(0, twoHeight);
                    }
                });
                break;
            case R.id.m_layout_three: // 美妆穿搭
                mLayoutOne.setBackgroundColor(cl_b064f2);
                mLayoutTwo.setBackgroundColor(cl_b064f2);
                mLayoutThree.setBackgroundColor(cl_9754d2);
                mLayoutFour.setBackgroundColor(cl_b064f2);
                mLayoutFive.setBackgroundColor(cl_b064f2);
                //滚动到美妆穿搭
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //为你推荐+超值爆款+字体像素高度
                        int threeHeight = mLayoutTvOne.getHeight() + mRlOne.getHeight() + mLayoutTvTwo.getHeight() + mRlTwo.getHeight();
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_four: // 居家日用
                mLayoutOne.setBackgroundColor(cl_b064f2);
                mLayoutTwo.setBackgroundColor(cl_b064f2);
                mLayoutThree.setBackgroundColor(cl_b064f2);
                mLayoutFour.setBackgroundColor(cl_9754d2);
                mLayoutFive.setBackgroundColor(cl_b064f2);
                //滚动到居家日用
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        // 为你推荐+超值爆款+美妆穿搭+字体像素高度
                        int threeHeight = mLayoutTvOne.getHeight() + mRlOne.getHeight() + mLayoutTvTwo.getHeight() + mRlTwo.getHeight() + mLayoutTvThree.getHeight() + mRlThree.getHeight();
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_five: // 厨卫家电
                mLayoutOne.setBackgroundColor(cl_b064f2);
                mLayoutTwo.setBackgroundColor(cl_b064f2);
                mLayoutThree.setBackgroundColor(cl_b064f2);
                mLayoutFour.setBackgroundColor(cl_b064f2);
                mLayoutFive.setBackgroundColor(cl_9754d2);
                //滚动到厨卫家电
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        // 为你推荐+超值爆款+美妆穿搭+居家日用+字体像素高度
                        int threeHeight = mLayoutTvOne.getHeight() + mRlOne.getHeight() + mLayoutTvTwo.getHeight() +
                                mRlTwo.getHeight() + mLayoutTvThree.getHeight() + mRlThree.getHeight() +
                                mLayoutTvFour.getHeight() + mRlFour.getHeight();
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
        }
    }
}