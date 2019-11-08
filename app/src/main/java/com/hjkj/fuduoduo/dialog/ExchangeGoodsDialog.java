package com.hjkj.fuduoduo.dialog;


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

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.ShopCartAdapter;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQuerySpecificationsData;

import java.util.ArrayList;

/**
 * 申请换货Dialog
 */
public class ExchangeGoodsDialog extends Dialog implements View.OnClickListener {
    private Context context;

    private int type = 0;//默认第一种
    private OnClickListener listener;
    private RecyclerView mRecyclerView;
    private TextView mTvClose;
    private TextView mTvReduce;
    private TextView mTvNum;
    private TextView mTvAdd;
    private ArrayList<DoQuerySpecificationsData> mData;
    private ShopCartAdapter mAdapter;
    private int currentCount = 1;

    public ExchangeGoodsDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public ExchangeGoodsDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_goods_dialog);
        mRecyclerView = findViewById(R.id.m_recycler_view);
        mTvClose = findViewById(R.id.m_tv_close);
        mTvReduce = findViewById(R.id.tv_reduce);
        mTvNum = findViewById(R.id.tv_num);
        mTvAdd = findViewById(R.id.tv_add);

        mTvClose.setOnClickListener(this);
        mTvReduce.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        initRecyclerView();
        actionView();
    }

    private void initRecyclerView() {
//        mData = new ArrayList<>();
//        mAdapter = new ShopCartAdapter(R.layout.item_shop_cart, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void actionView() {
//        mData.clear();
//        for (int i = 0; i < 3; i++) {
//            mData.add(new TestBean("item" + i));
//        }
//        mAdapter.notifyDataSetChanged();
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
            case R.id.tv_reduce: //减
                if (currentCount == 1) {
                    return;
                } else {
                    currentCount--;
                    mTvNum.setText(String.valueOf(currentCount));
                }
                break;
            case R.id.tv_add:  //加
                currentCount++;
                mTvNum.setText(String.valueOf(currentCount));
                break;
        }
    }

    public interface OnClickListener {
        void onClick(int type);
    }
}