package com.hjkj.fuduoduo.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.activity.sort_fragment.ClassifiedSearchActivity;
import com.hjkj.fuduoduo.adapter.HistoryAdapter;
import com.hjkj.fuduoduo.adapter.HotListAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.BestSellingData;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.entity.bean.DoqueryassociationData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.view.ClearEditText;
import com.hjkj.fuduoduo.view.selfSearchGridView;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索查询-有历史搜索-热门推荐
 */
public class HomeSearchActivity extends BaseActivity {
    @BindView(R.id.m_tv_return)
    TextView mTvArrow;
    @BindView(R.id.m_layout_history)
    RelativeLayout mLayoutHistory;
    @BindView(R.id.m_rl_recycler)
    RelativeLayout mRlRecycler;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_recycler_view_hot)
    RecyclerView mRecyclerViewHot;
    @BindView(R.id.m_tv_clear)
    TextView mTvClear;
    @BindView(R.id.m_tv_title)
    TextView mTvTitle;
    @BindView(R.id.m_cet_search)
    ClearEditText mCetSearch;
    @BindView(R.id.grid_view_history)
    selfSearchGridView gridViewHistory;


    private ArrayList<String> mHistoryData = new ArrayList<String>();
    private ArrayList<BestSellingData> mHotData;
    private HistoryAdapter historyAdapter;

    private String backtitle = "取消", searchtitle = "搜索";
    private HotListAdapter mHotAdapter;
    private ArrayList<DoFindMaybeYouLikeData> mData;
    private SearchAdapter mAdapter;
    private String trimText;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, HomeSearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bestSelling();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new SearchAdapter(R.layout.m_item_search, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeSearchActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        String historyData = "澳洲美食,长沙美食,韩国料理,日本料理,舌尖上的中国,意大利餐,山西菜";
        List<String> history = Arrays.asList(historyData.split(","));
        if (history != null) {
            mHistoryData.addAll(history);
            historyAdapter = new HistoryAdapter(HomeSearchActivity.this, mHistoryData);
            gridViewHistory.setAdapter(historyAdapter);
        }

        mHotData = new ArrayList<>();
        mHotAdapter = new HotListAdapter(R.layout.m_item_hot, mHotData);
//        mRecyclerViewHot.setLayoutManager(new GridLayoutManager(HomeSearchActivity.this, 3));
//        mRecyclerViewHot.setAdapter(mHotAdapter);
        LinearLayoutManager layoutManager02 = new LinearLayoutManager(HomeSearchActivity.this);
        mRecyclerViewHot.setLayoutManager(layoutManager02);
        mRecyclerViewHot.setAdapter(mHotAdapter);
    }

    @Override
    protected void actionView() {
        mCetSearch.addTextChangedListener(new MyTextWatcher());
        mCetSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    String searchtext = mCetSearch.getText().toString().trim();

                    executeSearch_and_NotifyDataSetChanged(searchtext);

                    return true;
                }
                return false;
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ClassifiedSearchActivity.openActivity(HomeSearchActivity.this, "",trimText);
                finish();
            }
        });

        mHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(HomeSearchActivity.this, mHotData.get(position).getId());
            }
        });
    }

    //文本观察者
    private class MyTextWatcher implements TextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        //当文本改变时候的操作
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            //如果编辑框中文本的长度大于0就显示删除按钮否则不显示
            if (s.length() > 0) {
                trimText = s.toString().trim();
                mTvArrow.setText(searchtitle);
                integratedQuery(trimText);
            } else {
                mTvArrow.setText(backtitle);
            }
        }

    }

    @OnClick({R.id.m_tv_return, R.id.m_tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_tv_return:   // 返回
                String searchtext = mCetSearch.getText().toString().trim();
                if (mTvArrow.getText().toString().equals(searchtitle)) {
//                    Toast.makeText(context, "点击button搜索" + searchtext, Toast.LENGTH_SHORT).show();
                    mLayoutHistory.setVisibility(View.VISIBLE);
                    executeSearch_and_NotifyDataSetChanged(searchtext);
                } else {
//                    Toast.makeText(context, "点击button  返回", Toast.LENGTH_SHORT).show();
                    if (!clickBack()) {
                        finish();
                    }
                }
                break;
            case R.id.m_tv_clear: //清除
                mHistoryData.clear();
                historyAdapter.notifyDataSetChanged();
                mLayoutHistory.setVisibility(View.GONE);
                break;
        }
    }


    private void executeSearch_and_NotifyDataSetChanged(String str) {
        if ((!str.equals(""))) {
            if (mHistoryData.size() > 0 && mHistoryData.get(0).equals(str)) {
            } else {
                mHistoryData.add(0, str);//把最新的添加到前面
                historyAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 搜索接口
     *
     * @param name
     */
//    private void onLenovo(String name) {
//        OkGo.<AppResponse<ArrayList<DoqueryassociationData>>>get(Api.CATEGORY_DOQUERYASSOCIATION)//
//                .params("name", name)
//                .execute(new JsonCallBack<AppResponse<ArrayList<DoqueryassociationData>>>() {
//                    @Override
//                    public void onSuccess(AppResponse<ArrayList<DoqueryassociationData>> simpleResponseAppResponse) {
//                        if (simpleResponseAppResponse.isSucess()) {
//                            mData.clear();
//                            ArrayList<DoqueryassociationData> appResponseData = simpleResponseAppResponse.getData();
//                            mData.addAll(appResponseData);
//                        }
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        mAdapter.notifyDataSetChanged();
//                        if (mData.size() > 0) {
//                            mRlRecycler.setVisibility(View.VISIBLE);
//                        } else {
//                            mRlRecycler.setVisibility(View.GONE);
//                        }
//                    }
//                });
//    }

    /**
     * 根据名称综合查询
     */
    private void integratedQuery(String name) {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DOQUERYBYNAME)//
                .params("name", name)
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
                        if (mData.size() > 0) {
                            mRlRecycler.setVisibility(View.VISIBLE);
                        } else {
                            mRlRecycler.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 爆款热卖
     */
    private void bestSelling() {
        OkGo.<AppResponse<ArrayList<BestSellingData>>>get(Api.COMMODITY_HOTSALE)//
                .execute(new JsonCallBack<AppResponse<ArrayList<BestSellingData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<BestSellingData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mHotData.clear();
                            ArrayList<BestSellingData> data = simpleResponseAppResponse.getData();
                            mHotData.addAll(data);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mHotAdapter.notifyDataSetChanged();
                    }
                });
    }

}
