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
import com.fdw.fdd.adapter.EnjoymentAdapter;
import com.fdw.fdd.adapter.PackageAdapter;
import com.fdw.fdd.adapter.PopularAdapter;
import com.fdw.fdd.adapter.TodayAdapter;
import com.fdw.fdd.adapter.WatchAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.TestBean;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 生日关怀页面
 */
public class BirthdayActivity extends BaseActivity {
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
    @BindColor(R.color.cl_e95d62)
    int cl_e95d62;
    @BindColor(R.color.cl_ff8483)
    int cl_ff8483;
    private ArrayList<TestBean> mTodayData;
    private TodayAdapter mTodayAdapter;

    private ArrayList<TestBean> mEnjoymentData;
    private EnjoymentAdapter mEnjoymentAdapter;


    private ArrayList<TestBean> mPackageData;
    private PackageAdapter mPackageAdapter;

    private ArrayList<TestBean> mPopularData;
    private PopularAdapter mPopularAdapter;

    private ArrayList<TestBean> mWatchData;
    private WatchAdapter mWatchAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, BirthdayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.actvity_birthday;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mTodayData = new ArrayList<>();
        mTodayAdapter = new TodayAdapter(R.layout.item_today, mTodayData);
        LinearLayoutManager layoutManagerHometown = new LinearLayoutManager(BirthdayActivity.this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwOne.setLayoutManager(layoutManagerHometown);
        mRecyclerVeiwOne.setAdapter(mTodayAdapter);


        mEnjoymentData = new ArrayList<>();
        mEnjoymentAdapter = new EnjoymentAdapter(R.layout.item_enjoyment, mEnjoymentData);
        LinearLayoutManager layoutManagerFruits = new LinearLayoutManager(BirthdayActivity.this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwTwo.setLayoutManager(layoutManagerFruits);
        mRecyclerVeiwTwo.setAdapter(mEnjoymentAdapter);


        mPackageData = new ArrayList<>();
        mPackageAdapter = new PackageAdapter(R.layout.item_package, mPackageData);
        GridLayoutManager gridLayoutManagerProducts = new GridLayoutManager(BirthdayActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwThree.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwThree.setLayoutManager(gridLayoutManagerProducts);
        mRecyclerVeiwThree.setAdapter(mPackageAdapter);


        mPopularData = new ArrayList<>();
        mPopularAdapter = new PopularAdapter(R.layout.item_popular, mPopularData);
        GridLayoutManager gridLayoutManagerGoods = new GridLayoutManager(BirthdayActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwFour.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwFour.setLayoutManager(gridLayoutManagerGoods);
        mRecyclerVeiwFour.setAdapter(mPopularAdapter);

        mWatchData = new ArrayList<>();
        mWatchAdapter = new WatchAdapter(R.layout.item_watch, mWatchData);
        GridLayoutManager gridLayoutManagerSemiFinished = new GridLayoutManager(BirthdayActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwFive.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwFive.setLayoutManager(gridLayoutManagerSemiFinished);
        mRecyclerVeiwFive.setAdapter(mWatchAdapter);
    }

    @Override
    protected void actionView() {
        mTodayData.clear();
        for (int i = 0; i < 10; i++) {
            mTodayData.add(new TestBean("item" + i));
        }
        mTodayAdapter.notifyDataSetChanged();
        mTodayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(BirthdayActivity.this);
            }
        });

        mEnjoymentData.clear();
        for (int i = 0; i < 10; i++) {
            mEnjoymentData.add(new TestBean("item" + i));
        }
        mEnjoymentAdapter.notifyDataSetChanged();
        mEnjoymentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(BirthdayActivity.this);
            }
        });


        mPackageData.clear();
        for (int i = 0; i < 10; i++) {
            mPackageData.add(new TestBean("item" + i));
        }
        mPackageAdapter.notifyDataSetChanged();
        mPackageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(BirthdayActivity.this);
            }
        });


        mPopularData.clear();
        for (int i = 0; i < 10; i++) {
            mPopularData.add(new TestBean("item" + i));
        }
        mPopularAdapter.notifyDataSetChanged();
        mPopularAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(BirthdayActivity.this);
            }
        });

        mWatchData.clear();
        for (int i = 0; i < 10; i++) {
            mWatchData.add(new TestBean("item" + i));
        }
        mWatchAdapter.notifyDataSetChanged();
        mWatchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(BirthdayActivity.this);
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
            case R.id.m_layout_one: // 今日头牌
                mLayoutOne.setBackgroundColor(cl_e95d62);
                mLayoutTwo.setBackgroundColor(cl_ff8483);
                mLayoutThree.setBackgroundColor(cl_ff8483);
                mLayoutFour.setBackgroundColor(cl_ff8483);
                mLayoutFive.setBackgroundColor(cl_ff8483);
                //滚动到顶部
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                break;
            case R.id.m_layout_two: // 专享好物
                mLayoutOne.setBackgroundColor(cl_ff8483);
                mLayoutTwo.setBackgroundColor(cl_e95d62);
                mLayoutThree.setBackgroundColor(cl_ff8483);
                mLayoutFour.setBackgroundColor(cl_ff8483);
                mLayoutFive.setBackgroundColor(cl_ff8483);
                //专享好物
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //今日头牌高度+字体像素高度
                        int twoHeight = mTvOne.getHeight() + mRlOne.getHeight() + (int) DensityUtil.px2dp(15 + 16);
                        mScrollView.smoothScrollTo(0, twoHeight);
                    }
                });
                break;
            case R.id.m_layout_three: // 品质美包
                mLayoutOne.setBackgroundColor(cl_ff8483);
                mLayoutTwo.setBackgroundColor(cl_ff8483);
                mLayoutThree.setBackgroundColor(cl_e95d62);
                mLayoutFour.setBackgroundColor(cl_ff8483);
                mLayoutFive.setBackgroundColor(cl_ff8483);
                //滚动到品质美包
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //专享好物+今日头牌+字体像素高度
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() + mRlTwo.getHeight() + (int) DensityUtil.px2dp(15 + 16 + 15 + 16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_four: // 流行配饰
                mLayoutOne.setBackgroundColor(cl_ff8483);
                mLayoutTwo.setBackgroundColor(cl_ff8483);
                mLayoutThree.setBackgroundColor(cl_ff8483);
                mLayoutFour.setBackgroundColor(cl_e95d62);
                mLayoutFive.setBackgroundColor(cl_ff8483);
                //滚动到流行配饰
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        // 专享好物+今日头牌+品质美包+字体像素高度
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() + mRlTwo.getHeight() + mTvThree.getHeight() + mRlThree.getHeight() + (int) DensityUtil.px2dp(15 + 16 + 15 + 16 + 15 + 16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_five: // 品质腕表
                mLayoutOne.setBackgroundColor(cl_ff8483);
                mLayoutTwo.setBackgroundColor(cl_ff8483);
                mLayoutThree.setBackgroundColor(cl_ff8483);
                mLayoutFour.setBackgroundColor(cl_ff8483);
                mLayoutFive.setBackgroundColor(cl_e95d62);
                //滚动到品质腕表
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        // 专享好物+今日头牌+品质美包+流行配饰+字体像素高度
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
