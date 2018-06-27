package com.tgi.mldemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tgi.mldemo.R;

import java.util.ArrayList;

public class ThumbNailsAdapter extends RecyclerView.Adapter<ThumbNailsAdapter.ViewHolder> {

    private ArrayList<Bitmap> mBitmaps;
    private Context mContext;

    public ThumbNailsAdapter(ArrayList<Bitmap> bitmaps, Context context) {
        mBitmaps = bitmaps;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v=View.inflate(mContext,R.layout.item_thumb_nail,null);
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_thumb_nail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bitmap bitmap = mBitmaps.get(position);
        holder.mImageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mBitmaps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.item_pic_thumb_nail_iv_thumb_nail);
        }
    }
}
