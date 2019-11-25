package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.activity.login.UserActivationActivity;
import com.fdw.fdd.adapter.FudouTransferAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.MoneyEntity;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.StatusBarUtil;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.view.ClearEditText;
import com.fdw.fdd.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 转赠中心-页面
 * Author：Created by shihongfei on 2019/9/30 14:05
 * Email：1511808259@qq.com
 */
public class FudouTransferActivity extends BaseActivity {
    @BindView(R.id.m_iv_fodou_arrow)
    ImageView mIvFodouArrow;
    @BindView(R.id.m_tv_money)
    TextView mTvMoney;
    @BindView(R.id.m_et_phone_number)
    ClearEditText mEtPhoneNumber;
    @BindView(R.id.m_tv_bean_value)
    TextView mTvBeanvalue;
    @BindView(R.id.m_tv_fudoubalance)
    TextView mTvFudoubalance;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    @BindColor(R.color.cl_999)
    int cl_999;
    private String fudouBalance;

    public static void openActivity(Context context, String fudouBalance) {
        Intent intent = new Intent(context, FudouTransferActivity.class);
        intent.putExtra("fudouBalance", fudouBalance);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fudou_transfer;
    }

    @Override
    protected void initPageData() {
        fudouBalance = getIntent().getStringExtra("fudouBalance");
        mTvFudoubalance.setText(DoubleUtil.double2Str(fudouBalance));
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(FudouTransferActivity.this, cl_e51C23, 1);
        initRecyclerView();
    }

    private void initRecyclerView() {
        //防止recycleview闪烁
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        List<MoneyEntity> data = new ArrayList<MoneyEntity>();

        data.add(new MoneyEntity("20.00", "", false));
        data.add(new MoneyEntity("30.00", "", false));
        data.add(new MoneyEntity("50.00", "", false));
        data.add(new MoneyEntity("100.00", "", false));
        data.add(new MoneyEntity("200.00", "", false));
        data.add(new MoneyEntity("300.00", "", false));
        data.add(new MoneyEntity("500.00", "", false));
        data.add(new MoneyEntity("1000.00", "", false));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(3, 10, true));
        FudouTransferAdapter adapter = new FudouTransferAdapter(data, this);
        adapter.setMoneyInputListener(new FudouTransferAdapter.MoneyInputListener() {
            @Override
            public void onGetMoneyInput(String money) {
                mTvBeanvalue.setText(money);
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
                if (textIsEmpty(mEtPhoneNumber)) {
                    Toasty.info(FudouTransferActivity.this, "请输入对方手机号").show();
                    return;
                }
                if (mEtPhoneNumber.length() != 11) {
                    Toasty.info(FudouTransferActivity.this, "您输入的手机号不正确").show();
                    return;
                }
                new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setTitle("")
                        .setTextColor(cl_999)
                        .setTitle("转豆操作提醒")
                        .setText("确定要给" + getTextString(mEtPhoneNumber) + "的用户转" + getTextString(mTvBeanvalue) + "颗福豆吗？")
                        .configText(params -> {
                            // params.gravity = Gravity.LEFT | Gravity.TOP;
                            params.padding = new int[]{100, 50, 100, 50};
                        })
                        .setNegative("取消", null)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                requestData();
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
        }
    }

    @Override
    protected void requestData() {
        String userId = UserManager.getUserId(FudouTransferActivity.this);
        String phoneNumber = getTextString(mEtPhoneNumber);
        String beanvalue = getTextString(mTvBeanvalue);
        Double mul = DoubleUtil.mul(Double.parseDouble(beanvalue), 100.00);
        OkGo.<AppResponse>get(Api.USER_DOEXCHANGE)//
                .params("id", userId)
                .params("phoneNumber", phoneNumber)
                .params("balance", DoubleUtil.doubleTransf(mul))
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            double sub = DoubleUtil.sub(Double.parseDouble(fudouBalance), Double.parseDouble(beanvalue));
                            mTvFudoubalance.setText(DoubleUtil.double2Str(String.valueOf(sub)));
                            Toasty.normal(FudouTransferActivity.this, "转赠成功").show();
                        }
                    }
                });
    }
}
