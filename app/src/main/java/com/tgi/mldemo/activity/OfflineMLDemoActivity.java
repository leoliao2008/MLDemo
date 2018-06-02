package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.tgi.mldemo.R;
import com.tgi.mldemo.adapter.FoodImagesAdapter;
import com.tgi.mldemo.callback.ImageLabelerModuleCallBack;
import com.tgi.mldemo.fragment.FoodImageFragment;
import com.tgi.mldemo.module.ImageLabelerModule;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfflineMLDemoActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_identify)
    Button mBtnIdentify;
    private FoodImagesAdapter mAdapter;
    private ImageLabelerModule mImageLabelerModule;

    public static void start(Context context) {
        Intent starter = new Intent(context, OfflineMLDemoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_ml_demo);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initData() {
        mAdapter = new FoodImagesAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mImageLabelerModule=new ImageLabelerModule();

    }


    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBtnIdentify.setText("This is ......");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBtnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mViewPager.getCurrentItem();
                FoodImageFragment item = (FoodImageFragment) mAdapter.getItem(currentItem);
                Bitmap bitmap = item.getBitmap();
                if(bitmap==null){
                    showLog("bitmap == null!");
                    return;
                }
                mImageLabelerModule.identify(item.getBitmap(),new ImageLabelerModuleCallBack(){
                    @Override
                    public void onSuccess(List<FirebaseVisionLabel> firebaseVisionLabels) {
                        super.onSuccess(firebaseVisionLabels);
                        StringBuffer sb=new StringBuffer();
                        sb.append("It could be ");
                        int size = firebaseVisionLabels.size();
                        showLog("This object is identified as:");
                        for(int i = 0; i < size; i++){
                            FirebaseVisionLabel temp = firebaseVisionLabels.get(i);
                            showLog("Option "+(i+1)+":");
                            showLog("Label:"+temp.getLabel());
                            showLog("EntityId:"+temp.getEntityId());
                            showLog("Confidence:"+temp.getConfidence());
                            sb.append(temp.getLabel());
                            if(i!=size-1){
                                sb.append(" / ");
                            }else {
                                sb.append(".");
                            }
                        }
                        mBtnIdentify.setText(sb.toString());
                    }

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        super.onFailure(e);
                        mBtnIdentify.setText(e.getMessage());
                    }
                });
            }
        });

    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
