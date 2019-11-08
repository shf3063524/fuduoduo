package com.hjkj.fuduoduo.activity.product;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.mine_fragment.AppraiseSuccessfulActivity;
import com.hjkj.fuduoduo.activity.mine_fragment.LookAppraiseActivity;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 支付成功-页面
 */
public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_tv_look_appraise)
    TextView mTvLookAppraise;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private ArrayList<TestBean> mData;
    private ShoppingFragmentAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, PaySuccessActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_pay_successs;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(PaySuccessActivity.this, cl_e51C23, 1);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(PaySuccessActivity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mData.clear();
        for (int i = 0; i < 10; i++) {
            mData.add(new TestBean("item" + i));
        }
        myScrollView.smoothScrollTo(0, 20);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_look_appraise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_look_appraise:   // 查看订单
                Toasty.info(PaySuccessActivity.this, "查看订单").show();
                break;
        }
    }
}
