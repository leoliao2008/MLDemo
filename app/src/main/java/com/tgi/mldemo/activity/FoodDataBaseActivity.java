package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.adapter.NdbDataBaseAdapter;
import com.tgi.mldemo.module.NdbSqlModule;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport;
import com.tgi.mldemo.utils.AlertDialogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDataBaseActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView listView;
//    private FatSecretSQLModule mFatSecretSQLModule;
    private ArrayList<FoodReport> mList;
    private NdbDataBaseAdapter mAdapter;
    private NdbSqlModule mSqlModule;

    public static void start(Context context) {
        Intent starter = new Intent(context, FoodDataBaseActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_data_base);
        ButterKnife.bind(this);
        mSqlModule=new NdbSqlModule(this);
        mList=mSqlModule.batchQueryFoodReports();
//        mFatSecretSQLModule =new FatSecretSQLModule(this);
//        mList = mFatSecretSQLModule.batchQueryFood();
        mAdapter = new NdbDataBaseAdapter(this, mList);
        listView.setDividerHeight(0);
        listView.setAdapter(mAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialogUtils.showYesOrNoDialogue(
                        FoodDataBaseActivity.this,
                        "Warning",
                        "Press Yes to delete this data, press No to cancel.",
                         new Runnable(){
                            @Override
                            public void run() {
                                String ndbNo = mList.get(position).getDesc().getNdbNo();
                                mSqlModule.deleteFoodReport(ndbNo);
                                mList.remove(position);
                                mAdapter.notifyDataSetChanged();
                            }
                         },null
                );
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodReport report = mList.get(position);
                FoodReportActivity.start(FoodDataBaseActivity.this,report);
            }
        });
    }
}
