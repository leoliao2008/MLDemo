package com.tgi.mldemo.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.tgi.mldemo.data.Static;
import com.tgi.mldemo.fragment.FoodImageFragment;

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
public class FoodImagesAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private ArrayList<String> mPaths=new ArrayList<>();
    private final AssetManager mAssetManager;
    private TreeMap<Integer,FoodImageFragment> mMap=new TreeMap<>();

    public FoodImagesAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext=context;
        mAssetManager = mContext.getResources().getAssets();
        try {
            String [] foods = mAssetManager.list("foods");
            for(String temp:foods){
                mPaths.add("foods/"+temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showLog(e.getMessage());

        }
    }


    @Override
    public Fragment getItem(int position) {
        if(mPaths!=null&&mPaths.size()>0){
            FoodImageFragment fragment;
            if(mMap.get(position)!=null){
                fragment=mMap.get(position);
            }else {
                fragment = new FoodImageFragment();
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
        Log.e("Log from FoodImageAdt",msg);
    }
}
