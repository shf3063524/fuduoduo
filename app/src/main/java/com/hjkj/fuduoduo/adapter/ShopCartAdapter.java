package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.bean.DoQuerySpecificationsData;
import com.hjkj.fuduoduo.entity.bean.MoneyEntity;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ShopCartAdapter extends BaseQuickAdapter<DoQuerySpecificationsData, BaseViewHolder> {
    public ShopCartAdapter(int layoutResId, @Nullable List<DoQuerySpecificationsData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoQuerySpecificationsData item) {
        ArrayList<DoQuerySpecificationsData> mTypeData = new ArrayList<>();
        mTypeData.clear();
        mTypeData.add(item);
        initRecyclerView(helper, mTypeData);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView(BaseViewHolder helper, ArrayList<DoQuerySpecificationsData> dataList) {
//        //防止recycleview闪烁
//        ((SimpleItemAnimator) ((RecyclerView) helper.getView(R.id.m_recycler_view)).getItemAnimator()).setSupportsChangeAnimations(false);
//        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
//        List<MoneyEntity> data = new ArrayList<MoneyEntity>();
////        data.add(new MoneyEntity("N°17嫣粉色", "", true));
////        data.add(new MoneyEntity("N°17嫣粉色", "", false));
////        data.add(new MoneyEntity("N°19玫红色", "", false));
////        data.add(new MoneyEntity("N°7", "", false));
////        data.add(new MoneyEntity("N°52星星色", "", false));
////        data.add(new MoneyEntity("N°7", "", false));
////        data.add(new MoneyEntity("N°717嫣粉色", "", false));
//        for (DoQuerySpecificationsData doQuerySpecificationsData : dataList) {
//            String commoditySpecification = doQuerySpecificationsData.getCommoditySpecification();
//            if (doQuerySpecificationsData == dataList.get(0)) {
//                data.add(new MoneyEntity(commoditySpecification, "", true));
//            } else {
//                data.add(new MoneyEntity(commoditySpecification, "", false));
//            }
//        }
//
//        ((RecyclerView) helper.getView(R.id.m_recycler_view)).addItemDecoration(new SpaceItemDecoration(4, 10, true));
//        ShopCartListAdapter adapter = new ShopCartListAdapter(data, mContext);
//        adapter.setMoneyInputListener(new ShopCartListAdapter.MoneyInputListener() {
//            @Override
//            public void onGetMoneyInput(String money) {
////                mTvMoney.setText("¥" + money + "立即充值");
//            }
//        });
//        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setLayoutManager(manager);
//        ((RecyclerView) helper.getView(R.id.m_recycler_view)).setAdapter(adapter);
    }
}
