package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.ViewLogisticsAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryOrdersDetailsData;
import com.hjkj.fuduoduo.entity.bean.DoqueryorderexpressesData;
import com.hjkj.fuduoduo.entity.bean.ExpressBean;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 查看物流页面
 * Author：Created by shihongfei on 2019/10/10 21:50
 * Email：1511808259@qq.com
 */
public class ViewLogisticsActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_tv_number)
    TextView mTvNumber;
    private ArrayList<DoqueryorderexpressesData> mData;
    private ViewLogisticsAdapter mAdapter;
    private String orderNumber;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ViewLogisticsActivity.class);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, String orderNumber) {
        Intent intent = new Intent(context, ViewLogisticsActivity.class);
        intent.putExtra("orderNumber", orderNumber);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_view_logistice;
    }

    @Override
    protected void initPageData() {
        orderNumber = getIntent().getStringExtra("orderNumber");
        onlLogistice(orderNumber);
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onlLogistice(orderNumber);
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ViewLogisticsAdapter(R.layout.item_view_logistics, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewLogisticsActivity.this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        super.actionView();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onLookLogisticeInfo(mData.get(position).getFreight().getId());
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
     * 物流数据
     */
    private void onlLogistice(String orderNumber) {
        OkGo.<AppResponse<ArrayList<DoqueryorderexpressesData>>>get(Api.ORDERS_DOQUERYORDEREXPRESSES)//
                .params("orderNumber", orderNumber)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoqueryorderexpressesData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoqueryorderexpressesData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoqueryorderexpressesData> tempList = simpleResponseAppResponse.getData();
                            mData.addAll(tempList);
                            mTvNumber.setText(mData.size() + "个包裹已发出");
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void onLookLogisticeInfo(String freightId) {
        OkGo.<AppResponse<ArrayList<ExpressBean>>>get(Api.ORDERS_DOQUERYEXPRESS)//
                .params("freightId", freightId)
                .execute(new JsonCallBack<AppResponse<ArrayList<ExpressBean>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<ExpressBean>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<ExpressBean> expressBeans = simpleResponseAppResponse.getData();
                            LogisticsInfoActivity.openActivity(ViewLogisticsActivity.this, expressBeans);

                        }
                    }
                });
    }
}
