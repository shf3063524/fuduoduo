package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.adapter.FudouBanlanceAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoQueryTransactionRecordData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.StatusBarUtil;
import com.fdw.fdd.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 福豆余额页面
 * Author：Created by shihongfei on 2019/9/28 20:28
 * Email：1511808259@qq.com
 */
public class FudouBanlanceActivity extends BaseActivity {
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_fodou_arrow)
    ImageView mIvFodouArrow;
    @BindView(R.id.m_tv_recording)
    TextView mTvRecording;
    @BindView(R.id.m_tv_balance)
    TextView mTvBalance;

    private ArrayList<DoQueryTransactionRecordData> mData;
    private FudouBanlanceAdapter mAdapter;

    public static void openActivity(Context context, String balance) {
        Intent intent = new Intent(context, FudouBanlanceActivity.class);
        intent.putExtra("balance", balance);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fudou_banlance;
    }

    @Override
    protected void initPageData() {
        String fudouBalance = getIntent().getStringExtra("balance");
        mTvBalance.setText(DoubleUtil.double2Str(fudouBalance));
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(FudouBanlanceActivity.this, cl_e51C23, 1);


        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new FudouBanlanceAdapter(R.layout.item_foudou_banlance, mData);
        // mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FudouBanlanceActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
    }

    @OnClick({R.id.m_iv_fodou_arrow, R.id.m_tv_recording})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_fodou_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_recording:   // 记录
                RecordingActivity.openActivity(FudouBanlanceActivity.this);
                break;
        }
    }

    @Override
    protected void requestData() {
        super.requestData();
        String userId = UserManager.getUserId(FudouBanlanceActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryTransactionRecordData>>>get(Api.ORDERS_DOQUERYTRANSACTIONRECORD)//
                .params("id", userId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryTransactionRecordData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryTransactionRecordData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoQueryTransactionRecordData> tempList = simpleResponseAppResponse.getData();
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
