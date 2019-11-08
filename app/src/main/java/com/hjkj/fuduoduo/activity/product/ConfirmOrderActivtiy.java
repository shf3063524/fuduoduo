package com.hjkj.fuduoduo.activity.product;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.mine_fragment.FudouRechargeActivity;
import com.hjkj.fuduoduo.activity.mine_fragment.MyShippingAddressActivity;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.dialog.ConfirmPaymentDialog;
import com.hjkj.fuduoduo.dialog.GoRechargeDialog;
import com.hjkj.fuduoduo.dialog.PayPasswordDialog;
import com.hjkj.fuduoduo.entity.bean.CommodityBean;
import com.hjkj.fuduoduo.entity.bean.CommoditySpecificationBeans;
import com.hjkj.fuduoduo.entity.bean.DefaultAddressBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryData;
import com.hjkj.fuduoduo.entity.bean.DoconfirmordersData;
import com.hjkj.fuduoduo.entity.bean.FreightTemplateBean;
import com.hjkj.fuduoduo.entity.bean.ShopBean;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.ClearEditText;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 确认订单-商品详情页面
 */
public class ConfirmOrderActivtiy extends BaseActivity {
    private static final int REQUEST_RECIPIENT_ADDRESS = 1000;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_default)
    ImageView mIvDefault;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_delivery_time)
    TextView mTvDeliveryTime;
    @BindView(R.id.m_tv_integral_one)
    TextView mTvIntegralOne;
    @BindView(R.id.m_tv_num)
    TextView mTvNum;
    @BindView(R.id.m_tv_freight)
    TextView mTvFreight;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.tv_reduce)
    TextView mTvReduce;
    @BindView(R.id.m_tv_username)
    TextView mTvusername;
    @BindView(R.id.m_tv_integral)
    TextView mTvIntegral;
    @BindView(R.id.m_tv_phone)
    TextView mTvPhone;
    @BindView(R.id.m_tv_address)
    TextView mTvAddress;
    @BindView(R.id.m_tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.m_tv_store)
    TextView mTvStore;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.m_tv_pay)
    TextView mTvPay;
    @BindView(R.id.m_et_order_notes)
    ClearEditText mEtOrderNotes;
    @BindView(R.id.m_layout_address)
    RelativeLayout mLayoutAddress;
    @BindColor(R.color.cl_333)
    int cl_333;
    private int currentCount = 1;
    private DoconfirmordersData doconfirmordersData;
    private String freightAddressId;

    public static void openActivity(Context context, String commoditySpecificationId, String number) {
        Intent intent = new Intent(context, ConfirmOrderActivtiy.class);
        intent.putExtra("commoditySpecificationId", commoditySpecificationId);
        intent.putExtra("number", number);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initPageData() {
        String commoditySpecificationId = getIntent().getStringExtra("commoditySpecificationId");
        String number = getIntent().getStringExtra("number");
        confirmOrder(commoditySpecificationId, number);
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.m_iv_arrow, R.id.tv_reduce, R.id.tv_add, R.id.m_tv_pay, R.id.m_layout_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.tv_reduce: //减法
                if (currentCount == 1) {
                    return;
                } else {
                    currentCount--;
                    tvNum.setText(String.valueOf(currentCount));
                    calculateTotalPoints(doconfirmordersData.getCommoditySpecification().getSalePrice(), String.valueOf(currentCount));
                }
                break;
            case R.id.tv_add: //加法
                currentCount++;
                tvNum.setText(String.valueOf(currentCount));
                calculateTotalPoints(doconfirmordersData.getCommoditySpecification().getSalePrice(), String.valueOf(currentCount));
                break;
            case R.id.m_tv_pay: //提交订单
                new ConfirmPaymentDialog(ConfirmOrderActivtiy.this, getTextString(mTvTotalPrice))
                        .setListener(new ConfirmPaymentDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                submitOrder();
                            }
                        }).show();
                break;
            case R.id.m_layout_address: // 收货人地址
                MyShippingAddressActivity.openActivityForResult(ConfirmOrderActivtiy.this, REQUEST_RECIPIENT_ADDRESS);
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
     * 确认订单接口
     */
    private void confirmOrder(String commoditySpecificationId, String number) {
        String userId = UserManager.getUserId(ConfirmOrderActivtiy.this);
        OkGo.<AppResponse<DoconfirmordersData>>get(Api.ORDERS_DOCONFIRMORDER)//
                .params("consumerId", userId)
                .params("commoditySpecificationId", commoditySpecificationId)
                .params("number", number)
                .execute(new JsonCallBack<AppResponse<DoconfirmordersData>>() {

                    @Override
                    public void onSuccess(AppResponse<DoconfirmordersData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            doconfirmordersData = simpleResponseAppResponse.getData();
                            // 地址
                            DefaultAddressBean defaultAddress = doconfirmordersData.getDefaultAddress();
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

                            // 店铺名称
                            ShopBean shop = doconfirmordersData.getShop();
                            mTvStore.setText("  " + shop.getName() + " ");

                            // 商品
                            CommodityBean commodity = doconfirmordersData.getCommodity();
                            // 商品内容
                            mTvContent.setText(commodity.getName());

                            // 规格
                            CommoditySpecificationBeans commoditySpecification = doconfirmordersData.getCommoditySpecification();
                            // 商品价格
                            mTvIntegralOne.setText(DoubleUtil.double2Str(commoditySpecification.getSalePrice()) + " 积分");
                            // 规格图片
                            GlideUtils.loadImage(ConfirmOrderActivtiy.this, commoditySpecification.getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
                            // 规格名称
                            mTvSpecification.setText(commoditySpecification.getCommoditySpecification());

                            // 发货时间
                            FreightTemplateBean freightTemplate = doconfirmordersData.getFreightTemplate();
                            mTvDeliveryTime.setText("发货时间：买家承诺" + freightTemplate.getSendTime() + "小时");
                            // 运费0不包邮  1包邮
                            if ("1".equals(freightTemplate.getFreeShipping())) {
                                mTvFreight.setText("包邮");
                            } else {
                                mTvFreight.setText("不包邮");
                            }
                            //选择商品数量
                            tvNum.setText(doconfirmordersData.getNumber());
                            int number = Integer.parseInt(doconfirmordersData.getNumber());
                            currentCount = number;
                            // 计算总的积分（两个小计）
                            calculateTotalPoints(commoditySpecification.getSalePrice(), doconfirmordersData.getNumber());
                        }
                    }
                });
    }

    /**
     * 计算总的积分（两个小计）
     *
     * @param price  规格价格
     * @param number 商品数量
     */
    private void calculateTotalPoints(String price, String number) {
        Double mul = DoubleUtil.mul(Double.parseDouble(price), Double.parseDouble(number));
        mTvIntegral.setText(DoubleUtil.double2Str(String.valueOf(DoubleUtil.round(mul, 2))));
        mTvTotalPrice.setText(DoubleUtil.double2Str(String.valueOf(DoubleUtil.round(mul, 2))));
    }

    /**
     * 单个商品提交订单
     */
    private void submitOrder() {
        String userId = UserManager.getUserId(ConfirmOrderActivtiy.this);
        String supplierId = doconfirmordersData.getShop().getSupplierId();
        Double mul = DoubleUtil.mul(Double.parseDouble(getTextString(mTvTotalPrice)), 100.00);
        freightAddressId = doconfirmordersData.getDefaultAddress().getId();
        String note = getTextString(mEtOrderNotes);
        String commodityId = doconfirmordersData.getCommodity().getId();
        String commoditySpecificationId = doconfirmordersData.getCommoditySpecification().getId();
        String price = doconfirmordersData.getCommoditySpecification().getSalePrice();
        String number = getTextString(tvNum);
        OkGo.<AppResponse<VcodeLoginData>>get(Api.ORDERS_DOSUBMITORDER)//
                .params("supplierId", supplierId) // 供货商id
                .params("consumerId", userId) //	用户id
                .params("actualPrice", DoubleUtil.doubleTransf(mul)) // 实收款
                .params("freightAddressId", freightAddressId) //	货运地址id
                .params("note", note) // 备注
                .params("commodityId", commodityId) // 商品id
                .params("commoditySpecificationId", commoditySpecificationId) //	商品规格id
                .params("price", price) //单价
                .params("number", number) // 	数量
                .execute(new JsonCallBack<AppResponse<VcodeLoginData>>() {

                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.getState() == 0) {
                            new GoRechargeDialog(ConfirmOrderActivtiy.this, simpleResponseAppResponse.getMessage())
                                    .setListener(new GoRechargeDialog.OnClickListener() {
                                        @Override
                                        public void onClick(int type) {
                                            FudouRechargeActivity.openActivity(ConfirmOrderActivtiy.this);
                                            finish();
                                        }
                                    }).show();
                        } else {// 积分充足时，直接弹出支付Dialog
                            String payNumbers = simpleResponseAppResponse.getData().getVcode();
                            new PayPasswordDialog(ConfirmOrderActivtiy.this)
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
     * 多订单立即支付
     */
    private void ordersDoPay(String payPassword, String payNumbers) {
        String id = UserManager.getUserId(ConfirmOrderActivtiy.this);
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
                            Toasty.info(ConfirmOrderActivtiy.this,simpleResponseAppResponse.getMessage()).show();
                            PayFailureActivity.openActivity(ConfirmOrderActivtiy.this);
                        }else {
                            PaySuccessActivity.openActivity(ConfirmOrderActivtiy.this);
                            finish();
                        }
                    }
                });

    }
}