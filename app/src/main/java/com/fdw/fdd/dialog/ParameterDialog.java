package com.fdw.fdd.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.adapter.ParameterAdapter;
import com.fdw.fdd.entity.bean.AttributesBean;
import com.fdw.fdd.entity.bean.DoQueryCommodityDetailsData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.SharedPrefUtil;
import com.fdw.fdd.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

public class ParameterDialog extends Dialog implements View.OnClickListener {
    public static final int M_LAYOUT_ONE = 1;
    private Context context;
    private TextView mTvClose;

    private int type = 1;//默认第一种
    private OnClickListener listener;
    private RecyclerView mRecyclerView;

    public ParameterDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public ParameterDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patameter_dialog);
        shopDetalis();
        mTvClose = findViewById(R.id.m_tv_close);
        mRecyclerView = findViewById(R.id.m_recycler_view);
        mTvClose.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        if (getWindow() != null) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(params);
            getWindow().setGravity(Gravity.BOTTOM);
            setCanceledOnTouchOutside(true);
        }
        // if (!haveWXPay&&!haveAliPay&&!haveBalance){
        //     dismiss();
        // }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_tv_close:
                listener.onClick(type);
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick(int type);
    }


    private void shopDetalis() {
        String userId = UserManager.getUserId(context);
        String shopId = SharedPrefUtil.getString(context, "shopId", "");
        OkGo.<AppResponse<DoQueryCommodityDetailsData>>get(Api.COMMODITY_DOQUERYCOMMODITYDETAILS)//
                .params("id", shopId)
                .params("userId", userId)
                .execute(new JsonCallBack<AppResponse<DoQueryCommodityDetailsData>>() {
                    @Override
                    public void onSuccess(AppResponse<DoQueryCommodityDetailsData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            DoQueryCommodityDetailsData data = simpleResponseAppResponse.getData();
                            refreshUi(data);
                        }

                    }
                });
    }

    private void refreshUi(DoQueryCommodityDetailsData data) {
        ArrayList<AttributesBean> mData = new ArrayList<>();
        ParameterAdapter mAdapter = new ParameterAdapter(R.layout.item_parameter, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        // 参数
        ArrayList<AttributesBean> attributes = data.getAttributes();
        mData.clear();
        mData.addAll(attributes);
    }
}
