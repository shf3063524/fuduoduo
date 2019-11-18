package com.hjkj.fuduoduo.activity.shop_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.mine_fragment.FudouRechargeActivity;
import com.hjkj.fuduoduo.activity.mine_fragment.MyShippingAddressActivity;
import com.hjkj.fuduoduo.activity.product.ConfirmOrderActivtiy;
import com.hjkj.fuduoduo.activity.product.PayFailureActivity;
import com.hjkj.fuduoduo.activity.product.PaySuccessActivity;
import com.hjkj.fuduoduo.adapter.ConfirmOrdersAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.dialog.ConfirmPaymentDialog;
import com.hjkj.fuduoduo.dialog.GoRechargeDialog;
import com.hjkj.fuduoduo.dialog.PayPasswordDialog;
import com.hjkj.fuduoduo.entity.bean.CommoditieBeans;
import com.hjkj.fuduoduo.entity.bean.CommoditiesBeans;
import com.hjkj.fuduoduo.entity.bean.DefaultAddressBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryData;
import com.hjkj.fuduoduo.entity.bean.DosubmitordersData;
import com.hjkj.fuduoduo.entity.bean.OrdersDoConfirmOrdersData;
import com.hjkj.fuduoduo.entity.bean.SpecBean;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 确认订单-购物车跳转
 */
public class ConfirmOrder02Activtiy extends BaseActivity {
    private static final int REQUEST_RECIPIENT_ADDRESS = 1000;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_layout_address)
    RelativeLayout mLayoutAddress;
    @BindView(R.id.m_tv_number)
    TextView mTvNumber;
    @BindView(R.id.m_tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_default)
    ImageView mIvDefault;
    @BindView(R.id.m_tv_username)
    TextView mTvusername;
    @BindView(R.id.m_tv_phone)
    TextView mTvPhone;
    @BindView(R.id.m_tv_address)
    TextView mTvAddress;
    @BindView(R.id.m_tv_pay)
    TextView mTvPay;
    private ArrayList<CommoditiesBeans> mData;
    private ConfirmOrdersAdapter mAdapter;
    private OrdersDoConfirmOrdersData ordersDoConfirmOrdersData;
    private String freightAddressId;

    public static void openActivity(Context context, OrdersDoConfirmOrdersData ordersData) {
        Intent intent = new Intent(context, ConfirmOrder02Activtiy.class);
        intent.putExtra("OrdersDoConfirmOrdersData", ordersData);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_confirm_order02;
    }

    @Override
    protected void initPageData() {
        super.initPageData();
        ordersDoConfirmOrdersData = (OrdersDoConfirmOrdersData) getIntent().getSerializableExtra("OrdersDoConfirmOrdersData");
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        refreshUi(ordersDoConfirmOrdersData);
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ConfirmOrdersAdapter(R.layout.item_confirm_orders, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ConfirmOrder02Activtiy.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_layout_address, R.id.m_tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_address: // 收货人地址
                MyShippingAddressActivity.openActivityForResult(ConfirmOrder02Activtiy.this, REQUEST_RECIPIENT_ADDRESS,"ConfirmOrder02Activtiy");
                break;
            case R.id.m_tv_pay: //提交订单
                new ConfirmPaymentDialog(ConfirmOrder02Activtiy.this, getTextString(mTvTotalPrice))
                        .setListener(new ConfirmPaymentDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                onOrders();
                            }
                        }).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_RECIPIENT_ADDRESS:
                    DoQueryData doQueryData = (DoQueryData) data.getSerializableExtra("address");
                    freightAddressId = doQueryData.getId();
                    // 默认地址
                    if (doQueryData.getDefaultAddress().equals("1")) {
                        mIvDefault.setVisibility(View.VISIBLE);
                    } else {
                        mIvDefault.setVisibility(View.GONE);
                    }
                    // 用户姓名
                    mTvusername.setText(doQueryData.getName());
                    // 用户电话
                    mTvPhone.setText(doQueryData.getMobilephoneNumber());
                    // 用户地址
                    mTvAddress.setText(doQueryData.getProvince() + doQueryData.getCity() + doQueryData.getArea() + doQueryData.getStreet());
                    break;
            }
        }
    }

    /**
     * 多个商品同时付款数据处理
     *
     * @param ordersData
     */
    private void refreshUi(OrdersDoConfirmOrdersData ordersData) {
        // 地址
        DefaultAddressBean defaultAddress = ordersData.getDefaultAddress();
        freightAddressId = defaultAddress.getId();
        // 默认地址
        if (defaultAddress.getDefaultAddress().equals("1")) {
            mIvDefault.setVisibility(View.VISIBLE);
        } else {
            mIvDefault.setVisibility(View.GONE);
        }
        // 用户姓名
        mTvusername.setText(defaultAddress.getName());
        // 用户电话
        mTvPhone.setText(defaultAddress.getMobilephoneNumber());
        // 用户地址
        mTvAddress.setText(defaultAddress.getProvince() + defaultAddress.getCity() + defaultAddress.getArea() + defaultAddress.getStreet());


        // 总共几件
        String size = ordersData.getSize();
        mTvNumber.setText("共" + size + "件");

        // 合计
        String price = ordersData.getPrice();
        mTvTotalPrice.setText(DoubleUtil.double2Str(price));

        mData.clear();
        ArrayList<CommoditiesBeans> commodities = ordersData.getCommodities();
        mData.addAll(commodities);
        mAdapter.notifyDataSetChanged();
    }

    private void onOrders() {
        String userId = UserManager.getUserId(ConfirmOrder02Activtiy.this);
        OkGo.<AppResponse<VcodeLoginData>>post(Api.ORDERS_DOSUBMITORDERS)//
                .params("consumerId", userId)
                .params("freightAddressId", freightAddressId)
                .params("orderDetails", getOrderUploadParameter())
                .execute(new DialogCallBack<AppResponse<VcodeLoginData>>(ConfirmOrder02Activtiy.this, "") {

                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.getState() == 0) {
                            new GoRechargeDialog(ConfirmOrder02Activtiy.this, simpleResponseAppResponse.getMessage())
                                    .setListener(new GoRechargeDialog.OnClickListener() {
                                        @Override
                                        public void onClick(int type) {
                                            FudouRechargeActivity.openActivity(ConfirmOrder02Activtiy.this);
                                            finish();
                                        }
                                    }).show();
                        } else {// 积分充足时，直接弹出支付Dialog
                            String payNumbers = simpleResponseAppResponse.getData().getVcode();
                            new PayPasswordDialog(ConfirmOrder02Activtiy.this)
                                    .setListener(new PayPasswordDialog.OnClickListener() {
                                        @Override
                                        public void onClick(String payPassword) {
                                            ordersDoPay(payPassword, payNumbers);
                                        }
                                    }).show();
                        }
                    }

                });
    }

    /**
     * 要上传数据
     *
     * @return
     */
    private String getOrderUploadParameter() {
        ArrayList<DosubmitordersData> dosubmitordersData = new ArrayList<>();
        // 供货商id
        ArrayList<CommoditiesBeans> commoditiyes = ordersDoConfirmOrdersData.getCommodities();
        for (CommoditiesBeans commodity : commoditiyes) {
            String supplierId = commodity.getShop().getSupplierId(); // 供货商id
            String freightPrice = commodity.getFreightPrice();
            ArrayList<SpecBean> specifications = commodity.getSpecifications();
            ArrayList<CommoditieBeans> commodities = new ArrayList<>();
            for (SpecBean specification : specifications) {
                String commodityId = specification.getCommodity().getId(); // 商品id
                String commoditySpecificationId = specification.getSpecification().getId(); //商品规格id
                String price = specification.getSpecification().getSalePrice(); // 单价
                String number = specification.getNumber(); // 数量
                commodities.add(new CommoditieBeans(price, number, commodityId, commoditySpecificationId));
            }
            dosubmitordersData.add(new DosubmitordersData(supplierId, freightPrice, commodities, ""));
        }
        return new Gson().toJson(dosubmitordersData);
    }

    /**
     * 多订单立即支付
     */
    private void ordersDoPay(String payPassword, String payNumbers) {
        String id = UserManager.getUserId(ConfirmOrder02Activtiy.this);
        Double mul = DoubleUtil.mul(Double.parseDouble(getTextString(mTvTotalPrice)), 100.00);
        OkGo.<AppResponse>get(Api.ORDERS_DOPAY)//
                .params("id", id) //
                .params("payPassword", payPassword) //
                .params("actualPrice", DoubleUtil.doubleTransf(mul)) //
                .params("payNumbers", payNumbers) //
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.getState() == 0) {
                            Toasty.info(ConfirmOrder02Activtiy.this, simpleResponseAppResponse.getMessage()).show();
                            PayFailureActivity.openActivity(ConfirmOrder02Activtiy.this);
                        } else {
                            PaySuccessActivity.openActivity(ConfirmOrder02Activtiy.this);
                            finish();
                        }
                    }
                });

    }
}
