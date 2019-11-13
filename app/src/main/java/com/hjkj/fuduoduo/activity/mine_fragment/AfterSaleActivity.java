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
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

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
        requestData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new AfterSaleAdapter(R.layout.item_after_sale, mData);
        // mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // mAdapter.isFirstOnly(true);
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
//                         ExchangeDetailsActivity.openActivity(AfterSaleActivity.this); //换货详情页面

                        RefundDetailsActivity.openActivity(AfterSaleActivity.this);//退款详情页面
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
                            ArrayList<DoqueryreturnordersData> tempList = simpleResponseAppResponse.getData();
                            mData.clear();
                            mData.addAll(tempList);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
