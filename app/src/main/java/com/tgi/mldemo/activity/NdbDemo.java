package com.tgi.mldemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport;
import com.tgi.mldemo.ndb_package.bean.FoodSearchResponses;
import com.tgi.mldemo.ndb_package.builder.FoodReportRequestBuilder;
import com.tgi.mldemo.ndb_package.builder.FoodSearchRequestBuilder;
import com.tgi.mldemo.ndb_package.callback.FoodReportRequestCallBack;
import com.tgi.mldemo.ndb_package.callback.FoodSearchRequestCallBack;
import com.tgi.mldemo.utils.AlertDialogUtils;
import com.tgi.mldemo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NdbDemo extends AppCompatActivity {
    @BindView(R.id.edt_search_item)
    EditText edt_searchItem;
    @BindView(R.id.list_view)
    ListView listView;
    private ArrayList<FoodSearchResponses.Response.Item> mList=new ArrayList<>();
    private ArrayAdapter<FoodSearchResponses.Response.Item> mAdapter;
    private ProgressDialog mProgressDialog;

    public static void start(Context context) {
        Intent starter = new Intent(context, NdbDemo.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndb_demo);
        ButterKnife.bind(this);
        mAdapter = new ArrayAdapter<FoodSearchResponses.Response.Item>(
                this,
                android.R.layout.simple_list_item_1,
                mList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                FoodSearchResponses.Response.Item item = mList.get(position);
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setText(item.toString());
                return tv;
            }
        };
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodSearchResponses.Response.Item item = mList.get(position);
                String foodId = item.getNdbNo();
                ArrayList<String> list=new ArrayList<>();
                list.add(foodId);
                new FoodReportRequestBuilder().addSearchItems(list).build().post(
                        new FoodReportRequestCallBack(){
                            @Override
                            public void onPreExecute() {
                                super.onPreExecute();
                                mProgressDialog=AlertDialogUtils.showProgressDialog(
                                        NdbDemo.this,
                                        "Info",
                                        "Loading...,Please Wait...",
                                        false
                                );
                            }

                            @Override
                            public void onPostExecute(FoodReportResponses responses) {
                                super.onPostExecute(responses);
                                if(mProgressDialog!=null){
                                    mProgressDialog.dismiss();
                                }
                                FoodReport report = responses.getSearchResults().get(0).getFoodReport();
                                FoodReportActivity.start(
                                        NdbDemo.this,
                                        report
                                );
                            }

                            @Override
                            public void onError(Exception e) {
                                super.onError(e);
                                if(mProgressDialog!=null){
                                    mProgressDialog.dismiss();
                                }
                                ToastUtil.showToast(getApplicationContext(),e.getMessage());
                                showLog(e.getMessage());
                            }
                        }
                );
            }
        });
    }

    public void SearchFood(View view) {
        String searchItem = edt_searchItem.getText().toString();
        if (TextUtils.isEmpty(searchItem)) {
            return;
        }
        new FoodSearchRequestBuilder().searchTerm(searchItem).build().post(new FoodSearchRequestCallBack(){
            public void onPreExecute() {
                mList.clear();
                mProgressDialog = AlertDialogUtils.showProgressDialog(
                        NdbDemo.this,
                        "Info",
                        "Loading....Please Wait...",
                        false
                );
            }

            public void onPostExecute(FoodSearchResponses responses) {
                if(mProgressDialog!=null){
                    mProgressDialog.dismiss();
                }
                FoodSearchResponses.Response response = responses.getResponse();
                if(response !=null){
                    List<FoodSearchResponses.Response.Item> items = response.getItems();
                    if(items!=null&&items.size()>0){
                        mList.addAll(items);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            public void onError(Exception e) {
                if(mProgressDialog!=null){
                    mProgressDialog.dismiss();
                }
                ToastUtil.showToast(NdbDemo.this,e.getMessage());

            }
        });


    }

    private void showLog(String msg) {
        Log.e(getClass().getSimpleName(), msg);
    }


}
