package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.CollectionsBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryCollectionsShopData;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/5 21:00
 * Email：1511808259@qq.comhost
 */
public class MyCollectionShoppingAdapter extends BaseQuickAdapter<DoQueryCollectionsShopData, BaseViewHolder> {
    private boolean singleSelect = false;

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
}
