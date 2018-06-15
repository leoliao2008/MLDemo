package com.tgi.mldemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fatsecret.platform.model.CompactFood;
import com.tgi.mldemo.R;

import java.util.ArrayList;

public class BasicFoodInfoAdapter extends BaseAdapter {
    private ArrayList<CompactFood> mList;
    private Context mContext;

    public BasicFoodInfoAdapter(ArrayList<CompactFood> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            convertView=View.inflate(mContext,R.layout.item_basic_food_info,null);
            vh=new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        CompactFood temp = mList.get(position);
        StringBuilder sb=new StringBuilder();
        sb.append("Name:").append(temp.getName()).append("\n");
        sb.append("ID:").append(temp.getId()).append("\n");
        sb.append("Type:").append(temp.getType()).append("\n");
        sb.append("Description:").append(temp.getDescription()).append('\n');
        sb.append("BrandName:").append(temp.getBrandName());
        vh.getTv_info().setText(sb.toString());
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_info;

        public ViewHolder(View rootView) {
            tv_info=rootView.findViewById(R.id.item_basic_food_info_tv_food_info);
        }

        public TextView getTv_info() {
            return tv_info;
        }

    }
}
