package com.hjkj.fuduoduo.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 * Author：Created by shihongfei on 2019/9/24 13:42
 * Email：1511808259@qq.com
 */
public abstract class BaseFragment extends BaseStatusFragment {
    private View mRootView;
    protected Unbinder unbinder;
    /**
     * 空间是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 数据是否以加载完毕
     */
    private boolean isLoadDataComplected;

    /**
     * 联网请求数据
     * <p>
     * 留给子类获取数据的方法
     */
    protected void requestData() {
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutRes();

    /**
     * 初始化页面数据
     *
     * @param bundle
     */
    protected void initPageData(Bundle bundle) {
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
     * 延迟加载
     */
    protected abstract void lazyLoad();

    /**
     * 更新视图
     */
    protected void refreshUI() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            unbinder = ButterKnife.bind(this, mRootView);
            initPageData(savedInstanceState);
            initViews();
            actionView();
        }
        // 缓存的mRootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        isViewCreated = true;
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (unbinder != null) {
                unbinder.unbind();
                unbinder = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewCreated && !isLoadDataComplected) {
            lazyLoad();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            lazyLoad();
        }
    }

    public String getTextString(TextView view) {
        return view.getText().toString().trim();
    }
}
