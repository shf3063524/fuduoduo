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
import com.fdw.fdd.adapter.OperatingAdapter;
import com.fdw.fdd.adapter.WorkUniformAdapter;
import com.fdw.fdd.adapter.OutdoorEssentialAdapter;
import com.fdw.fdd.adapter.SafetyAdapter;
import com.fdw.fdd.adapter.SafetyFirstAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.TestBean;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 劳保用品页面
 */
public class LabourActivity extends BaseActivity {
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
    @BindColor(R.color.cl_d64f00)
    int cl_d64f00;
    @BindColor(R.color.cl_e8671b)
    int cl_e8671b;
    private ArrayList<TestBean> mSafetyFirstData;
    private SafetyFirstAdapter mSafetyFirstAdapter;

    private ArrayList<TestBean> mOutdoorEssentialData;
    private OutdoorEssentialAdapter mOutdoorEssentialAdapter;


    private ArrayList<TestBean> mWorkUniformData;
    private WorkUniformAdapter mWorkUniformAdapter;

    private ArrayList<TestBean> mOperatingData;
    private OperatingAdapter mOperatingAdapter;

    private ArrayList<TestBean> mSafetyData;
    private SafetyAdapter mSafetyAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LabourActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_labour;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mSafetyFirstData = new ArrayList<>();
        mSafetyFirstAdapter = new SafetyFirstAdapter(R.layout.item_safety_first, mSafetyFirstData);
        LinearLayoutManager layoutManagerHometown = new LinearLayoutManager(LabourActivity.this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwOne.setLayoutManager(layoutManagerHometown);
        mRecyclerVeiwOne.setAdapter(mSafetyFirstAdapter);


        mOutdoorEssentialData = new ArrayList<>();
        mOutdoorEssentialAdapter = new OutdoorEssentialAdapter(R.layout.item_outdoor_essential, mOutdoorEssentialData);
        LinearLayoutManager layoutManagerFruits = new LinearLayoutManager(LabourActivity.this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwTwo.setLayoutManager(layoutManagerFruits);
        mRecyclerVeiwTwo.setAdapter(mOutdoorEssentialAdapter);


        mWorkUniformData = new ArrayList<>();
        mWorkUniformAdapter = new WorkUniformAdapter(R.layout.item_work_uniform, mWorkUniformData);
        GridLayoutManager gridLayoutManagerProducts = new GridLayoutManager(LabourActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwThree.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwThree.setLayoutManager(gridLayoutManagerProducts);
        mRecyclerVeiwThree.setAdapter(mWorkUniformAdapter);


        mOperatingData = new ArrayList<>();
        mOperatingAdapter = new OperatingAdapter(R.layout.item_operating, mOperatingData);
        GridLayoutManager gridLayoutManagerGoods = new GridLayoutManager(LabourActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwFour.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwFour.setLayoutManager(gridLayoutManagerGoods);
        mRecyclerVeiwFour.setAdapter(mOperatingAdapter);

        mSafetyData = new ArrayList<>();
        mSafetyAdapter = new SafetyAdapter(R.layout.item_safety, mSafetyData);
        GridLayoutManager gridLayoutManagerSemiFinished = new GridLayoutManager(LabourActivity.this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerVeiwFive.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        mRecyclerVeiwFive.setLayoutManager(gridLayoutManagerSemiFinished);
        mRecyclerVeiwFive.setAdapter(mSafetyAdapter);
    }

    @Override
    protected void actionView() {
        mSafetyFirstData.clear();
        for (int i = 0; i < 10; i++) {
            mSafetyFirstData.add(new TestBean("item" + i));
        }
        mSafetyFirstAdapter.notifyDataSetChanged();
        mSafetyFirstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(LabourActivity.this);
            }
        });

        mOutdoorEssentialData.clear();
        for (int i = 0; i < 10; i++) {
            mOutdoorEssentialData.add(new TestBean("item" + i));
        }
        mOutdoorEssentialAdapter.notifyDataSetChanged();
        mOutdoorEssentialAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(LabourActivity.this);
            }
        });


        mWorkUniformData.clear();
        for (int i = 0; i < 10; i++) {
            mWorkUniformData.add(new TestBean("item" + i));
        }
        mWorkUniformAdapter.notifyDataSetChanged();
        mWorkUniformAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(LabourActivity.this);
            }
        });


        mOperatingData.clear();
        for (int i = 0; i < 10; i++) {
            mOperatingData.add(new TestBean("item" + i));
        }
        mOperatingAdapter.notifyDataSetChanged();
        mOperatingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(LabourActivity.this);
            }
        });

        mSafetyData.clear();
        for (int i = 0; i < 10; i++) {
            mSafetyData.add(new TestBean("item" + i));
        }
        mSafetyAdapter.notifyDataSetChanged();
        mSafetyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(LabourActivity.this);
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
            case R.id.m_layout_one: // 安全第一
                mLayoutOne.setBackgroundColor(cl_d64f00);
                mLayoutTwo.setBackgroundColor(cl_e8671b);
                mLayoutThree.setBackgroundColor(cl_e8671b);
                mLayoutFour.setBackgroundColor(cl_e8671b);
                mLayoutFive.setBackgroundColor(cl_e8671b);
                //滚动到顶部
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                break;
            case R.id.m_layout_two: // 户外必备
                mLayoutOne.setBackgroundColor(cl_e8671b);
                mLayoutTwo.setBackgroundColor(cl_d64f00);
                mLayoutThree.setBackgroundColor(cl_e8671b);
                mLayoutFour.setBackgroundColor(cl_e8671b);
                mLayoutFive.setBackgroundColor(cl_e8671b);
                //户外必备
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //安全第一高度+字体像素高度
                        int twoHeight = mTvOne.getHeight() + mRlOne.getHeight() + (int) DensityUtil.px2dp(15 + 16);
                        mScrollView.smoothScrollTo(0, twoHeight);
                    }
                });
                break;
            case R.id.m_layout_three: // 工作制服
                mLayoutOne.setBackgroundColor(cl_e8671b);
                mLayoutTwo.setBackgroundColor(cl_e8671b);
                mLayoutThree.setBackgroundColor(cl_d64f00);
                mLayoutFour.setBackgroundColor(cl_e8671b);
                mLayoutFive.setBackgroundColor(cl_e8671b);
                //滚动到工作制服
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //安全第一+户外必备+字体像素高度
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() + mRlTwo.getHeight() + (int) DensityUtil.px2dp(15 + 16 + 15 + 16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_four: // 操作用具
                mLayoutOne.setBackgroundColor(cl_e8671b);
                mLayoutTwo.setBackgroundColor(cl_e8671b);
                mLayoutThree.setBackgroundColor(cl_e8671b);
                mLayoutFour.setBackgroundColor(cl_d64f00);
                mLayoutFive.setBackgroundColor(cl_e8671b);
                //滚动到操作用具
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        // 安全第一+户外必备+工作制服+字体像素高度
                        int threeHeight = mTvOne.getHeight() + mRlOne.getHeight() + mTvTwo.getHeight() + mRlTwo.getHeight() + mTvThree.getHeight() + mRlThree.getHeight() + (int) DensityUtil.px2dp(15 + 16 + 15 + 16 + 15 + 16);
                        mScrollView.smoothScrollTo(0, threeHeight);
                    }
                });
                break;
            case R.id.m_layout_five: // 安全护具
                mLayoutOne.setBackgroundColor(cl_e8671b);
                mLayoutTwo.setBackgroundColor(cl_e8671b);
                mLayoutThree.setBackgroundColor(cl_e8671b);
                mLayoutFour.setBackgroundColor(cl_e8671b);
                mLayoutFive.setBackgroundColor(cl_d64f00);
                //滚动到安全护具
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        // 安全第一+户外必备+工作制服+操作用具+字体像素高度
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
