package com.fdw.fdd.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoQueryData;

import java.util.List;

public class ApplyForAreplacementAdapter extends BaseQuickAdapter<DoQueryData, BaseViewHolder> {
    public ApplyForAreplacementAdapter(int layoutResId, @Nullable List<DoQueryData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQueryData item) {
            if (item.isClickable()){
                ((CheckBox) helper.getView(R.id.m_cb)).setClickable(true);
                ((CheckBox) helper.getView(R.id.m_cb)).setChecked(true);
            }else{
                ((CheckBox) helper.getView(R.id.m_cb)).setClickable(false);
                ((CheckBox) helper.getView(R.id.m_cb)).setChecked(false);
            }
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
    }
}
