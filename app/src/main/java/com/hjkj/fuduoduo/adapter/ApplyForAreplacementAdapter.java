package com.hjkj.fuduoduo.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.TestBean;

import java.util.List;

public class ApplyForAreplacementAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    public ApplyForAreplacementAdapter(int layoutResId, @Nullable List<TestBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
            if (item.isClickable()){
                ((CheckBox) helper.getView(R.id.m_cb)).setClickable(true);
                ((CheckBox) helper.getView(R.id.m_cb)).setChecked(true);
            }else{
                ((CheckBox) helper.getView(R.id.m_cb)).setClickable(false);
                ((CheckBox) helper.getView(R.id.m_cb)).setChecked(false);
            }
    }
}
