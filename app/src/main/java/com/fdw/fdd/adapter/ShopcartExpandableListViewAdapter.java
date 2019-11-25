package com.fdw.fdd.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.CartBean;
import com.fdw.fdd.entity.bean.CartDoQueryData;
import com.fdw.fdd.entity.bean.GroupInfo;
import com.fdw.fdd.entity.bean.ProductInfo;
import com.fdw.fdd.entity.bean.ShopBean;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.view.SlideView;

import java.util.List;
import java.util.Map;


public class ShopcartExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<GroupInfo> groups;
    private Map<String, List<ProductInfo>> children;
    private Context context;
    //HashMap<Integer, View> groupMap = new HashMap<Integer, View>();
    //HashMap<Integer, View> childrenMap = new HashMap<Integer, View>();
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;

    private List<CartDoQueryData> mDatas;

    /**
     * 构造函数
     *
     * @param groups   组元素列表
     * @param children 子元素列表
     * @param context
     */
    public ShopcartExpandableListViewAdapter(List<GroupInfo> groups, Map<String, List<ProductInfo>> children, Context context) {
        super();
        this.groups = groups;
        this.children = children;
        this.context = context;
    }

    public ShopcartExpandableListViewAdapter(Context context, List<CartDoQueryData> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    @Override
    public int getGroupCount() {
//        return groups.size();
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        String groupId = groups.get(groupPosition).getId();
//        return children.get(groupId).size();
//        Log.e("子列表",groupPosition+"");
        return mDatas != null ? mDatas.get(groupPosition).getCart().size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
//        return groups.get(groupPosition);
        Log.e("子列表", groupPosition + "");
        return mDatas != null ? mDatas.get(groupPosition).getShop() : null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
//        List<ProductInfo> childs = children.get(groups.get(groupPosition).getId());

//        return childs.get(childPosition);
        return mDatas != null ? mDatas.get(groupPosition).getCart().get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder gholder;
        if (convertView == null) {
            gholder = new GroupHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopcart_group, null);
            gholder.cb_check = (CheckBox) convertView.findViewById(R.id.determine_chekbox);
            gholder.determine_rl = (RelativeLayout) convertView.findViewById(R.id.determine_rl);
            gholder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_source_name);
            convertView.setTag(gholder);
        } else {
            gholder = (GroupHolder) convertView.getTag();
        }

        CartDoQueryData cartDoQueryData = mDatas.get(groupPosition);

        if (cartDoQueryData != null && cartDoQueryData.getShop() != null) {
            ShopBean shopBean = cartDoQueryData.getShop();
            gholder.tv_group_name.setText(shopBean.getName());
            gholder.determine_rl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkInterface.checkGroup(groupPosition, shopBean.isChoosed() ? false : true);// 暴露组选接口
                }
            });

            //显示状态
            boolean choosed = shopBean.isChoosed();
            Log.e("是否被選中", choosed + "");
            gholder.cb_check.setChecked(choosed);

        }


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SlideView slideView = null;
        final ChildHolder cholder;
        Log.e("子列表", childPosition + "");
        if (convertView == null) {
            cholder = new ChildHolder();
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopcart_product, null);
            slideView = new SlideView(parent.getContext(), parent.getContext().getResources(), view);
            convertView = slideView;
            cholder.cb_check = (CheckBox) convertView.findViewById(R.id.check_box);

            cholder.iv_shopping = (ImageView) convertView.findViewById(R.id.m_iv_shopping);

            cholder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_name);
            cholder.tv_specification = (TextView) convertView.findViewById(R.id.m_tv_specification);
            cholder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            cholder.iv_increase = (TextView) convertView.findViewById(R.id.tv_add);
            cholder.iv_decrease = (TextView) convertView.findViewById(R.id.tv_reduce);
            cholder.tv_count = (TextView) convertView.findViewById(R.id.tv_num);

            cholder.tv_delete = (TextView) convertView.findViewById(R.id.back);
            convertView.setTag(cholder);
        } else {
            cholder = (ChildHolder) convertView.getTag();
        }
        CartBean cartBean = (CartBean) getChild(groupPosition, childPosition);
        if (cartBean != null) {
            GlideUtils.loadImage(context, cartBean.getCommoditySpecification().getSpecificationImage(), R.drawable.ic_all_background, cholder.iv_shopping);
            if (cartBean.getCommodity() != null && !cartBean.getCommodity().equals("")) {
                cholder.tv_product_name.setText(cartBean.getCommodity().getName());
            }
            cholder.tv_specification.setText(cartBean.getCommoditySpecification().getCommoditySpecification());
            cholder.tv_price.setText(DoubleUtil.double2Str(cartBean.getCommoditySpecification().getSalePrice()));
            cholder.tv_count.setText(cartBean.getCart().getNumber());


        }
        cholder.cb_check.setChecked(cartBean.isChoosed());
        cholder.cb_check.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cartBean.setChoosed(((CheckBox) v).isChecked());
                cholder.cb_check.setChecked(((CheckBox) v).isChecked());
                checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
            }
        });


        cholder.iv_increase.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(groupPosition, childPosition, cholder.tv_count, cholder.cb_check.isChecked());// 暴露增加接口
            }
        });
        cholder.iv_decrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease(groupPosition, childPosition, cholder.tv_count, cholder.cb_check.isChecked());// 暴露删减接口
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 组元素绑定器
     */
    private class GroupHolder {
        CheckBox cb_check;
        RelativeLayout determine_rl;
        TextView tv_group_name;
    }

    /**
     * 子元素绑定器
     */
    private class ChildHolder {
        CheckBox cb_check;


        ImageView iv_shopping;
        TextView tv_product_name;
        TextView tv_specification;
        TextView tv_price;
        TextView iv_increase;
        TextView tv_count;
        TextView iv_decrease;
        TextView tv_delete;
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        public void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        public void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);
    }

}
