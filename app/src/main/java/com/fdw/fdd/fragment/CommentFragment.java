package com.fdw.fdd.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.mine_fragment.OrderDetails04Activity;
import com.fdw.fdd.activity.mine_fragment.PostEvaluationActivity;
import com.fdw.fdd.activity.mine_fragment.ViewLogisticsActivity;
import com.fdw.fdd.activity.product.StoreDetailsActivity;
import com.fdw.fdd.adapter.CommentAdapter;
import com.fdw.fdd.base.BaseFragment;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.UserManager;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import ezy.ui.layout.LoadingLayout;

/**
 * 待评价-Fragmentnt
 * Author：Created by shihongfei on 2019/10/8 20:23
 * Email：1511808259@qq.com
 */
public class CommentFragment extends BaseFragment {
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    private ArrayList<DoQueryOrdersDetailsData> mData;
    private CommentAdapter mAdapter;
    private String saleState;

    public static CommentFragment newInstance(String saleState) {
        CommentFragment fragment = new CommentFragment();
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

    @Override
    protected void initPageData(Bundle bundle) {
        super.initPageData(bundle);
        if (getArguments() != null) {
            saleState = getArguments().getString("saleState");
            orderData(saleState);
        }
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new CommentAdapter(R.layout.item_comment, mData);
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
                        OrderDetails04Activity.openActivity(mContext, mData.get(position).getOrder().getId());
                        break;
                    case R.id.m_tv_one: //查看物流
                        orderDetails(mData.get(position).getOrder().getId(), "查看物流");
                        break;
                    case R.id.m_tv_three:// 评价
                        orderDetails(mData.get(position).getOrder().getId(), "评价");
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
     * @param orderId
     */
    private void orderDetails(String orderId, String state) {
        String userId = UserManager.getUserId(mContext);
        OkGo.<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>get(Api.ORDERS_DOQUERYORDERSDETAILS)//
                .params("id", userId)
                .params("orderId", orderId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryOrdersDetailsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoQueryOrdersDetailsData> responseData = simpleResponseAppResponse.getData();
                            if ("查看物流".equals(state)) {
                                ViewLogisticsActivity.openActivity(mContext, responseData.get(0).getOrder().getOrderNumber());
                            } else {
                                PostEvaluationActivity.openActivity(mContext, responseData);
                            }
                        }
                    }
                });
    }
}