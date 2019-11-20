package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import ezy.ui.layout.LoadingLayout;

/**
 * 评价成功-页面
 */
public class AppraiseSuccessfulActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_tv_look_appraise)
    TextView mTvLookAppraise;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private int startPage = 1;
    // 一次请求多少数据
    private static final int REQUEST_COUNT = 20;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, AppraiseSuccessfulActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_appraise_successful;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(AppraiseSuccessfulActivity.this, cl_e51C23, 1);
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
        onLove();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(true);
    }

    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
//        mLoadingLayout.setEmptyImage(R.drawable.ic_no_address);
        mLoadingLayout.setEmptyText("暂无数据");
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLove();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(AppraiseSuccessfulActivity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(AppraiseSuccessfulActivity.this, mData.get(position).getCommodity().getId());
            }
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mAdapter.notifyDataSetChanged();
                startPage++;
                onLove();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mData.clear();
                startPage = 1;
                onLove();
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_look_appraise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_look_appraise:   // 查看评价
                LookAppraiseActivity.openActivity(AppraiseSuccessfulActivity.this);
                break;
        }
    }

    /**
     * 猜你喜欢接口
     */
    private void onLove() {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DOFINDMAYBEYOULIKE)//
                .params("page", startPage)//
                .params("size", REQUEST_COUNT)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindMaybeYouLikeData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoFindMaybeYouLikeData> data = simpleResponseAppResponse.getData();
                            mData.addAll(data);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        myScrollView.smoothScrollTo(0, 20);
                        mAdapter.notifyDataSetChanged();
                        if (mData.size() > 0) {
                            mLoadingLayout.showContent();
                        }
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
                });
    }
}
