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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.bean.DietLog;
import com.tgi.mldemo.module.NdbSqlModule;
import com.tgi.mldemo.utils.AlertDialogUtils;
import com.tgi.mldemo.utils.ToastUtil;

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
                            R.layout.item_diet_log,
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
                vh.consumption.setText(record.getWeight()+"g");
                Bitmap bitmap=null;
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
                TextView consumption;
                TextView date;
                ViewHolder(View convertView){
                   thumbNail=convertView.findViewById(R.id.item_diet_log_iv_thumb_nail);
                   name=convertView.findViewById(R.id.item_diet_log_tv_name);
                   date=convertView.findViewById(R.id.item_diet_log_tv_date);
                   consumption=convertView.findViewById(R.id.item_diet_log_tv_consumption);
                }
            }
        };
        listView.setDividerHeight(0);
        listView.setAdapter(mAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialogUtils.showYesOrNoDialogue(
                        DietRecordActivity.this,
                        "Info",
                        "Press Ok to delete the diet record, press no to cancel",
                        new Runnable() {
                            @Override
                            public void run() {
                                DietLog log = mDietLogs.get(position);
                                int i = mSqlModule.deleteDietLog(log.getName(), log.getDate());
                                if(i>0){
                                    mDietLogs.remove(position);
                                    mAdapter.notifyDataSetChanged();
                                }else {
                                    ToastUtil.showToast(DietRecordActivity.this,"Fail to delete record.");
                                }
                            }
                        },
                        null
                );
                return true;
            }
        });
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
