package com.tgi.mldemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tgi.mldemo.callback.CameraViewCallBack;

import java.util.concurrent.atomic.AtomicBoolean;

public class CameraViewBundles extends FrameLayout {

    private CameraView mCameraView;
    private Paint mPaint;
    private float mX;
    private float mY;
    private int mRadius;
    private AtomicBoolean mIsAnimating=new AtomicBoolean(false);
    private View mView;

    public CameraViewBundles(@NonNull Context context) {
        this(context,null);
    }

    public CameraViewBundles(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraViewBundles(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);

        mCameraView = new CameraView(context);
        FrameLayout.LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mCameraView,params);
        mCameraView.setAnimationValueCallBack(new CameraView.AnimationValueCallBack() {
            @Override
            public void onAnimationUpdate(float x, float y, int radius) {
                mX=x;
                mY=y;
                mRadius =radius;
                mView.postInvalidate();
            }

            @Override
            public void onAnimationStart() {
                mIsAnimating.set(true);

            }

            @Override
            public void onAnimationEnd() {
                mIsAnimating.set(false);

            }

            @Override
            public void onAnimationCancel() {
                mIsAnimating.set(false);
            }
        });
        mView = new View(context){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if(mIsAnimating.get()){
                    canvas.drawCircle(mX,mY, mRadius,mPaint);
                }
            }
        };
        addView(mView,params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setCameraViewCallBack(CameraViewCallBack cameraViewCallBack) {
        mCameraView.setCameraViewCallBack(cameraViewCallBack);
    }

    public void takePicture() {
        mCameraView.takePicture();
    }


}
