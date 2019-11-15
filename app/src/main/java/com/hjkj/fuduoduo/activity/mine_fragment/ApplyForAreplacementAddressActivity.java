package com.hjkj.fuduoduo.activity.mine_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.ApplyForAreplacementAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请换货-地址
 */
public class ApplyForAreplacementAddressActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    private ArrayList<DoQueryData> mData;
    private ApplyForAreplacementAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ApplyForAreplacementAddressActivity.class);
        context.startActivity(intent);
    }
    public static void openActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ApplyForAreplacementAddressActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_apply_for_areplacement_address;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        requestData();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ApplyForAreplacementAdapter(R.layout.item_apply_for_areplacement_address, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ApplyForAreplacementAddressActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mData.get(position).setClickable(true);
                mAdapter.notifyDataSetChanged();

                Intent intent = new Intent();
                intent.putExtra("address", mData.get(position));
                setResult(RESULT_OK, intent);
                finish();
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
        String userId = UserManager.getUserId(ApplyForAreplacementAddressActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryData>>>get(Api.FREIGHTADDRESS_DOQUERY)//
                .params("userId", userId)
                .params("type", "0")
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoQueryData> tempList = simpleResponseAppResponse.getData();
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
