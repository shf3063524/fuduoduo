package com.hjkj.fuduoduo.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.HistoryAdapter;
import com.hjkj.fuduoduo.adapter.PopularListAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.view.ClearEditText;
import com.hjkj.fuduoduo.view.selfSearchGridView;

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
    @BindView(R.id.m_tv_clear)
    TextView mTvClear;
    @BindView(R.id.m_cet_search)
    ClearEditText mCetSearch;
    @BindView(R.id.grid_view_history)
    selfSearchGridView gridViewHistory;
    @BindView(R.id.grid_view_popular)
    selfSearchGridView gridViewPopular;


    private ArrayList<String> mHistoryData = new ArrayList<String>();
    private ArrayList<String> mPopularDataList = new ArrayList<String>();
    private HistoryAdapter historyAdapter;

    private String backtitle = "取消", searchtitle = "搜索";
    private PopularListAdapter popularAdapter;

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

    private void initRecyclerView() {
        String historyData = "澳洲美食,长沙美食,韩国料理,日本料理,舌尖上的中国,意大利餐,山西菜";
        List<String> history = Arrays.asList(historyData.split(","));
        if (history != null) {
            mHistoryData.addAll(history);
            historyAdapter = new HistoryAdapter(HomeSearchActivity.this, mHistoryData);
            gridViewHistory.setAdapter(historyAdapter);
        }

        String popularData = "火龙果,长沙美食,雅诗兰黛,日本料理,舌尖上的中国,意大利餐,山西菜,小米吹风机,饼干," +
                "长沙美食,雅诗兰黛,日本料理,舌尖上的中国,意大利餐,山西菜,长沙美食,雅诗兰黛,日本料理,舌尖上的中国";
        List<String> popular = Arrays.asList(popularData.split(","));
        if (popular != null) {
            mPopularDataList.addAll(popular);
            popularAdapter = new PopularListAdapter(HomeSearchActivity.this, mPopularDataList);
            gridViewPopular.setAdapter(popularAdapter);

        }
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
                mTvArrow.setText(searchtitle);
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

}
