package com.fdw.fdd.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.home_fragment.HomeSearchActivity;
import com.fdw.fdd.adapter.ProductListAdapter;
import com.fdw.fdd.adapter.ProductTypeAdapter;
import com.fdw.fdd.base.BaseFragment;
import com.fdw.fdd.entity.bean.DoFindCategoryData;
import com.fdw.fdd.entity.bean.DoQueryCategoryDetailsData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import ezy.ui.layout.LoadingLayout;

/**
 * 分类-Fragment
 * Author：Created by shihongfei on 2019/9/24 14:34
 * Email：1511808259@qq.com
 */
public class SortFragment extends BaseFragment {
    @BindView(R.id.m_product_type_recycler)
    RecyclerView mProductTypeRecycler;
    @BindView(R.id.m_product_recycler)
    RecyclerView mProductRecycler;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_layout_search)
    LinearLayout mLayoutSearch;
    //  产品种类适配器和数据源
    private ProductTypeAdapter mProductTypeAdapter;
    private ArrayList<DoFindCategoryData> mProductTypeData;
    //  产品列表适配器和数据源
    private ProductListAdapter mProductListAdapter;
    private ArrayList<DoQueryCategoryDetailsData> mProductData;
    private String templateId = "-1";

    public static SortFragment newInstance() {
        SortFragment fragment = new SortFragment();
        fragment.setArguments(null);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initViews() {
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
    }

    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
        mLoadingLayout.setEmptyImage(R.drawable.ic_backgroud_shop);
        mLoadingLayout.setEmptyText("暂无商品");
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        leftData();
    }

    private void initRecyclerView() {
        mProductTypeData = new ArrayList<>();
        mProductTypeAdapter = new ProductTypeAdapter(R.layout.item_product_type, mProductTypeData);
        LinearLayoutManager layoutManagerList = new LinearLayoutManager(mContext);
        mProductTypeRecycler.setLayoutManager(layoutManagerList);
        mProductTypeRecycler.setAdapter(mProductTypeAdapter);

        mProductData = new ArrayList<>();
        mProductListAdapter = new ProductListAdapter(R.layout.item_product_list, mProductData);
        LinearLayoutManager layoutManagerDetail = new LinearLayoutManager(mContext);
        mProductRecycler.setLayoutManager(layoutManagerDetail);
        mProductRecycler.setAdapter(mProductListAdapter);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void actionView() {
        super.actionView();
        mProductTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if ("-1".equals(templateId)) {
                    mRefreshLayout.setEnableRefresh(true);
                    mRefreshLayout.setEnableAutoLoadMore(true);
                }
                // 根据item的数量建一个集合，记录每个position的点击状态，
                //  在onBindViewHolder中检查表中点击的状态然后设置数据，
                // 这样就不会出现错乱的问题
                for (DoFindCategoryData data : mProductTypeData) {
                    data.setClickable(false);
                }
                mProductTypeData.get(position).setClickable(true);
                mProductTypeAdapter.notifyDataSetChanged();
                templateId = mProductTypeData.get(position).getCategoryId();
                rightData(templateId);
                mRefreshLayout.finishRefresh();
            }
        });

    }

    @OnClick({R.id.m_layout_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_layout_search:   // 搜索
                HomeSearchActivity.openActivity(mContext);
                break;
        }
    }

    /**
     * 左侧数据加载
     */
    private void leftData() {
        OkGo.<AppResponse<ArrayList<DoFindCategoryData>>>get(Api.CATEGORY_DOQUERYCATEGOR)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindCategoryData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindCategoryData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoFindCategoryData> tempList = simpleResponseAppResponse.getData();
                            mProductTypeData.clear();
                            mProductTypeData.addAll(tempList);

                            mProductTypeData.get(0).setClickable(true);
                            mProductTypeAdapter.notifyDataSetChanged();
                            templateId = mProductTypeData.get(0).getCategoryId();
                            rightData(templateId);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mProductTypeAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 右边数据加载
     *
     */
    private void rightData(String id) {
        OkGo.<AppResponse<ArrayList<DoQueryCategoryDetailsData>>>get(Api.CATEGORY_DOQUERYCATEGORYDETAILS)//
                .params("id", id)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryCategoryDetailsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryCategoryDetailsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mProductData.clear();
                            ArrayList<DoQueryCategoryDetailsData> tempList = simpleResponseAppResponse.getData();
                            mProductData.addAll(tempList);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mProductListAdapter.notifyDataSetChanged();
                        if (mProductData.size() > 0 && unbinder != null) {
                            mLoadingLayout.showContent();
                        }else {
                            mLoadingLayout.showEmpty();
                        }
                        if (unbinder != null) {
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.finishLoadMore();
                        }
                    }
                });
    }
}
