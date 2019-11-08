package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.SpecBean;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;

import java.util.List;

public class ConfirmOrderListAdapter extends BaseQuickAdapter<SpecBean, BaseViewHolder> {
    public ConfirmOrderListAdapter(int layoutResId, @Nullable List<SpecBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecBean item) {
        // 商品图片
        GlideUtils.loadImage(mContext, item.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        // 商品内容
        helper.setText(R.id.m_tv_content,item.getCommodity().getName());
        // 商品规格
        helper.setText(R.id.m_tv_specification,item.getSpecification().getCommoditySpecification());
        // 商品规格价格
        helper.setText(R.id.m_tv_price,"¥ " + DoubleUtil.double2Str(item.getSpecification().getSalePrice()));
        //选择商品数量
        helper.setText(R.id.m_tv_number,"x " + item.getNumber());
    }
}
