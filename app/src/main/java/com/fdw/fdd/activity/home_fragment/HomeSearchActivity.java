package com.fdw.fdd.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.activity.sort_fragment.ClassifiedSearchActivity;
import com.fdw.fdd.adapter.HistoryAdapter;
import com.fdw.fdd.adapter.HotListAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.BestSellingData;
import com.fdw.fdd.entity.bean.DoFindMaybeYouLikeData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.view.ClearEditText;
import com.fdw.fdd.view.selfSearchGridView;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import scut.carson_ho.searchview.RecordSQLiteOpenHelper;

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
    @BindView(R.id.grid_view_history)
    selfSearchGridView histotyRecycler;
    @BindView(R.id.m_recycler_view_hot)
    RecyclerView mRecyclerViewHot;
    @BindView(R.id.m_tv_clear)
    TextView mTvClear;
    @BindView(R.id.m_tv_title)
    TextView mTvTitle;
    @BindView(R.id.m_cet_search)
    ClearEditText mCetSearch;


    private ArrayList<BestSellingData> mHotData;
    private String backtitle = "取消", searchtitle = "搜索";
    private HotListAdapter mHotAdapter;
    private ArrayList<DoFindMaybeYouLikeData> mData;
    private SearchAdapter mAdapter;
    private String trimText;

    private HistoryAdapter historyAdapter;
    //    private HistorySearchAdapter adapter;
    private RecordSQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private BaseAdapter mHisAdapter;

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
        initHistory();
    }


    @Override
    protected void onResume() {
        super.onResume();
        bestSelling();
    }

    private void initHistory() {
        helper = new RecordSQLiteOpenHelper(HomeSearchActivity.this);
        // 3. 第1次进入时查询所有的历史搜索记录
        queryData("");
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new SearchAdapter(R.layout.m_item_search, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeSearchActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

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
        mCetSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                    boolean hasData = hasData(mCetSearch.getText().toString().trim());
                    // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                    if (!hasData) {
                        insertData(mCetSearch.getText().toString().trim());
                        queryData("");
                    }
                }
                return false;
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ClassifiedSearchActivity.openActivity(HomeSearchActivity.this, "", trimText);
                // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                boolean hasData = hasData(trimText);
                // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                if (!hasData) {
                    insertData(mCetSearch.getText().toString().trim());
                    queryData("");
                }
                finish();
            }
        });

        mHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(HomeSearchActivity.this, mHotData.get(position).getId());
            }
        });
        /**
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
        histotyRecycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取用户点击列表里的文字,并自动填充到搜索框内
                TextView textView = (TextView) view.findViewById(R.id.text);
                String name = textView.getText().toString();
                ClassifiedSearchActivity.openActivity(HomeSearchActivity.this, "", name);
                finish();
            }
        });
    }

    /**
     * 关注1
     * 模糊查询数据 & 显示到ListView列表上
     */
    private void queryData(String tempName) {

        // 1. 模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
        mHisAdapter = new SimpleCursorAdapter(HomeSearchActivity.this, R.layout.search_olddata_item, cursor, new String[]{"name"},
                new int[]{R.id.text}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 3. 设置适配器
        histotyRecycler.setAdapter(mHisAdapter);
        mHisAdapter.notifyDataSetChanged();

//        System.out.println(cursor.getCount());
        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if (tempName.equals("") && cursor.getCount() != 0){
            mLayoutHistory.setVisibility(View.VISIBLE);
        }
        else {
            mLayoutHistory.setVisibility(View.GONE);
        };

    }

    /**
     * 关注2：清空数据库
     */
    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    /**
     * 关注3
     * 检查数据库中是否已经有该搜索记录
     */
    private boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //  判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 关注4
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
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
                if (mTvArrow.getText().toString().equals(searchtitle)) {
                    mLayoutHistory.setVisibility(View.VISIBLE);
                    // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                    boolean hasData = hasData(mCetSearch.getText().toString().trim());
                    // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                    if (!hasData) {
                        insertData(mCetSearch.getText().toString().trim());
                        queryData("");
                    }
                } else {
//                    Toast.makeText(context, "点击button  返回", Toast.LENGTH_SHORT).show();
                    if (!clickBack()) {
                        finish();
                    }
                }
                break;
            case R.id.m_tv_clear: //清除
                // 清空数据库->>关注2
                deleteData();
                // 模糊搜索空字符 = 显示所有的搜索历史（此时是没有搜索记录的）
                queryData("");
                mLayoutHistory.setVisibility(View.GONE);
                break;
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
