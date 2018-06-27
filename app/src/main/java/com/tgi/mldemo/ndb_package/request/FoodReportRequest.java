package com.tgi.mldemo.ndb_package.request;

import com.google.gson.Gson;
import com.tgi.mldemo.ndb_package.base.AbsRequest;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses;
import com.tgi.mldemo.ndb_package.callback.FoodReportRequestCallBack;

/**
 * Details of this class can be found via:
 * https://ndb.nal.usda.gov/ndb/doc/apilist/API-FOOD-REPORTV2.md
 */
public class FoodReportRequest extends AbsRequest<FoodReportResponses> {
    public FoodReportRequest(String strRq) {
        super(strRq);
    }

    @Override
    protected FoodReportResponses genResponse(String json) {
        return new Gson().fromJson(json,FoodReportResponses.class);
    }

    public void post(FoodReportRequestCallBack callBack){
        postRequest(callBack);
    }

}
