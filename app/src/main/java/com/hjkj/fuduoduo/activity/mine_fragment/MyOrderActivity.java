package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.MyFragmentPagerAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.CartDoQueryData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.fragment.CommentFragment;
import com.hjkj.fuduoduo.fragment.MyOrderFragment;
import com.hjkj.fuduoduo.fragment.PendingPaymentFragment;
import com.hjkj.fuduoduo.fragment.DeliveredFragment;
import com.hjkj.fuduoduo.fragment.PendingReceiptFragment;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的订单页面
 * Author：Created by shihongfei on 2019/10/3 16:08
 * Email：1511808259@qq.com
 */
public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.m_iv_return)
    ImageView mIvReturn;
    @BindView(R.id.m_iv_inquire)
    ImageView mIvInquire;
    @BindView(R.id.m_iv_pop_ups)
    ImageView mIvPopUps;
    @BindView(R.id.m_slidingtablayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.m_viewPager)
    ViewPager mViewPager;

    private ArrayList<String> mTabList;
    private ArrayList<Fragment> mFragments;
    // 跳转Fragment设定的状态
    private String positionStatus = "0";

    public static void openActivity(Context context, String positionStatus) {
        Intent intent = new Intent(context, MyOrderActivity.class);
        intent.putExtra("positionStatus", positionStatus);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initPageData() {
        super.initPageData();
        positionStatus = getIntent().getStringExtra("positionStatus");
    }

    @Override
    protected void initViews() {
        initSlidingTabLayout();

        initFragment(positionStatus);
    }

    /**
     * 按钮点击跳转相对应的Fragment
     *
     * @param positionStatus
     */
    private void initFragment(String positionStatus) {
        switch (positionStatus){
            case "0": // 全部订单
                mViewPager.setCurrentItem(0);
                break;
            case "1": // 待付款
                mViewPager.setCurrentItem(1);
                break;
            case "2": // 待发货
                mViewPager.setCurrentItem(2);
                break;
            case "3":// 待收货
                mViewPager.setCurrentItem(3);
                break;
            case "4":// 待评价
                mViewPager.setCurrentItem(4);
                break;
        }
    }

    private void initSlidingTabLayout() {

        mTabList = new ArrayList<>();
        mTabList.add("全部");
        mTabList.add("待付款");
        mTabList.add("待发货");
        mTabList.add("待收货");
        mTabList.add("待评价");

        mFragments = new ArrayList<>();
        mFragments.add(MyOrderFragment.newInstance("0"));
        mFragments.add(PendingPaymentFragment.newInstance("1"));
        mFragments.add(DeliveredFragment.newInstance("2"));
        mFragments.add(PendingReceiptFragment.newInstance("3"));
        mFragments.add(CommentFragment.newInstance("4"));

        MyFragmentPagerAdapter mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTabList);
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @OnClick({R.id.m_iv_return, R.id.m_iv_inquire, R.id.m_iv_pop_ups})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_return:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_iv_inquire:   // 查询

                break;
            case R.id.m_iv_pop_ups:   // 小弹窗

                break;
        }
    }
}
