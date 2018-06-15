package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tgi.mldemo.R;
import com.tgi.mldemo.adapter.ThumbNailsAdapter;
import com.tgi.mldemo.callback.CameraViewCallBack;
import com.tgi.mldemo.utils.ToastUtil;
import com.tgi.mldemo.view.CameraView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraViewDemo extends AppCompatActivity {
    @BindView(R.id.camera_view)
    CameraView cameraView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private ThumbNailsAdapter mAdapter;
    private ArrayList<Bitmap> mPics;

    public static void start(Context context) {
        Intent starter = new Intent(context, CameraViewDemo.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view_demo);
        ButterKnife.bind(this);

        initRecyclerView();
        initCameraView();

    }

    private void initCameraView() {
        cameraView.setCameraViewCallBack(new CameraViewCallBack(){
            @Override
            public void onError(Exception e) {
                super.onError(e);
                ToastUtil.showToast(CameraViewDemo.this,e.getMessage());
            }

            @Override
            public void onGetPic(Bitmap pic) {
                super.onGetPic(pic);
                mPics.add(pic);
                mAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(Integer.MAX_VALUE);
            }
        });
    }

    private void initRecyclerView() {
        mPics = new ArrayList<>();
        mAdapter = new ThumbNailsAdapter(mPics,this);
        LinearLayoutManager manager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(manager);
    }

    public void TakePic(View view) {
        cameraView.takePicture();
    }
}
