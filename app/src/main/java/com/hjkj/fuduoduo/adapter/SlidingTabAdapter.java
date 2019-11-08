package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.CommodityBean;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/9/25 11:03
 * Email：1511808259@qq.com
 */
public class SlidingTabAdapter extends BaseQuickAdapter<DoFindMaybeYouLikeData, BaseViewHolder> {
    public SlidingTabAdapter(int layoutResId, @Nullable List<DoFindMaybeYouLikeData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoFindMaybeYouLikeData item) {
        CommodityBean commodity = item.getCommodity();
        // 商品图片
        GlideUtils.loadImage(mContext, PictureFileUtils.getImage(commodity.getImages()), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        // 商品名称
        helper.setText(R.id.m_tv_name, commodity.getName());
        //商品价格
        helper.setText(R.id.m_tv_integral_one, DoubleUtil.double2Str(commodity.getPrice()));
        // 月销
        helper.setText(R.id.m_tv_salesnumber, "月销" + commodity.getSaleVolume() + "笔");
    }
}

