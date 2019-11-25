package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.BestSellingData;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.GlideUtils;

import java.util.List;

public class BestSellingAdapter extends BaseQuickAdapter<BestSellingData, BaseViewHolder> {
    public BestSellingAdapter(int layoutResId, @Nullable List<BestSellingData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BestSellingData item) {
        // 商品图片
        GlideUtils.loadImage(mContext,item.getImage(), R.drawable.ic_all_background,helper.getView(R.id.m_iv_shopping));
        // 商品名称
        helper.setText(R.id.m_tv_name,item.getName());
        //商品价格
        helper.setText(R.id.m_tv_integral,DoubleUtil.double2Str(item.getPrice()));
        // 月销
        helper.setText(R.id.m_tv_salesnumber,"月销" + item.getSalesNumber() + "笔");
    }
}