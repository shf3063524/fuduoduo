package com.fdw.fdd.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.entity.bean.DoQuerySpecificationsData;

import java.util.List;

public class ShopCartListAdapter extends RecyclerView.Adapter {
    private MoneyInputListener listener;
    private List<DoQuerySpecificationsData> data;
    // private EditText editText;
    private int oldPostion = -1;
    private Context context;

    public ShopCartListAdapter(List<DoQuerySpecificationsData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyTextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange_goods, null));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final TextView mTvYuan = ((MyTextViewHolder) holder).mTvYuan;
        final RelativeLayout mLayoutOne = ((MyTextViewHolder) holder).mLayoutOne;
        mTvYuan.setText(data.get(position).getCommoditySpecification());

        if (data.get(position).isSelected()) {
            mLayoutOne.setSelected(true);
            mTvYuan.setTextColor(ContextCompat.getColor(mTvYuan.getContext(), R.color.cl_e51C23));
        } else {
            mLayoutOne.setSelected(false);
            mTvYuan.setTextColor(ContextCompat.getColor(mTvYuan.getContext(), R.color.cl_333));
        }
        mLayoutOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (DoQuerySpecificationsData datum : data) {
                    datum.setSelected(false);
                }
                mLayoutOne.setSelected(true);
                DoQuerySpecificationsData specificationsData = data.get(position);
                data.set(position, new DoQuerySpecificationsData(specificationsData.getId(), specificationsData.getCommoditySpecification(), specificationsData.getSalePrice(), specificationsData.getNumber(), specificationsData.getSpecificationImage(), true));

                if (listener != null) {
                    listener.onGetMoneyInput(data.get(position).getId(),data.get(position).getCommoditySpecification(), data.get(position).getSalePrice(), data.get(position).getNumber(), data.get(position).getSpecificationImage());
                }
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow((scanForActivity(context)).getWindow().getDecorView().getWindowToken(), 0);
                }
                notifyDataSetChanged();
            }
        });
    }

    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    private void notifyItem(int posiont) {
        if (oldPostion >= 0) {
            data.get(oldPostion).setSelected(false);
            notifyItemChanged(oldPostion);
        }
        oldPostion = posiont;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyTextViewHolder extends RecyclerView.ViewHolder {
        TextView mTvYuan;
        RelativeLayout mLayoutOne;

        public MyTextViewHolder(View itemView) {
            super(itemView);
            mTvYuan = (TextView) itemView.findViewById(R.id.m_tv_yuan);
            mLayoutOne = (RelativeLayout) itemView.findViewById(R.id.m_layout_one);
        }
    }

    // class MyEditViewHolder extends RecyclerView.ViewHolder {
    //     EditText editText;
    //
    //     public MyEditViewHolder(View itemView) {
    //         super(itemView);
    //         editText = (EditText) itemView.findViewById(et);
    //     }
    // }


    public void setMoneyInputListener(MoneyInputListener listener) {
        this.listener = listener;
    }

    public interface MoneyInputListener {
        void onGetMoneyInput(String commoditySpecificationId,String commoditySpecification, String salePrice, String number, String specificationImage);
    }
}
