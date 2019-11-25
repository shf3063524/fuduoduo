package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.SonBean;
import com.fdw.fdd.tool.GlideUtils;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/9/26 18:28
 * Email：1511808259@qq.com
 */
public class TypeDetailAdapter extends BaseQuickAdapter<SonBean, BaseViewHolder> {
    public TypeDetailAdapter(int layoutResId, @Nullable List<SonBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SonBean item) {
        // 商品图片
        GlideUtils.loadImage(mContext, item.getImage(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        // 商品名称
        helper.setText(R.id.m_tv_name, item.getName());
    }
}
