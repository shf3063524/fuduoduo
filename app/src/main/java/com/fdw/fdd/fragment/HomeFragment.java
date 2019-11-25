package com.fdw.fdd.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.fdw.fdd.activity.home_fragment.BeautyNewcomerActivity;
import com.fdw.fdd.activity.home_fragment.ElectricApplianceNewcomerActivity;
import com.fdw.fdd.activity.home_fragment.HomeSearchActivity;
import com.fdw.fdd.activity.home_fragment.MessageCenterActivity;
import com.fdw.fdd.activity.home_fragment.NewcomerEnjoymentActivity;
import com.fdw.fdd.activity.home_fragment.NewcomerExclusiveBenefitsActivity;
import com.fdw.fdd.adapter.BestSellingAdapter;
import com.fdw.fdd.adapter.PageSortsAdapter;
import com.fdw.fdd.entity.bean.BestSellingData;
import com.fdw.fdd.entity.bean.DoFindCategoryData;
import com.fdw.fdd.entity.bean.DoFindHomePageSortsData;
import com.fdw.fdd.entity.bean.DoquerycarouselData;
import com.fdw.fdd.entity.bean.DoqueryhotsaleData;
import com.fdw.fdd.entity.bean.VcodeLoginData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.AdViewpagerUtil;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.tool.GlobalParms;
import com.fdw.fdd.activity.home_fragment.BirthdayActivity;
import com.fdw.fdd.activity.home_fragment.GlobeActivity;
import com.fdw.fdd.activity.home_fragment.JdActivity;
import com.fdw.fdd.activity.home_fragment.LabourActivity;
import com.fdw.fdd.activity.home_fragment.MarriageActivity;
import com.fdw.fdd.activity.home_fragment.ReductionActivity;
import com.fdw.fdd.activity.home_fragment.SinstrokeActivity;
import com.fdw.fdd.activity.home_fragment.TravelActivity;
import com.fdw.fdd.activity.home_fragment.NationalDayActivity;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.MyFragmentPagerAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.base.BaseFragment;
import com.fdw.fdd.tool.UserManager;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 首页-Fragment
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.m_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.m_viewPager_tab)
    ViewPager mViewPagerTab;
    @BindView(R.id.m_point_group)
    LinearLayout mPointGroup;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_recycler_view_sort)
    RecyclerView mRecyclerViewSort;
    @BindView(R.id.m_iv_national)
    ImageView mIvNational;
    @BindView(R.id.m_iv_outdoor_sports)
    ImageView mIvOutdoorSports;
    @BindView(R.id.m_iv_overseas)
    ImageView mIvOvrseas;
    @BindView(R.id.m_iv_search)
    ImageView mIvSearch;
    @BindView(R.id.m_iv_message_center)
    ImageView mIvMessageCenter;
    @BindView(R.id.m_iv_day_work)
    ImageView mIvDayWork;
    @BindView(R.id.m_iv_beauty)
    ImageView mIvBeauty;
    @BindView(R.id.m_iv_new_exclusive_one)
    ImageView mIvNewExclusiveOne;
    @BindView(R.id.m_iv_new_exclusive_two)
    ImageView mIvNewExclusiveTwo;
    @BindView(R.id.m_iv_new_exclusive_three)
    ImageView mIvNewExclusiveThree;
    @BindView(R.id.m_iv_new_exclusive_four)
    ImageView mIvNewExclusiveFour;
    @BindView(R.id.m_tv_app_name)
    TextView mTvAppName;
    @BindView(R.id.m_slid_tab)
    SlidingTabLayout mSlidTab;

    @BindView(R.id.m_layout_jd)
    RelativeLayout mLayoutJd;
    @BindView(R.id.m_layout_globe)
    RelativeLayout mLayoutGlobe;
    @BindView(R.id.m_layout_reduction)
    RelativeLayout mLayoutReduction;
    @BindView(R.id.m_layout_birthday)
    RelativeLayout mLayoutBirthday;
    @BindView(R.id.m_layout_marriage)
    RelativeLayout mLayoutMarriage;
    @BindView(R.id.m_layout_sinstroke)
    RelativeLayout mLayoutSinstroke;
    @BindView(R.id.m_layout_travel)
    RelativeLayout mLayoutTravel;
    @BindView(R.id.m_layout_labour)
    RelativeLayout mLayoutLabour;
    @BindView(R.id.m_layout_as_soon)
    RelativeLayout mLayoutAsSoon;
    @BindView(R.id.m_layout_more_sort)
    RelativeLayout mLayoutMoreSort;
    @BindView(R.id.m_layout_expert_system)
    LinearLayout mLayoutExpertSystem;
    private AdViewpagerUtil adViewpagerUtil;
    private String[] urls;

    private String message;
    private String imageType;
    /**
     * 导航数据和adapter
     * mData&& BestSellingAdapter
     */
    private ArrayList<BestSellingData> mData;
    private BestSellingAdapter mBestSellingAdapter;

    /**
     * 轮播下分类数据和adapter
     * mPageSortsData && PageSortsAdapter
     */
    private ArrayList<DoFindHomePageSortsData> mPageSortsData;
    private PageSortsAdapter mPageSortsAdapter;
    private MyFragmentPagerAdapter mTabAdapter;
    private ArrayList<DoqueryhotsaleData> responseData;
    private ArrayList<DoquerycarouselData> responseAppResponseData;

    public static HomeFragment newInstance(String message) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPageData(Bundle bundle) {
        super.initPageData(bundle);
        if (getArguments() != null) {
            message = getArguments().getString("message");
        }
    }

    @Override
    protected void initViews() {
        initNewUser(message);
        initCarousel();
        onCarousel();
        bestSelling();
        initRecyclerView();
        classificationType();
        sortType();
        onDoQueryHotSale();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mBestSellingAdapter = new BestSellingAdapter(R.layout.item_best_selling, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mBestSellingAdapter);

        mPageSortsData = new ArrayList<>();
        mPageSortsAdapter = new PageSortsAdapter(R.layout.item_page_sorts, mPageSortsData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerViewSort.addItemDecoration(new GridSpacingItemDecoration(5, 22, true));
        mRecyclerViewSort.setLayoutManager(gridLayoutManager);
        mRecyclerViewSort.setAdapter(mPageSortsAdapter);
    }

    /**
     * @param message 0：新用户还是1：老用户判断标识
     */
    private void initNewUser(String message) {
        if ("0".equals(message)) {
            mLayoutExpertSystem.setVisibility(View.VISIBLE);
            mTvAppName.setText("福多多");
        } else {
            mLayoutExpertSystem.setVisibility(View.GONE);
            mTvAppName.setText(UserManager.getUserCompany(mContext));
        }
    }


    /**
     * 首页轮播
     */
    public void initCarousel() {

        // 图片资源id
        urls = new String[5];

        adViewpagerUtil = new AdViewpagerUtil(mContext, mViewPager, mPointGroup, 8, 4, urls);
        adViewpagerUtil.setOnAdItemClickListener(new AdViewpagerUtil.OnAdItemClickListener() {
            @Override
            public void onItemClick(View v, int flag) {
                ProductDetailsActivity.openActivity(mContext, responseAppResponseData.get(flag).getCommodityId());
            }
        });

        for (int i = 0; i < 4; i++) {
            Carousel("newConsumer", i);
        }
    }

    @Override
    protected void actionView() {
        mPageSortsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String jumpType = mPageSortsData.get(position).getJumpType();
                switch (jumpType) {
                    case "1": //京东，乐高，防暑，差旅
                        JdActivity.openActivity(mContext, mPageSortsData.get(position).getId());
                        break;
                    case "2"://劳保，婚孕，生日
                        JdActivity.openActivity(mContext, mPageSortsData.get(position).getId());
//                        LabourActivity.openActivity(mContext);
                        break;
                    case "3"://扶贫
                        JdActivity.openActivity(mContext, mPageSortsData.get(position).getId());
                        break;
                    case "4"://捐赠
                        Toasty.info(mContext, "捐赠").show();
                        break;
                    case "5"://更多分类
                        GlobalParms.sChangeFragment.changge(1);
                        break;
                }
            }
        });

        mBestSellingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(mContext, mData.get(position).getId());
            }
        });
    }

    @OnClick({R.id.m_iv_national, R.id.m_iv_outdoor_sports, R.id.m_iv_overseas, R.id.m_iv_day_work,
            R.id.m_iv_beauty, R.id.m_layout_jd, R.id.m_layout_globe, R.id.m_layout_reduction, R.id.m_layout_birthday, R.id.m_layout_marriage,
            R.id.m_layout_sinstroke, R.id.m_layout_travel, R.id.m_layout_labour, R.id.m_layout_as_soon, R.id.m_layout_more_sort,
            R.id.m_iv_message_center, R.id.m_iv_new_exclusive_one, R.id.m_iv_new_exclusive_two, R.id.m_iv_new_exclusive_three,
            R.id.m_iv_new_exclusive_four, R.id.m_iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_national:   // 欢度国庆
                NationalDayActivity.openActivity(mContext, responseData.get(0).getId());
                break;
            case R.id.m_iv_outdoor_sports:   // 户外活动
                NationalDayActivity.openActivity(mContext, responseData.get(1).getId());
                break;
            case R.id.m_iv_overseas:   // 海外精选
                NationalDayActivity.openActivity(mContext, responseData.get(2).getId());
                break;
            case R.id.m_iv_day_work:   // 日用洗护
                NationalDayActivity.openActivity(mContext, responseData.get(3).getId());
                break;
            case R.id.m_iv_beauty:   // 美妆防嗮
                NationalDayActivity.openActivity(mContext, responseData.get(4).getId());
                break;
            case R.id.m_layout_jd:   // 京东
//                JdActivity.openActivity(mContext);
                break;
            case R.id.m_layout_globe:   // 乐高
                GlobeActivity.openActivity(mContext);
                break;
            case R.id.m_layout_reduction:   // 扶贫
                ReductionActivity.openActivity(mContext);
                break;
            case R.id.m_layout_birthday:   // 生日
                BirthdayActivity.openActivity(mContext);
                break;
            case R.id.m_layout_marriage:   // 婚孕
                MarriageActivity.openActivity(mContext);
                break;
            case R.id.m_layout_sinstroke:   // 防暑
                SinstrokeActivity.openActivity(mContext);
                break;
            case R.id.m_layout_travel:   // 差旅
                TravelActivity.openActivity(mContext);
                break;
            case R.id.m_layout_labour:   // 劳保
                LabourActivity.openActivity(mContext);
                break;
            case R.id.m_layout_as_soon:   // 捐赠
                Toasty.info(mContext, "捐赠").show();
                break;
            case R.id.m_layout_more_sort:   // 更多分类
                GlobalParms.sChangeFragment.changge(1);
                break;
            case R.id.m_iv_message_center:   // 消息中心
                MessageCenterActivity.openActivity(mContext);
                break;
            case R.id.m_iv_new_exclusive_one: // 新人专享福利
                NewcomerExclusiveBenefitsActivity.openActivity(mContext);
                break;
            case R.id.m_iv_new_exclusive_two: //新人专享好物
                NewcomerEnjoymentActivity.openActivity(mContext);
                break;
            case R.id.m_iv_new_exclusive_three: // 美妆新人
                BeautyNewcomerActivity.openActivity(mContext);
                break;
            case R.id.m_iv_new_exclusive_four: // 电器新人
                ElectricApplianceNewcomerActivity.openActivity(mContext);
                break;
            case R.id.m_iv_search: // 搜索界面
                HomeSearchActivity.openActivity(mContext);
                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }

    /**
     * 轮播接口
     */

    private void Carousel(String typeImage, int page) {
        OkGo.<AppResponse<VcodeLoginData>>get(Api.IMAGE_DOFINDIMAGES)//
                .params("folder", typeImage)//
                .params("image", "page" + page)//
                .execute(new JsonCallBack<AppResponse<VcodeLoginData>>() {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            if (typeImage.equals("Carousel")) {
                                urls[page] = simpleResponseAppResponse.getData().getVcode();
                                adViewpagerUtil.setUrls(urls);
                                adViewpagerUtil.initVps();
                            } else if (typeImage.equals("Recommend")) {
                                switch (page) {
                                    case 0:
                                        GlideUtils.loadImage(mContext, simpleResponseAppResponse.getData().getVcode(), R.drawable.ic_all_background, mIvNational);
                                        break;
                                    case 1:
                                        GlideUtils.loadImage(mContext, simpleResponseAppResponse.getData().getVcode(), R.drawable.ic_all_background, mIvOvrseas);
                                        break;
                                    case 2:
                                        GlideUtils.loadImage(mContext, simpleResponseAppResponse.getData().getVcode(), R.drawable.ic_all_background, mIvOutdoorSports);
                                        break;
                                    case 3:
                                        GlideUtils.loadImage(mContext, simpleResponseAppResponse.getData().getVcode(), R.drawable.ic_all_background, mIvBeauty);
                                        break;
                                    case 4:
                                        GlideUtils.loadImage(mContext, simpleResponseAppResponse.getData().getVcode(), R.drawable.ic_all_background, mIvDayWork);
                                        break;
                                }
                            } else if (typeImage.equals("newConsumer")) {
                                switch (page) {
                                    case 0:
                                        GlideUtils.loadImage(mContext, simpleResponseAppResponse.getData().getVcode(), R.drawable.ic_all_background, mIvNewExclusiveOne);
                                        break;
                                    case 1:
                                        GlideUtils.loadImage(mContext, simpleResponseAppResponse.getData().getVcode(), R.drawable.ic_all_background, mIvNewExclusiveTwo);
                                        break;
                                    case 2:
                                        GlideUtils.loadImage(mContext, simpleResponseAppResponse.getData().getVcode(), R.drawable.ic_all_background, mIvNewExclusiveThree);
                                        break;
                                    case 3:
                                        GlideUtils.loadImage(mContext, simpleResponseAppResponse.getData().getVcode(), R.drawable.ic_all_background, mIvNewExclusiveFour);
                                        break;
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 轮播下分类展示接口
     */
    private void sortType() {
        OkGo.<AppResponse<ArrayList<DoFindHomePageSortsData>>>get(Api.HOMEPAGESORT_DOFINDHOMEPAGESORTS)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindHomePageSortsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindHomePageSortsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mPageSortsData.clear();
                            ArrayList<DoFindHomePageSortsData> tempList = simpleResponseAppResponse.getData();
                            mPageSortsData.addAll(tempList);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mPageSortsAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 爆款热卖
     */
    private void bestSelling() {
        OkGo.<AppResponse<ArrayList<BestSellingData>>>get(Api.COMMODITY_HOTSALE)//
                .execute(new JsonCallBack<AppResponse<ArrayList<BestSellingData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<BestSellingData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<BestSellingData> data = simpleResponseAppResponse.getData();
                            mData.clear();
                            mData.addAll(data);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mBestSellingAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 首页分类接口
     */
    private void classificationType() {
        OkGo.<AppResponse<ArrayList<DoFindCategoryData>>>get(Api.CATEGORY_DOFINDCATEGORY)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindCategoryData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindCategoryData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<String> mTabList = new ArrayList<>();
                            ArrayList<Fragment> mFragments = new ArrayList<>();
                            ArrayList<DoFindCategoryData> tempList = simpleResponseAppResponse.getData();
                            for (DoFindCategoryData doFindCategoryData : tempList) {
                                String name = doFindCategoryData.getNickName();
                                mTabList.add(name);
                                String categoryId = doFindCategoryData.getCategoryId();
                                mFragments.add(SlidingTabFragment.newInstance(categoryId));
                            }
                            mTabAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTabList);
                            mViewPagerTab.setAdapter(mTabAdapter);
                            mSlidTab.setViewPager(mViewPagerTab);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mTabAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 首页热卖活动信息获取接口
     */
    private void onDoQueryHotSale() {
        OkGo.<AppResponse<ArrayList<DoqueryhotsaleData>>>get(Api.HOMEPAGESORT_DOQUERYHOTSALE)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoqueryhotsaleData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoqueryhotsaleData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            responseData = simpleResponseAppResponse.getData();
                            refreshUi(responseData);
                        }
                    }
                });
    }

    /**
     * @param responseData
     */
    private void refreshUi(ArrayList<DoqueryhotsaleData> responseData) {
        // 欢度国庆
        GlideUtils.loadImage(mContext, responseData.get(0).getSortImage(), R.drawable.ic_all_background, mIvNational);
        GlideUtils.loadImage(mContext, responseData.get(1).getSortImage(), R.drawable.ic_all_background, mIvOvrseas);
        GlideUtils.loadImage(mContext, responseData.get(2).getSortImage(), R.drawable.ic_all_background, mIvOutdoorSports);
        GlideUtils.loadImage(mContext, responseData.get(3).getSortImage(), R.drawable.ic_all_background, mIvBeauty);
        GlideUtils.loadImage(mContext, responseData.get(4).getSortImage(), R.drawable.ic_all_background, mIvDayWork);
    }

    /**
     * 轮播图接口
     */
    private void onCarousel() {
        OkGo.<AppResponse<ArrayList<DoquerycarouselData>>>get(Api.HOMEPAGESORT_DOQUERYCAROUSEL)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoquerycarouselData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoquerycarouselData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            responseAppResponseData = simpleResponseAppResponse.getData();

                            for (int i = 0; i < responseAppResponseData.size(); i++) {
                                urls[i] = responseAppResponseData.get(i).getImage();
                            }
                            adViewpagerUtil.setUrls(urls);
                            adViewpagerUtil.initVps();
                        }
                    }
                });
    }
}