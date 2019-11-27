package com.fdw.fdd.activity.product;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.adapter.AllReviewAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoQueryCommodityDetailsData;
import com.fdw.fdd.entity.bean.EvaluationBeans;
import com.fdw.fdd.entity.bean.EvaluationsBean;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.SharedPrefUtil;
import com.fdw.fdd.tool.UserManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评价-商品详情页面跳转
 */
public class AllReviewActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_tv_all_review)
    TextView mTvAllReview;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_iv_pop_ups)
    ImageView mIvPopUps;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayoutSetReturn;
    private ArrayList<EvaluationBeans> mData;
    private AllReviewAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, AllReviewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_all_review;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        initQQPop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void initRecyclerView() {

        mData = new ArrayList<>();
        mAdapter = new AllReviewAdapter(R.layout.item_all_reviview, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AllReviewActivity.this, LinearLayoutManager.VERTICAL, false) {
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
        mIvPopUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int height = mLayoutSetReturn.getHeight();
                showQQPop(view, height);
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


    @Override
    protected void requestData() {
        String userId = UserManager.getUserId(AllReviewActivity.this);
        String shopId = SharedPrefUtil.getString(AllReviewActivity.this, "shopId", "");
        OkGo.<AppResponse<DoQueryCommodityDetailsData>>get(Api.COMMODITY_DOQUERYCOMMODITYDETAILS)//
                .params("id", shopId)
                .params("userId", userId)
                .execute(new JsonCallBack<AppResponse<DoQueryCommodityDetailsData>>() {
                    @Override
                    public void onSuccess(AppResponse<DoQueryCommodityDetailsData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            DoQueryCommodityDetailsData data = simpleResponseAppResponse.getData();
                            refreshUi(data);
                        }

                    }
                });
    }

    /**
     * 数据处理
     *
     * @param data
     */
    private void refreshUi(DoQueryCommodityDetailsData data) {
        // 商品评价条数
        EvaluationsBean evaluations = data.getEvaluations();
        mTvAllReview.setText("全部评价（" + evaluations.getNumber() + ")");
        // 所有评价
        ArrayList<EvaluationBeans> evaluation = evaluations.getEvaluation();
        mData.clear();
        mData.addAll(evaluation);
        mAdapter.notifyDataSetChanged();
    }
}
