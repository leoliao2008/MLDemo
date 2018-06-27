package com.tgi.mldemo.presenters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.ResponseListener;
import com.tgi.mldemo.activity.FinalTestActivity;
import com.tgi.mldemo.activity.FoodDetailActivity;
import com.tgi.mldemo.adapter.ImagesFlipperAdapter;
import com.tgi.mldemo.adapter.SearchResultAdapter;
import com.tgi.mldemo.bean.CloudVisionResponse;
import com.tgi.mldemo.callback.JavaHttpUrlRequestCallBack;
import com.tgi.mldemo.data.ImageCategory;
import com.tgi.mldemo.data.Static;
import com.tgi.mldemo.fragment.ImageViewFragment;
import com.tgi.mldemo.module.FatSecretApiModule;
import com.tgi.mldemo.module.CloudVisionModule;
import com.tgi.mldemo.module.ResultHelper;
import com.tgi.mldemo.utils.AlertDialogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FinalTestPresenter {
    private FinalTestActivity mActivity;
    private RecyclerView mRecyclerView;
    private FrameLayout.LayoutParams mParams;
    private ViewPager mViewPager;
    private ImagesFlipperAdapter mImagesFlipperAdapter;
    private CloudVisionModule mCloudVisionModule;
    private AtomicBoolean mIsRecyclerViewShowing=new AtomicBoolean(false);
    private ProgressDialog mProgressDialog;
    private ArrayList<String> mPossibleResultList = new ArrayList<>();
    private SearchResultAdapter mSearchResultAdapter;
    private FatSecretApiModule mFatSecretApiModule;

    //public:
    public FinalTestPresenter(FinalTestActivity activity) {
        mActivity = activity;
    }

    public void init(){
        initViewPager();
        initRecyclerView();
        initCloudVisionModule();
        initFatSecretApiModule();
    }

    public void identify(){
        ImageViewFragment item = (ImageViewFragment) mImagesFlipperAdapter.getItem(mViewPager.getCurrentItem());
        Bitmap bitmap = item.getBitmap();
        mCloudVisionModule.identifyBitmap(bitmap);
    }


    public void onDestroy() {

    }



    //private:
    private void initFatSecretApiModule() {
        mFatSecretApiModule=new FatSecretApiModule(
                mActivity,
                new ResponseListener() {
                    @Override
                    public void onFoodListRespone(Response<CompactFood> response) {
                        mProgressDialog.dismiss();
                        if(response==null){
                            Toast.makeText(mActivity,"Fail to get ingredient data...",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<CompactFood> results = response.getResults();
                        if(results!=null&&results.size()>0){
                            CompactFood compactFood = results.get(0);
                            FoodDetailActivity.start(mActivity,compactFood.getId());
                        }
                    }

                }
        );
    }
    private void initCloudVisionModule() {

        mCloudVisionModule =new CloudVisionModule(
                mActivity,
                new JavaHttpUrlRequestCallBack(){
                    @Override
                    public void onPreExecute() {
                        super.onPreExecute();
                        if(mIsRecyclerViewShowing.get()){
                            hideRecyclerView();
                        }
                        mProgressDialog = AlertDialogUtils.showProgressDialog(
                                mActivity,
                                "Searching...",
                                "Identifying Ingredient......Please Wait...",
                                false
                        );
                    }

                    @Override
                    public void onPostExecute(CloudVisionResponse response) {
                        super.onPostExecute(response);
                        mProgressDialog.dismiss();
                        String bestResult = ResultHelper.findBestResult(response);
                        AlertDialogUtils.confirmFoodAnnotation(
                                mActivity,
                                "Identify Result",
                                "Is this a "+bestResult+"? Press Confirm to proceed or press Disagree to choose from the list:",
                                new Runnable(){

                                    @Override
                                    public void run() {
                                        searchFoodNutrientValue(bestResult);
                                    }
                                },
                                new Runnable(){
                                    @Override
                                    public void run() {
                                        updateRecyclerViewContent(response);
                                        if(!mIsRecyclerViewShowing.get()){
                                            showRecyclerView();
                                        }
                                    }
                                }
                        );

                    }

                    @Override
                    public void onError(Exception e) {
                        super.onError(e);
                        mProgressDialog.dismiss();
                        handleException(e);
                    }
                },
                Static.GOOGLE_CLOUD_API_KEY
        );

    }

    private void searchFoodNutrientValue(String keyWord) {
        mProgressDialog=AlertDialogUtils.showProgressDialog(
                mActivity,
                "Searching...",
                "Searching for food nutrient values, please wait...",
                false
        );
        mFatSecretApiModule.getBasicFoodInfo(keyWord);

    }

    private void updateRecyclerViewContent(CloudVisionResponse response) {
        ArrayList<String> results=ResultHelper.pickPossibleResults(response);
        mPossibleResultList.clear();
        mPossibleResultList.addAll(results);
        mSearchResultAdapter.notifyDataSetChanged();
    }


    private void initRecyclerView() {
        mRecyclerView = mActivity.getRecyclerView();
        mParams = (FrameLayout.LayoutParams) mRecyclerView.getLayoutParams();
        mSearchResultAdapter = new SearchResultAdapter(mPossibleResultList,mActivity);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mSearchResultAdapter);
        mSearchResultAdapter.setOnItemClickListener(new SearchResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                searchFoodNutrientValue(mPossibleResultList.get(position));
            }
        });
    }

    private void initViewPager() {
        mImagesFlipperAdapter = new ImagesFlipperAdapter(
                mActivity,
                mActivity.getSupportFragmentManager(),
                ImageCategory.FOODS
        );
        mViewPager = mActivity.getViewPager();
        mViewPager.setAdapter(mImagesFlipperAdapter);
    }

    private void hideRecyclerView(){
        if(mIsRecyclerViewShowing.get()){
            toggleRecyclerView(false);
        }

    }

    private void showRecyclerView(){
        if(!mIsRecyclerViewShowing.get()){
            toggleRecyclerView(true);
        }
    }

    private void toggleRecyclerView(final boolean isToShow){
        int start=isToShow?1500:0;
        int end=1500-start;
        ValueAnimator animator = ValueAnimator
                .ofInt(start, end)
                .setDuration(1000);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (int) animation.getAnimatedValue();
                mParams.leftMargin=value;
                mRecyclerView.setLayoutParams(mParams);
                mRecyclerView.requestLayout();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mIsRecyclerViewShowing.set(isToShow);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animator.start();
    }

    private void handleException(Exception e){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
