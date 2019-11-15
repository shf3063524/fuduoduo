package com.hjkj.fuduoduo.dialog;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.adapter.ShopCartListAdapter;
import com.hjkj.fuduoduo.entity.bean.DoQuerySpecificationsData;
import com.hjkj.fuduoduo.entity.bean.MoneyEntity;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.hjkj.fuduoduo.tool.SharedPrefUtil;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * 加入购物车Dialog
 */
public class ShopCartDialog extends Dialog implements View.OnClickListener {
    private Context context;

    private int type = 0;//默认第一种
    private OnClickListener listener;
    private RecyclerView mRecyclerView;
    private TextView mTvClose;
    private TextView mTvReduce;
    private TextView mTvNum;
    private TextView mTvAdd;
    private ImageView mIvDismiss;
    private int currentCount = 1;
    private ShopCartListAdapter mAdapter;
    private ImageView mIvShopping;
    private TextView mTvIntegral;
    private TextView mTvStock;
    private TextView mTvSelected;
    private String SpecificationId;

    public ShopCartDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public ShopCartDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_cart_dialog);
        mRecyclerView = findViewById(R.id.m_recycler_view);
        mTvClose = findViewById(R.id.m_tv_close);
        mTvReduce = findViewById(R.id.tv_reduce);
        mIvShopping = findViewById(R.id.m_iv_shopping);
        mTvIntegral = findViewById(R.id.m_tv_integral);
        mTvStock = findViewById(R.id.m_tv_stock);
        mTvSelected = findViewById(R.id.m_tv_selected);
        mTvNum = findViewById(R.id.tv_num);
        mTvAdd = findViewById(R.id.tv_add);
        mIvDismiss = findViewById(R.id.m_iv_dismiss);

        mTvClose.setOnClickListener(this);
        mTvReduce.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        mIvDismiss.setOnClickListener(this);
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
                listener.onClick(SpecificationId, getTextString(mTvSelected), getTextString(mTvNum));
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
            case R.id.m_iv_dismiss:
                dismiss();
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
        String commodityId = SharedPrefUtil.getString(context, "commodityId", "");
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
                                    mTvSelected.setText(commoditySpecification);
                                    mTvIntegral.setText(DoubleUtil.double2Str(salePrice));
                                    mTvStock.setText("库存" + number + "件");
                                    GlideUtils.loadImage(context, specificationImage, R.drawable.ic_all_background, mIvShopping);
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
                                    mTvSelected.setText(commoditySpecification);
                                    mTvIntegral.setText(DoubleUtil.double2Str(salePrice));
                                    mTvStock.setText("库存" + number + "件");
                                    SpecificationId = commoditySpecificationId;
                                    GlideUtils.loadImage(context, specificationImage, R.drawable.ic_all_background, mIvShopping);
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
