package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.PayFailureActivity;
import com.fdw.fdd.activity.product.PaySuccessActivity;
import com.fdw.fdd.activity.product.StoreDetailsActivity;
import com.fdw.fdd.adapter.MyOrderAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.dialog.CancelOrderDialog;
import com.fdw.fdd.dialog.ConfirmPaymentDialog;
import com.fdw.fdd.dialog.PayPasswordDialog;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.kefu.LoginKeFu02Activity;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.tool.kefutool.Constant;
import com.fdw.fdd.view.ClearEditText;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ezy.ui.layout.LoadingLayout;

/**
 * 查询订单-页面
 */
public class InquireOrderActivity extends BaseActivity {
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_et_shop)
    ClearEditText mEtShop;

    private ArrayList<DoQueryOrdersDetailsData> mData;
    private MyOrderAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, InquireOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_inquire_order;
    }

    @Override
    protected void initViews() {
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
        orderData("0");
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(true);
    }

    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
        mLoadingLayout.setEmptyImage(R.drawable.ic_no_orders);
        mLoadingLayout.setEmptyText("");
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new MyOrderAdapter(R.layout.item_my_order, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(InquireOrderActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_cv_item: // 条目点击
                        switch (mAdapter.getData().get(position).getOrder().getSaleState()) {
                            case "1":
                                OrderDetailsActivity.openActivity(InquireOrderActivity.this, mAdapter.getData().get(position).getOrder().getId());
                                break;
                            case "2":
                                OrderDetails02Activity.openActivity(InquireOrderActivity.this, mAdapter.getData().get(position).getOrder().getId());
                                break;
                            case "3":
                                OrderDetails03Activity.openActivity(InquireOrderActivity.this, mAdapter.getData().get(position).getOrder().getId());
                                break;
                            case "4":
                                OrderDetails04Activity.openActivity(InquireOrderActivity.this, mAdapter.getData().get(position).getOrder().getId());
                                break;
                        }
                        break;
                    case R.id.m_layout_store: // 店铺详情
                        StoreDetailsActivity.openActivity(InquireOrderActivity.this, mAdapter.getData().get(position).getOrder().getSupplierId());
                        break;
                    case R.id.m_tv_one: // 联系买家
                        String phoneNumber = UserManager.getPhoneNumber(InquireOrderActivity.this);
                        Intent intent = new Intent();
                        intent.putExtra(Constant.MESSAGE_TO_INTENT_EXTRA, Constant.MESSAGE_TO_AFTER_SALES);
                        intent.putExtra("phone", phoneNumber);
                        intent.setClass(InquireOrderActivity.this, LoginKeFu02Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.m_tv_two: // 取消订单
                        new CancelOrderDialog(InquireOrderActivity.this)
                                .setListener(new CancelOrderDialog.OnCancleOrderClickListener() {
                                    @Override
                                    public void onancleOrderClick(String cancleOrderType) {
                                        onCancelOrder(mAdapter.getData().get(position).getOrder().getId(), mAdapter.getData().get(position).getOrder().getSupplierId(), cancleOrderType);
                                    }
                                }).show();
                        break;
                    case R.id.m_tv_three: // 立即付款
                        new ConfirmPaymentDialog(InquireOrderActivity.this, DoubleUtil.double2Str(mAdapter.getData().get(position).getOrder().getActualPrice()))
                                .setListener(new ConfirmPaymentDialog.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        new PayPasswordDialog(InquireOrderActivity.this)
                                                .setListener(new PayPasswordDialog.OnClickListener() {
                                                    @Override
                                                    public void onClick(String payPassword) {
                                                        ordersDoPay(payPassword, mAdapter.getData().get(position).getOrder().getPayNumber(), DoubleUtil.double2Str(mData.get(position).getOrder().getActualPrice()));
                                                    }
                                                }).show();
                                    }
                                }).show();
                        break;
                    case R.id.m_tv_four: // 提醒发货
                        remindDhipment(mAdapter.getData().get(position).getOrder().getId());
                        break;
                    case R.id.m_tv_five: // 查看物流
                        orderDetails(mAdapter.getData().get(position).getOrder().getId(), "查看物流");
                        break;
                    case R.id.m_tv_six: // 确认收货
                        onConfirm(mAdapter.getData().get(position).getOrder().getId());
                        break;
                    case R.id.m_tv_seven: // 查看物流
                        orderDetails(mAdapter.getData().get(position).getOrder().getId(), "查看物流");
                        break;
                    case R.id.m_tv_eight: // 评价
                        orderDetails(mAdapter.getData().get(position).getOrder().getId(), "评价");
                        break;
                }
            }
        });

        mEtShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String newText = charSequence.toString().trim();
                mAdapter.getFilter().filter(newText); // 当数据改变时，调用过滤器；
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
    private void orderData(String saleState) {
        String userId = UserManager.getUserId(InquireOrderActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>get(Api.ORDERS_DOQUERYORDERSDETAILS)//
                .params("id", userId)
                .params("saleState", saleState)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryOrdersDetailsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoQueryOrdersDetailsData> tempList = simpleResponseAppResponse.getData();
                            mData.addAll(tempList);

                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                        if (mData.size() > 0) {
                            mLoadingLayout.showContent();
                        }
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
                });
    }

    /**
     * 取消订单
     *
     * @param ordersId     订单id
     * @param supplierId   商户id
     * @param cancelReason 取消原因
     */
    private void onCancelOrder(String ordersId, String supplierId, String cancelReason) {
        String consumerId = UserManager.getUserId(InquireOrderActivity.this);
        OkGo.<AppResponse>get(Api.ORDERS_DOCANCELORDER)//
                .params("ordersId", ordersId)
                .params("supplierId", supplierId)
                .params("consumerId", consumerId)
                .params("cancelReason", cancelReason)
                .params("type", "0")
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            Toasty.info(InquireOrderActivity.this, "取消订单成功").show();
                        }
                    }
                });
    }

    /**
     * 提醒发货
     */
    private void remindDhipment(String orderId) {
        OkGo.<AppResponse>get(Api.ORDERS_DOREMINDSEND)//
                .params("orderId", orderId)
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            Toasty.info(InquireOrderActivity.this, "提醒发货成功啦！").show();
                        }
                    }
                });
    }

    /**
     * 多订单立即支付
     */
    private void ordersDoPay(String payPassword, String payNumbers, String totalPrice) {
        String id = UserManager.getUserId(InquireOrderActivity.this);
        Double mul = DoubleUtil.mul(Double.parseDouble(totalPrice), 100.00);
        OkGo.<AppResponse>get(Api.ORDERS_DOPAY)//
                .params("id", id) //
                .params("payPassword", payPassword) //
                .params("actualPrice", DoubleUtil.doubleTransf(mul)) //
                .params("payNumbers", payNumbers) //
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.getState() == 0) {
                            Toasty.info(InquireOrderActivity.this, simpleResponseAppResponse.getMessage()).show();
                            PayFailureActivity.openActivity(InquireOrderActivity.this);
                        } else {
                            PaySuccessActivity.openActivity(InquireOrderActivity.this);
                        }
                    }
                });

    }

    /**
     * @param orderId
     */
    private void orderDetails(String orderId, String state) {
        String userId = UserManager.getUserId(InquireOrderActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>get(Api.ORDERS_DOQUERYORDERSDETAILS)//
                .params("id", userId)
                .params("orderId", orderId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryOrdersDetailsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoQueryOrdersDetailsData> responseData = simpleResponseAppResponse.getData();
                            if ("查看物流".equals(state)) {
                                ViewLogisticsActivity.openActivity(InquireOrderActivity.this, responseData.get(0).getOrder().getOrderNumber());
                            } else {
                                PostEvaluationActivity.openActivity(InquireOrderActivity.this, responseData);
                            }
                        }
                    }
                });
    }


    /**
     * 订单确认收货
     */
    private void onConfirm(String id) {
        OkGo.<AppResponse>get(Api.ORDERS_DORECEIVE)//
                .params("id", id) // 订单id
                .params("saleState", "4") //交易状态
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            Toasty.info(InquireOrderActivity.this, "确认收货成功").show();
                            mData.clear();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
