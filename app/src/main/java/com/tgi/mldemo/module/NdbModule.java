package com.tgi.mldemo.module;

import android.content.Context;
import android.graphics.Bitmap;

import com.tgi.mldemo.bean.DietLog;
import com.tgi.mldemo.callback.NdbModuleCallBack;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport;
import com.tgi.mldemo.ndb_package.bean.FoodSearchResponses;
import com.tgi.mldemo.ndb_package.bean.FoodSearchResponses.Response.Item;
import com.tgi.mldemo.ndb_package.builder.FoodReportRequestBuilder;
import com.tgi.mldemo.ndb_package.builder.FoodSearchRequestBuilder;
import com.tgi.mldemo.ndb_package.callback.FoodReportRequestCallBack;
import com.tgi.mldemo.ndb_package.callback.FoodSearchRequestCallBack;
import com.tgi.mldemo.utils.LocalStorageUtils;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A set of API provided by America National Agriculture Dept to acquire food nutrients.
 * <a href="https://ndb.nal.usda.gov/ndb/doc/index#">Here is the link.</a>
 * By U.S. Department of Agriculture, Agricultural Research Service. 2018. USDA National
 * Nutrient Database for Standard Reference, Release . Nutrient Data Laboratory Home Page,
 * http://www.ars.usda.gov/nutrientdata
 */
public class NdbModule {
    private NdbSqlModule mSqlModule;
    private NdbModuleCallBack mCallBack;
    private AtomicBoolean mIsSaveDietLog =new AtomicBoolean(false);
    private Context mContext;

    public void setIsSaveDietLog(boolean isToSave){
        mIsSaveDietLog.set(isToSave);
    }

    public NdbModule(Context context,NdbModuleCallBack callBack) {
        mSqlModule =new NdbSqlModule(context);
        mCallBack=callBack;
        mContext=context;
    }

    public void getFoodReport(String foodName,Bitmap bitmap){
        mCallBack.onPreExecute("Trying to search locally...");
        FoodReport report = mSqlModule.queryLocalFoodReport(foodName);
        if(report!=null){
            mCallBack.onPostExecute(report);
        }else {
            searchFoodByNet(foodName,bitmap);
        }
    }

    private void searchFoodByNet(String foodName, Bitmap bitmap) {
        new FoodSearchRequestBuilder().searchTerm(foodName).build().post(new FoodSearchRequestCallBack(){
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                mCallBack.onPreExecute("Turn to net search for "+foodName);
            }

            @Override
            public void onPostExecute(FoodSearchResponses responses) {
                super.onPostExecute(responses);
                try {
                    Item item = responses.getResponse().getItems().get(0);
                    searchFoodReportByNet(foodName,item.getNdbNo(),bitmap);
                }catch (NullPointerException e){
                    mCallBack.onError(new Exception("No result matches the food name."));
                }
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                mCallBack.onError(e);
            }
        });
    }

    public void updateDietLog(String searchItem, Bitmap bitmap) {
        DietLog dietLog=new DietLog();
        dietLog.setName(searchItem);
        dietLog.setDate(System.currentTimeMillis());
        String path="";
        try {
           path= LocalStorageUtils.saveThumbNail(mContext,bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dietLog.setThumbNail(path);
        mSqlModule.insertNewDietLog(dietLog);
    }

    private void searchFoodReportByNet(String searchItem, String ndbNo, Bitmap bitmap) {
        ArrayList<String> list=new ArrayList<>();
        list.add(ndbNo);
        new FoodReportRequestBuilder().addSearchItems(list).build().post(new FoodReportRequestCallBack(){
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                mCallBack.onPreExecute("Try to net search by ndb number...");
            }

            @Override
            public void onPostExecute(FoodReportResponses responses) {
                super.onPostExecute(responses);
                try {
                    FoodReport report = responses.getSearchResults().get(0).getFoodReport();
                    if(mIsSaveDietLog.get()){
                        updateDietLog(searchItem,bitmap);
                    }
                    mSqlModule.insertNewFoodReport(report,searchItem,bitmap);
                    mCallBack.onPostExecute(mSqlModule.queryLocalFoodReport(searchItem));
                }catch (NullPointerException e){
                    mCallBack.onError(new Exception("No report for "+searchItem+" is found."));
                }
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                mCallBack.onError(e);
            }
        });
    }




}
