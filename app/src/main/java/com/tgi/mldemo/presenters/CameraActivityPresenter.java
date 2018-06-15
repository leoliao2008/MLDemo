package com.tgi.mldemo.presenters;

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.View;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.ResponseListener;
import com.tgi.mldemo.activity.CameraActivity;
import com.tgi.mldemo.adapter.SearchResultAdapter;
import com.tgi.mldemo.bean.CloudVisionResponse;
import com.tgi.mldemo.callback.CameraModuleCallback;
import com.tgi.mldemo.callback.JavaHttpUrlRequestCallBack;
import com.tgi.mldemo.data.Static;
import com.tgi.mldemo.module.CameraModule;
import com.tgi.mldemo.module.FatSecretApiModule;
import com.tgi.mldemo.module.JavaHttpUrlRequestModule;
import com.tgi.mldemo.module.ResultHelper;
import com.tgi.mldemo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CameraActivityPresenter {
    private CameraActivity mActivity;
    private CameraModule mCameraModule;
    private JavaHttpUrlRequestModule mJavaHttpUrlRequestModule;
    private FatSecretApiModule mFatSecretApiModule;
    private ArrayList<String> mImageAnnotations;
    private SearchResultAdapter mSearchResultAdapter;
    private Bitmap mSnapShot;


    //public:
    public CameraActivityPresenter(CameraActivity activity) {
        mActivity = activity;
    }

    public void init(){
        initRecyclerView();
        initCloudVisionModule();
        initFatSecretModule();
        initListeners();
    }



    public void takePicture(){
        try {
            mCameraModule.takePic();
        } catch (Exception e) {
            handleException(e);
        }
    }

    public void onStart() {
//        if(!mCameraModule.hasCameraFeature()){
//            showToast("No camera on this device.");
//            mActivity.onBackPressed();
//        }
        initCameraModule();

    }

    public void onResume(){

    }


    public void onPause(){
        try {
            mCameraModule.closeCamera();
        } catch (Exception e) {
            handleException(e);
        }
    }

    public void onStop() {

    }

    //private:
    private void initListeners() {
        mActivity.getIv_check().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJavaHttpUrlRequestModule.identifyBitmap(mSnapShot);
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
            mActivity.getIv_check().setVisibility(View.GONE);
            mActivity.getIv_clear().setVisibility(View.GONE);
            mActivity.getIv_snapShot().setVisibility(View.GONE);
        }
    }

    private void initFatSecretModule() {
        mFatSecretApiModule=new FatSecretApiModule(mActivity, new ResponseListener() {
            @Override
            public void onFoodListRespone(Response<CompactFood> response) {
                List<CompactFood> results = response.getResults();
                if(results==null||results.size()<1){
                    mActivity.getTv_basicNutrients().setText("No matching result.");
                    return;
                }
                String description = results.get(0).getDescription();
                if(TextUtils.isEmpty(description)){
                    description="No data";
                }
                mActivity.getTv_basicNutrients().setText(description);

            }
        });
    }

    private void initCloudVisionModule() {
        mJavaHttpUrlRequestModule=new JavaHttpUrlRequestModule(mActivity,new JavaHttpUrlRequestCallBack(){
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                showToast("Searching food by image...");
            }

            @Override
            public void onPostExecute(CloudVisionResponse response) {
                super.onPostExecute(response);
                if(response!=null&&response.getResponses()!=null){
                    String result = ResultHelper.findBestResult(response);
                    if(!TextUtils.isEmpty(result)){
                        mActivity.getTv_bestResult().setText(result);
                        mFatSecretApiModule.getBasicFoodInfo(result);
                    }else {
                        mActivity.getTv_bestResult().setText("No Result");
                    }
                    ArrayList<String> list = ResultHelper.pickPossibleResults(response);
                    updateRecyclerView(list);
                }else {
                    showToast("No response from google server/No result is found.");
                }

            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                handleException(e);
            }
        }, Static.GOOGLE_CLOUD_API_KEY);

    }

    private void initCameraModule() {
        mActivity.getSfv_camera().getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mCameraModule=new CameraModule(mActivity,new CameraModuleCallback(){
                    @Override
                    public void onGetBitmap(Bitmap bitmap) {
                        super.onGetBitmap(bitmap);
                        toggleTopLayers(true);
                        mSnapShot=bitmap;
                        mActivity.getIv_snapShot().setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Exception e) {
                        handleException(e);
                    }
                });
                if(!mCameraModule.hasCameraFeature()){
                    showToast("No camera on this device.");
                    mActivity.onBackPressed();
                    return;
                }
                try {
                    mCameraModule.initCamera(mActivity.getSfv_camera().getHolder());
                } catch (Exception e) {
                    handleException(e);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }

    private void handleException(Exception e) {
        showToast(e.getMessage());
    }

    private void showToast(String msg){
        ToastUtil.showToast(mActivity,msg);
    }

    private void initRecyclerView() {
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
        updateBasicNutrientsWindow(bestResult);
    }

    private void updateBasicNutrientsWindow(String bestResult) {
        mFatSecretApiModule.getBasicFoodInfo(bestResult);
    }

    private void updateRecyclerView(ArrayList<String> newResults){
        mImageAnnotations.clear();
        if(newResults!=null){
            mImageAnnotations.addAll(newResults);
        }
        mSearchResultAdapter.notifyDataSetChanged();
    }


}
