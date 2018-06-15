package com.tgi.mldemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tgi.mldemo.R;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private Context mContext;
    private ArrayList<String> mList;
    private OnItemClickListener mListener;

    public SearchResultAdapter(ArrayList<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_search_result, null);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        holder.mTvResult.setText(mList.get(position));
        holder.mTvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvResult;
        SearchResultViewHolder(View itemView) {
            super(itemView);
            mTvResult = itemView.findViewById(R.id.item_search_result_tv_result);
        }
    }

    public interface OnItemClickListener{
        void onItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
