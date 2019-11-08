package com.hjkj.fuduoduo.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.MessageCenterAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.tool.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 消息中心页面
 */
public class MessageCenterActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_layout_window_notice)
    RelativeLayout mLayoutWindowNotice;
    @BindView(R.id.m_layout_trade)
    RelativeLayout mLayoutTrade;
    @BindView(R.id.m_layout_special_offers)
    RelativeLayout mLayoutSpecialOffers;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private ArrayList<TestBean> mData;
    private MessageCenterAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, MessageCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(MessageCenterActivity.this, cl_e51C23, 1);
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {

        mData = new ArrayList<>();
        mAdapter = new MessageCenterAdapter(R.layout.item_message_center, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MessageCenterActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void actionView() {
        mData.clear();
        for (int i = 0; i < 10; i++) {
            mData.add(new TestBean("item" + i));
        }
        mAdapter.notifyDataSetChanged();
    }
    @OnClick({R.id.m_iv_arrow,R.id.m_layout_window_notice,R.id.m_layout_trade,R.id.m_layout_special_offers})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_window_notice:   // 系统通知
                WindowNoticeActivity.openActivity(MessageCenterActivity.this);
                break;
            case R.id.m_layout_trade:   // 交易消息
                Toasty.info(this,"交易消息").show();
                break;
            case R.id.m_layout_special_offers:   // 优惠券活动
                Toasty.info(this,"优惠券活动").show();
                break;
        }
    }
}


