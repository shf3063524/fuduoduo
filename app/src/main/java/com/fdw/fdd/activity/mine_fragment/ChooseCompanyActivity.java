package com.fdw.fdd.activity.mine_fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.adapter.ChooseCompanyAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoqueryexpresscompanyData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.view.ClearEditText;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 选择物流公司
 */
public class ChooseCompanyActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_et_search)
    ClearEditText mEtSearch;
    private ArrayList<DoqueryexpresscompanyData> mData;
    private ChooseCompanyAdapter mAdapter;

    public static void openActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ChooseCompanyActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_choose_company;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        requestData();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ChooseCompanyAdapter(R.layout.item_choose_company, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChooseCompanyActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String newText = charSequence.toString().trim();
                mAdapter.getFilter().filter(newText); // 当数据改变时，调用过滤器；
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("company", mAdapter.getData().get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * 查询物流公司
     */
    @Override
    protected void requestData() {
        OkGo.<AppResponse<ArrayList<DoqueryexpresscompanyData>>>post(Api.ORDERS_DOQUERYEXPRESSCOMPANY)//
                .execute(new DialogCallBack<AppResponse<ArrayList<DoqueryexpresscompanyData>>>(this, "正在获取...") {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoqueryexpresscompanyData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoqueryexpresscompanyData> responseData = simpleResponseAppResponse.getData();
                            mData.addAll(responseData);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
