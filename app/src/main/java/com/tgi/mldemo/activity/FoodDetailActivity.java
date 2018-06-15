package com.tgi.mldemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Serving;
import com.fatsecret.platform.services.android.ResponseListener;
import com.tgi.mldemo.R;
import com.tgi.mldemo.data.Static;
import com.tgi.mldemo.fragment.NutrientTableFragment;
import com.tgi.mldemo.module.FatSecretApiModule;
import com.tgi.mldemo.utils.AlertDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_food_description)
    TextView tvFoodDescription;
    @BindView(R.id.fragment)
    FrameLayout mFrameLayout;
    @BindView(R.id.spinner)
    AppCompatSpinner mSpinner;
    private FatSecretApiModule mModule;
    private ProgressDialog mProgressDialog;
    private NutrientTableFragment mFragment;

    public static void start(Context context, long foodId) {
        Intent starter = new Intent(context, FoodDetailActivity.class);
        starter.putExtra(Static.KEY_FOOD_ID, foodId);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        ButterKnife.bind(this);

        mModule = new FatSecretApiModule(this, new ResponseListener() {
            @Override
            public void onFoodResponse(Food food) {
                mProgressDialog.dismiss();
                mFragment = new NutrientTableFragment();
                if(food==null){
                    return;
                }
                getSupportFragmentManager().beginTransaction().add(R.id.fragment, mFragment).commit();
                tvFoodDescription.setText(food.getName());
                initSpinner(food);
            }

        });
        long foodId = getIntent().getLongExtra(Static.KEY_FOOD_ID, -1);
        if (foodId != -1) {
            mProgressDialog = AlertDialogUtils.showProgressDialog(
                    this,
                    "Info",
                    "Loading Food Nutrition Values, Please Wait...",
                    false
            );
            mModule.getDetailFoodInfo(foodId);
        }

    }

    private void initSpinner(Food food) {
        List<Serving> servingList = food.getServings();
        ArrayList<String> list=new ArrayList<>();
        for(Serving sv:servingList){
            list.add(sv.getServingDescription());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list
        ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Serving serving = servingList.get(position);
                mFragment.updateTable(serving);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void showLog(String msg) {
        Log.e(getClass().getSimpleName(), msg);
    }


}
