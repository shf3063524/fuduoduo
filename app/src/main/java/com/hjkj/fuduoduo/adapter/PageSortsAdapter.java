package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoFindHomePageSortsData;
import com.hjkj.fuduoduo.tool.GlideUtils;

import java.util.List;

public class PageSortsAdapter extends BaseQuickAdapter<DoFindHomePageSortsData, BaseViewHolder> {
    public PageSortsAdapter(int layoutResId, @Nullable List<DoFindHomePageSortsData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoFindHomePageSortsData item) {
        // 分类图片
        GlideUtils.loadImage(mContext,item.getSortImage(), R.drawable.ic_all_background,helper.getView(R.id.m_iv_name));
        // 分类名称
        helper.setText(R.id.m_tv_name,item.getName());
    }
}

