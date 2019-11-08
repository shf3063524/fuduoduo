package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.CommoditiesBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryCollectionsShopData;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/7 22:47
 * Email：1511808259@qq.com
 */
public class FootPrintTypelAdapter extends BaseQuickAdapter<CommoditiesBean, BaseViewHolder> {
    private boolean isShow;
    private boolean isCheck = false;

    public FootPrintTypelAdapter(int layoutResId, @Nullable List<CommoditiesBean> data, boolean isShow) {
        super(layoutResId, data);
        this.isShow = isShow;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommoditiesBean item) {
        // 商品图片
        GlideUtils.loadImage(mContext, PictureFileUtils.getImage(item.getImages()), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        // 商品积分
        helper.setText(R.id.m_tv_integral, DoubleUtil.double2Str(item.getPrice()) + "  积分");

        if (isShow) {
            helper.setGone(R.id.m_iv_integral, true);
        } else {
            helper.setGone(R.id.m_iv_integral, false);
        }

        // 根据选中状态 更改UI
        final boolean check = item.isClickable();
        if (check) {
            helper.setImageResource(R.id.m_iv_integral, R.drawable.ic_yes_check);
        } else {
            helper.setImageResource(R.id.m_iv_integral, R.drawable.ic_no_check);
        }

        helper.getView(R.id.m_cv_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 按钮选中与未选中状态
                if (isCheck) {
                    isCheck = false;
                    helper.setImageResource(R.id.m_iv_integral, R.drawable.ic_no_check);
                } else {
                    isCheck = true;
                    helper.setImageResource(R.id.m_iv_integral, R.drawable.ic_yes_check);
                }
            }
        });

        helper.addOnClickListener(R.id.m_iv_shopping);
    }

    public ArrayList<CommoditiesBean> getCheckIdLists() {
        ArrayList<CommoditiesBean> checkIdLists = new ArrayList<>();
        for (CommoditiesBean authBizUserUserListData : mData) {
            if (authBizUserUserListData.isCheck()) {
                checkIdLists.add(authBizUserUserListData);
            }
        }
        return checkIdLists;
    }

}
