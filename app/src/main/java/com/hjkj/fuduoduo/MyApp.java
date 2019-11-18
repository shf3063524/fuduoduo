package com.hjkj.fuduoduo;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.liys.doubleclicklibrary.helper.ViewDoubleHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class MyApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //**********************客服云*************************
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey("1489190906061827#kefuchannelapp74285");//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId("74285");//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”

        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(this, options)) {
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(this);
        //打印日志，方便测试
        ChatClient.getInstance().init(this, new ChatClient.Options().setConsoleLog(true));

        //后面可以设置其他属性
        //**********************客服云*************************

        this.mContext = this;
        //针对点击按钮页面跳转2次
//        ViewDoubleHelper.init(this); //默认时间：1秒
        ViewDoubleHelper.init(this, 1000); //自定义点击间隔时间(单位：毫秒)
        initSmartRefreshLayout();

    }

    public static Context getmContext() {
        return mContext;
    }

    private void initSmartRefreshLayout() {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置（优先级最低）
                layout.setEnableLoadMore(false);
                layout.setEnableRefresh(false);
                layout.setEnableAutoLoadMore(true);
                layout.setEnableOverScrollDrag(true);
                layout.setEnableOverScrollBounce(true);
                layout.setEnableLoadMoreWhenContentNotFull(true);
                layout.setEnableScrollContentWhenRefreshed(true);
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
                layout.setPrimaryColorsId(R.color.cl_999, R.color.cl_999);
                // return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
                return new ClassicsHeader(context)
                        .setSpinnerStyle(SpinnerStyle.FixedBehind)
                        .setPrimaryColorId(R.color.cl_f6)
                        .setAccentColorId(R.color.cl_333);
            }
        });
    }

}
