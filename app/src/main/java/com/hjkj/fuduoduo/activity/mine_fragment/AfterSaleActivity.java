package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.AfterSaleAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnordersData;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import ezy.ui.layout.LoadingLayout;

/**
 * 售后页面
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
        requestData();
    }

    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
        mLoadingLayout.setEmptyImage(R.drawable.ic_backgroud_shop);
        mLoadingLayout.setEmptyText("暂无售后");
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
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
}
