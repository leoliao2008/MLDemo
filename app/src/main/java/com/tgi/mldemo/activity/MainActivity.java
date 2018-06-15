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
        ImageCognitiveDemo.start(this);
    }

    public void toCVTest(View view) {
        CloudVisionApiTestActivity.start(this);
    }

    public void compareModules(View view) {
        ModulesComparisonActivity.start(this);

    }

    public void toResultHelperTest(View view) {
        ResultHelperDemo.start(this);
    }

    public void toNutritionAPITest(View view) {
        FoodSearchActivity.start(this);

    }

    public void toFinalTest(View view) {
        FinalTestActivity.start(this);
    }

    public void toCameraMLActivity(View view){
        CameraActivity.start(this);
    }

    public void toFoodLogsActivity(View view) {
        FoodLogsActivity.start(this);
    }

    public void toCameraViewDemo(View view) {
        CameraViewDemo.start(this);
    }
}
