package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.LogisticsData;
import com.hjkj.fuduoduo.view.LogisticsInformationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 物流信息页面
 * Author：Created by shihongfei on 2019/10/10 19:31
 * Email：1511808259@qq.com
 */
public class LogisticsInfoActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.logistics_InformationView)
    LogisticsInformationView logistics_InformationView;

    List<LogisticsData> logisticsDataList;
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LogisticsInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_logistics_info;
    }

    @Override
    protected void initViews() {
        initData();
    }

    private void initData() {

        logisticsDataList=new ArrayList<>();
        logisticsDataList.add(new LogisticsData().setTime("2017-1-20 07:23:06").setContext("[杭州市]浙江杭州江干公司派件员：吕敬桥15555555551正在为您派件正在为您派件正在为您派件正在为您派件正在为您派件正在为您派件正在为您派件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:11:37").setContext("[杭州市]浙江杭州江干区新杭派公司派件员：吕敬桥  15555555552  正在为您派件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:08:06").setContext("[杭州市]浙江派件员：吕敬桥  15555555553  正在为您派件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:08:06").setContext("[杭州市]员：吕敬桥  15555555554  正在为您派件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-20 11:23:07").setContext("[杭州市]浙江杭州江干区新杭派公司进行签收扫描，快件已被  已签收  签收，感谢使用韵达快递，期待再次为您服务"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 15:52:43").setContext("[泰州市]韵达快递  江苏靖江市公司收件员  已揽件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 12:39:15").setContext("包裹正等待揽件"));
        logisticsDataList.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】已签收,签收人是【王漾】,签收网点是【忻州原平】"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-20 11:23:07").setContext("[杭州市]浙江杭州江干区新杭派公司进行签收扫描，快件已被  已签收  签收，感谢使用韵达快递，期待再次为您服务"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-20 06:48:37").setContext("到达目的地网点浙江杭州江干区新杭派，快件很快进行派送"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:11:37").setContext("[苏州市]江苏苏州分拨中心  已发出"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:08:06").setContext("[苏州市]快件已到达  江苏苏州分拨中心"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 15:52:43").setContext("[泰州市]韵达快递  江苏靖江市公司收件员  已揽件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 12:39:15").setContext("菜鸟驿站代收，请及时取件，如有疑问请联系 程先生:18061208980"));


        logistics_InformationView.setLogisticsDataList(logisticsDataList);

        // logistics_InformationView.setOnPhoneClickListener(new LogisticsInformationView.OnPhoneClickListener() {
        //     @Override
        //     public void onPhoneClick(String phoneNumber) {
        //         dialogCreateCall(phoneNumber).show();
        //     }
        // });


        // initPermissionChecker();
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
