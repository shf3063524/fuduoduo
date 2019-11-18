package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.MultipleListBean;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/9/26 10:24
 * Email：1511808259@qq.com
 */
public class NationalDayAdapter extends BaseQuickAdapter<MultipleListBean, BaseViewHolder> {
    public NationalDayAdapter(int layoutResId, @Nullable List<MultipleListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleListBean item) {
        // 商品图片
        GlideUtils.loadImage(mContext, PictureFileUtils.getImage(item.getImages()), R.drawable.ic_all_background, helper.getView(R.id.m_iv_shopping));
        // 商品名称
        helper.setText(R.id.m_tv_content, item.getName());
        // 商品价格
        helper.setText(R.id.m_tv_price, DoubleUtil.double2Str(item.getPrice()));
    }
}

