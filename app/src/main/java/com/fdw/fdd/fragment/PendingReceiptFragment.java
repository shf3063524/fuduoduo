package com.fdw.fdd.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.mine_fragment.OrderDetails03Activity;
import com.fdw.fdd.activity.mine_fragment.ViewLogisticsActivity;
import com.fdw.fdd.activity.product.StoreDetailsActivity;
import com.fdw.fdd.adapter.PendingReceiptAdapter;
import com.fdw.fdd.base.BaseFragment;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.UserManager;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import ezy.ui.layout.LoadingLayout;

/**
 * 待收货-Fragment
 * Author：Created by shihongfei on 2019/10/8 20:14
 * Email：1511808259@qq.com
 */
public class PendingReceiptFragment extends BaseFragment {
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    private ArrayList<DoQueryOrdersDetailsData> mData;
    private PendingReceiptAdapter mAdapter;
    private String saleState;

    public static PendingReceiptFragment newInstance(String saleState) {
        PendingReceiptFragment fragment = new PendingReceiptFragment();
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
            orderData();
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
        orderData();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new PendingReceiptAdapter(R.layout.item_pending_receipt, mData);
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
                    case R.id.m_cv_item: // 条目
                        OrderDetails03Activity.openActivity(mContext, mData.get(position).getOrder().getId());
                        break;
                    case R.id.m_tv_one: // 查看物流
                        ViewLogisticsActivity.openActivity(mContext, mData.get(position).getOrder().getOrderNumber());
                        break;
                    case R.id.m_tv_three: // 确认收货
                        new CircleDialog.Builder()
                                .setCanceledOnTouchOutside(false)
                                .setCancelable(false)
                                .setTitle("确认收货")
                                .setText("您确定要收货吗？")
                                .configText(params -> {
                                    // params.gravity = Gravity.LEFT | Gravity.TOP;
                                    params.padding = new int[]{100, 0, 100, 50};
                                })
                                .setNegative("取消", null)
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onConfirm(mData.get(position).getOrder().getId());
                                    }
                                })
                                .show(getChildFragmentManager());
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

    private void orderData() {
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
                            Toasty.info(mContext, "确认收货成功").show();
                            mData.clear();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}