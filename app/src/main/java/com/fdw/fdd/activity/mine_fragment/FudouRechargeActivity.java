package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.view.SpaceItemDecoration;
import com.fdw.fdd.adapter.FudouRechargeAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.MoneyEntity;
import com.fdw.fdd.tool.StatusBarUtil;
import com.mylhyl.circledialog.CircleDialog;

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
    @BindView(R.id.m_layout_money)
    RelativeLayout mLayoutMoney;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    @BindColor(R.color.cl_333)
    int cl_333;

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
        initRecyclerView();
    }

    private void initRecyclerView() {
        //防止recycleview闪烁
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        List<MoneyEntity> data = new ArrayList<MoneyEntity>();
        // data.add(new MoneyEntity("30元", false));
        // data.add(new MoneyEntity("50元", false));
        // data.add(new MoneyEntity("100元", false));
        // data.add(new MoneyEntity("200元", false));
        // data.add(new MoneyEntity("500元", false));
        // data.add(new MoneyEntity("1000元", false));

        data.add(new MoneyEntity("30元", "充值送3积分", true));
        data.add(new MoneyEntity("50元", "充值送5积分", false));
        data.add(new MoneyEntity("100元", "充值送10积分", false));
        data.add(new MoneyEntity("200元", "充值送20积分", false));
        data.add(new MoneyEntity("500元", "充值送50积分", false));
        data.add(new MoneyEntity("1000元", "充值送100积分", false));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(3, 10, true));
        FudouRechargeAdapter adapter = new FudouRechargeAdapter(data, this);
        adapter.setMoneyInputListener(new FudouRechargeAdapter.MoneyInputListener() {
            @Override
            public void onGetMoneyInput(String money) {
                mTvMoney.setText("¥" + money + "立即充值");
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
                new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setTitle("")
                        .setTextColor(cl_333)
                        .setText("是否允许打开微信")
                        .configText(params -> {
                            // params.gravity = Gravity.LEFT | Gravity.TOP;
                            params.padding = new int[]{100, 50, 100, 50};
                        })
                        .setNegative("取消", null)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                return;
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
        }
    }
}
