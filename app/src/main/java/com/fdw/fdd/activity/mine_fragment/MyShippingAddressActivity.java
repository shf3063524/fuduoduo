package com.fdw.fdd.activity.mine_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.adapter.MyShippingAddressAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoQueryData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.UserManager;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import ezy.ui.layout.LoadingLayout;

/**
 * 我的收货地址页面
 * Author：Created by shihongfei on 2019/10/2 14:38
 * Email：1511808259@qq.com
 */
public class MyShippingAddressActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_return)
    ImageView mIvReTurn;
    @BindView(R.id.m_add_address)
    TextView mAddAddress;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private ArrayList<DoQueryData> mData;
    private MyShippingAddressAdapter mAdapter;
    private String jumpKey;

    public static void openActivity(Context context, String jumpKey) {
        Intent intent = new Intent(context, MyShippingAddressActivity.class);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }

    public static void openActivityForResult(Activity activity, int requestCode, String jumpKey) {
        Intent intent = new Intent(activity, MyShippingAddressActivity.class);
        intent.putExtra("jumpKey", jumpKey);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_shipping_address;
    }

    @Override
    protected void initPageData() {
        jumpKey = getIntent().getStringExtra("jumpKey");
    }

    @Override
    protected void initViews() {
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
    }
    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
        mLoadingLayout.setEmptyImage(R.drawable.ic_no_address);
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
        mAdapter = new MyShippingAddressAdapter(R.layout.item_my_shipping_address, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyShippingAddressActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_iv_writer://编辑收货地址
                        EditShippingAddressActivity.openActivity(MyShippingAddressActivity.this, mData.get(position));
                        break;
                    case R.id.m_layout_item://点击条目
                        if ("ConfirmOrder02Activtiy".equals(jumpKey) || "ConfirmOrderActivtiy".equals(jumpKey)) {
                            Intent intent = new Intent();
                            intent.putExtra("address", mData.get(position));
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        break;
                }
            }
        });
    }

    @OnClick({R.id.m_iv_return, R.id.m_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_return:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_add_address:   // 添加收货地址
                AddAddressActivity.openActivity(MyShippingAddressActivity.this, "MyShippingAddressActivity");
                break;
        }
    }

    @Override
    protected void requestData() {
        String userId = UserManager.getUserId(MyShippingAddressActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryData>>>get(Api.FREIGHTADDRESS_DOQUERY)//
                .params("userId", userId)
                .params("type", "0")
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoQueryData> tempList = simpleResponseAppResponse.getData();
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
