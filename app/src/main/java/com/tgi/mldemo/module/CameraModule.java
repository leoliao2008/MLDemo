package com.tgi.mldemo.module;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.tgi.mldemo.callback.CameraModuleCallback;

public class CameraModule {
    private Context mContext;
    private Camera mCamera;
//    private AtomicBoolean mIsFocused=new AtomicBoolean(false);
    private CameraModuleCallback mCallback;
    private String mTag;
    private final int mScreenWidth;
    private final int mScreenHeight;

    public CameraModule(Context context, CameraModuleCallback callback) {
        mContext = context;
        mCallback = callback;
        mTag=getClass().getSimpleName();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        mScreenWidth = display.getWidth();
        mScreenHeight = display.getHeight();
    }

    /**
     * Check if any camera are available in this device
     * @return
     */
    public boolean hasCameraFeature(){
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * open the back camera of the device
     * @throws Exception since we use the func of Camera.open(), we should be always to prepare to catch exceptions in case it crash
     */
    public void initCamera(SurfaceHolder holder)throws Exception{
        closeCamera();
        mCamera=Camera.open();
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewDisplay(holder);
        mCamera.startPreview();
        Camera.Parameters parameters=mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setJpegQuality(100);
        parameters.setPictureSize(mScreenWidth,mScreenHeight);
        parameters.setPreviewSize(mScreenWidth,mScreenHeight);
        mCamera.setParameters(parameters);
//        mCamera.autoFocus(new Camera.AutoFocusCallback() {
//            @Override
//            public void onAutoFocus(boolean success, Camera camera) {
//                mIsFocused.set(success);
//            }
//        });
    }



    /**
     * take pic using the open camera
     */
    public void takePic() throws Exception{
//        if(!mIsFocused.get()){
//            mCallback.onError(new Exception("Camera is not ready to take pic yet."));
//            return;
//        }
        mCamera.takePicture(
                new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {

                    }
                },
                new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {

                    }
                },
                new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        if(bitmap!=null){
                            mCallback.onGetBitmap(bitmap);
                        }else {
                            mCallback.onError(new Exception("Bitmap taken from camera is null!"));
                        }
                        mCamera.startPreview();
                    }
                }
        );
    }


    /**
     * close the camera, release relevant resources.
     * @throws Exception
     */
    public void closeCamera()throws Exception{
        if(mCamera!=null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera=null;
        }
    }

    private void showLog(String message) {
        Log.e(mTag,message);
    }


    public Camera.Parameters getParameters() {
        if(mCamera!=null){
           return mCamera.getParameters();
        }
        return null;

    }

    public void resetParams(Camera.Parameters param) {
        if(mCamera!=null){
            mCamera.setParameters(param);
        }
    }
}
