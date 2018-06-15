package com.tgi.mldemo.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.tgi.mldemo.data.ImageCategory;
import com.tgi.mldemo.data.Static;
import com.tgi.mldemo.fragment.ImageViewFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 创建者     $Author$
 * 创建时间   2018/6/2 16:32
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class ImagesFlipperAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private ArrayList<String> mPaths=new ArrayList<>();
    private final AssetManager mAssetManager;
    private TreeMap<Integer,ImageViewFragment> mMap=new TreeMap<>();
    

    public ImagesFlipperAdapter(Context context,FragmentManager fm, ImageCategory category) {
        super(fm);
        mContext=context;
        mAssetManager = mContext.getResources().getAssets();
        showImagesResources(category);
    }

    private void showImagesResources(ImageCategory category) {
        StringBuffer sb=new StringBuffer();
        sb.append("image_resource/");
        if(category == ImageCategory.FOODS){
            sb.append("foods");
        }else if(category==ImageCategory.CARS){
            sb.append("cars");
        }else if(category==ImageCategory.SEA_FOODS){
            sb.append("seafoods");
        }
        try {
            String path = sb.toString();
            String[] list = mAssetManager.list(path);
            mPaths.clear();
            for(String temp:list){
                mPaths.add(path+"/"+temp);
            }
        } catch (IOException e) {
            showLog(e.getMessage());
        }
    }


    @Override
    public Fragment getItem(int position) {
        if(mPaths!=null&&mPaths.size()>0){
            ImageViewFragment fragment;
            if(mMap.get(position)!=null){
                fragment=mMap.get(position);
            }else {
                fragment = new ImageViewFragment();
                Bundle bundle=new Bundle();
                bundle.putString(Static.KEY_ASSET_PATH,mPaths.get(position));
                fragment.setArguments(bundle);
                mMap.put(position,fragment);
            }
            return fragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        if(mPaths!=null&&mPaths.size()>0){
            return mPaths.size();
        }
        return 0;
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
    
}
