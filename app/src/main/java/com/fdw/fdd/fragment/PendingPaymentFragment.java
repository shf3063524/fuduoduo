package com.fdw.fdd.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.mine_fragment.OrderDetailsActivity;
import com.fdw.fdd.activity.product.StoreDetailsActivity;
import com.fdw.fdd.adapter.PendingPaymentAdapter;
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
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ezy.ui.layout.LoadingLayout;

/**
 * 待付款-Fragment
 * Author：Created by shihongfei on 2019/10/8 19:28
 * Email：1511808259@qq.com
 */
public class PendingPaymentFragment extends BaseFragment {
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.all_chekbox)
    CheckBox mAllChekbox;
    @BindView(R.id.m_tv_go_to_pay)
    TextView mToGoToPay;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    private ArrayList<DoQueryOrdersDetailsData> mData;
    private PendingPaymentAdapter mAdapter;
    /**
     * 是否全选 默认未全选
     */
    private boolean isAllCheck = false;
    private String saleState;

    public static PendingPaymentFragment newInstance(String saleState) {
        PendingPaymentFragment fragment = new PendingPaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("saleState", saleState);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_pending_payment;
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
        mAdapter = new PendingPaymentAdapter(R.layout.item_pending_payment, mData);
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
                    case R.id.m_cv_item: // 订单详情
                        OrderDetailsActivity.openActivity(mContext, mData.get(position).getOrder().getId());
                        break;
                    case R.id.m_tv_one:// 联系卖家
                        Toasty.info(mContext, "联系卖家").show();
                        break;
                    case R.id.m_tv_two:
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

                    case R.id.m_layout_store: // 店铺详情
                        StoreDetailsActivity.openActivity(mContext, mData.get(position).getOrder().getSupplierId());
                        break;
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.all_chekbox, R.id.m_tv_go_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_chekbox:   // 全选
                // 修改数据源
                isAllCheck = !isAllCheck;
                for (DoQueryOrdersDetailsData data : mData) {
                    data.setClickable(isAllCheck);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.m_tv_go_to_pay:   // 合并付款
                Toasty.info(mContext, "合并付款").show();
                break;
        }
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
}

