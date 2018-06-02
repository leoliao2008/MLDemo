package com.tgi.mldemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tgi.mldemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toOffLineMLDemo(View view) {
        OfflineMLDemoActivity.start(this);
    }

    public void toGoogleCloudVisionDemo(View view) {

    }
}
