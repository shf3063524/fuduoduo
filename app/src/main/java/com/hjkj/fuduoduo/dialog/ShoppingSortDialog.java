package com.hjkj.fuduoduo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.shop_fragment.ConfirmOrder02Activtiy;
import com.hjkj.fuduoduo.entity.bean.CartBean;
import com.hjkj.fuduoduo.entity.bean.CartDoQueryData;
import com.hjkj.fuduoduo.entity.bean.DoconfirmforeigncommoditiesData;
import com.hjkj.fuduoduo.entity.bean.OrdersDoConfirmOrdersData;
import com.hjkj.fuduoduo.entity.bean.UserBillingBean;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingSortDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private OnClickListener listener;
    private List<CartDoQueryData> mDatas;
    private TextView mTvClose;
    private TextView mTvDetermine;
    private RelativeLayout mRlOne;
    private RelativeLayout mRlTwo;
    private CheckBox icChekbox;
    private CheckBox ic_chekbox02;
    private DoconfirmforeigncommoditiesData responseData;
    private String type;//默认第一种
    private TextView mTvIn;
    private TextView mTvOut;

    public ShoppingSortDialog(@NonNull Context context, List<CartDoQueryData> mDatas) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.mDatas = mDatas;


    }

    public ShoppingSortDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_sort_dialog);

        mTvDetermine = findViewById(R.id.m_tv_determine);
        mTvClose = findViewById(R.id.m_tv_close);
        mTvIn = findViewById(R.id.m_tv_in);
        mTvOut = findViewById(R.id.m_tv_out);

        mRlOne = findViewById(R.id.m_rl_one);
        mRlTwo = findViewById(R.id.m_rl_two);
        icChekbox = findViewById(R.id.ic_chekbox);
        ic_chekbox02 = findViewById(R.id.ic_chekbox02);

        onOrders();
        mRlOne.setOnClickListener(this);
        mRlTwo.setOnClickListener(this);
        mTvDetermine.setOnClickListener(this);
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
            getWindow().setGravity(Gravity.CENTER);
            setCanceledOnTouchOutside(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_rl_one: // 进口商品
                icChekbox.setChecked(true);
                ic_chekbox02.setChecked(false);
                type = responseData.getCommodityOut();
                break;
            case R.id.m_rl_two: // 其他商品
                icChekbox.setChecked(false);
                ic_chekbox02.setChecked(true);
                type = responseData.getCommodityIn();
                break;
            case R.id.m_tv_determine: // 确认
                listener.onClick(type);
                dismiss();
                break;
            case R.id.m_tv_close:
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick(String type);
    }

    private void onOrders() {
        OkGo.<AppResponse<DoconfirmforeigncommoditiesData>>post(Api.ORDERS_DOCONFIRMFOREIGNCOMMODITIES)//
                .params("orderDetails", getOrderUploadParameter())
                .execute(new DialogCallBack<AppResponse<DoconfirmforeigncommoditiesData>>(context,"") {

                    @Override
                    public void onSuccess(AppResponse<DoconfirmforeigncommoditiesData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()){
                            responseData = simpleResponseAppResponse.getData();
                            mTvIn.setText(responseData.getIn());
                            mTvOut.setText(responseData.getOut());
                        }
                    }

                });
    }

    private String getOrderUploadParameter() {
        ArrayList<UserBillingBean> userBillingBeans = new ArrayList<>();
        for (CartDoQueryData data : mDatas) {
            for (CartBean cartBean : data.getCart()) {
                if (cartBean.isChoosed()) {
                    String number = cartBean.getCart().getNumber();
                    String id = cartBean.getCommoditySpecification().getId();
                    userBillingBeans.add(new UserBillingBean(id, number));
                }
            }
        }
        return new Gson().toJson(userBillingBeans);
    }
}
