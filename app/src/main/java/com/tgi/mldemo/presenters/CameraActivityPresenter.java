package com.tgi.mldemo.presenters;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.tgi.mldemo.activity.CameraActivity;
import com.tgi.mldemo.adapter.SearchResultAdapter;
import com.tgi.mldemo.bean.CloudVisionResponse;
import com.tgi.mldemo.callback.CameraViewCallBack;
import com.tgi.mldemo.callback.JavaHttpUrlRequestCallBack;
import com.tgi.mldemo.callback.NdbModuleCallBack;
import com.tgi.mldemo.data.Static;
import com.tgi.mldemo.module.CloudVisionModule;
import com.tgi.mldemo.module.NdbModule;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport;
import com.tgi.mldemo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CameraActivityPresenter {
    private CameraActivity mActivity;
    private CloudVisionModule mCloudVisionModule;
    private ArrayList<String> mImageAnnotations;
    private SearchResultAdapter mSearchResultAdapter;
    private Bitmap mSnapShot;
    private LinearLayout.LayoutParams mLayoutParams;
    private AtomicBoolean mIsRecyclerShown=new AtomicBoolean(false);
    private NdbModule mNdbModule;
    private FoodReport mFoodReport;


    //public:
    public CameraActivityPresenter(CameraActivity activity) {
        mActivity = activity;
    }

    public void init(){
        initRecyclerView();
        initCloudVisionModule();
        initCameraView();
        initOnClickListeners();
        initNdbModule();
    }



    public void takePicture(){
        if(mActivity.getIv_snapShot().getVisibility()==View.GONE){
            mActivity.getCameraView().takePicture();
        }else {
            showToast("Please resume to camera first.");
        }
    }

    public void updateDietLog() {
        if(mFoodReport!=null){
            mNdbModule.updateDietLog(mFoodReport.getSearchItem(),mSnapShot);
            showToast("Saved to diet log successfully.");
        }else {
            showToast("No food report is available.");
        }

    }

    public void onStart() {
    }


    public void onResume(){

    }


    public void onPause(){

    }

    public void onStop() {

    }


    //private:
    private void initNdbModule() {
        mNdbModule=new NdbModule(
                mActivity,
                new NdbModuleCallBack(){
                    @Override
                    public void onPreExecute(String msg) {
                        super.onPreExecute(msg);
                        mFoodReport=null;
                        showIntervalMsg(msg);
                    }

                    @Override
                    public void onPostExecute(FoodReport report) {
                        super.onPostExecute(report);
                        mFoodReport=report;
                        showBasicNutrientInfo(report);
                    }

                    @Override
                    public void onError(Exception e) {
                        super.onError(e);
                        mFoodReport=null;
                        showIntervalMsg(e.getMessage());
                    }
                }
        );
    }

    private void initCameraView() {
        mActivity.getCameraView().setCameraViewCallBack(new CameraViewCallBack(){
            @Override
            public void onGetPic(Bitmap pic) {
                super.onGetPic(pic);
                mSnapShot=pic;
                mActivity.getIv_snapShot().setImageBitmap(mSnapShot);
                toggleTopLayers(true);
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                handleException(e);
            }
        });
    }


    private void initOnClickListeners() {
        mActivity.getIv_check().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCloudVisionModule.identifyBitmap(mSnapShot);
            }
        });

        mActivity.getIv_clear().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToCamera();
            }
        });

    }

    private void backToCamera() {
        toggleTopLayers(false);
    }

    private void toggleTopLayers(boolean isToShow) {
        if(isToShow){
            mActivity.getIv_check().setVisibility(View.VISIBLE);
            mActivity.getIv_clear().setVisibility(View.VISIBLE);
            mActivity.getIv_snapShot().setVisibility(View.VISIBLE);
        }else {
            mFoodReport=null;
            mActivity.getIv_check().setVisibility(View.GONE);
            mActivity.getIv_clear().setVisibility(View.GONE);
            mActivity.getIv_snapShot().setVisibility(View.GONE);
        }
    }


    private void initCloudVisionModule() {
        mCloudVisionModule =new CloudVisionModule(mActivity,new JavaHttpUrlRequestCallBack(){
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                toggleRecyclerView(false);
                mActivity.getTv_bestResult().setText("Identifying image...");
                mImageAnnotations.clear();
                mSearchResultAdapter.notifyDataSetChanged();
                showIntervalMsg("Waiting for food annotation result....");
            }

            @Override
            public void onPostExecute(CloudVisionResponse response) {
                super.onPostExecute(response);
                if(response!=null&&response.getResponses()!=null){
                    String result = mCloudVisionModule.findBestResult(response);
                    if(!TextUtils.isEmpty(result)){
                        mActivity.getTv_bestResult().setText(result);
                        searchFoodNutrient(result);
                    }else {
                        mActivity.getTv_bestResult().setText("No Result");
                    }
                    ArrayList<String> list = mCloudVisionModule.pickPossibleResults(response);
                    updateRecyclerView(list);
                }else {
                    showToast("No response from google server/No result is found.");
                }
                toggleRecyclerView(true);

            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                handleException(e);
                toggleRecyclerView(false);
            }
        }, Static.GOOGLE_CLOUD_API_KEY);

    }

    private void handleException(Exception e) {
        showToast(e.getMessage());
    }

    private void showToast(String msg){
        ToastUtil.showToast(mActivity,msg);
    }

    private void initRecyclerView() {
        mLayoutParams = (LinearLayout.LayoutParams) mActivity.getRecyclerView().getLayoutParams();
        RecyclerView recyclerView = mActivity.getRecyclerView();
        mImageAnnotations = new ArrayList<>();
        LinearLayoutManager layoutManager= new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false);
        mSearchResultAdapter = new SearchResultAdapter(mImageAnnotations,mActivity);
        recyclerView.setAdapter(mSearchResultAdapter);
        recyclerView.setLayoutManager(layoutManager);
        mSearchResultAdapter.setOnItemClickListener(new SearchResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                updateBestResult(mImageAnnotations.get(position));
            }
        });
    }

    private void updateBestResult(String bestResult) {
        mActivity.getTv_bestResult().setText(bestResult);
        searchFoodNutrient(bestResult);
    }

    private void searchFoodNutrient(String bestResult) {
        mNdbModule.getFoodReport(bestResult,mSnapShot);
    }

    private void updateRecyclerView(ArrayList<String> newResults){
        mImageAnnotations.clear();
        if(newResults!=null){
            mImageAnnotations.addAll(newResults);
        }
        mSearchResultAdapter.notifyDataSetChanged();
        mActivity.getRecyclerView().smoothScrollToPosition(0);
    }


    private void showIntervalMsg(String msg){
        mActivity.getTvCover().setVisibility(View.VISIBLE);
        mActivity.getTvCover().setText(msg);
    }

    private void showBasicNutrientInfo(FoodReport food){
        mActivity.getTvCover().setVisibility(View.GONE);
        mActivity.getTvProtein().setText(food.getProtein());
        mActivity.getTvCal().setText(food.getCalories());
        mActivity.getTvCarb().setText(food.getCarbohydrate());
        mActivity.getTvFat().setText(food.getFat());
    }

    private void toggleRecyclerView(boolean isToShow){
        if(mIsRecyclerShown.get()==isToShow){
            return;
        }
        int start=isToShow?0:100;
        int stop=100-start;
        ValueAnimator animator=ValueAnimator.ofInt(start,stop);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (int) animation.getAnimatedValue();
                mLayoutParams.height=value;
                mActivity.getRecyclerView().setLayoutParams(mLayoutParams);
                mActivity.getRecyclerView().requestLayout();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsRecyclerShown.set(isToShow);
                mSearchResultAdapter.notifyDataSetChanged();

            }

            @Override
            public void onAnimationCancel(Animator animation) {


            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }



}
