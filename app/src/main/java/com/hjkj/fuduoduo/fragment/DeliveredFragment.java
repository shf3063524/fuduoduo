package com.hjkj.fuduoduo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.mine_fragment.OrderDetails02Activity;
import com.hjkj.fuduoduo.activity.product.StoreDetailsActivity;
import com.hjkj.fuduoduo.adapter.DeliveredAdapter;
import com.hjkj.fuduoduo.base.BaseFragment;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryOrdersDetailsData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

/**
 * 待发货-Fragment
 * Author：Created by shihongfei on 2019/10/8 19:52
 * Email：1511808259@qq.com
 */
public class DeliveredFragment extends BaseFragment {
    // @BindView(R.id.m_refresh_layout)
    // SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    private ArrayList<DoQueryOrdersDetailsData> mData;
    private DeliveredAdapter mAdapter;
    private String saleState;

    public static DeliveredFragment newInstance(String saleState) {
        DeliveredFragment fragment = new DeliveredFragment();
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
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        orderData(saleState);
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new DeliveredAdapter(R.layout.item_delivered, mData);
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
                    case R.id.m_cv_item: // 点击条目
                        OrderDetails02Activity.openActivity(mContext, mData.get(position).getOrder().getId());
                        break;
                    case R.id.m_tv_two: // 提醒发货
                        remindDhipment(mData.get(position).getOrder().getId());
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
                            ArrayList<DoQueryOrdersDetailsData> tempList = simpleResponseAppResponse.getData();
                            if (unbinder != null) {
                                mData.clear();
                                mData.addAll(tempList);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
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
                            Toasty.info(mContext,"提醒发货成功啦！").show();
                        }
                    }
                });
    }
}

