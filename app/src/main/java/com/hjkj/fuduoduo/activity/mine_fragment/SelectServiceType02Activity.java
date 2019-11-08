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
 * 选择服务类型-评价页面
 */
public class SelectServiceType02Activity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_layout_rechange_china_pic)
    RelativeLayout mLayoutRechangeChinaPic;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SelectServiceType02Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_select_service_type02;
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.m_iv_arrow, R.id.m_layout_rechange_china_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_rechange_china_pic:   // 我要换货 申请换货
                ApplyForAReplacementActivity.openActivity(SelectServiceType02Activity.this);
                break;
        }
    }
}
