package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tgi.mldemo.R;

public class FoodLogsActivity extends AppCompatActivity {
    
    public static void start(Context context) {
        Intent starter = new Intent(context, FoodLogsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_logs);
    }
}
