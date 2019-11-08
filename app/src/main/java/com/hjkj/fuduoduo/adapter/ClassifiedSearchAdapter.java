package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.CommodityBean;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.entity.bean.DoQueryCollectionsShopData;
import com.hjkj.fuduoduo.entity.bean.ShopBean;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassifiedSearchAdapter extends BaseQuickAdapter<DoFindMaybeYouLikeData, BaseViewHolder> {
    public ClassifiedSearchAdapter(int layoutResId, @Nullable List<DoFindMaybeYouLikeData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoFindMaybeYouLikeData item) {
        //商品
        CommodityBean commodity = item.getCommodity();
        // 图片
        GlideUtils.loadImage(mContext, PictureFileUtils.getImage(commodity.getImages()), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        // 图片内容
        helper.setText(R.id.m_tv_content, commodity.getName());
        // 积分
        helper.setText(R.id.m_tv_integral, DoubleUtil.double2Str(commodity.getPrice()));
        // 已售
        helper.setText(R.id.m_tv_salesnumber, "已售：" + commodity.getSaleVolume());


        // 店铺
        ShopBean shop = item.getShop();
        helper.setText(R.id.m_tv_store, shop.getName());
    }
}


