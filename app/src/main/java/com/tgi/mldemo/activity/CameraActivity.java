package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.presenters.CameraActivityPresenter;
import com.tgi.mldemo.view.CameraViewBundles;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity {
    @BindView(R.id.camera_view)
    CameraViewBundles sfv_camera;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.tv_best_result)
    TextView tv_bestResult;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_check)
    ImageView iv_check;
    @BindView(R.id.iv_snap_shot)
    ImageView iv_snapShot;
    @BindView(R.id.widget_basic_nutrient_board_tv_cal)
    TextView tvCal;
    @BindView(R.id.widget_basic_nutrient_board_tv_protein)
    TextView tvProtein;
    @BindView(R.id.widget_basic_nutrient_board_tv_fat)
    TextView tvFat;
    @BindView(R.id.widget_basic_nutrient_board_tv_carb)
    TextView tvCarb;
    @BindView(R.id.widget_basic_nutrient_board_tv_cover)
    TextView tvCover;
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

    public CameraViewBundles getCameraView() {
        return sfv_camera;
    }

    public ImageView getIv_clear() {
        return iv_clear;
    }

    public TextView getTv_bestResult() {
        return tv_bestResult;
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

    public TextView getTvCal() {
        return tvCal;
    }

    public TextView getTvProtein() {
        return tvProtein;
    }

    public TextView getTvFat() {
        return tvFat;
    }

    public TextView getTvCarb() {
        return tvCarb;
    }

    public TextView getTvCover() {
        return tvCover;
    }

    public void saveToDietLog(View view) {
        mPresenter.updateDietLog();
    }

}
