package com.fdw.fdd.activity.product;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.home_fragment.HomeSearchActivity;
import com.fdw.fdd.adapter.StoreDetailsAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.CommoditieBean;
import com.fdw.fdd.entity.bean.CommodityBean;
import com.fdw.fdd.entity.bean.DoQueryShopDetailsData;
import com.fdw.fdd.entity.bean.ShopBean;
import com.fdw.fdd.entity.bean.VcodeLoginData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.tool.StatusBarUtil;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 店铺详情-页面
 */
public class StoreDetailsActivity extends BaseActivity {
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
    @BindView(R.id.m_tv_store_name)
    TextView mTvStoreName;
    @BindView(R.id.m_tv_price)
    TextView mTvPrice;
    @BindView(R.id.m_iv_price)
    ImageView mIvPrice;
    @BindView(R.id.m_iv_search)
    ImageView mIvSearch;
    @BindView(R.id.m_layout_background)
    LinearLayout mLayoutBackground;
    @BindView(R.id.m_layout_price)
    LinearLayout mLayoutPrice;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    @BindColor(R.color.color_dark_transparent)
    int color_dark_transparent;
    @BindColor(R.color.cl_fff)
    int cl_fff;
    /**
     * 收藏-按钮点击判断标识
     */
    private boolean checkCollect = false;
    /**
     * 价格-上下标切换标识
     */
    private boolean check = false;

    private ArrayList<CommodityBean> mData;
    private StoreDetailsAdapter mAdapter;
    private String supplierId;
    private String collection;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, StoreDetailsActivity.class);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param supplierId 商户id
     */
    public static void openActivity(Context context, String supplierId) {
        Intent intent = new Intent(context, StoreDetailsActivity.class);
        intent.putExtra("supplierId", supplierId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_store_details;
    }

    @Override
    protected void initPageData() {
        supplierId = getIntent().getStringExtra("supplierId");

        storeDetail();
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(StoreDetailsActivity.this, color_dark_transparent, 1);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new StoreDetailsAdapter(R.layout.item_store_details, mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(StoreDetailsActivity.this, 2));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 10, true));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(StoreDetailsActivity.this, mData.get(position).getId());
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_iv_collect, R.id.m_tv_complex, R.id.m_tv_volume, R.id.m_layout_price, R.id.m_iv_search})
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
                    mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_white_collection));
                    delete();
                } else {
                    checkCollect = true;
                    mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_red_collection));
                    addCollection();
                }
                break;
            case R.id.m_tv_complex: // 综合
                mTvComplex.setTextColor(cl_e51C23);
                mTvVolume.setTextColor(cl_fff);
                mTvPrice.setTextColor(cl_fff);
                mTvComplex.setBackground(getResources().getDrawable(R.drawable.ic_select_white));
                mTvVolume.setBackgroundColor(getResources().getColor(R.color.transparent));
                mLayoutPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
                mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_price_sort));
                storeDetail();
                break;
            case R.id.m_tv_volume: // 销量
                mTvVolume.setTextColor(cl_e51C23);
                mTvComplex.setTextColor(cl_fff);
                mTvPrice.setTextColor(cl_fff);
                mTvComplex.setBackgroundColor(getResources().getColor(R.color.transparent));
                mTvVolume.setBackground(getResources().getDrawable(R.drawable.ic_select_white));
                mLayoutPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
                mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_price_sort));
                salesData();
                break;
            case R.id.m_layout_price: // 价格
                if (check) {
                    check = false;
                    mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_from_high_to_low));
                    priceData("0");
                } else {
                    check = true;
                    mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_from_low_to_high));
                    priceData("1");
                }
                mTvVolume.setTextColor(cl_fff);
                mTvComplex.setTextColor(cl_fff);
                mTvPrice.setTextColor(cl_e51C23);
                mTvComplex.setBackgroundColor(getResources().getColor(R.color.transparent));
                mTvVolume.setBackgroundColor(getResources().getColor(R.color.transparent));
                mLayoutPrice.setBackground(getResources().getDrawable(R.drawable.ic_select_white));
                break;
            case R.id.m_iv_search: // 搜索
                HomeSearchActivity.openActivity(StoreDetailsActivity.this);
                break;
        }
    }

    /**
     * 综合数据
     */
    protected void storeDetail() {
        String userId = UserManager.getUserId(StoreDetailsActivity.this);
        OkGo.<AppResponse<DoQueryShopDetailsData>>get(Api.COMMODITY_DOQUERYSHOPDETAILS)//
                .params("supplierId", supplierId)
                .params("userId", userId)
                .execute(new JsonCallBack<AppResponse<DoQueryShopDetailsData>>() {
                    @Override
                    public void onSuccess(AppResponse<DoQueryShopDetailsData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            DoQueryShopDetailsData data = simpleResponseAppResponse.getData();
                            refreshUi(data);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 数据处理
     *
     * @param data
     */
    private void refreshUi(DoQueryShopDetailsData data) {
        // 店铺背景
        GlideUtils.loadBgForTheView(StoreDetailsActivity.this, data.getShop().getShopImage(), mLayoutBackground);
        // 商店地址
        ShopBean shop = data.getShop();
        String name = shop.getName();
        mTvStoreName.setText(name);

        ArrayList<CommoditieBean> commodities = data.getCommodities();
        mData.clear();
        for (CommoditieBean commoditieBean : commodities) {
            CommodityBean commodity = commoditieBean.getCommodity();
            mData.add(commodity);
        }

        // 收藏
        collection = data.getCollection();
        if ("0".equals(collection)) {
            mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_white_collection));
            checkCollect = false;
        } else {
            mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_red_collection));
            checkCollect = true;
        }
    }

    /**
     * 销量数据 商户id
     */
    private void salesData() {
        String userId = UserManager.getUserId(StoreDetailsActivity.this);
        OkGo.<AppResponse<ArrayList<CommoditieBean>>>get(Api.COMMODITY_DOQUERYBYFICTITIOUSVOLUME)//
                .params("supplierId", supplierId)
                .execute(new JsonCallBack<AppResponse<ArrayList<CommoditieBean>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<CommoditieBean>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<CommoditieBean> data = simpleResponseAppResponse.getData();
                            mData.clear();
                            for (CommoditieBean commoditieBean : data) {
                                CommodityBean commodity = commoditieBean.getCommodity();
                                mData.add(commodity);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 价格数据
     *
     * @param state
     */
    private void priceData(String state) {
        OkGo.<AppResponse<ArrayList<CommoditieBean>>>get(Api.COMMODITY_DOQUERYBYPRICE)//
                .params("supplierId", supplierId)
                .params("state", state)
                .execute(new JsonCallBack<AppResponse<ArrayList<CommoditieBean>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<CommoditieBean>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<CommoditieBean> data = simpleResponseAppResponse.getData();
                            mData.clear();
                            for (CommoditieBean commoditieBean : data) {
                                CommodityBean commodity = commoditieBean.getCommodity();
                                mData.add(commodity);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 添加收藏夹接口
     */
    private void addCollection() {
        String userId = UserManager.getUserId(StoreDetailsActivity.this);
        OkGo.<AppResponse<VcodeLoginData>>get(Api.USER_DOCOLLECT)//
                .params("userId", userId)
                .params("collectionsId", supplierId)
                .params("type", "1")
                .execute(new JsonCallBack<AppResponse<VcodeLoginData>>() {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            String vcode = simpleResponseAppResponse.getData().getVcode();
                            collection = vcode;
                            Toasty.info(StoreDetailsActivity.this, "收藏成功").show();
                        }

                    }
                });
    }

    /**
     * 删除需要传的id
     */
    private void delete() {
        OkGo.<AppResponse>get(Api.USER_DODELETECOLLECTIONS)//
                .params("ids", collection)
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        Toasty.normal(StoreDetailsActivity.this, "删除成功").show();
                    }
                });
    }
}
