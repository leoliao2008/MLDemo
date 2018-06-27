package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.tgi.mldemo.R;
import com.tgi.mldemo.adapter.ImagesFlipperAdapter;
import com.tgi.mldemo.bean.CloudVisionResponse;
import com.tgi.mldemo.callback.JavaHttpUrlRequestCallBack;
import com.tgi.mldemo.callback.MLImageLabelModuleCallBack;
import com.tgi.mldemo.callback.VisionRequestModuleCallBack;
import com.tgi.mldemo.data.ImageCategory;
import com.tgi.mldemo.data.Static;
import com.tgi.mldemo.fragment.ImageViewFragment;
import com.tgi.mldemo.module.CloudAPITutorialModule;
import com.tgi.mldemo.module.CloudVisionModule;
import com.tgi.mldemo.module.MLImageLabelModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageCognitiveDemo extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_identify)
    Button mBtnIdentify;
    @BindView(R.id.rdbtn_1)
    RadioButton mRdbtnMLDevice;
    @BindView(R.id.rdbtn_2)
    RadioButton mRdbtnMLOnline;
    @BindView(R.id.rdbtn_3)
    RadioButton mRdbtnCloud;
    @BindView(R.id.group_approaches)
    RadioGroup mGroupApproaches;
    @BindView(R.id.tv_result)
    TextView mTvResult;
    private ImagesFlipperAdapter mAdapter;
    private MLImageLabelModule mMLImageLabelModule;
    private ImageCategory mCategory= ImageCategory.FOODS;
    private CloudAPITutorialModule mCloudAPITutorialModule;
    private CloudVisionModule mCloudVisionModule;


    public static void start(Context context) {
        Intent starter = new Intent(context, ImageCognitiveDemo.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cognitive_demo);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initData() {
        mAdapter = new ImagesFlipperAdapter(this, getSupportFragmentManager(), mCategory);
        mViewPager.setAdapter(mAdapter);
        mMLImageLabelModule = new MLImageLabelModule();
        mCloudAPITutorialModule =new CloudAPITutorialModule(this,new VisionRequestModuleCallBack(){
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                mTvResult.setText("Searching...Please hold onTask.");
            }

            @Override
            public void onGetResults(ArrayList<String> results) {
                super.onGetResults(results);
                int size = results.size();
                StringBuffer sb=new StringBuffer();
                sb.append("It could be ");
                for(int i=0;i<size;i++){
                    sb.append(results.get(i));
                    if(i!=size-1){
                        sb.append(" / ");
                    }else {
                        sb.append(".");
                    }
                    mTvResult.setText(sb.toString());
                }
            }

            @Override
            public void onNoResult() {
                super.onNoResult();
                mTvResult.setText("No result to be found.");
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                mTvResult.setText(e.getMessage());
            }
        });

        mCloudVisionModule =new CloudVisionModule(this,new JavaHttpUrlRequestCallBack(){
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                mTvResult.setText("Searching...Please Hold onTask...");
            }

            @Override
            public void onPostExecute(CloudVisionResponse response) {
                super.onPostExecute(response);
                StringBuffer sb=new StringBuffer();
                sb.append("It could be ");
                List<CloudVisionResponse.Response.WebDetection.WebEntities> entities = response.getResponses().get(0).getWebDetection().getWebEntities();
                for(CloudVisionResponse.Response.WebDetection.WebEntities temp:entities){
                    sb.append(temp.getDescription()).append("/");
                }
                mTvResult.setText(sb.toString());

            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                mTvResult.setText(e.getMessage());
            }
        }, Static.GOOGLE_CLOUD_API_KEY);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvResult.setText("This is ......");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBtnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mViewPager.getCurrentItem();
                ImageViewFragment item = (ImageViewFragment) mAdapter.getItem(currentItem);
                Bitmap bitmap = item.getBitmap();
                if (bitmap == null) {
                    showLog("bitmap == null!");
                    return;
                }
                if(mRdbtnMLDevice.isChecked()){
                    identifyUsingMKOffline(bitmap);
                }else if(mRdbtnMLOnline.isChecked()){
                    identifyUsingMKOnline(bitmap);
                }else if(mRdbtnCloud.isChecked()){
                    identifyUsingCloud(bitmap);
                }
            }
        });

    }

    private void identifyUsingCloud(Bitmap bitmap) {
//        mCloudAPITutorialModule.identifyBitmap(bitmap);
        mCloudVisionModule.identifyBitmap(bitmap);
    }

    private void identifyUsingMKOnline(Bitmap bitmap) {
        mMLImageLabelModule.identifyUsingMLOnline(bitmap,new MLImageLabelModuleCallBack(){
            @Override
            public void onGetOnlineResult(List<FirebaseVisionCloudLabel> results) {
                super.onGetOnlineResult(results);
                StringBuffer sb = new StringBuffer();
                sb.append("It could be ");
                int size = results.size();
                showLog("This object is identified as:");
                for (int i = 0; i < size; i++) {
                    FirebaseVisionCloudLabel temp = results.get(i);
                    showLog("Option " + (i + 1) + ":");
                    showLog("Label:" + temp.getLabel());
                    showLog("EntityId:" + temp.getEntityId());
                    showLog("Confidence:" + temp.getConfidence());
                    sb.append(temp.getLabel());
                    if (i != size - 1) {
                        sb.append(" / ");
                    } else {
                        sb.append(".");
                    }
                }
                mTvResult.setText(sb.toString());
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                super.onFailure(e);
            }
        });

    }

    private void identifyUsingMKOffline(Bitmap bitmap) {

        mMLImageLabelModule.identifyUsingMLOffline(bitmap, new MLImageLabelModuleCallBack() {
            @Override
            public void onGetOfflineResult(List<FirebaseVisionLabel> firebaseVisionLabels) {
                super.onGetOfflineResult(firebaseVisionLabels);
                StringBuffer sb = new StringBuffer();
                sb.append("It could be ");
                int size = firebaseVisionLabels.size();
                showLog("This object is identified as:");
                for (int i = 0; i < size; i++) {
                    FirebaseVisionLabel temp = firebaseVisionLabels.get(i);
                    showLog("Option " + (i + 1) + ":");
                    showLog("Label:" + temp.getLabel());
                    showLog("EntityId:" + temp.getEntityId());
                    showLog("Confidence:" + temp.getConfidence());
                    sb.append(temp.getLabel());
                    if (i != size - 1) {
                        sb.append(" / ");
                    } else {
                        sb.append(".");
                    }
                }
                mTvResult.setText(sb.toString());
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                super.onFailure(e);
                mTvResult.setText(e.getMessage());
            }
        });

    }

    private void showLog(String msg) {
        Log.e(getClass().getSimpleName(), msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_recognitive_demo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category_foods:
                if(mCategory!=ImageCategory.FOODS){
                    mCategory=ImageCategory.FOODS;
                    toggleCategory(mCategory);
                }
                break;
            case R.id.category_cars:
                if(mCategory!=ImageCategory.CARS){
                    mCategory=ImageCategory.CARS;
                    toggleCategory(mCategory);
                }
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    private void toggleCategory(ImageCategory category) {
        mAdapter=new ImagesFlipperAdapter(this,getSupportFragmentManager(),category);
        mViewPager.setAdapter(mAdapter);
        mTvResult.setText("This is ...");
    }
}
