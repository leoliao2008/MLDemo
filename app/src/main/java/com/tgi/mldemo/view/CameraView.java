package com.tgi.mldemo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
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
    private Camera.AutoFocusCallback mFocusCallback=new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            mIsReadyToPic.set(success);
        }
    };
    private AtomicBoolean mIsAnimFocusing=new AtomicBoolean(false);
    private AnimationValueCallBack mAnimationValueCallBack;


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
                    mCamera=null;
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
                    parameters.setJpegQuality(100);
                    parameters.setRotation(90);
                    parameters.setPictureFormat(ImageFormat.JPEG);
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                    mCamera.autoFocus(mFocusCallback);
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                releaseCamera();
            }
        });
    }

    private Camera.Size getBestSize(
            List<Camera.Size> supportPreSizes,
            List<Camera.Size> supportPicSizes,
            int outWidth,
            int outHeight) {
        ArrayList<Camera.Size> list=new ArrayList<>(supportPreSizes);
        for(Camera.Size size:supportPicSizes){
            if(list.contains(size)){
                continue;
            }
            list.add(size);
        }
        Collections.sort(list, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size size1, Camera.Size size2) {
                return size1.width-size2.width;
            }
        });
        for(Camera.Size size:list){
            if(size.width>=outWidth&&size.height>=outHeight){
                return size;
            }
        }
        return null;
    }

    public void takePicture(){
        try {
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
                        mCamera.autoFocus(mFocusCallback);
                    }
                });
            }
        }catch (Exception e){
            handleException(e);
        }
    }



    public void setCameraViewCallBack(CameraViewCallBack cameraCallBack){
        mCallBack=cameraCallBack;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mIsAnimFocusing.get()){
            return true;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                showFocusingAnimation(x,y);
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
            showLog("Camera focus onTask: x="+x+" y="+y);
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
            }
            mCamera.setParameters(param);
            mCamera.startPreview();
            mCamera.autoFocus(mFocusCallback);
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

    private void showFocusingAnimation(float downX, float downY){

        ValueAnimator animator=ValueAnimator.ofInt(70,0);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(mAnimationValueCallBack!=null){
                    mAnimationValueCallBack.onAnimationUpdate(downX,downY,(int) animation.getAnimatedValue());
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimFocusing.set(true);
                if(mAnimationValueCallBack!=null){
                    mAnimationValueCallBack.onAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimFocusing.set(false);
                if(mAnimationValueCallBack!=null){
                    mAnimationValueCallBack.onAnimationEnd();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mIsAnimFocusing.set(false);
                if(mAnimationValueCallBack!=null){
                    mAnimationValueCallBack.onAnimationCancel();
                }

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public interface AnimationValueCallBack{
        void onAnimationUpdate(float x, float y, int radius);

        void onAnimationStart();

        void onAnimationEnd();

        void onAnimationCancel();
    }

    public void setAnimationValueCallBack(AnimationValueCallBack animationValueCallBack) {
        mAnimationValueCallBack = animationValueCallBack;
    }
}
