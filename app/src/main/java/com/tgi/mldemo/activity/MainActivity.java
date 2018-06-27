package com.tgi.mldemo.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tgi.mldemo.R;
import com.tgi.mldemo.module.PermissionChecker;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CAMERA = 321;
    private PermissionChecker mPermissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPermissionChecker=new PermissionChecker();
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

    public void toDietRecordActivity(View view) {
        DietRecordActivity.start(this);
    }

    public void toCameraViewDemo(View view) {
        CameraViewDemo.start(this);
    }

    public void toFoodDataBase(View view) {
        FoodDataBaseActivity.start(this);
    }

    public void toNdbDemo(View view) {
        NdbDemo.start(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String[] permissions = mPermissionChecker.checkPermissions(
                this,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                });
        if(permissions.length>0){
            mPermissionChecker.requestPermissions(this,permissions,REQ_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionChecker.onRequestPermissionsResult(permissions, grantResults,
                null,
                new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                });
    }
}
