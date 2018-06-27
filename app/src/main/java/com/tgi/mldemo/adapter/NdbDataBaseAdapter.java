package com.tgi.mldemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NdbDataBaseAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FoodReport> mList;
    private final SimpleDateFormat mDateFormat;

    public NdbDataBaseAdapter(Context context, ArrayList<FoodReport> list) {
        mContext = context;
        mList = list;
        mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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
        ViewHolder vh=null;
        if(convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_food_data_base,parent,false);
            vh=new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        FoodReport report = mList.get(position);
        vh.tv_name.setText(report.getSearchItem());
        vh.tv_desc.setText(report.getDesc().getFoodName());
        String path = report.getThumbNailPath();
        Bitmap bitmap=null;
        if(!TextUtils.isEmpty(path)){
            bitmap = BitmapFactory.decodeFile(path);
            if(bitmap!=null){
                vh.iv_thumbNail.setImageBitmap(bitmap);
            }
        }
        if(bitmap==null){
            vh.iv_thumbNail.setImageResource(R.drawable.ic_error_outline_red_64dp);
        }
        String date = mDateFormat.format(new Date(report.getDate()));
        vh.tv_date.setText(date);
        return convertView;
    }

    private class ViewHolder {
        TextView tv_name;
        TextView tv_desc;
        ImageView iv_thumbNail;
        TextView tv_date;

        public ViewHolder(View convertView) {
            tv_name=convertView.findViewById(R.id.item_food_data_base_tv_name);
            tv_desc=convertView.findViewById(R.id.item_food_data_base_tv_description);
            iv_thumbNail=convertView.findViewById(R.id.item_food_data_base_iv_thumb_nail);
            tv_date=convertView.findViewById(R.id.item_food_data_base_tv_date);
        }
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
