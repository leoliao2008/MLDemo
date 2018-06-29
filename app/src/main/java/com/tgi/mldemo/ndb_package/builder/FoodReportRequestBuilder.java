package com.tgi.mldemo.ndb_package.builder;

import android.text.TextUtils;
import android.util.Log;

import com.tgi.mldemo.ndb_package.Static;
import com.tgi.mldemo.ndb_package.base.AbsRequestBuilder;
import com.tgi.mldemo.ndb_package.request.FoodReportRequest;

import java.util.ArrayList;
import java.util.List;

public class FoodReportRequestBuilder extends AbsRequestBuilder<FoodReportRequest> {
    private ArrayList<String> mSearchItems=new ArrayList<>();
    @Override
    protected void initRequestParams() {
//        Parameter	Required	Default	    Description
//        api_key	y	          n/a	    Must be a data.gov registered API key
//        ndbno	    y	          n/a	    A list of up to 25 NDB numbers
//        type	    n	          b (basic)	Report type: [b]asic or [f]ull or [s]tats
//        format1	n	          JSON	    Report format: xml or json
        mParams=new String[]{"b","JSON"};
    }


    public FoodReportRequestBuilder addSearchItem(String ndbNo){
        checkAndAddSearchItem(ndbNo);
        return this;
    }

    private void checkAndAddSearchItem(String item){
        if(!TextUtils.isEmpty(item)){
            item=item.toLowerCase();
            if(!mSearchItems.contains(item)){
                mSearchItems.add(item);
            }
        }
    }


    public FoodReportRequestBuilder addSearchItems(List<String> list){
        for(String temp:list){
            checkAndAddSearchItem(temp);
        }
        return this;
    }

    @Override
    public FoodReportRequest build() {
        int size = mSearchItems.size();
        if(size >0){
            StringBuilder sb=new StringBuilder();
            sb.append(Static.END_POINT).append("/V2/reports?");
            for(int i = 0; i< size; i++){
                sb.append("ndbno=").append(mSearchItems.get(i)).append('&');
            }
            sb.append("type=").append(mParams[0]).append("&format=").append(mParams[1]).append('&');
            sb.append("api_key=").append(Static.API_KEY);
            return new FoodReportRequest(sb.toString());
        }else {
            Log.e(getClass().getSimpleName(),"Search Items Must Not Be Empty.");
        }
        return null;
    }
}
