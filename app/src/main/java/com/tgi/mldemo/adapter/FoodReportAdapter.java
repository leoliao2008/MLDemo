package com.tgi.mldemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport.Nutrient;

import java.util.List;

public class FoodReportAdapter extends BaseAdapter {
    private Context mContext;
    private final List<Nutrient> mNutrients;


    public FoodReportAdapter(List<Nutrient> list, Context context) {
        mNutrients = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mNutrients.size();
    }

    @Override
    public Object getItem(int position) {
        return mNutrients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Nutrient nutrient = mNutrients.get(position);
        ViewHolder vh;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_food_report,parent,false);
            vh=new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        vh.tv_name.setText("Name:"+nutrient.getNutrientName());
        vh.tv_value.setText("Value:"+nutrient.getValue());
        vh.tv_group.setText("Group:"+nutrient.getGroup());
        vh.tv_id.setText("Nutrient ID:"+nutrient.getNutrientId());
        vh.tv_drv.setText("Derivation:"+nutrient.getDerivationInfo());
        vh.tv_unit.setText("Unit:"+nutrient.getUnit());
        vh.tv_order.setText(String.format("%02d",position+1));
        return convertView;
    }

    private class ViewHolder{
        TextView tv_name;
        TextView tv_drv;
        TextView tv_id;
        TextView tv_unit;
        TextView tv_group;
        TextView tv_value;
        TextView tv_order;

        public ViewHolder(View convertView) {
            tv_name=convertView.findViewById(R.id.item_food_report_tv_name);
            tv_drv =convertView.findViewById(R.id.item_food_report_tv_derivation);
            tv_id=convertView.findViewById(R.id.item_food_report_tv_id);
            tv_unit=convertView.findViewById(R.id.item_food_report_tv_unit);
            tv_group=convertView.findViewById(R.id.item_food_report_tv_group);
            tv_value=convertView.findViewById(R.id.item_food_report_tv_value);
            tv_order=convertView.findViewById(R.id.item_food_report_tv_order);
        }
    }
}
