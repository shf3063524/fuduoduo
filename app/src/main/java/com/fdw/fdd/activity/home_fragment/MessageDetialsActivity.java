package com.fdw.fdd.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.fdw.fdd.R;
import com.fdw.fdd.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息详情页面
 */
public class MessageDetialsActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, MessageDetialsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_message_details;
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.m_iv_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
        }
    }
}
