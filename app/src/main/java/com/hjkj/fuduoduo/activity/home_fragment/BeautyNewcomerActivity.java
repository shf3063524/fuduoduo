package com.hjkj.fuduoduo.activity.home_fragment;

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
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.adapter.BeautyWearAdapter;
import com.hjkj.fuduoduo.adapter.HomeDailyAdapter;
import com.hjkj.fuduoduo.adapter.KitchenAppliancesAdapter;
import com.hjkj.fuduoduo.adapter.RecommendedToYouAdapter;
import com.hjkj.fuduoduo.adapter.ValueExplosionAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class BeautyNewcomerActivity extends BaseActivity {
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
    @BindColor(R.color.cl_c12a23)
    int cl_c12a23;
    @BindColor(R.color.cl_e35b4e)
    int cl_e35b4e;
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
        Intent intent = new Intent(context, BeautyNewcomerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_beauty_newcomer;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecommendedToYouData = new ArrayList<>();
        mRecommendedToYouAdapter = new RecommendedToYouAdapter(R.layout.item_newcomer, mRecommendedToYouData);
        GridLayoutManager gridLayoutManagerRecommendedToYou = new GridLayoutManager(BeautyNewcomerActivity.this, 3, GridLayoutManager.VERTICAL, false) {
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
        GridLayoutManager gridLayoutManagerValueExplosion = new GridLayoutManager(BeautyNewcomerActivity.this, 3, GridLayoutManager.VERTICAL, false) {
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
        GridLayoutManager gridLayoutManagerProducts = new GridLayoutManager(BeautyNewcomerActivity.this, 3, GridLayoutManager.VERTICAL, false) {
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
        GridLayoutManager gridLayoutManagerGoods = new GridLayoutManager(BeautyNewcomerActivity.this, 3, GridLayoutManager.VERTICAL, false) {
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
        GridLayoutManager gridLayoutManagerSemiFinished = new GridLayoutManager(BeautyNewcomerActivity.this, 3, GridLayoutManager.VERTICAL, false) {
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
                ProductDetailsActivity.openActivity(BeautyNewcomerActivity.this);
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
                ProductDetailsActivity.openActivity(BeautyNewcomerActivity.this);
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
                ProductDetailsActivity.openActivity(BeautyNewcomerActivity.this);
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
                ProductDetailsActivity.openActivity(BeautyNewcomerActivity.this);
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
                ProductDetailsActivity.openActivity(BeautyNewcomerActivity.this);
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
                mLayoutOne.setBackgroundColor(cl_c12a23);
                mLayoutTwo.setBackgroundColor(cl_e35b4e);
                mLayoutThree.setBackgroundColor(cl_e35b4e);
                mLayoutFour.setBackgroundColor(cl_e35b4e);
                mLayoutFive.setBackgroundColor(cl_e35b4e);
                //滚动到顶部
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                break;
            case R.id.m_layout_two: // 超值爆款
                mLayoutOne.setBackgroundColor(cl_e35b4e);
                mLayoutTwo.setBackgroundColor(cl_c12a23);
                mLayoutThree.setBackgroundColor(cl_e35b4e);
                mLayoutFour.setBackgroundColor(cl_e35b4e);
                mLayoutFive.setBackgroundColor(cl_e35b4e);
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
                mLayoutOne.setBackgroundColor(cl_e35b4e);
                mLayoutTwo.setBackgroundColor(cl_e35b4e);
                mLayoutThree.setBackgroundColor(cl_c12a23);
                mLayoutFour.setBackgroundColor(cl_e35b4e);
                mLayoutFive.setBackgroundColor(cl_e35b4e);
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
                mLayoutOne.setBackgroundColor(cl_e35b4e);
                mLayoutTwo.setBackgroundColor(cl_e35b4e);
                mLayoutThree.setBackgroundColor(cl_e35b4e);
                mLayoutFour.setBackgroundColor(cl_c12a23);
                mLayoutFive.setBackgroundColor(cl_e35b4e);
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
                mLayoutOne.setBackgroundColor(cl_e35b4e);
                mLayoutTwo.setBackgroundColor(cl_e35b4e);
                mLayoutThree.setBackgroundColor(cl_e35b4e);
                mLayoutFour.setBackgroundColor(cl_e35b4e);
                mLayoutFive.setBackgroundColor(cl_c12a23);
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

