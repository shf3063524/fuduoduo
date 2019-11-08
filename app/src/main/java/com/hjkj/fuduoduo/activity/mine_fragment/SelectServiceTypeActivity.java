package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择服务类型-待收货页面
 * Author：Created by shihongfei on 2019/10/10 22:44
 * Email：1511808259@qq.com
 */
public class SelectServiceTypeActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_layout_refund)
    RelativeLayout mLayoutRefund;
    @BindView(R.id.m_layout_refund_china_pic)
    RelativeLayout mLayoutRefundChinaPic;
    @BindView(R.id.m_layout_rechange_china_pic)
    RelativeLayout mLayoutRechangeChinaPic;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SelectServiceTypeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_select_service_type;
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.m_iv_arrow, R.id.m_layout_refund, R.id.m_layout_refund_china_pic, R.id.m_layout_rechange_china_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_refund:   // 我要退款 申请退款
                RequestARefundActivity.openActivity(SelectServiceTypeActivity.this);
                break;
            case R.id.m_layout_refund_china_pic:   // 我要退货退款
                RequestARefund02Activity.openActivity(SelectServiceTypeActivity.this);
                break;
            case R.id.m_layout_rechange_china_pic:   // 我要换货 申请换货
                ApplyForAReplacementActivity.openActivity(SelectServiceTypeActivity.this);
                break;
        }
    }
}
