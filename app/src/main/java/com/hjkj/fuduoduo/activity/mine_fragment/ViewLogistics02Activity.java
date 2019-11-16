package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.ExpressBean;
import com.hjkj.fuduoduo.entity.bean.LogisticsData;
import com.hjkj.fuduoduo.view.LogisticsInformationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 查看物流-换货
 */
public class ViewLogistics02Activity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_express_delivery)
    TextView mTvExpressDelivery;
    @BindView(R.id.m_tv_status)
    TextView mTvStatus;
    @BindView(R.id.m_tv_waybill_number)
    TextView mTvWaybillNumber;
    @BindView(R.id.logistics_InformationView)
    LogisticsInformationView logistics_InformationView;

    List<LogisticsData> logisticsDataList;


    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ViewLogistics02Activity.class);
        context.startActivity(intent);
    }
    public static void openActivity(Context context, ArrayList<ExpressBean> expressBeans) {
        Intent intent = new Intent(context, ViewLogistics02Activity.class);
        intent.putExtra("expressBeans", expressBeans);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_view_logistice02;
    }

    @Override
    protected void initViews() {
        ArrayList<ExpressBean> expressBeans = (ArrayList<ExpressBean>) getIntent().getSerializableExtra("expressBeans");
//        initData(expressBeans);
    }

    private void initData(ArrayList<ExpressBean> expressBeans) {
        logisticsDataList = new ArrayList<>();
        for (ExpressBean expressBean : expressBeans) {
            String context = expressBean.getContext();
            String ftime = expressBean.getFtime();
            logisticsDataList.add(new LogisticsData().setTime(ftime).setContext(context));
        }

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
