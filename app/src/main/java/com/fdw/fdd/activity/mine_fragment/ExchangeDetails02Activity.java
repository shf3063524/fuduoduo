package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.ShoppingFragmentAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.dialog.ApplicationCanceledDialog;
import com.fdw.fdd.entity.bean.DefaultAddressBean;
import com.fdw.fdd.entity.bean.DoFindMaybeYouLikeData;
import com.fdw.fdd.entity.bean.DoqueryreturnorderdetailsData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.tool.StatusBarUtil;
import com.fdw.fdd.tool.TimeLeftUtil2;
import com.fdw.fdd.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import ezy.ui.layout.LoadingLayout;

/**
 * 换货详情页面-换货中跳转
 */
public class ExchangeDetails02Activity extends BaseActivity {
    @BindView(R.id.m_layout_modify_application)
    LinearLayout mLayoutModifyApplication;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_change_to)
    TextView mTvChangeto;
    @BindView(R.id.m_tv_time)
    TextView mTvTime;
    @BindView(R.id.m_tv_exchange_reason)
    TextView mTvExchangeReason;
    @BindView(R.id.m_tv_exchange_number)
    TextView mTvExchangeNumber;
    @BindView(R.id.m_tv_application_time)
    TextView mTvApplicationTime;
    @BindView(R.id.m_tv_exchange_numbering)
    TextView mTvExchangenumbering;
    @BindView(R.id.m_tv_address)
    TextView mTvAddress;
    @BindView(R.id.m_tv_title)
    TextView mTvTtile;
    @BindView(R.id.m_tv_title02)
    TextView mTvTtile02;
    @BindView(R.id.m_tv_title03)
    TextView mTvTtile03;
    @BindView(R.id.m_tv_text01)
    TextView mTvText01;
    @BindView(R.id.m_tv_text02)
    TextView mTvText02;
    @BindView(R.id.m_tv_text03)
    TextView mTvText03;
    @BindView(R.id.m_tv_address_name)
    TextView mTvAddressName;
    @BindView(R.id.m_tv_addres_phone)
    TextView mTvAddressPhone;
    @BindView(R.id.m_tv_address_address)
    TextView mTvAddressAddress;
    @BindView(R.id.m_cv_negotiation_history)
    CardView mCvNegotiationHistory;
    @BindView(R.id.m_tv_application_canceled)
    TextView mTvApplicationCancled;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private DoqueryreturnorderdetailsData appResponseData;
    private String orderDetailsId;
    private String jumpKey;
    private int startPage = 1;
    // 一次请求多少数据
    private static final int REQUEST_COUNT = 20;

    public static void openActivity(Context context, String orderDetailsId, String jumpKey) {
        Intent intent = new Intent(context, ExchangeDetails02Activity.class);
        intent.putExtra("orderDetailsId", orderDetailsId);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_exchange_details02;
    }

    @Override
    protected void initPageData() {
        jumpKey = getIntent().getStringExtra("jumpKey");
        orderDetailsId = getIntent().getStringExtra("orderDetailsId");
        onRefundDetails(jumpKey, orderDetailsId);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(ExchangeDetails02Activity.this, cl_e51C23, 1);
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(true);
    }

    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
//        mLoadingLayout.setEmptyImage(R.drawable.ic_no_address);
        mLoadingLayout.setEmptyText("暂无数据");
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLove();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(ExchangeDetails02Activity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(ExchangeDetails02Activity.this, mData.get(position).getCommodity().getId());
            }
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mAdapter.notifyDataSetChanged();
                startPage++;
                onLove();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mData.clear();
                startPage = 1;
                onLove();
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_cv_negotiation_history, R.id.m_tv_application_canceled, R.id.m_layout_modify_application})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_cv_negotiation_history: // 协商历史
                NegotiationHistoryActivity.openActivity(ExchangeDetails02Activity.this, appResponseData.getReturnOrderDetails().getId(), appResponseData.getCommodity().getSupplierId());
                break;
            case R.id.m_tv_application_canceled: // 撤销申请
                new ApplicationCanceledDialog(ExchangeDetails02Activity.this)
                        .setListener(new ApplicationCanceledDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                onApplicationCanceled();
                            }
                        }).show();
                break;
            case R.id.m_layout_modify_application: // 我已寄出 申请换货
                if (jumpKey.equals("换货中")) {
                    ApplyForAReplacement02Activity.openActivity(ExchangeDetails02Activity.this, appResponseData, "换货中");
                } else if (jumpKey.equals("退款中")) {
                    ApplyForAReplacement02Activity.openActivity(ExchangeDetails02Activity.this, appResponseData, "退款中");
                }
                break;
        }
    }

    /**
     * 撤销申请接口
     */
    private void onApplicationCanceled() {
        OkGo.<AppResponse>get(Api.ORDERS_DOCANCELRETURNORDER)//
                .params("id", appResponseData.getReturnOrderDetails().getId()) //退货订单详情id
                .params("agree", "5") //	撤销申请
                .execute(new DialogCallBack<AppResponse>(this, "") {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            finish();
                        }
                    }
                });
    }

    /**
     * 查询退货订单详情
     */
    private void onRefundDetails(String jumpKey, String orderDetailsId) {
        OkGo.<AppResponse<DoqueryreturnorderdetailsData>>get(Api.ORDERS_DOQUERYRETURNORDERDETAILS)//
                .params("orderDetailsId", orderDetailsId) //订单详情
                .execute(new DialogCallBack<AppResponse<DoqueryreturnorderdetailsData>>(this, "") {
                    @Override
                    public void onSuccess(AppResponse<DoqueryreturnorderdetailsData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            appResponseData = simpleResponseAppResponse.getData();
                            refreshUi(jumpKey, appResponseData);
                            onCheckAddress(appResponseData.getCommodity().getSupplierId());
                        }
                    }
                });
    }

    /**
     * 处理数据
     *
     * @param appResponseData
     * @param jumpKey
     */
    private void refreshUi(String jumpKey, DoqueryreturnorderdetailsData appResponseData) {
        if (jumpKey.equals("换货中")) {
            mTvTtile.setText("换货详情");
            mTvTtile02.setText("请退货并填写物流信息收件人按钮");
            mTvTtile03.setText("换货信息");
            mTvText01.setText("若商家同意：换货申请达成，请您及时退货。");
            mTvText02.setText("若商家拒绝：换货申请将关闭，您可以联系商家协商处理。");
            mTvText03.setText("若商家超时不响应：若线上库存充足则默认达成换货申请，若不足或商品已下架，则换货申请失败。");
            // 商品图片
            GlideUtils.loadImage(ExchangeDetails02Activity.this, appResponseData.getExchangeSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
            //换成
            mTvChangeto.setText("换成：" + appResponseData.getExchangeSpecification().getCommoditySpecification());
            // 商品名称
            mTvContent.setText(appResponseData.getCommodity().getName());
            // 原有
            mTvSpecification.setText("原有：" + appResponseData.getCommoditySpecification().getCommoditySpecification());
            // 换货原因
            mTvExchangeReason.setText("换货原因：" + appResponseData.getReturnOrderDetails().getReturnReason());
            // 换货数量
            mTvExchangeNumber.setText("换货数量：" + appResponseData.getReturnOrderDetails().getNumber());
            // 申请时间：
            mTvApplicationTime.setText("申请时间：" + appResponseData.getReturnOrderDetails().getCreateTime());
            // 换货时间倒计时
            mTvTime.setText(TimeLeftUtil2.doCalculate(appResponseData.getReturnOrderDetails().getCreateTime()));
            // 换货编号:
            mTvExchangenumbering.setText("换货编号：" + appResponseData.getReturnOrderDetails().getReturnOrderNumber());

            DefaultAddressBean freightAddress = appResponseData.getFreightAddress();
            // 地址
            mTvAddress.setText("收货地址：" + freightAddress.getName() + "  " + freightAddress.getMobilephoneNumber() + "  " + freightAddress.getProvince() + freightAddress.getCity() + freightAddress.getArea() + freightAddress.getStreet());
        } else if (jumpKey.equals("退款中")) {
            mTvTtile.setText("退款详情");
            mTvTtile02.setText("请退货并填写物流信息");
            mTvTtile03.setText("退款信息");
            mTvText01.setText("未与商家协商一致，请勿使用到付或平邮，以免商家拒签货物。");
            mTvText02.setText("交易的钱款还在福多多中间账号，确保您资金安全。");
            mTvText03.setText("请填写真实的物流信息，逾期未填写，退货申请将撤销。");
            // 商品图片
            GlideUtils.loadImage(ExchangeDetails02Activity.this, appResponseData.getCommoditySpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
            // 商品名称
            mTvContent.setText(appResponseData.getCommodity().getName());
            // 原有
            mTvSpecification.setText(appResponseData.getCommoditySpecification().getCommoditySpecification());
            // 退款原因
            mTvExchangeReason.setText("退款原因：" + appResponseData.getReturnOrderDetails().getReturnReason());
            // 退款金额
            mTvExchangeNumber.setText("退款金额：" + DoubleUtil.double2Str(appResponseData.getReturnOrder().getPrice()) + "积分");
            // 申请时间：
            mTvApplicationTime.setText("申请时间：" + appResponseData.getReturnOrderDetails().getCreateTime());
            // 换货时间倒计时
            mTvTime.setText(TimeLeftUtil2.doCalculate(appResponseData.getReturnOrderDetails().getCreateTime()));
            // 退款编号:
            mTvExchangenumbering.setText("退款编号：" + appResponseData.getReturnOrder().getReturnNumber());
        }
    }

    /**
     * 查询退货物流地址
     *
     * @param supplierId
     */
    private void onCheckAddress(String supplierId) {
        OkGo.<AppResponse<DefaultAddressBean>>get(Api.ORDERS_DOQUERYRETURNFREIGHTADDRESS)//
                .params("supplierId", supplierId) //订单详情
                .execute(new DialogCallBack<AppResponse<DefaultAddressBean>>(this, "") {
                    @Override
                    public void onSuccess(AppResponse<DefaultAddressBean> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            DefaultAddressBean responseAppResponseData = simpleResponseAppResponse.getData();
                            onAddress(responseAppResponseData);
                        }
                    }
                });
    }

    /**
     * 商家收货地址数据
     *
     * @param responseAppResponseData
     */
    private void onAddress(DefaultAddressBean responseAppResponseData) {
        // 收货人
        mTvAddressName.setText(responseAppResponseData.getName());
        // 收货人电话
        mTvAddressPhone.setText(responseAppResponseData.getMobilephoneNumber());
        // 收货人地址
        mTvAddressAddress.setText(responseAppResponseData.getProvince() + responseAppResponseData.getCity() + responseAppResponseData.getArea() + responseAppResponseData.getStreet());
    }

    /**
     * 猜你喜欢接口
     */
    private void onLove() {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DOFINDMAYBEYOULIKE)//
                .params("page", startPage)//
                .params("size", REQUEST_COUNT)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindMaybeYouLikeData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoFindMaybeYouLikeData> data = simpleResponseAppResponse.getData();
                            mData.addAll(data);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
//                        myScrollView.smoothScrollTo(0, 20);
                        mAdapter.notifyDataSetChanged();
                        if (mData.size() > 0) {
                            mLoadingLayout.showContent();
                        }
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
                });
    }
}
