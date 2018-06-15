package com.tgi.mldemo.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tgi.mldemo.callback.CameraViewCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CameraView extends SurfaceView {
    private SurfaceHolder mSurfaceHolder;
    private CameraViewCallBack mCallBack;
    private Camera mCamera;
    private int mViewWidth;
    private int mViewHeight;
    private AtomicBoolean mIsReadyToPic=new AtomicBoolean(false);

    public CameraView(Context context) {
        this(context,null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //check if the current device has a camera
        if(!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return;
        }
        setKeepScreenOn(true);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mCamera=Camera.open();
                }catch (Exception e){
                    handleException(e);
                    return;
                }
                try {
                    mCamera.setPreviewDisplay(mSurfaceHolder);
                    mCamera.setDisplayOrientation(90);
                } catch (IOException e) {
                    handleException(e);
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if(mCamera!=null){
                    mCamera.stopPreview();
                    mCamera.cancelAutoFocus();
                    Camera.Parameters parameters = mCamera.getParameters();
                    List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
                    Camera.Size preViewSize= getBestSize(previewSizes,mViewWidth,mViewHeight);
                    if(preViewSize!=null){
                        parameters.setPreviewSize(preViewSize.width,preViewSize.height);
                    }
                    List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
                    Camera.Size picSize = getBestSize(pictureSizes, mViewWidth, mViewHeight);
                    if(picSize!=null){
                        parameters.setPictureSize(picSize.width,picSize.height);
                    }
                    parameters.setJpegQuality(100);
                    parameters.setRotation(90);
                    parameters.setPictureFormat(ImageFormat.JPEG);
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                    mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {
                            mIsReadyToPic.set(success);
                        }
                    });
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                releaseCamera();
            }
        });
    }

    private Camera.Size getBestSize(List<Camera.Size> sizes, int viewWidth, int viewHeight ) {
        float ratio=viewWidth*1.f/viewHeight;
        Collections.sort(sizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size o1, Camera.Size o2) {
                return o2.width-o1.width;
            }
        });
        for(Camera.Size size:sizes){
            float supportRatio = size.width *1.f/ size.height;
            if(ratio==supportRatio){
                return size;
            }
        }
        for(Camera.Size size:sizes){
            float supportRatio = size.width *1.f/ size.height;
            if(ratio-supportRatio<=0.1){
                return size;
            }
        }
        return null;
    }

    public void takePicture(){
        if(mCamera!=null){
            if(!mIsReadyToPic.get()){
                mCallBack.onError(new Exception("Camera is not focus.Take pic only when the camera is focus."));
                return;
            }
            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if(mCallBack!=null){
                        mCallBack.onGetPic(bitmap);
                    }
                    //when a pic is taken the camera will stop previewing, so here we resume the preview
                    mCamera.cancelAutoFocus();
                    mCamera.startPreview();
                    mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {
                            mIsReadyToPic.set(success);
                        }
                    });
                }
            });

        }
    }



    public void setCameraViewCallBack(CameraViewCallBack cameraCallBack){
        mCallBack=cameraCallBack;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                focusCameraOn(convertDownXToCameraX(x),convertDownYToCameraY(y));
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
    }

    private void focusCameraOn(int x, int y) {
        if(mCamera!=null){
            showLog("Camera focus on: x="+x+" y="+y);
            mCamera.stopPreview();
            mCamera.cancelAutoFocus();
            Camera.Parameters param = mCamera.getParameters();
            if(param.getMaxNumFocusAreas()>0){
                List<Camera.Area> list=new ArrayList<>();
                int left=Math.max(-1000,x-50);
                int top=Math.max(-1000,y-50);
                int right=Math.min(x+50,1000);
                int bottom=Math.min(y+50,1000);
                list.add(new Camera.Area(new Rect(left,top,right,bottom),800));
                param.setMeteringAreas(list);
                mCamera.setParameters(param);
                mCamera.startPreview();
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        mIsReadyToPic.set(success);
                    }
                });
            }
        }

    }

    /**
     * refer to https://developer.android.com/guide/topics/media/camera#saving-media
     * @param y
     * @return
     */
    private int convertDownYToCameraY(float y) {
        return (int) (2000*y/mViewHeight-1000+0.5f);
    }

    private int convertDownXToCameraX(float x) {
        return (int) (2000 * x / mViewWidth - 1000 + 0.5f);
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }

    private void handleException(Exception e){
        if(mCallBack!=null){
            mCallBack.onError(e);
        }
    }

    private void releaseCamera(){
        if(mCamera!=null){
            try {
                mCamera.setPreviewDisplay(null);
            } catch (IOException e) {
                handleException(e);
            }
            mCamera.stopPreview();
            mCamera.release();
            mCamera=null;
        }
    }
}
