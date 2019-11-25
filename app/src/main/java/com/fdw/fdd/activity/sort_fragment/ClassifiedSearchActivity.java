package com.fdw.fdd.activity.sort_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.ClassifiedSearchAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoFindMaybeYouLikeData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.view.ClearEditText;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索界面-查询
 */
public class ClassifiedSearchActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_price)
    ImageView mIvPrice;
    @BindView(R.id.m_tv_complex)
    TextView mTvComplex;
    @BindView(R.id.m_tv_volume)
    TextView mTvVolume;
    @BindView(R.id.m_tv_return)
    TextView mTvReturn;
    @BindView(R.id.m_tv_price)
    TextView mTvPrice;
    @BindView(R.id.m_et_shop)
    ClearEditText mEtShop;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    @BindColor(R.color.cl_999)
    int cl_999;

    private boolean check = false;
    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ClassifiedSearchAdapter mAdapter;
    private String categoryId;
    private String inputText;
    private String backtitle = "取消", searchtitle = "搜索";

    public static void openActivity(Context context, String id, String searchtext) {
        Intent intent = new Intent(context, ClassifiedSearchActivity.class);
        intent.putExtra("categoryId", id);
        intent.putExtra("searchtext", searchtext);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_classified_search;
    }

    @Override
    protected void initPageData() {
        categoryId = getIntent().getStringExtra("categoryId");
        String searchtext = getIntent().getStringExtra("searchtext");
        inputText = searchtext;
        mEtShop.setText(searchtext);
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (inputText == null || "".equals(inputText)) {
            complexData();
        } else {
            integratedQuery(inputText);
        }
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ClassifiedSearchAdapter(R.layout.item_classified_search, mData);
//        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
//        mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ClassifiedSearchActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(ClassifiedSearchActivity.this, mData.get(position).getCommodity().getId());
            }
        });

        mEtShop.addTextChangedListener(new MyTextWatcher());
        mEtShop.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    inputText = mEtShop.getText().toString().trim();

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
                mTvReturn.setText(searchtitle);
            } else {
                mTvReturn.setText(backtitle);
            }
        }
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_complex, R.id.m_tv_volume, R.id.m_tv_price, R.id.m_tv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_complex: // 综合
                mTvComplex.setTextColor(cl_e51C23);
                mTvVolume.setTextColor(cl_999);
                mTvPrice.setTextColor(cl_999);
                mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_price_sort));
                if (inputText == null) {
                    complexData();
                } else {
                    integratedQuery(inputText);
                }
                break;
            case R.id.m_tv_volume: // 销量
                mTvVolume.setTextColor(cl_e51C23);
                mTvComplex.setTextColor(cl_999);
                mTvPrice.setTextColor(cl_999);
                mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_price_sort));
                salesData(inputText);
                break;
            case R.id.m_tv_price: // 价格
                if (check) {
                    check = false;
                    mTvVolume.setTextColor(cl_999);
                    mTvComplex.setTextColor(cl_999);
                    mTvPrice.setTextColor(cl_e51C23);
                    mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_from_high_to_low));
                    priceData(inputText, "0");
                } else {
                    check = true;
                    mTvVolume.setTextColor(cl_999);
                    mTvComplex.setTextColor(cl_999);
                    mTvPrice.setTextColor(cl_e51C23);
                    mIvPrice.setImageDrawable(getResources().getDrawable(R.drawable.ic_from_low_to_high));
                    priceData(inputText, "1");
                }
                break;
            case R.id.m_tv_return:
                closeKeyboard();
                String searchtext = mEtShop.getText().toString().trim();
                if (getTextString(mTvReturn).equals(searchtitle)) {
                    integratedQuery(searchtext);
                } else {
                    return;
                }
                break;
        }
    }

    //只是关闭软键盘
    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 综合数据
     */
    private void complexData() {
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
                    }
                });
    }

    /**
     * 销量数据
     *
     * @param name 搜索名称
     */
    private void salesData(String name) {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DOQUERYBYFICTITIOUSVOLUME)//
                .params("categoryId", categoryId)
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
                    }
                });
    }

    /**
     * 价格数据
     *
     * @param name  搜索名称
     * @param state
     */
    private void priceData(String name, String state) {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DOQUERYBYPRICE)//
                .params("categoryId", categoryId)
                .params("name", name)
                .params("state", state)
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
                    }
                });
    }

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
                    }
                });
    }

}
