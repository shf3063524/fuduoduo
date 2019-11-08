package com.hjkj.fuduoduo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.entity.bean.MoneyEntity;

import java.util.List;

public class ExchangeGoodsAdapter extends RecyclerView.Adapter {
    private MoneyInputListener listener;
    private List<MoneyEntity> data;
    // private EditText editText;
    private int oldPostion = -1;
    private Context context;

    public ExchangeGoodsAdapter(List<MoneyEntity> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        // if (position == data.size()) {//最后一个
        //     return 2;
        // } else {
        return 1;
        // }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // if (viewType == 1) {
        return new MyTextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange_goods, null));
        // } else {
        //     return new MyEditViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit, null));
        // }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // if (position == data.size()) { //position 0-5,size=5
        // editText = ((MyEditViewHolder) holder).editText;
        // //当选择项目从TextView转移到EditText，editText重新获取焦点
        // editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        //     @Override
        //     public void onFocusChange(View v, boolean hasFocus) {
        //         if (hasFocus) {
        //             notifyItem(oldPostion);
        //             editText.setSelected(true);
        //             listener.onGetMoneyInput(getMoneyString(editText.getText().toString()));
        //         }
        //     }
        // });
        //
        // editText.addTextChangedListener(new TextWatcher() {
        //     @Override
        //     public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //     }
        //
        //     @Override
        //     public void onTextChanged(CharSequence s, int start, int before, int count) {
        //     }
        //
        //     public void afterTextChanged(Editable s) {
        //         listener.onGetMoneyInput(getMoneyString(s.toString()));
        //         String temp = s.toString();
        //         int d = temp.indexOf(".");
        //         if (d < 0) return;
        //         if (temp.length() - d - 1 > 2) {
        //             s.delete(d + 3, d + 4);
        //         } else if (d == 0) {
        //             s.delete(d, d + 1);
        //         }
        //
        //     }
        // });

        // } else {
        final TextView mTvYuan = ((MyTextViewHolder) holder).mTvYuan;
        final RelativeLayout mLayoutOne = ((MyTextViewHolder) holder).mLayoutOne;
        mTvYuan.setText(data.get(position).getMoney());

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
                for (MoneyEntity datum : data) {
                    datum.setSelected(false);
                }
                mLayoutOne.setSelected(true);
                MoneyEntity moneyEntity = data.get(position);
                data.set(position,new MoneyEntity(moneyEntity.getMoney(),moneyEntity.getIntegral(),true));

//                if (position != oldPostion) {
//                    notifyItem(position);
//                }
                listener.onGetMoneyInput(data.get(position).getMoney());
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
                }
                // editText.clearFocus();
                // editText.setSelected(false);
                notifyDataSetChanged();
            }
        });
        // }
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


    //定义一个处理字符串的方法

    /**
     * 如果最后一个字符为"."，则自动在后面加上"00"，如果"."在倒数第二个位置，则自动加上"0"
     *
     * @param money
     * @return
     */
    private String getMoneyString(String money) {
        String overMoney = "";//结果
        if ("".equals(money)) {
            return "0.00元";
        }
        if (money.contains(".")) {
            if (money.lastIndexOf(".") == money.length() - 1) {
                overMoney = money + "00元";
            } else if (money.lastIndexOf(".") == money.length() - 2) {
                overMoney = money + "0元";
            } else {
                overMoney = money;
            }
        } else {
            overMoney = money + ".00元";
        }
        return overMoney;
    }

    public interface MoneyInputListener {
        void onGetMoneyInput(String money);
    }
}
