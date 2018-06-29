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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is used to execute the main functions of NDB Api while handling the local storage
 * of the search result.
 */
public class NdbManager {
    private NdbSqlModule mSqlModule;
    private NdbModuleCallBack mCallBack;
    private AtomicBoolean mIsSaveDietLog =new AtomicBoolean(false);
    private Context mContext;

    public void setIsSaveDietLog(boolean isToSave){
        mIsSaveDietLog.set(isToSave);
    }

    public NdbManager(Context context, NdbModuleCallBack callBack) {
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
                    searchFoodReportByNet(foodName,findTheBestMatchingResult(foodName,responses),bitmap);
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

    public void insertNewDietLog(String foodName, Bitmap bitmap) {
        DietLog dietLog=new DietLog();
        dietLog.setName(foodName);
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
        new FoodReportRequestBuilder().addSearchItem(ndbNo).build().post(new FoodReportRequestCallBack(){
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                mCallBack.onPreExecute("Try to net search by ndb number...");
            }

            @Override
            public void onPostExecute(FoodReportResponses responses) {
                super.onPostExecute(responses);
                try {
                    //in our case, there is always only 1 search result or null.
                    FoodReport report = responses.getSearchResults().get(0).getFoodReport();
                    if(mIsSaveDietLog.get()){
                        insertNewDietLog(searchItem,bitmap);
                    }
                    //store the data in local database
                    report.setDate(System.currentTimeMillis());
                    report.setSearchItem(searchItem);
                    String path = "";
                    try {
                        path = LocalStorageUtils.saveThumbNail(mContext, bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    report.setThumbNailPath(path);
                    mSqlModule.insertNewFoodReport(report);
                    mCallBack.onPostExecute(report);
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

    /**
     * This func is developed with reference to the actual test results on
     * <a href=https://ndb.nal.usda.gov/ndb/search/list?home=true>USDA Food Composition Databases</a>.
     * Its purpose is to try to extract the most possible result for our food search.
     * It sores each batch of returned results with certain logic and take the one with the highest score as the best.
     * If no best result is found, it takes the first returned result because food search api itself
     * orders the results by relevance.
     * @param keyWord the food name we want to search
     * @param responses the previous food search response. if the results are null, the return
     *                  nutrient database number is also null.
     * @return the nutrient database number for the keyword food.
     */
    private String findTheBestMatchingResult(String keyWord, FoodSearchResponses responses){
        String ndbNo=null;
        try {
            keyWord=keyWord.toLowerCase();
            //we trim this because we want to eliminate the dev factor caused by the plural form and singular form of the key word.
            //those contains key word in the front are more likely to be correct.
            if(keyWord.length()>2){
                keyWord=keyWord.substring(0,keyWord.length()-2);
            }
            List<Item> items = responses.getResponse().getItems();
            int [] scores=new int[items.size()];
            Arrays.fill(scores,0);
            for(int i=0;i<items.size();i++){
                Item item=items.get(i);
                String name = item.getName().toLowerCase();
                if(name.indexOf(keyWord)==0){
                    scores[i]+=10;
                }
                //those contains the word "raw" tends are more likely to be correct.
                if(name.contains("raw".toLowerCase())){
                    scores[i]+=10;
                }
                //those contains the word "without" tend to be not wanted.
                if(name.contains("without".toLowerCase())){
                    scores[i]-=10;
                }
            }
            //get the item with the highest score, if all the scores are the same, take the first item.
            ndbNo=items.get(findIndexOfMaxValue(scores)).getNdbNo();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return ndbNo;
    }

    private int findIndexOfMaxValue(int[] scores) {
        int index=0;
        int max=0;
        for(int i=0;i<scores.length;i++){
            if(max<scores[i]){
               max=scores[i];
               index=i;
            }
        }
        return index;
    }


}
