package com.fdw.fdd.base;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.fdw.fdd.MainActivity;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.home_fragment.MessageCenterActivity;
import com.fdw.fdd.activity.mine_fragment.MyOrderActivity;
import com.fdw.fdd.kefu.LoginKeFu02Activity;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.tool.kefutool.Constant;
import com.fdw.fdd.view.TriangleDrawable;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.ButterKnife;

/**
 * Activity基类
 * Author：Created by shihongfei on 2019/9/23 20:45
 * Email：1511808259@qq.com
 */
public abstract class BaseActivity extends BaseStatusActivity {

    public EasyPopup mQQPop;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 初始化页面数据
     */
    protected void initPageData() {
    }

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 控件的事件监听
     */
    protected void actionView() {
    }

    /**
     * 联网请求数据
     */
    protected void requestData() {
    }

    /**
     * 更新视图
     */
    protected <T> void refreshUI(@Nullable T data) {
    }

    /**
     * 初始化ToolBar
     *
     * @param toolbar         工具栏
     * @param homeAsUpEnabled 是否有返回键
     * @param title           页面标题
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    /**
     * 初始化ToolBar
     *
     * @param toolbar         工具栏
     * @param homeAsUpEnabled 是否有返回键
     * @param title           页面标题
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int title) {
        initToolBar(toolbar, homeAsUpEnabled, getString(title));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        ButterKnife.bind(this);
        initPageData();
        initViews();
        actionView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 返回按钮
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 留给子类重写标题上的返回按钮以及返回键的方法
     *
     * @return 是否重写返回
     */
    public boolean clickBack() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else {// 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onBackPressed() {
        if (clickBack()) {

        } else {
            finish();
        }
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 判断TextView的内容是否为空
     *
     * @param view TextView
     * @return
     */
    public boolean textIsEmpty(TextView view) {
        String str = view.getText().toString().trim();
        return TextUtils.isEmpty(str);
    }

    public String getTextString(TextView view) {
        return view.getText().toString().trim();
    }

    public void initQQPop() {
        // 消息
// 首页
// 个人中心
// 客服小秘
//                .setBackgroundDimEnable(true)
//                .setDimValue(0.5f)
//                .setDimColor(Color.RED)
//                .setDimView(mTitleBar)
        mQQPop = EasyPopup.create()
                .setContext(this)
                .setContentView(R.layout.layout_right_pop)
                .setAnimationStyle(R.style.RightTop2PopAnim)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {
                        View arrowView = view.findViewById(R.id.v_arrow);
                        TextView mTvMessage = (TextView) view.findViewById(R.id.m_tv_message);
                        TextView mTvHome = (TextView) view.findViewById(R.id.m_tv_home);
                        TextView mTvCenter = (TextView) view.findViewById(R.id.m_tv_center);
                        TextView mTvKefu = (TextView) view.findViewById(R.id.m_tv_kefu);
                        arrowView.setBackground(new TriangleDrawable(TriangleDrawable.TOP, Color.parseColor("#696969")));
                        // 消息
                        mTvMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MessageCenterActivity.openActivity(BaseActivity.this);
                            }
                        });
                        // 首页
                        mTvHome.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MainActivity.openActivity(BaseActivity.this);
                            }
                        });
                        // 个人中心
                        mTvCenter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MainActivity.openActivity(BaseActivity.this,"BaseActivity");
                            }
                        });
                        // 客服小秘
                        mTvKefu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String phoneNumber = UserManager.getPhoneNumber(BaseActivity.this);
                                Intent intent = new Intent();
                                intent.putExtra(Constant.MESSAGE_TO_INTENT_EXTRA, Constant.MESSAGE_TO_AFTER_SALES);
                                intent.putExtra("phone", phoneNumber);
                                intent.setClass(BaseActivity.this, LoginKeFu02Activity.class);
                                startActivity(intent);
                            }
                        });
                    }
                })
                .setFocusAndOutsideEnable(true)
//                .setBackgroundDimEnable(true)
//                .setDimValue(0.5f)
//                .setDimColor(Color.RED)
//                .setDimView(mTitleBar)
                .apply();
    }

    public void showQQPop(View view, int height) {
        int offsetX = SizeUtils.dp2px(20) - view.getWidth() / 2;
        int offsetY = (height - view.getHeight()) / 2;
        mQQPop.showAtAnchorView(view, YGravity.BELOW, XGravity.ALIGN_RIGHT, offsetX, offsetY);
    }
}
