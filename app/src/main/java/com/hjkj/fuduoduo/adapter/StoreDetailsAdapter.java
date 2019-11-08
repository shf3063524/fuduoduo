package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.CommoditieBean;
import com.hjkj.fuduoduo.entity.bean.CommodityBean;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.List;

public class StoreDetailsAdapter extends BaseQuickAdapter<CommodityBean, BaseViewHolder> {
    public StoreDetailsAdapter(int layoutResId, @Nullable List<CommodityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityBean item) {

        // 商品图片
        GlideUtils.loadImage(mContext, PictureFileUtils.getImage(item.getImages()), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        //商品名称
        helper.setText(R.id.m_tv_name, item.getName());
        // 已售
        helper.setText(R.id.m_tv_salesnumber, "已售：" + item.getFictitiousVolume());
        //积分
        helper.setText(R.id.m_tv_integral, DoubleUtil.double2Str(item.getPrice()));
    }
}


