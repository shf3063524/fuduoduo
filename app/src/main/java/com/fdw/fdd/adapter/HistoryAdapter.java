package com.fdw.fdd.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;

import java.util.ArrayList;

/**
 * Created by liuyunming on 2016/7/3.
 */
public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list = new ArrayList<String>();

    public HistoryAdapter(Context context, ArrayList<String> strs) {

        this.context = context;

        list = strs;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.search_olddata_item, null);

            holder.tv = (TextView) view.findViewById(R.id.text);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.tv.setText(list.get(i));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetailsActivity.openActivity(context);
            }
        });

        return view;
    }

    public class ViewHolder {
        TextView tv;
    }


}
