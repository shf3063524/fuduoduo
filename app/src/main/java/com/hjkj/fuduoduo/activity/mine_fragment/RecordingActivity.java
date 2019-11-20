package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.RecordingAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryData;
import com.hjkj.fuduoduo.entity.bean.DoQueryTransactionRecordData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import ezy.ui.layout.LoadingLayout;

/**
 * 交易记录页面
 * Author：Created by shihongfei on 2019/9/29 09:42
 * Email：1511808259@qq.com
 */
public class RecordingActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_back)
    ImageView mIvBack;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private ArrayList<DoQueryTransactionRecordData> mData;
    private RecordingAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RecordingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_recording;
    }

    @Override
    protected void initViews() {
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
    }
    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
        mLoadingLayout.setEmptyImage(R.drawable.ic_no_recording);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
    }
    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new RecordingAdapter(R.layout.item_recording, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(RecordingActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
    }

    @OnClick({R.id.m_iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_back:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void requestData() {
        super.requestData();
        String userId = UserManager.getUserId(RecordingActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryTransactionRecordData>>>get(Api.ORDERS_DOQUERYTRANSACTIONRECORD)//
                .params("id", userId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryTransactionRecordData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryTransactionRecordData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoQueryTransactionRecordData> tempList = simpleResponseAppResponse.getData();
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
}
