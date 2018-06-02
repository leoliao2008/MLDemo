package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tgi.mldemo.R;

public class GoogleCloudVisionDemoActivity extends AppCompatActivity {


    public static void start(Context context) {
        Intent starter = new Intent(context, GoogleCloudVisionDemoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_cloud_vision_demo);
    }
}
