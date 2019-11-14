package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ConfirmOrderActivtiy;
import com.hjkj.fuduoduo.adapter.ExchangeDetailsAdapter;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.dialog.ApplicationCanceledDialog;
import com.hjkj.fuduoduo.dialog.GoRechargeDialog;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryOrdersDetailsData;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnorderdetailsData;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
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
import es.dmoral.toasty.Toasty;

/**
 * 退款详情-待发货跳转
 */
public class OrderDetails02RefundDetailsActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_tv_application_canceled)
    TextView mTvApplicationCanceled;
    @BindView(R.id.m_tv_modify_application)
    TextView mTvModifyApplication;
    @BindView(R.id.m_tv_reason_for_return)
    TextView mTvReasonForReturn;
    @BindView(R.id.m_tv_refund_amount)
    TextView mTvRefundAmount;
    @BindView(R.id.m_tv_application_time)
    TextView mTvApplicationTime;
    @BindView(R.id.m_tv_refund_number)
    TextView mTvRefundNumber;
    @BindView(R.id.m_tv_remaining_time)
    TextView mTvRemainingTime;
    @BindView(R.id.m_cv_negotiation_history)
    CardView mCvNegotiationHistory;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;

    private ArrayList<TestBean> mData;
    private ShoppingFragmentAdapter mAdapter;
    private String freightPrice;
    private ArrayList<DoQueryOrdersDetailsData> detailsData;
    private OrderDetailsBean orderDetailsBean;
    private DoqueryreturnorderdetailsData appResponseData;

    /**
     * @param context
     * @param orderDetailsBean 单个商品数据
     * @param detailsData      整个订单数据
     * @param freightPrice     计算的运费
     */
    public static void openActivity(Context context, OrderDetailsBean orderDetailsBean, ArrayList<DoQueryOrdersDetailsData> detailsData, String freightPrice) {
        Intent intent = new Intent(context, OrderDetails02RefundDetailsActivity.class);
        intent.putExtra("OrderDetailsBean", orderDetailsBean);
        intent.putExtra("detailsData", detailsData);
        intent.putExtra("freightPrice", freightPrice);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_details02_refund_details;
    }

    @Override
    protected void initPageData() {
        freightPrice = getIntent().getStringExtra("freightPrice");
        detailsData = (ArrayList<DoQueryOrdersDetailsData>) getIntent().getSerializableExtra("detailsData");
        orderDetailsBean = (OrderDetailsBean) getIntent().getSerializableExtra("OrderDetailsBean");
        onRefundDetails(orderDetailsBean.getOrderDetail().getId());
        onProcessingData(orderDetailsBean);
    }

    /**
     * 数据处理
     *
     * @param orderDetailsBean
     */
    private void onProcessingData(OrderDetailsBean orderDetailsBean) {
        // 商品图片
        GlideUtils.loadImage(OrderDetails02RefundDetailsActivity.this, orderDetailsBean.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        // 商品名称
        mTvContent.setText(orderDetailsBean.getCommodity().getName());
        // 商品规格
        mTvSpecification.setText(orderDetailsBean.getSpecification().getCommoditySpecification());

    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(OrderDetails02RefundDetailsActivity.this, cl_e51C23, 1);
        initRecyclerView();
    }

    private void initRecyclerView() {

        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(OrderDetails02RefundDetailsActivity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {

        mData.clear();
        for (int i = 0; i < 10; i++) {
            mData.add(new TestBean("item" + i));
        }
        myScrollView.smoothScrollTo(0, 20);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_application_canceled, R.id.m_tv_modify_application, R.id.m_cv_negotiation_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_application_canceled: // 撤销申请
                new ApplicationCanceledDialog(OrderDetails02RefundDetailsActivity.this)
                        .setListener(new ApplicationCanceledDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                onApplicationCanceled();
                            }
                        }).show();
                break;
            case R.id.m_tv_modify_application: // 修改申请
                if (appResponseData.getReturnOrderDetails().getCreateTime().equals(appResponseData.getReturnOrderDetails().getUpdateTime())) {
                    ModifyApplicationActivity.openActivity(OrderDetails02RefundDetailsActivity.this, detailsData, orderDetailsBean, freightPrice, appResponseData);
                } else {
                    Toasty.info(OrderDetails02RefundDetailsActivity.this, "抱歉，您目前没有可发起的服务类型").show();
                    return;
                }
                break;
            case R.id.m_cv_negotiation_history: // 协商历史
                NegotiationHistoryActivity.openActivity(OrderDetails02RefundDetailsActivity.this,orderDetailsBean.getOrderDetail().getId(),orderDetailsBean.getCommodity().getSupplierId());
                break;
        }
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
                            refreshUi(appResponseData);
                        }
                    }
                });
    }

    /**
     * 接口数据处理
     *
     * @param responseData
     */
    private void refreshUi(DoqueryreturnorderdetailsData responseData) {
        // 退款原因
        mTvReasonForReturn.setText("退款原因：" + responseData.getReturnOrderDetails().getReturnReason());
        // 退款金额
        mTvRefundAmount.setText("退款金额：" + DoubleUtil.double2Str(responseData.getReturnOrder().getTotalPrice()));
        // 申请时间
        mTvApplicationTime.setText("申请时间：" + responseData.getReturnOrderDetails().getCreateTime());
        // 退款编号
        mTvRefundNumber.setText("退款编号：" + responseData.getReturnOrder().getReturnOrderNumber());
        // 还剩余多长时间
        mTvRemainingTime.setText(TimeLeftUtil2.doCalculate(responseData.getReturnOrder().getCreateTime()));
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
                            OrderDetails02RefundDetails02Activity.openActivity(OrderDetails02RefundDetailsActivity.this,orderDetailsBean);
                            finish();
                        }
                    }
                });
    }

}
