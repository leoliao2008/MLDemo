package com.tgi.mldemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatsecret.platform.model.Serving;
import com.tgi.mldemo.R;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NutrientTableFragment extends Fragment {
    @BindView(R.id.tv_serving_size)
    TextView tvServingSize;
    @BindView(R.id.tv_calories)
    TextView tvCalories;
    @BindView(R.id.tv_total_fat)
    TextView tvTotalFat;
    @BindView(R.id.tv_saturated_fat)
    TextView tvSaturatedFat;
    @BindView(R.id.tv_polyunsaturated_fat)
    TextView tvPolyunsaturatedFat;
    @BindView(R.id.tv_monounsaturated_fat)
    TextView tvMonounsaturatedFat;
    @BindView(R.id.tv_cholesterol)
    TextView tvCholesterol;
    @BindView(R.id.tv_sodium)
    TextView tvSodium;
    @BindView(R.id.tv_potassium)
    TextView tvPotassium;
    @BindView(R.id.tv_total_carbohydrate)
    TextView tvTotalCarbohydrate;
    @BindView(R.id.tv_dietary_fiber)
    TextView tvDietaryFiber;
    @BindView(R.id.tv_sugars)
    TextView tvSugars;
    @BindView(R.id.tv_protein)
    TextView tvProtein;
    @BindView(R.id.tv_vitamin_a)
    TextView tvVitaminA;
    @BindView(R.id.tv_vitamin_c)
    TextView tvVitaminC;
    @BindView(R.id.tv_calcium)
    TextView tvCalcium;
    @BindView(R.id.tv_iron)
    TextView tvIron;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrient_table, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateTable(Serving sv){
        BigDecimal amount = sv.getMetricServingAmount();
        if(amount!=null){
            String servingAmount = String.valueOf(amount.intValue());
            tvServingSize.setText(servingAmount+sv.getMetricServingUnit());
        }
        tvCalories.setText(sv.getCalories()+"cal");
        tvTotalFat.setText(sv.getFat()+"g");
        tvSaturatedFat.setText(sv.getFat()+"g");
        tvPolyunsaturatedFat.setText(sv.getPolyunsaturatedFat()+"g");
        tvMonounsaturatedFat.setText(sv.getMonounsaturatedFat()+"g");
        tvCholesterol.setText(sv.getCholesterol()+"mg");
        tvSodium.setText(sv.getSodium()+"mg");
        tvPotassium.setText(sv.getPotassium()+"mg");
        tvTotalCarbohydrate.setText(sv.getCarbohydrate()+"g");
        tvDietaryFiber.setText(sv.getFiber()+"g");
        tvSugars.setText(sv.getSugar()+"g");
        tvProtein.setText(sv.getProtein()+"g");
        tvVitaminA.setText(sv.getVitaminA()+"%");
        tvVitaminC.setText(sv.getVitaminC()+"%");
        tvCalcium.setText(sv.getCalcium()+"%");
        tvIron.setText(sv.getIron()+"%");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
