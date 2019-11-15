package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.bean.DoQueryData;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/10/2 15:07
 * Email：1511808259@qq.com
 */
public class MyShippingAddressAdapter extends BaseQuickAdapter<DoQueryData, BaseViewHolder> {
    public MyShippingAddressAdapter(int layoutResId, @Nullable List<DoQueryData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryData item) {
        // 姓
        String name = item.getName();
        char words[] = name.toCharArray();
        helper.setText(R.id.m_tv_xing, String.valueOf(words[0]));
        // 名字
        helper.setText(R.id.m_tv_name, item.getName());

        // 手机号
        helper.setText(R.id.m_tv_phone, item.getMobilephoneNumber());
        // 地址
        helper.setText(R.id.m_tv_address, item.getProvince() + item.getCity() + item.getArea() + item.getStreet());
        // 默认地址
        if ("1".equals(item.getDefaultAddress())){
            helper.setGone(R.id.m_iv_default,true);
        }else {
            helper.setGone(R.id.m_iv_default,false);
        }
        helper.addOnClickListener(R.id.m_iv_writer);
        helper.addOnClickListener(R.id.m_layout_item);
    }
}

