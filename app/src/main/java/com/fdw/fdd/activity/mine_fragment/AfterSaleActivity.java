package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.adapter.AfterSaleAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.bean.DoqueryreturnorderdetailsData;
import com.fdw.fdd.entity.bean.DoqueryreturnordersData;
import com.fdw.fdd.entity.bean.OrderDetailsBean;
import com.fdw.fdd.entity.bean.VcodeLoginData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.UserManager;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import ezy.ui.layout.LoadingLayout;

/**
 * 售后-页面
 * Author：Created by shihongfei on 2019/10/8 20:30
 * Email：1511808259@qq.com
 */
public class AfterSaleActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private ArrayList<DoqueryreturnordersData> mData;
    private AfterSaleAdapter mAdapter;
    private ArrayList<DoQueryOrdersDetailsData> doQueryOrdersDetailsData;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, AfterSaleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_after_sale;
    }

    @Override
    protected void initViews() {
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
    }

    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
        mLoadingLayout.setEmptyImage(R.drawable.ic_no_orders);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new AfterSaleAdapter(R.layout.item_after_sale, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AfterSaleActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_cv_item:
                        switch (mData.get(position).getRefunding()) {
                            case "买家取消":
                            case "卖家取消":
                                OrderDetails05Activity.openActivity(AfterSaleActivity.this, mData.get(position).getReturnOrder().getId());
                                break;
                            case "仅退款处理中":
                                orderDetails(mData.get(position).getReturnOrder().getId());
                                break;
                            case "卖家申请换货":
                                ExchangeDetailsActivity.openActivity(AfterSaleActivity.this, mData.get(position).getReturnDetailsList().getReturnOrderDetails().getOrderDetailsId());
                                break;
                            case "等待商家处理换货申请":
                                ExchangeDetailsActivity.openActivity(AfterSaleActivity.this, mData.get(position).getReturnDetailsList().getReturnOrderDetails().getOrderDetailsId());
                                break;
                            case "换货中":
                                onRefundDetails(mData.get(position).getReturnDetailsList().getReturnOrderDetails().getOrderDetailsId(), "换货中");
                                break;
                            case "商家拒绝换货请求":
                                OrderDetails05Activity.openActivity(AfterSaleActivity.this, mData.get(position).getReturnOrder().getId());
                                break;
                            case "换货完成":
                                ReplacementCompletedActivity.openActivity(AfterSaleActivity.this, mData.get(position).getReturnDetailsList().getReturnOrderDetails().getOrderDetailsId());
                                break;
                            case "商家发起仅退款":
                                orderDetail(mData.get(position).getOrder().getId(), mData.get(position).getReturnOrder().getOrderDetailsId());
                                break;
                            case "商家发起退货退款":
                                orderDetail(mData.get(position).getOrder().getId(), mData.get(position).getReturnOrder().getOrderDetailsId());
                                break;
                            case "退货退款处理中":
                                orderDetail(mData.get(position).getOrder().getId(), mData.get(position).getReturnOrder().getOrderDetailsId());
                                break;
                            case "退款中":
                                onRefundDetails(mData.get(position).getReturnDetailsList().getReturnOrderDetails().getOrderDetailsId(), "退款中");
                                break;
                            case "商家拒绝退款":
                                OrderDetails05Activity.openActivity(AfterSaleActivity.this, mData.get(position).getReturnOrder().getId());
                                break;
                            case "退款成功":
                                RefundDetailsActivity.openActivity(AfterSaleActivity.this, mData.get(position).getReturnDetailsList().getReturnOrderDetails().getOrderDetailsId());
                                break;
                        }

                        break;
                }
            }
        });
    }

    @OnClick({R.id.m_iv_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
        }
    }

    private void orderDetail(String orderId, String orderDetailsId) {
        OkGo.<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>get(Api.ORDERS_DOQUERYORDERSDETAILS)//
                .params("orderId", orderId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryOrdersDetailsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            doQueryOrdersDetailsData = simpleResponseAppResponse.getData();
                            refreshUi(doQueryOrdersDetailsData, orderDetailsId);
                        }
                    }
                });
    }

    private void refreshUi(ArrayList<DoQueryOrdersDetailsData> doQueryOrdersDetailsData, String orderDetailsId) {
        ArrayList<OrderDetailsBean> orderDetails = doQueryOrdersDetailsData.get(0).getOrderDetails();
        ArrayList<OrderDetailsBean> mOrderDetailsData = new ArrayList<>();
        for (OrderDetailsBean orderDetail : orderDetails) {
            if (orderDetailsId.equals(orderDetail.getOrderDetail().getId())) {
                mOrderDetailsData.add(new OrderDetailsBean(orderDetail.getCommodity(), orderDetail.getOrderDetail(), orderDetail.getRefunding(), orderDetail.getSpecification()));
            }
        }
        onFreightCalculation(mOrderDetailsData.get(0).getCommodity().getSupplierId(), mOrderDetailsData.get(0).getCommodity().getFreightTemplateName(), mOrderDetailsData.get(0).getOrderDetail().getNumber(), mOrderDetailsData.get(0));
    }

    /**
     * 单个商品订单详情运费计算
     *
     * @param supplierId       商户id
     * @param name             运费模板名称
     * @param number           商品数量
     * @param orderDetailsBean
     */
    private void onFreightCalculation(String supplierId, String name, String number, OrderDetailsBean orderDetailsBean) {
        OkGo.<AppResponse<VcodeLoginData>>get(Api.ORDERS_DOCOUNTFREIGHTPRICE)//
                .params("supplierId", supplierId)
                .params("name", name)
                .params("number", number)
                .execute(new JsonCallBack<AppResponse<VcodeLoginData>>() {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            String freightPrice = simpleResponseAppResponse.getData().getVcode();
                            OrderDetails02RefundDetailsActivity.openActivity(AfterSaleActivity.this, orderDetailsBean, doQueryOrdersDetailsData, freightPrice, "OrderDetails03Activity");
                        }
                    }
                });
    }

    @Override
    protected void requestData() {
        String userId = UserManager.getUserId(AfterSaleActivity.this);
        OkGo.<AppResponse<ArrayList<DoqueryreturnordersData>>>get(Api.ORDERS_DOQUERYRETURNORDERS)//
                .params("consumerId", userId)//
                .execute(new DialogCallBack<AppResponse<ArrayList<DoqueryreturnordersData>>>(this, "正在获取...") {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoqueryreturnordersData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoqueryreturnordersData> tempList = simpleResponseAppResponse.getData();
                            mData.addAll(tempList);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                        if (mData.size() > 0) {
                            mLoadingLayout.showContent();
                        } else {
                            mLoadingLayout.showEmpty();
                        }
                    }
                });
    }

    /**
     * @param returnOrderId 售后订单id
     */
    private void orderDetails(String returnOrderId) {
        OkGo.<AppResponse<ArrayList<DoqueryreturnordersData>>>get(Api.ORDERS_DOQUERYRETURNORDERS)//
                .params("returnOrderId", returnOrderId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoqueryreturnordersData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoqueryreturnordersData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoqueryreturnordersData> responseData = simpleResponseAppResponse.getData();
                            OrderDetails05RefundDetailsActivity.openActivity(AfterSaleActivity.this, responseData.get(0));
                        }
                    }
                });
    }

    /**
     * 查询退货订单详情
     */
    private void onRefundDetails(String orderDetailsId, String jumpKey) {
        OkGo.<AppResponse<DoqueryreturnorderdetailsData>>get(Api.ORDERS_DOQUERYRETURNORDERDETAILS)//
                .params("orderDetailsId", orderDetailsId) //订单详情
                .execute(new DialogCallBack<AppResponse<DoqueryreturnorderdetailsData>>(this, "") {
                    @Override
                    public void onSuccess(AppResponse<DoqueryreturnorderdetailsData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            DoqueryreturnorderdetailsData appResponseData = simpleResponseAppResponse.getData();
                            if ("1".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails02Activity.openActivity(AfterSaleActivity.this, orderDetailsId, jumpKey);
                            } else if ("2".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails03Activity.openActivity(AfterSaleActivity.this, orderDetailsId, jumpKey);
                            } else if ("3".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails04Activity.openActivity(AfterSaleActivity.this, orderDetailsId);
                            } else if ("4".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails05Activity.openActivity(AfterSaleActivity.this, orderDetailsId);
                            }
                        }
                    }
                });
    }

}
