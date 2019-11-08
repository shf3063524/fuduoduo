package com.hjkj.fuduoduo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.home_fragment.HomeSearchActivity;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;

import java.util.ArrayList;

public class PopularListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list = new ArrayList<String>();

    public PopularListAdapter(Context context, ArrayList<String> strs) {

        this.context =context;

        list= strs;
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

    public class ViewHolder{
        TextView tv;
    }


}
