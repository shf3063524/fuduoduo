package com.hjkj.fuduoduo.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.WindowNoticeAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.mylhyl.circledialog.CircleDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 系统通知页面
 */
public class WindowNoticeActivity extends BaseActivity {

    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    private ArrayList<TestBean> mData;
    private WindowNoticeAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, WindowNoticeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_window_notice;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {

        mData = new ArrayList<>();
        mAdapter = new WindowNoticeAdapter(R.layout.item_window_notice, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(WindowNoticeActivity.this);
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

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_iv_delete:
                        new CircleDialog.Builder()
                                .setCanceledOnTouchOutside(false)
                                .setCancelable(false)
                                .setText("确定删除该条信息吗？")
                                .configText(params -> {
                                    // params.gravity = Gravity.LEFT | Gravity.TOP;
                                    params.padding = new int[]{100, 130, 100, 130};
                                })
                                .setNegative("取消", null)
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // deleteCard(position);
                                        // mData.remove(position);
                                    }
                                })
                                .show(getSupportFragmentManager());
                        break;

                    case R.id.m_layout_item: // 消息详情
                        MessageDetialsActivity.openActivity(WindowNoticeActivity.this);
                        break;
                }
            }
        });
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
