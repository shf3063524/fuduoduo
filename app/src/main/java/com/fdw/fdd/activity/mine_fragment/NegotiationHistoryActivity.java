package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fdw.fdd.R;
import com.fdw.fdd.adapter.NegotiationHistoryAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoqueryconsultData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.tool.UserManager;
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
    @BindView(R.id.m_iv_pop_ups)
    ImageView mIvPopUps;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayoutSetReturn;
    private ArrayList<DoqueryconsultData> mData;
    private NegotiationHistoryAdapter mAdapter;

    public static void openActivity(Context context, String orderDetailsId, String supplierId) {
        Intent intent = new Intent(context, NegotiationHistoryActivity.class);
        intent.putExtra("orderDetailsId", orderDetailsId);
        intent.putExtra("supplierId", supplierId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_negotiation_history;
    }

    @Override
    protected void initPageData() {
        String orderDetailsId = getIntent().getStringExtra("orderDetailsId");
        String supplierId = getIntent().getStringExtra("supplierId");
        onNegotiationHistory(orderDetailsId, supplierId);
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        initQQPop();
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
        mIvPopUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int height = mLayoutSetReturn.getHeight();
                showQQPop(view, height);
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
