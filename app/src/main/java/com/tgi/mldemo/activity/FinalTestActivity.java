package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tgi.mldemo.R;
import com.tgi.mldemo.presenters.FinalTestPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinalTestActivity extends AppCompatActivity {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private FinalTestPresenter mPresenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, FinalTestActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_test);
        ButterKnife.bind(this);
        mPresenter=new FinalTestPresenter(this);
        mPresenter.init();
    }

    public void identify(View view) {
        mPresenter.identify();
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isFinishing()){
            mPresenter.onDestroy();
        }
    }
}
