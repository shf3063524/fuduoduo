package com.hjkj.fuduoduo.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.adapter.SlidingTabAdapter;
import com.hjkj.fuduoduo.base.BaseFragment;
import com.hjkj.fuduoduo.entity.bean.BestSellingData;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 首页下的导航下页面对应的Fragmnet
 */
public class SlidingTabFragment extends BaseFragment {

    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private SlidingTabAdapter mAdapter;
    /**
     * 点击条目传的id
     */
    private String categoryId;

    public static SlidingTabFragment newInstance(String categoryId) {
        SlidingTabFragment fragment = new SlidingTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_sliding_tab;
    }

    @Override
    protected void initPageData(Bundle bundle) {
        super.initPageData(bundle);
        if (getArguments() != null) {
            categoryId = getArguments().getString("categoryId");
        }
    }

    @Override
    protected void initViews() {
        initRefreshLayout();
        initRecyclerView();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (categoryId == null) {
            requestData();
        } else {
            doRecommendCategory(categoryId);
        }
    }
    private void initRefreshLayout() {
//        mRefreshLayout.setEnableRefresh(false);
//        mRefreshLayout.setEnableLoadMore(true);
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new SlidingTabAdapter(R.layout.item_sliding_tab, mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        if (unbinder != null) {
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ProductDetailsActivity.openActivity(mContext,mData.get(position).getCommodity().getId());
                }
            });
            mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {
                }

                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    if (categoryId == null) {
                        requestData();
                    } else {
                        doRecommendCategory(categoryId);
                    }
                }
            });
        }

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void requestData() {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DOFINDMAYBEYOULIKE)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindMaybeYouLikeData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoFindMaybeYouLikeData> data = simpleResponseAppResponse.getData();
                            mData.clear();
                            mData.addAll(data);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                        if (unbinder != null) {
//                            mRefreshLayout.finishRefresh();
//                            mRefreshLayout.finishLoadMore();
                        }
                    }
                });
    }

    /**
     * 首页类目推荐
     */
    private void doRecommendCategory(String categoryId) {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DORECOMMENDCATEGORY)//
                .params("categoryId", categoryId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindMaybeYouLikeData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoFindMaybeYouLikeData> data = simpleResponseAppResponse.getData();
                            mData.clear();
                            mData.addAll(data);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                        if (unbinder != null) {
//                            mRefreshLayout.finishRefresh();
//                            mRefreshLayout.finishLoadMore();
                        }
                    }
                });
    }
}