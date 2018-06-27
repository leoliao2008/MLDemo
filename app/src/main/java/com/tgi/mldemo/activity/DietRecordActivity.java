package com.tgi.mldemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.bean.DietLog;
import com.tgi.mldemo.module.NdbSqlModule;
import com.tgi.mldemo.utils.AlertDialogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DietRecordActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView listView;
//    private FatSecretSQLModule mFatSecretSQLModule;
    private ArrayList<DietLog> mDietLogs =new ArrayList<>();
    private SimpleDateFormat mSimpleDateFormat;
    private BaseAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private NdbSqlModule mSqlModule;

    public static void start(Context context) {
        Intent starter = new Intent(context, DietRecordActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_record);
        ButterKnife.bind(this);
        mSimpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        mSqlModule=new NdbSqlModule(this);
//        mFatSecretSQLModule =new FatSecretSQLModule(this);
        mAdapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return mDietLogs.size();
            }

            @Override
            public Object getItem(int position) {
                return mDietLogs.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder vh=null;
                if(convertView==null){
                    convertView= LayoutInflater.from(DietRecordActivity.this).inflate(
                            R.layout.item_food_data_base,
                            parent,
                            false
                    );
                    vh=new ViewHolder(convertView);
                    convertView.setTag(vh);
                }else {
                    vh= (ViewHolder) convertView.getTag();
                }
                DietLog record = mDietLogs.get(position);
                vh.name.setText(record.getName());
                vh.date.setText(mSimpleDateFormat.format(new Date(record.getDate())));
//                MyCompactFood compactFood = mFatSecretSQLModule.queryFood(record.getName());
                Bitmap bitmap=null;
                vh.desc.setText(record.getName());
                bitmap = BitmapFactory.decodeFile(record.getThumbNail());
                if(bitmap!=null){
                    vh.thumbNail.setImageBitmap(bitmap);
                }else {
                    vh.thumbNail.setImageResource(R.drawable.ic_error_outline_red_64dp);
                }
                return convertView;
            }

            class ViewHolder{
                ImageView thumbNail;
                TextView name;
                TextView desc;
                TextView date;
                ViewHolder(View convertView){
                    thumbNail=convertView.findViewById(R.id.item_food_data_base_iv_thumb_nail);
                    name=convertView.findViewById(R.id.item_food_data_base_tv_name);
                    desc=convertView.findViewById(R.id.item_food_data_base_tv_description);
                    date=convertView.findViewById(R.id.item_food_data_base_tv_date);
                }
            }
        };
        listView.setDividerHeight(0);
        listView.setAdapter(mAdapter);
        mProgressDialog = AlertDialogUtils.showProgressDialog(
                DietRecordActivity.this,
                "Info",
                "Loading diet record...",
                false
        );
        ArrayList<DietLog> records = mSqlModule.batchQueryDietLogs();
        mDietLogs.clear();
        mDietLogs.addAll(records);
        mAdapter.notifyDataSetChanged();
        mProgressDialog.dismiss();
    }


}
