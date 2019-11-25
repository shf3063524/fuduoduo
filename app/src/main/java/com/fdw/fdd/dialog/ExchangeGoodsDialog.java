package com.fdw.fdd.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.adapter.ShopCartListAdapter;
import com.fdw.fdd.entity.bean.DoQuerySpecificationsData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * 申请换货Dialog
 */
public class ExchangeGoodsDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String commodityId;
    private String shoppingNumber;

    private int type = 0;//默认第一种
    private OnClickListener listener;
    private RecyclerView mRecyclerView;
    private TextView mTvClose;
    private TextView mTvReduce;
    private TextView mTvNum;
    private TextView mTvAdd;
    private ShopCartListAdapter mAdapter;
    private int currentCount = 1;
    private String SpecificationId;
    private String specificationName;
    private TextView mTvMaximum;

    public ExchangeGoodsDialog(@NonNull Context context, String shoppingNumber, String commodityId) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.shoppingNumber = shoppingNumber;
        this.commodityId = commodityId;
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
        mTvMaximum = findViewById(R.id.m_tv_maximum);

        mTvMaximum.setText("最多可换" + shoppingNumber + "个");
        mTvClose.setOnClickListener(this);
        mTvReduce.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        specification();
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_tv_close:
                listener.onClick(SpecificationId, specificationName, getTextString(mTvNum));
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
                if (currentCount < Integer.parseInt(shoppingNumber)) {
                    currentCount++;
                    mTvNum.setText(String.valueOf(currentCount));
                }else {

                    Toasty.info(context,"最多可换" + shoppingNumber + "个").show();
                    return;
                }
                break;
        }
    }

    public interface OnClickListener {
        void onClick(String commoditySpecificationId, String commoditySpecification, String number);
    }

    /**
     * 规格查询
     */
    private void specification() {
        OkGo.<AppResponse<ArrayList<DoQuerySpecificationsData>>>get(Api.COMMODITY_DOQUERYSPECIFICATIONS)//
                .params("commodityId", commodityId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQuerySpecificationsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQuerySpecificationsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoQuerySpecificationsData> mData = new ArrayList<>();
                            ArrayList<DoQuerySpecificationsData> tempList = simpleResponseAppResponse.getData();
                            mData.clear();
                            mData.addAll(tempList);

                            //防止recycleview闪烁
                            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                            GridLayoutManager manager = new GridLayoutManager(context, 4);
                            List<DoQuerySpecificationsData> data = new ArrayList<DoQuerySpecificationsData>();
                            for (DoQuerySpecificationsData doQuerySpecificationsData : mData) {
                                String id = doQuerySpecificationsData.getId();
                                String commoditySpecification = doQuerySpecificationsData.getCommoditySpecification();
                                String salePrice = doQuerySpecificationsData.getSalePrice();
                                String number = doQuerySpecificationsData.getNumber();
                                String specificationImage = doQuerySpecificationsData.getSpecificationImage();
                                if (doQuerySpecificationsData == mData.get(0)) {
                                    data.add(new DoQuerySpecificationsData(id, commoditySpecification, salePrice, number, specificationImage, true));
                                    SpecificationId = id;
                                    specificationName = commoditySpecification;
                                } else {
                                    data.add(new DoQuerySpecificationsData(id, commoditySpecification, salePrice, number, specificationImage, false));
                                }
                            }
                            mRecyclerView.addItemDecoration(new SpaceItemDecoration(4, 10, true));
                            mAdapter = new ShopCartListAdapter(data, context);
                            mRecyclerView.setLayoutManager(manager);
                            mRecyclerView.setAdapter(mAdapter);

                            mAdapter.setMoneyInputListener(new ShopCartListAdapter.MoneyInputListener() {

                                @Override
                                public void onGetMoneyInput(String commoditySpecificationId, String commoditySpecification, String salePrice, String number, String specificationImage) {
                                    specificationName = commoditySpecification;
                                    SpecificationId = commoditySpecificationId;
                                }
                            });
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    public String getTextString(TextView view) {
        return view.getText().toString().trim();
    }
}