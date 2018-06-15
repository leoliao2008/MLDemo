package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.adapter.ImagesFlipperAdapter;
import com.tgi.mldemo.bean.CloudVisionResponse;
import com.tgi.mldemo.callback.JavaHttpUrlRequestCallBack;
import com.tgi.mldemo.data.ImageCategory;
import com.tgi.mldemo.data.Static;
import com.tgi.mldemo.fragment.ImageViewFragment;
import com.tgi.mldemo.module.JavaHttpUrlRequestModule;
import com.tgi.mldemo.module.ResultHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultHelperDemo extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tv_result)
    TextView mTvResult;
    private ImagesFlipperAdapter mAdapter;
    private JavaHttpUrlRequestModule mModule;

    public static void start(Context context) {
        Intent starter = new Intent(context, ResultHelperDemo.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_helper_demo);
        ButterKnife.bind(this);
        initViewPager(ImageCategory.FOODS);
        mModule=new JavaHttpUrlRequestModule(
                this,
                new JavaHttpUrlRequestCallBack(){
                    @Override
                    public void onPreExecute() {
                        super.onPreExecute();
                        mTvResult.setText("Searching,please wait...");
                    }

                    @Override
                    public void onError(Exception e) {
                        super.onError(e);
                        mTvResult.setText(e.getMessage());
                    }

                    @Override
                    public void onPostExecute(CloudVisionResponse response) {
                        super.onPostExecute(response);
                        String result = ResultHelper.findBestResult(response);
                        if(!TextUtils.isEmpty(result)){
                            mTvResult.setText("This is a "+result);
                        }else {
                            mTvResult.setText("No proper result is found.");
                        }
                    }
                },
                Static.GOOGLE_CLOUD_API_KEY
        );

    }

    private void initViewPager(ImageCategory imageCategory) {
        mAdapter = new ImagesFlipperAdapter(
                this,
                getSupportFragmentManager(),
                imageCategory
        );
        mViewPager.setAdapter(mAdapter);
    }

    public void identify(View view) {
        ImageViewFragment fragment= (ImageViewFragment) mAdapter.getItem(mViewPager.getCurrentItem());
        mModule.identifyBitmap(fragment.getBitmap());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_final_cloud_vision_test,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_foods:
                initViewPager(ImageCategory.FOODS);
                break;
            case R.id.menu_cars:
                initViewPager(ImageCategory.CARS);
                break;
            case R.id.menu_sea_foods:
                initViewPager(ImageCategory.SEA_FOODS);
                break;
        }
        return true;
    }
}
