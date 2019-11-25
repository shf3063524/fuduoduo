package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.adapter.FootPrintAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoQueryFootprintData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.UserManager;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ezy.ui.layout.LoadingLayout;

/**
 * 足迹页面
 * Author：Created by shihongfei on 2019/10/6 15:43
 * Email：1511808259@qq.com
 */
public class FootPrintActivity extends BaseActivity {
    @BindView(R.id.m_iv_return)
    ImageView mIvReturn;
    @BindView(R.id.m_tv_finish)
    TextView mTvFinish;
    @BindView(R.id.m_tv_delete)
    TextView mTvDelete;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_rl_bottom)
    RelativeLayout mRlBottom;
    @BindView(R.id.m_view_line)
    View mViewLine;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private FootPrintAdapter mAdapter;
    private ArrayList<DoQueryFootprintData> mData;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, FootPrintActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_foot_print;
    }

    @Override
    protected void initViews() {
        mTvFinish.setText("管理");
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
    }
    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
        mLoadingLayout.setEmptyImage(R.drawable.ic_no_foot);
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
        mAdapter = new FootPrintAdapter(R.layout.item_foot_print, mData);
        LinearLayoutManager layoutManagerDetail = new LinearLayoutManager(FootPrintActivity.this);
        mRecyclerView.setLayoutManager(layoutManagerDetail);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {

    }

    @OnClick({R.id.m_iv_return, R.id.m_tv_finish, R.id.m_tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_return:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_finish:   // 管理
                if ("管理".equals(getTextString(mTvFinish))) {
                    mTvFinish.setText("完成");
                    mRlBottom.setVisibility(View.VISIBLE);
                    mViewLine.setVisibility(View.VISIBLE);
                    mAdapter.setVisibleCheckBox(true);
                } else {
                    mTvFinish.setText("管理");
                    mRlBottom.setVisibility(View.GONE);
                    mViewLine.setVisibility(View.GONE);
                    mAdapter.setVisibleCheckBox(false);
                }
                break;
            case R.id.m_tv_delete: //删除
//                delete(memberIds);
                break;
        }
    }

    @Override
    protected void requestData() {
        super.requestData();
        String userId = UserManager.getUserId(FootPrintActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryFootprintData>>>get(Api.USER_DOQUERYFOOTPRINT)//
                .params("userId", userId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryFootprintData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryFootprintData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoQueryFootprintData> tempList = simpleResponseAppResponse.getData();
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
     * 删除个人足迹
     */
    private void delete(String id) {
        OkGo.<AppResponse>get(Api.USER_DODELETEFOOTPRINT)//
                .params("ids", id)
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        Toasty.normal(FootPrintActivity.this, "删除成功").show();
                    }
                });
    }
}
