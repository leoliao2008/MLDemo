package com.tgi.mldemo.ndb_package.request;

import com.google.gson.Gson;
import com.tgi.mldemo.ndb_package.base.AbsRequest;
import com.tgi.mldemo.ndb_package.bean.FoodSearchResponses;
import com.tgi.mldemo.ndb_package.callback.FoodSearchRequestCallBack;

/**
 * <a href="https://ndb.nal.usda.gov/ndb/doc/apilist/API-SEARCH.md">Click Here for reference.</a>
 */
public class FoodSearchRequest extends AbsRequest<FoodSearchResponses> {

    public FoodSearchRequest(String strRq) {
        super(strRq);
    }

    @Override
    protected FoodSearchResponses genResponse(String json) {
        return new Gson().fromJson(json, FoodSearchResponses.class);
    }

    public void post(FoodSearchRequestCallBack callBack){
        postRequest(callBack);
    }



}
