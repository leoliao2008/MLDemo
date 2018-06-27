package com.tgi.mldemo.bean;

import com.fatsecret.platform.model.CompactFood;

import java.util.Formatter;
import java.util.regex.Pattern;

public class MyCompactFood extends CompactFood {
    private String thumbNail;
    private long mDate;
    private double mCal;//kcal
    private double mProtein;//g
    private double mFat;//g
    private double mCarb;//g

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public void setCompactFood(CompactFood compactFood) {
        setName(compactFood.getName());
        setBrandName(compactFood.getBrandName());
        setDescription(compactFood.getDescription());
        setId(compactFood.getId());
        setType(compactFood.getType());
        setUrl(compactFood.getUrl());
        initBasicNutrientsByDesc(compactFood.getDescription());
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
        initBasicNutrientsByDesc(description);
    }

    private void initBasicNutrientsByDesc(String desc) {
        String[] split = desc.split("-");
        if(split.length!=2){
            return;
        }
        //Per 100g
        String g_100 = split[0].replace("Per", "").replace("g", "").trim();
        double ratio=Double.valueOf(g_100)/100;
        //Calories:127kcal|Fat:3.6g|Carbs:20.00g|Proten:1.00g
        String[] nutrients = split[1].split(Pattern.quote("|"));
        if(nutrients.length!=4){
            return;
        }
        String[] n0 = nutrients[0].split(Pattern.quote(":"));
        if(n0.length!=2){
            return;
        }
        String cal = n0[1].replace("kcal", "").trim();
        mCal=Double.valueOf(cal)/ratio;
        String[] n1 = nutrients[1].split(Pattern.quote(":"));
        if(n1.length!=2){
            return;
        }
        String fat=n1[1].replace("g","").trim();
        mFat=Double.valueOf(fat)/ratio;
        String[] n2 = nutrients[2].split(Pattern.quote(":"));
        if(n2.length!=2){
            return;
        }
        String carbs = n2[1].replace("g", "").trim();
        mCarb=Double.valueOf(carbs)/ratio;
        String[] n3 = nutrients[3].split(Pattern.quote(":"));
        if(n3.length!=2){
            return;
        }
        String protein = n3[1].replace("g", "").trim();
        mProtein=Double.valueOf(protein)/ratio;

        mCal=Double.valueOf(new Formatter().format("%.2f", mCal).toString());
        mFat=Double.valueOf(new Formatter().format("%.2f", mFat).toString());
        mCarb=Double.valueOf(new Formatter().format("%.2f", mCarb).toString());
        mProtein=Double.valueOf(new Formatter().format("%.2f", mProtein).toString());
    }

    public CompactFood getCompactFood(){
        CompactFood food=new CompactFood();
        food.setUrl(getUrl());
        food.setBrandName(getBrandName());
        food.setName(getName());
        food.setDescription(getDescription());
        food.setId(getId());
        food.setType(getType());
        return food;
    }

    public double getCal() {
        return mCal;
    }

    public double getProtein() {
        return mProtein;
    }

    public double getFat() {
        return mFat;
    }

    public double getCarb() {
        return mCarb;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }
}
