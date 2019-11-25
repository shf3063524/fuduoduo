package com.fdw.fdd.base;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Activity基类
 * Author：Created by shihongfei on 2019/9/23 20:45
 * Email：1511808259@qq.com
 */
public abstract class BaseActivity extends BaseStatusActivity {
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
}
