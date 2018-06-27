package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.adapter.FoodReportAdapter;
import com.tgi.mldemo.ndb_package.Static;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodReportActivity extends AppCompatActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_ID)
    TextView tvID;
    @BindView(R.id.tv_data_source)
    TextView tvDataSource;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.widget_basic_nutrient_board_tv_cal)
    TextView tvCal;
    @BindView(R.id.widget_basic_nutrient_board_tv_protein)
    TextView tvProtein;
    @BindView(R.id.widget_basic_nutrient_board_tv_fat)
    TextView tvFat;
    @BindView(R.id.widget_basic_nutrient_board_tv_carb)
    TextView tvCarb;
    @BindView(R.id.widget_basic_nutrient_board_tv_cover)
    TextView tvCover;
    private FoodReport mReport;

    public static void start(Context context, FoodReport report) {
        Intent starter = new Intent(context, FoodReportActivity.class);
        starter.putExtra(Static.EXTRA_FOOD_REPORT, report);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_report);
        ButterKnife.bind(this);
        mReport = getIntent().getParcelableExtra(Static.EXTRA_FOOD_REPORT);
        if (mReport != null) {
            tvCover.setVisibility(View.GONE);
            tvCal.setText(mReport.getCalories());
            tvCarb.setText(mReport.getCarbohydrate());
            tvFat.setText(mReport.getFat());
            tvProtein.setText(mReport.getProtein());
            FoodReport.FoodMatadataDesc desc = mReport.getDesc();
            if (desc != null) {
                tvName.setText("FoodName:" + desc.getFoodName());
                tvDataSource.setText("DataSource:" + desc.getDataSource());
                tvID.setText("ID:" + desc.getNdbNo());
                tvUnit.setText("Unit: 100" + desc.getUnit());
            }
            FoodReportAdapter adapter = new FoodReportAdapter(
                    mReport.getNutrients(),
                    this
            );
            listView.setDividerHeight(0);
            listView.setAdapter(adapter);
        }

    }
}
