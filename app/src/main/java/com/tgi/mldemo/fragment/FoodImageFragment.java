package com.tgi.mldemo.fragment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.data.Static;

import java.io.IOException;
import java.io.InputStream;

/**
 * 创建者     $Author$
 * 创建时间   2018/6/2 16:39
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class FoodImageFragment extends Fragment {

    private Bitmap mBitmap;
    private AssetManager mAssetManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        String path = bundle.getString(Static.KEY_ASSET_PATH);
        mAssetManager=getContext().getAssets();
        if(!TextUtils.isEmpty(path)){
            try {
                InputStream inputStream = mAssetManager.open(path);
                mBitmap= BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                showLog(e.getMessage());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView view = (ImageView) inflater.inflate(R.layout.fragment_food_image_content_view, null);
        view.setImageBitmap(mBitmap);
        return view;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    private void showLog(String msg){
        Log.e("Log from FoodFrag:",msg);
    }




}
