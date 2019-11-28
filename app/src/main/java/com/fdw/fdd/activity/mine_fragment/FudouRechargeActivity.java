package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.bean.DounifyplaceorderData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.SharedPrefUtil;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.view.SpaceItemDecoration;
import com.fdw.fdd.adapter.FudouRechargeAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.MoneyEntity;
import com.fdw.fdd.tool.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值中心页面
 * Author：Created by shihongfei on 2019/9/29 14:34
 * Email：1511808259@qq.com
 */
public class FudouRechargeActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_fodou_arrow)
    ImageView mIvFodouArrow;
    @BindView(R.id.m_tv_money)
    TextView mTvMoney;
    @BindView(R.id.m_tv_phone)
    TextView mTvPhone;
    @BindView(R.id.m_layout_money)
    LinearLayout mLayoutMoney;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    @BindColor(R.color.cl_333)
    int cl_333;
    @BindColor(R.color.cl_ff0481df)
    int cl_ff0481df;
    @BindColor(R.color.cl_e8f2ff)
    int cl_e8f2ff;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, FudouRechargeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fudou_recharge;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(FudouRechargeActivity.this, cl_e51C23, 1);
        String phoneNumber = UserManager.getPhoneNumber(FudouRechargeActivity.this);
        mTvPhone.setText(phoneNumber);
        initRecyclerView();
    }

    private void initRecyclerView() {
        //防止recycleview闪烁
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        List<MoneyEntity> data = new ArrayList<MoneyEntity>();
        data.add(new MoneyEntity("30", "充值送3积分", true));
        data.add(new MoneyEntity("50", "充值送5积分", false));
        data.add(new MoneyEntity("100", "充值送10积分", false));
        data.add(new MoneyEntity("200", "充值送20积分", false));
        data.add(new MoneyEntity("500", "充值送50积分", false));
        data.add(new MoneyEntity("1000", "充值送100积分", false));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(3, 10, true));
        FudouRechargeAdapter adapter = new FudouRechargeAdapter(data, this);
        adapter.setMoneyInputListener(new FudouRechargeAdapter.MoneyInputListener() {
            @Override
            public void onGetMoneyInput(String money) {
                mTvMoney.setText(money);
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.m_iv_fodou_arrow, R.id.m_layout_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_fodou_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_money:   // 支付
                final String[] items = {"支付宝支付", "微信支付"};
                new CircleDialog.Builder()
                        .configDialog(params -> {
                            params.backgroundColorPress = cl_e8f2ff;
                            //增加弹出动画
                            params.animStyle = R.style.dialogWindowAnim;
                        })
                        .setTitle("选择支付方式")
                        .setTitleColor(cl_ff0481df)
                        .configTitle(params -> {
                            // params.backgroundColor = Color.RED;
                        })
                        .setItems(items, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position1, long l) {
                                switch (items[position1]) {
                                    case "支付宝支付":

                                        break;
                                    case "微信支付":
                                        onWxPay(getTextString(mTvMoney));
                                        break;
                                }
                            }
                        })
                        .setNegative("取消", null)
                        .show(getSupportFragmentManager());
                break;
        }
    }

    private void onWxPay(String price){
        String userId = UserManager.getUserId(FudouRechargeActivity.this);
        Double mul = DoubleUtil.mul(Double.parseDouble(price), 100.00);
        OkGo.<AppResponse<DounifyplaceorderData>>get(Api.ORDERS_DOUNIFYPLACEORDER)//
                .params("userId", userId)
                .params("type", 2)
                .params("price", DoubleUtil.doubleTransf(mul))
                .execute(new JsonCallBack<AppResponse<DounifyplaceorderData>>() {
                    @Override
                    public void onSuccess(AppResponse<DounifyplaceorderData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            weChatPay(simpleResponseAppResponse.getData());
                        }
                    }
                });
    }

    private void weChatPay(DounifyplaceorderData weChatInfo) {
        final IWXAPI weChatApi = WXAPIFactory.createWXAPI(this, null);
        // 将该app注册到微信
        weChatApi.registerApp(weChatInfo.getAppid());
        SharedPrefUtil.putString(this, getString(R.string.weChartId), weChatInfo.getAppid());
        // 调起支付
        PayReq request = new PayReq();
        request.appId = weChatInfo.getAppid();
        request.partnerId = weChatInfo.getMch_id();
        request.prepayId = weChatInfo.getPrepay_id();
        request.packageValue = weChatInfo.getWechatPackage();
        request.nonceStr = weChatInfo.getNonce_str();
        request.timeStamp = weChatInfo.getTimestamp();
        request.sign = weChatInfo.getSign();
        weChatApi.sendReq(request);
    }
}
