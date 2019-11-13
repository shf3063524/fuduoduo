package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.NegotiationHistoryAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.ConsultsBean;
import com.hjkj.fuduoduo.entity.bean.DoqueryconsultData;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 协商历史-页面
 */
public class NegotiationHistoryActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    private ArrayList<DoqueryconsultData> mData;
    private NegotiationHistoryAdapter mAdapter;
    private OrderDetailsBean orderDetailsBean;

    public static void openActivity(Context context, OrderDetailsBean orderDetailsBean) {
        Intent intent = new Intent(context, NegotiationHistoryActivity.class);
        intent.putExtra("OrderDetailsBean", orderDetailsBean);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_negotiation_history;
    }

    @Override
    protected void initPageData() {
        orderDetailsBean = (OrderDetailsBean) getIntent().getSerializableExtra("OrderDetailsBean");
        onNegotiationHistory(orderDetailsBean.getOrderDetail().getId(), orderDetailsBean.getCommodity().getSupplierId());
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new NegotiationHistoryAdapter(R.layout.item_negotiation_history, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(NegotiationHistoryActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {

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
    /**
     * 协商历史查询接口
     */
    private void onNegotiationHistory(String orderDetailsId, String supplierId) {
        String consumerId = UserManager.getUserId(NegotiationHistoryActivity.this);
        OkGo.<AppResponse<ArrayList<DoqueryconsultData>>>get(Api.ORDERS_DOQUERYCONSULT)//
                .params("orderDetailsId", orderDetailsId) //订单id
                .params("consumerId", consumerId) //订单详情
                .params("supplierId", supplierId) //订单详情
                .execute(new DialogCallBack<AppResponse<ArrayList<DoqueryconsultData>>>(this, "") {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoqueryconsultData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoqueryconsultData> responseData = simpleResponseAppResponse.getData();
                            mData.addAll(responseData);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
