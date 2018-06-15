package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.presenters.CameraActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity {
    @BindView(R.id.sfv_camera)
    SurfaceView sfv_camera;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.tv_best_result)
    TextView tv_bestResult;
    @BindView(R.id.tv_basic_nutrients)
    TextView tv_basicNutrients;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_check)
    ImageView iv_check;
    @BindView(R.id.iv_snap_shot)
    ImageView iv_snapShot;
    private CameraActivityPresenter mPresenter;


    public static void start(Context context) {
        Intent starter = new Intent(context, CameraActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        mPresenter = new CameraActivityPresenter(this);
        mPresenter.init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    public void takePic(View view) {
        mPresenter.takePicture();
    }

    public SurfaceView getSfv_camera() {
        return sfv_camera;
    }

    public ImageView getIv_clear() {
        return iv_clear;
    }

    public TextView getTv_bestResult() {
        return tv_bestResult;
    }

    public TextView getTv_basicNutrients() {
        return tv_basicNutrients;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public ImageView getIv_check() {
        return iv_check;
    }

    public ImageView getIv_snapShot() {
        return iv_snapShot;
    }


}
