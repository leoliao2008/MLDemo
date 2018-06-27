package com.tgi.mldemo.module;

import android.content.Context;
import android.os.AsyncTask;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.services.FatsecretService;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.ResponseListener;

import java.util.Iterator;
import java.util.List;

public class FatSecretApiModule {
    private static final String CONSUMER_KEY="e8a809c1bffc4e2baee1cb25fe722286";
    private static final String API_SHARE_SECRET="f2953be8e70b40309c4ed2d82cd57035";
    private Context mContext;
    private ResponseListener mListener;
    private final FatsecretService mService;
    //public:


    public FatSecretApiModule(Context context, ResponseListener listener) {
        mContext = context;
        mListener = listener;
        mService = new FatsecretService(CONSUMER_KEY,API_SHARE_SECRET);
    }

    public void getBasicFoodInfo(String keyWord){
        new GetBasicFoodsInfoTask(keyWord).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    public void getDetailFoodInfo(long id){
        new GetDetailFoodInfoTask(id).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    //private:


    private class GetBasicFoodsInfoTask extends AsyncTask<Void, Void, Response<CompactFood>> {
        private String mKeyWord;

        public GetBasicFoodsInfoTask(String keyWord) {
            mKeyWord = keyWord;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            return mService.searchFoods(mKeyWord);
        }

        @Override
        protected void onPostExecute(Response<CompactFood> response) {
            super.onPostExecute(response);
            List<CompactFood> results = response.getResults();
            if(results!=null){
                Iterator<CompactFood> iterator = results.iterator();
                while (iterator.hasNext()){
                    CompactFood food = iterator.next();
                    //we don't want anything not generic
                    if(!food.getType().equalsIgnoreCase("Generic")){
                        iterator.remove();
                    }
                }
            }
            mListener.onFoodListRespone(response);
        }
    }

    private class GetDetailFoodInfoTask extends AsyncTask<Void,Void,Food>{
        private long mId;

        public GetDetailFoodInfoTask(long id) {
            mId = id;
        }

        @Override
        protected Food doInBackground(Void... voids) {
            return mService.getFood(mId);
        }

        @Override
        protected void onPostExecute(Food food) {
            super.onPostExecute(food);
            mListener.onFoodResponse(food);
        }
    }

    public CompactFood getBestResult(List<CompactFood> results,String foodName){
        for(CompactFood temp:results){
//            //we only select generic food vs brand food
//            if(!temp.getType().equalsIgnoreCase("Generic")){
//                continue;
//            }
            //we need to find the name matching the given food name
            if(temp.getName().equalsIgnoreCase(foodName)){
                return temp;
            }
        }
        return null;

    }

}
