package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.fdw.fdd.MainActivity;
import com.fdw.fdd.activity.home_fragment.MessageCenterActivity;
import com.fdw.fdd.kefu.LoginKeFu02Activity;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.tool.kefutool.Constant;
import com.fdw.fdd.view.TriangleDrawable;
import com.flyco.tablayout.SlidingTabLayout;
import com.fdw.fdd.R;
import com.fdw.fdd.adapter.MyFragmentPagerAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.fragment.CommentFragment;
import com.fdw.fdd.fragment.MyOrderFragment;
import com.fdw.fdd.fragment.PendingPaymentFragment;
import com.fdw.fdd.fragment.DeliveredFragment;
import com.fdw.fdd.fragment.PendingReceiptFragment;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.ArrayList;

import butterknife.BindColor;
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
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayoutSetReturn;
    @BindView(R.id.m_slidingtablayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.m_viewPager)
    ViewPager mViewPager;
    private ArrayList<String> mTabList;
    private ArrayList<Fragment> mFragments;
    // 跳转Fragment设定的状态
    private String positionStatus = "0";
    private int index = Constant.INTENT_CODE_IMG_SELECTED_DEFAULT;

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
        initQQPop();
        initFragment(positionStatus);
    }

    /**
     * 按钮点击跳转相对应的Fragment
     *
     * @param positionStatus
     */
    private void initFragment(String positionStatus) {
        switch (positionStatus) {
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

    @Override
    protected void actionView() {
        mIvPopUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int height = mLayoutSetReturn.getHeight();
                showQQPop(view, height);
            }
        });
    }

    @OnClick({R.id.m_iv_return, R.id.m_iv_inquire})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_return:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_iv_inquire:   // 查询
                InquireOrderActivity.openActivity(MyOrderActivity.this);
                break;
        }
    }
}
