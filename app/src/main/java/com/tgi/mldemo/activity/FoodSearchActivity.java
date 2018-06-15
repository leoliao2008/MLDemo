package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Recipe;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.ResponseListener;
import com.tgi.mldemo.R;
import com.tgi.mldemo.adapter.BasicFoodInfoAdapter;
import com.tgi.mldemo.module.FatSecretApiModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodSearchActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.edt_key_word)
    EditText edtKeyWord;
    private FatSecretApiModule mModule;
    private ArrayList<CompactFood> mList=new ArrayList<>();
    private BasicFoodInfoAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, FoodSearchActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        ButterKnife.bind(this);
        mModule=new FatSecretApiModule(this, new ResponseListener() {
            @Override
            public void onFoodResponse(Food food) {

            }

            @Override
            public void onFoodListRespone(Response<CompactFood> foods) {
                if(foods==null){
                    return;
                }
                List<CompactFood> results = foods.getResults();
                mList.clear();
                mList.addAll(results);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRecipeResponse(Recipe recipe) {

            }
        });

        mAdapter=new BasicFoodInfoAdapter(mList,this);
        listView.setAdapter(mAdapter);
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompactFood compactFood = mList.get(position);
                FoodDetailActivity.start(FoodSearchActivity.this,compactFood.getId());
            }
        });

    }

    public void search(View view) {
        String kw = edtKeyWord.getText().toString().trim();
        if(TextUtils.isEmpty(kw))return;
        mModule.getBasicFoodInfo(kw);

    }
}
