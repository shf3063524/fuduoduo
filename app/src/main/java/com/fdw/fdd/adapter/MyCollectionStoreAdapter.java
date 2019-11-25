package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;
import android.widget.Filter;
import android.widget.Filterable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoQueryCollectionsStoreData;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;

/**
 * Author：Created by shihongfei on 2019/10/6 10:07
 * Email：1511808259@qq.com
 */
public class MyCollectionStoreAdapter extends BaseItemDraggableAdapter<DoQueryCollectionsStoreData, BaseViewHolder> implements Filterable {
    private AdapterFilter adapterFilter;
    /**
     * 当前处于打开状态的item
     */
    private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();
    public MyCollectionStoreAdapter(int layoutResId, @Nullable List<DoQueryCollectionsStoreData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryCollectionsStoreData item) {
        BGASwipeItemLayout mSwipeItemLayout = (BGASwipeItemLayout) helper.getView(R.id.m_swipe_item_layout);
        // 商店名称
        helper.setText(R.id.m_tv_store,item.getName());
        // 商店logo



        // if (Integer.parseInt(item.getHasUse()) > 0) {
        //     mSwipeItemLayout.setSwipeAble(false);
        // } else {
            mSwipeItemLayout.setSwipeAble(true);
        // }
        mSwipeItemLayout.setDelegate(new BGASwipeItemLayout.BGASwipeItemLayoutDelegate() {
            @Override
            public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout) {
                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
            }
        });

        helper.addOnClickListener(R.id.m_iv_delete);
        helper.addOnClickListener(R.id.m_cv_store);
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (BGASwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }

    public boolean isExistOpenItem() {
        if (!mOpenedSil.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public Filter getFilter() {
        if (adapterFilter == null) {
            adapterFilter = new AdapterFilter((List<DoQueryCollectionsStoreData>) mData);
        }
        return adapterFilter;
    }

    private class AdapterFilter extends Filter {

        private List<DoQueryCollectionsStoreData> originalData;

        public AdapterFilter(List<DoQueryCollectionsStoreData> list) {
            this.originalData = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = originalData;
                results.count = originalData.size();
            } else {
                List<DoQueryCollectionsStoreData> filterData = new ArrayList<DoQueryCollectionsStoreData>();
                for (DoQueryCollectionsStoreData data : originalData) {
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
            mData = (List<DoQueryCollectionsStoreData>) results.values;
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