package com.hjkj.fuduoduo.activity.mine_fragment;

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
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.dialog.ApplicationCanceledDialog;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DefaultAddressBean;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnorderdetailsData;
import com.hjkj.fuduoduo.entity.bean.FreightMapBean;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.tool.TimeLeftUtil2;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 换货详情-已发货页面
 */
public class ExchangeDetails03Activity extends BaseActivity {
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
    TextView mTvTitle;
    @BindView(R.id.m_tv_title02)
    TextView mTvTitle02;
    @BindView(R.id.m_tv_title03)
    TextView mTvTitle03;
    @BindView(R.id.m_tv_title04)
    TextView mTvTitle04;
    @BindView(R.id.m_tv_text01)
    TextView mTvText01;
    @BindView(R.id.m_tv_text02)
    TextView mTvText02;
    @BindView(R.id.m_tv_logistics_content)
    TextView mTvLogisticeContent;
    @BindView(R.id.m_tv_logistics_time)
    TextView mTvLogisticeTime;
    @BindView(R.id.m_cv_negotiation_history)
    CardView mCvNegotiationHistory;
    @BindView(R.id.m_layout_logistics)
    LinearLayout mLayoutLogistics;
    @BindView(R.id.m_tv_application_canceled)
    TextView mTvApplicationCancled;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private DoqueryreturnorderdetailsData appResponseData;
    private String jumpKey;

    public static void openActivity(Context context, String orderDetailsId, String jumpKey) {
        Intent intent = new Intent(context, ExchangeDetails03Activity.class);
        intent.putExtra("orderDetailsId", orderDetailsId);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_exchange_details03;
    }

    @Override
    protected void initPageData() {
        jumpKey = getIntent().getStringExtra("jumpKey");
        String orderDetailsId = getIntent().getStringExtra("orderDetailsId");
        onRefundDetails(orderDetailsId);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(ExchangeDetails03Activity.this, cl_e51C23, 1);
        initRecyclerView();
        onLove();
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
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(ExchangeDetails03Activity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(ExchangeDetails03Activity.this, mData.get(position).getCommodity().getId());
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_cv_negotiation_history, R.id.m_tv_application_canceled, R.id.m_layout_logistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_cv_negotiation_history: // 协商历史
                NegotiationHistoryActivity.openActivity(ExchangeDetails03Activity.this, appResponseData.getReturnOrderDetails().getId(), appResponseData.getCommodity().getSupplierId());
                break;
            case R.id.m_tv_application_canceled: // 撤销申请
                new ApplicationCanceledDialog(ExchangeDetails03Activity.this)
                        .setListener(new ApplicationCanceledDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                onApplicationCanceled();
                            }
                        }).show();
                break;
            case R.id.m_layout_logistics: // 物流信息
                ViewLogistics02Activity.openActivity(ExchangeDetails03Activity.this, appResponseData.getCommoditySpecification().getSpecificationImage(), appResponseData.getFreightMap());
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
    private void onRefundDetails(String orderDetailsId) {
        OkGo.<AppResponse<DoqueryreturnorderdetailsData>>get(Api.ORDERS_DOQUERYRETURNORDERDETAILS)//
                .params("orderDetailsId", orderDetailsId) //订单详情
                .execute(new DialogCallBack<AppResponse<DoqueryreturnorderdetailsData>>(this, "") {
                    @Override
                    public void onSuccess(AppResponse<DoqueryreturnorderdetailsData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            appResponseData = simpleResponseAppResponse.getData();
                            refreshUi(jumpKey, appResponseData);
                        }
                    }
                });
    }

    /**
     * 处理数据
     *
     * @param jumpKey
     * @param appResponseData
     */
    private void refreshUi(String jumpKey, DoqueryreturnorderdetailsData appResponseData) {
        if ("换货中".equals(jumpKey)) {
            mTvTitle.setText("换货详情");
            mTvTitle02.setText("请等待商家收货并再次发货");
            mTvTitle03.setText("如果商家收到货并验收无误，将再次发货给您");
            mTvTitle04.setText("换货信息");
            mTvText01.setText("如果商家拒绝，您可以咨询客服小秘解决。");
            mTvText02.setText("如果商家超时未处理，商家将自动确认收货。");
            // 商品图片
            GlideUtils.loadImage(ExchangeDetails03Activity.this, appResponseData.getExchangeSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
            // 商品名称
            mTvContent.setText(appResponseData.getCommodity().getName());
            //换成
            mTvChangeto.setText("换成：" + appResponseData.getExchangeSpecification().getCommoditySpecification());
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
            mTvAddress.setText(freightAddress.getName() + "  " + freightAddress.getMobilephoneNumber() + "  " + freightAddress.getProvince() + freightAddress.getCity() + freightAddress.getArea() + freightAddress.getStreet());
        } else if ("退款中".equals(jumpKey)) {
            mTvTitle.setText("退款详情");
            mTvTitle02.setText("请等待商家收货并退款");
            mTvTitle03.setText("如果商家收到货并验收无误，将操作退款给您");
            mTvTitle04.setText("退款信息");
            mTvText01.setText("如果商家拒绝，您可以咨询客服小秘解决。");
            mTvText02.setText("如果商家超时未处理，将自动退款给你。");
            // 商品图片
            GlideUtils.loadImage(ExchangeDetails03Activity.this, appResponseData.getCommoditySpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
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
//            // 退款编号:
//            if (appResponseData.getReturnOrder().getReturnNumber() != null && !appResponseData.getReturnOrder().getReturnNumber().isEmpty()) {
//                mTvExchangenumbering.setVisibility(View.VISIBLE);
//                mTvExchangenumbering.setText("退款编号：" + appResponseData.getReturnOrder().getReturnNumber());
//            } else {
//                mTvExchangenumbering.setVisibility(View.GONE);
//            }
            FreightMapBean freightMap = appResponseData.getFreightMap();
            // 退货物流
            mTvLogisticeContent.setText("退货物流：" + freightMap.getFreightCompany() + "(" + freightMap.getFreightCode() + ")" + freightMap.getFreightDate().get(0).getContext());
            // 物流时间
            mTvLogisticeTime.setText(freightMap.getFreightDate().get(0).getFtime());
        }
    }

    /**
     * 猜你喜欢接口
     */
    private void onLove() {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DOFINDMAYBEYOULIKE)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindMaybeYouLikeData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoFindMaybeYouLikeData> data = simpleResponseAppResponse.getData();
                            mData.addAll(data);
                            myScrollView.smoothScrollTo(0, 20);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}
