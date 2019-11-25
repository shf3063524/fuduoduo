package com.fdw.fdd.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.mine_fragment.OrderDetails02Activity;
import com.fdw.fdd.activity.mine_fragment.OrderDetails03Activity;
import com.fdw.fdd.activity.mine_fragment.OrderDetails04Activity;
import com.fdw.fdd.activity.mine_fragment.OrderDetailsActivity;
import com.fdw.fdd.activity.mine_fragment.ViewLogisticsActivity;
import com.fdw.fdd.activity.product.StoreDetailsActivity;
import com.fdw.fdd.adapter.MyOrderAdapter;
import com.fdw.fdd.base.BaseFragment;
import com.fdw.fdd.dialog.CancelOrderDialog;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.UserManager;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import ezy.ui.layout.LoadingLayout;

/**
 * 全部订单-Fragment
 * Author：Created by shihongfei on 2019/10/3 17:09
 * Email：1511808259@qq.com
 */
public class MyOrderFragment extends BaseFragment {
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    private ArrayList<DoQueryOrdersDetailsData> mData;
    private MyOrderAdapter mAdapter;
    private String saleState;

    public static MyOrderFragment newInstance(String saleState) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("saleState", saleState);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_my_order;
    }

    @Override
    protected void initPageData(Bundle bundle) {
        super.initPageData(bundle);
        if (getArguments() != null) {
            saleState = getArguments().getString("saleState");
            orderData(saleState);
        }
    }

    @Override
    protected void initViews() {
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
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

    @Override
    public void onResume() {
        super.onResume();
        orderData(saleState);
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new MyOrderAdapter(R.layout.item_my_order, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
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
                        switch (mData.get(position).getOrder().getSaleState()) {
                            case "1":
                                OrderDetailsActivity.openActivity(mContext, mData.get(position).getOrder().getId());
                                break;
                            case "2":
                                OrderDetails02Activity.openActivity(mContext, mData.get(position).getOrder().getId());
                                break;
                            case "3":
                                OrderDetails03Activity.openActivity(mContext, mData.get(position).getOrder().getId());
                                break;
                            case "4":
                                OrderDetails04Activity.openActivity(mContext, mData.get(position).getOrder().getId());
                                break;
                        }
                        break;
                    case R.id.m_layout_store: // 店铺详情
                        StoreDetailsActivity.openActivity(mContext, mData.get(position).getOrder().getSupplierId());
                        break;
                    case R.id.m_tv_one: // 联系买家
                        Toasty.info(mContext, "联系买家").show();
                        break;
                    case R.id.m_tv_two: // 取消订单
                        new CancelOrderDialog(mContext)
                                .setListener(new CancelOrderDialog.OnCancleOrderClickListener() {
                                    @Override
                                    public void onancleOrderClick(String cancleOrderType) {
                                        onCancelOrder(mData.get(position).getOrder().getId(), mData.get(position).getOrder().getSupplierId(), cancleOrderType);
                                    }
                                }).show();
                        break;
                    case R.id.m_tv_three: // 立即付款
                        Toasty.info(mContext, "立即付款").show();
                        break;
                    case R.id.m_tv_four: // 提醒发货
                        remindDhipment(mData.get(position).getOrder().getId());
                        break;
                    case R.id.m_tv_five: // 查看物流
                        ViewLogisticsActivity.openActivity(mContext);
                        break;
                    case R.id.m_tv_six: // 确认收货
                        Toasty.info(mContext, "确认收货").show();
                        break;
                    case R.id.m_tv_seven: // 查看物流
                        ViewLogisticsActivity.openActivity(mContext);
                        break;
                    case R.id.m_tv_eight: // 评价
//                        PostEvaluationActivity.openActivity(mContext);
                        break;
                }
            }
        });

    }

    @Override
    protected void lazyLoad() {

    }


    private void orderData(String saleState) {
        String userId = UserManager.getUserId(mContext);
        OkGo.<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>get(Api.ORDERS_DOQUERYORDERSDETAILS)//
                .params("id", userId)
                .params("saleState", saleState)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryOrdersDetailsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            if (unbinder != null) {
                                mData.clear();
                                ArrayList<DoQueryOrdersDetailsData> tempList = simpleResponseAppResponse.getData();
                                mData.addAll(tempList);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                        if (mData.size() > 0 && unbinder != null) {
                            mLoadingLayout.showContent();
                        }
                        if (unbinder != null) {
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.finishLoadMore();
                        }
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
        String consumerId = UserManager.getUserId(mContext);
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
                            Toasty.info(mContext, "取消订单成功").show();
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
                            Toasty.info(mContext, "提醒发货成功啦！").show();
                        }
                    }
                });
    }
}
