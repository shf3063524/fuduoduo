package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.CollectionsBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryCollectionsShopData;
import com.hjkj.fuduoduo.entity.bean.DoqueryexpresscompanyData;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/5 21:00
 * Email：1511808259@qq.comhost
 */
public class MyCollectionShoppingAdapter extends BaseQuickAdapter<DoQueryCollectionsShopData, BaseViewHolder> implements Filterable {
    private boolean singleSelect = false;
    private AdapterFilter adapterFilter;

    public MyCollectionShoppingAdapter(int layoutResId, @Nullable List<DoQueryCollectionsShopData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryCollectionsShopData item) {
        // 显示与不显示
        if (item.isClickcheck()) {
            helper.getView(R.id.m_img_is_check).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.m_img_is_check).setVisibility(View.GONE);
        }
        // 选定与不选定
        final boolean check = item.isCheck();
        if (check) {
            helper.setImageResource(R.id.m_img_is_check, R.drawable.ic_yes_check);
        } else {
            helper.setImageResource(R.id.m_img_is_check, R.drawable.ic_no_check);
        }
        helper.getView(R.id.m_img_is_check).setEnabled(true);
        helper.getView(R.id.m_img_is_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!singleSelect) {
                    if (!check) {
                        item.setCheck(true);
                    } else {
                        item.setCheck(false);
                    }
                } else {
                    for (DoQueryCollectionsShopData listData02 : mData) {
                        listData02.setCheck(false);
                    }
                    item.setCheck(true);
                }
                notifyDataSetChanged();
            }
        });
        CollectionsBean collections = item.getCollections();
        // 商品图片
        GlideUtils.loadImage(mContext, PictureFileUtils.getImage(collections.getImages()), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        // 商品名称
        helper.setText(R.id.m_tv_content, collections.getName());
        // 积分
        helper.setText(R.id.m_tv_integral, DoubleUtil.double2Str(collections.getPrice()) + "  积分");
        // 销量
        helper.setText(R.id.m_tv_number, "已售：" + collections.getSaleVolume());
        helper.addOnClickListener(R.id.m_cv_shop);

    }

    public ArrayList<DoQueryCollectionsShopData> getCheckIdLists() {
        ArrayList<DoQueryCollectionsShopData> checkIdLists = new ArrayList<>();
        for (DoQueryCollectionsShopData authBizUserUserListData : mData) {
            if (authBizUserUserListData.isCheck()) {
                checkIdLists.add(authBizUserUserListData);
            }
        }
        return checkIdLists;
    }

    /**
     * 设置单选
     */
    public void setSingleSelect(boolean singleSelect) {
        this.singleSelect = singleSelect;
    }

    @Override
    public Filter getFilter() {
        if (adapterFilter == null) {
            adapterFilter = new AdapterFilter((List<DoQueryCollectionsShopData>) mData);
        }
        return adapterFilter;
    }

    private class AdapterFilter extends Filter {

        private List<DoQueryCollectionsShopData> originalData;

        public AdapterFilter(List<DoQueryCollectionsShopData> list) {
            this.originalData = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = originalData;
                results.count = originalData.size();
            } else {
                List<DoQueryCollectionsShopData> filterData = new ArrayList<DoQueryCollectionsShopData>();
                for (DoQueryCollectionsShopData data : originalData) {
                    String searchContent = constraint.toString().toUpperCase();
                    // 名称是否包含搜索内容
                    String name = data.getCollections().getName();
                    boolean isNameIsContains = false;
//                    if (!TextUtils.isEmpty(name) && "0".equals(data.getHasAccount())) {
//                        // 如果是散客直接添加
//                        isNameIsContains = false;
//                    } else {
                    isNameIsContains = name.toUpperCase().contains(searchContent);
//                    }
                    // 积分
                    String price = DoubleUtil.double2Str(data.getCollections().getPrice());
                    boolean isPhoneIsContains = false;
                    if (!TextUtils.isEmpty(price)) {
                        isPhoneIsContains = price.toUpperCase().contains(searchContent);
                    }
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

                    if (isNameIsContains || isPhoneIsContains) {
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
            mData = (List<DoQueryCollectionsShopData>) results.values;
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
