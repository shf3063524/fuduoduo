package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.Filterable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoqueryexpresscompanyData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseCompanyAdapter extends BaseQuickAdapter<DoqueryexpresscompanyData, BaseViewHolder> implements Filterable {
    private AdapterFilter adapterFilter;

    public ChooseCompanyAdapter(int layoutResId, @Nullable List<DoqueryexpresscompanyData> data) {
        super(layoutResId, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, DoqueryexpresscompanyData item) {
        // 快递名称
        helper.setText(R.id.m_tv_name, item.getName());
    }

    @Override
    public Filter getFilter() {
        if (adapterFilter == null) {
            adapterFilter = new AdapterFilter((List<DoqueryexpresscompanyData>) mData);
        }
        return adapterFilter;
    }

    private class AdapterFilter extends Filter {

        private List<DoqueryexpresscompanyData> originalData;

        public AdapterFilter(List<DoqueryexpresscompanyData> list) {
            this.originalData = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = originalData;
                results.count = originalData.size();
            } else {
                List<DoqueryexpresscompanyData> filterData = new ArrayList<DoqueryexpresscompanyData>();
                for (DoqueryexpresscompanyData data : originalData) {
                    String searchContent = constraint.toString().toUpperCase();
                    // 名称是否包含搜索内容
                    String name = data.getName();
                    boolean isNameIsContains = false;
//                    if (!TextUtils.isEmpty(name) && "0".equals(data.getHasAccount())) {
//                        // 如果是散客直接添加
//                        isNameIsContains = false;
//                    } else {
                    isNameIsContains = name.toUpperCase().contains(searchContent);
//                    }
                    // 手机号是否包含搜索内容
//                    String phone = data.getPhone();
//                    boolean isPhoneIsContains = false;
//                    if (!TextUtils.isEmpty(phone)) {
//                        isPhoneIsContains = phone.toUpperCase().contains(searchContent);
//                    }
//                    // 自定义信息搜索内容
//                    String tmplData = data.getTmplData();
//                    Type type = new TypeToken<HashMap<String, String>>() {
//                    }.getType();
//                    HashMap<String, String> map = GsonHelper.fromJson(tmplData, type);
//                    boolean isValue = false;
//                    if (map != null && !map.isEmpty()) {
//                        StringBuilder sb = new StringBuilder();
//                        for (Map.Entry<String, String> set : map.entrySet()) {
//                            String value = set.getValue();
//                            if (!TextUtils.isEmpty(value)) {
//                                sb.append(value);
//                            }
//                        }
//                        isValue = sb.toString().trim().toUpperCase().contains(searchContent);
//                    }
//                    // 会员卡名称搜索内容
//                    String cardName = data.getCardName();
//                    boolean isCardNameIsContains = false;
//                    if (!TextUtils.isEmpty(cardName)) {
//                        isCardNameIsContains = cardName.toUpperCase().contains(searchContent);
//                    }

                    if (isNameIsContains) {
                        filterData.add(data);
                    }
                }
                results.values = filterData;
                results.count = filterData.size();
            }
            return results;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData = (List<DoqueryexpresscompanyData>) results.values;
//            List tempList = Arrays.asList(mData.toArray());
//            if (filterCondition != 0) {
//                Collections.sort(tempList, getComparator());
//                mData.clear();
//                mData.addAll(tempList);
//            }
            notifyDataSetChanged();
        }
    }
}
