package com.tgi.mldemo.module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Serving;
import com.tgi.mldemo.bean.DietLog;
import com.tgi.mldemo.bean.MyCompactFood;
import com.tgi.mldemo.utils.LocalStorageUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FatSecretSQLModule extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME ="FOOD_NUTRIENTS";
    private static final String FOOD_TABLE ="FOOD_TABLE";
    private static final String DIET_RECORD="DIET_RECORD";
    private static final int DATA_BASE_VER=3;

    public FatSecretSQLModule(Context context) {
        this(context, DATA_BASE_NAME, null, DATA_BASE_VER,null);
    }

    public FatSecretSQLModule(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFoodTable="create table if not exists '"+ FOOD_TABLE +"' (_id integer primary key autoincrement,name text,url text," +
                "type text,id long,description text,brandName text,servingList text,date long,thumbNail text)";
        db.execSQL(createFoodTable);
        String createDietRecord="create table if not exists '"+DIET_RECORD+"' (_id integer primary key autoincrement, name text, date long)";
        db.execSQL(createDietRecord);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String cmd1="drop table if exists '"+FOOD_TABLE+"'";
        db.execSQL(cmd1);
        String cmd2="drop table if exists '"+DIET_RECORD+"'";
        db.execSQL(cmd2);
        onCreate(db);
    }

    public boolean insertCompactFood(MyCompactFood food){
        if(isFoodExist(food.getName())){
            return true;
        }
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",food.getName().toLowerCase());
        contentValues.put("url",food.getUrl());
        contentValues.put("type",food.getType());
        contentValues.put("id",food.getId());
        contentValues.put("description",food.getDescription());
        contentValues.put("brandName",food.getBrandName());
        contentValues.put("date",System.currentTimeMillis());
        contentValues.put("thumbNail",food.getThumbNail());
        return database.insertOrThrow(FOOD_TABLE,null,contentValues)!=-1;
    }



    public void insertServingsTable(Food food) {
        String cmd="create table if not exists '"+food.getName().toLowerCase()+"'(_id integer primary key autoincrement," +
                "servingId long,servingDescription text,servingUrl text,metricServingAmount double,metricServingUnit text," +
                "numberOfUnits double,measurementDescription text,calories double,carbohydrate double,protein double,fat double," +
                "saturatedFat,double,polyunsaturatedFat double,monounsaturatedFat double,transFat double,cholesterol double," +
                "sodium double,potassium double,fiber double,sugar double,vitaminA double,vitaminC double,calcium double,iron double)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(cmd);
        for(Serving serving:food.getServings()){
            ContentValues values=new ContentValues();
            values.put("servingId",serving.getServingId());
            values.put("servingDescription",serving.getServingDescription());
            values.put("servingUrl",serving.getServingUrl());
            values.put("metricServingAmount",serving.getMetricServingAmount().doubleValue());
            values.put("metricServingUnit",serving.getMetricServingUnit());
            values.put("numberOfUnits",serving.getNumberOfUnits().doubleValue());
            values.put("measurementDescription",serving.getMeasurementDescription());
            values.put("calories",serving.getCalories().doubleValue());
            values.put("carbohydrate",serving.getCarbohydrate().doubleValue());
            values.put("protein",serving.getProtein().doubleValue());
            values.put("fat",serving.getFat().doubleValue());
            values.put("saturatedFat",serving.getSaturatedFat().doubleValue());
            values.put("polyunsaturatedFat",serving.getPolyunsaturatedFat().doubleValue());
            values.put("monounsaturatedFat",serving.getMonounsaturatedFat().doubleValue());
            values.put("transFat",serving.getTransFat().doubleValue());
            values.put("cholesterol",serving.getCholesterol().doubleValue());
            values.put("sodium",serving.getSodium().doubleValue());
            values.put("potassium",serving.getPotassium().doubleValue());
            values.put("fiber",serving.getFiber().doubleValue());
            values.put("sugar",serving.getSugar().doubleValue());
            values.put("vitaminA",serving.getVitaminA().doubleValue());
            values.put("vitaminC",serving.getVitaminC().doubleValue());
            values.put("calcium",serving.getCalcium().doubleValue());
            values.put("iron",serving.getIron().doubleValue());
            database.insert(food.getName().toLowerCase(),null,values);
        }
    }

    public void deleteServingsTable(String foodName){
        SQLiteDatabase database = getWritableDatabase();
        String cmd="drop table if exists '"+foodName.toLowerCase()+"'";
        database.execSQL(cmd);
    }

    public int deleteFood(String foodName){
        //delete the related servings
        deleteServingsTable(foodName);
        SQLiteDatabase database = getWritableDatabase();
        //delete the related local thumb nails
        Cursor cursor = database.query(FOOD_TABLE, new String[]{"thumbNail"}, "name=?", new String[]{foodName}, null, null, null);
        while (cursor.moveToNext()){
            String path = cursor.getString(cursor.getColumnIndex("thumbNail"));
            LocalStorageUtils.deleteThumbNail(path);
        }
        cursor.close();
        //finally delete the food itself
        return database.delete(FOOD_TABLE,"name=?",new String[]{foodName});
    }

    private boolean isFoodExist(String foodName){
        boolean isExist=false;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(FOOD_TABLE, new String[]{"name"}, "name=?", new String[]{foodName}, null, null, null);
        if(cursor.moveToNext()){
            isExist=true;
        }
        cursor.close();
        return isExist;
    }

    public MyCompactFood queryFood(String foodName){
        foodName=foodName.toLowerCase();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(FOOD_TABLE, null, "name=?", new String[]{foodName}, null, null, null);
        MyCompactFood food=null;
        if (cursor.moveToNext()){
            food=new MyCompactFood();
            food.setName(cursor.getString(cursor.getColumnIndex("name")));
            food.setBrandName(cursor.getString(cursor.getColumnIndex("brandName")));
            food.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            food.setType(cursor.getString(cursor.getColumnIndex("type")));
            food.setId(cursor.getLong(cursor.getColumnIndex("id")));
            food.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            food.setThumbNail(cursor.getString(cursor.getColumnIndex("thumbNail")));
            food.setDate(cursor.getLong(cursor.getColumnIndex("date")));
        }
        cursor.close();
        return food;
    }

    public ArrayList<MyCompactFood> batchQueryFood(){
        ArrayList<MyCompactFood> list=new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(FOOD_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            MyCompactFood food=new MyCompactFood();
            food.setName(cursor.getString(cursor.getColumnIndex("name")));
            food.setBrandName(cursor.getString(cursor.getColumnIndex("brandName")));
            food.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            food.setType(cursor.getString(cursor.getColumnIndex("type")));
            food.setId(cursor.getLong(cursor.getColumnIndex("id")));
            food.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            food.setThumbNail(cursor.getString(cursor.getColumnIndex("thumbNail")));
            food.setDate(cursor.getLong(cursor.getColumnIndex("date")));
            list.add(food);
        }
        cursor.close();
        return list;
    }


    public List<Serving> queryServingsTable(String foodName) {
        foodName=foodName.toLowerCase();
        List<Serving> list=new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(foodName, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            Serving serving=new Serving();
            serving.setCalcium(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("calcium"))));
            serving.setIron(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("iron"))));
            serving.setVitaminC(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("vitaminC"))));
            serving.setVitaminA(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("vitaminA"))));
            serving.setSugar(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("sugar"))));
            serving.setFiber(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("fiber"))));
            serving.setPotassium(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("potassium"))));
            serving.setSodium(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("sodium"))));
            serving.setCholesterol(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("cholesterol"))));
            serving.setTransFat(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("transFat"))));
            serving.setMonounsaturatedFat(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("monounsaturatedFat"))));
            serving.setPolyunsaturatedFat(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("polyunsaturatedFat"))));
            serving.setSaturatedFat(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("saturatedFat"))));
            serving.setFat(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("fat"))));
            serving.setProtein(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("protein"))));
            serving.setCarbohydrate(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("carbohydrate"))));
            serving.setCalories(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("calories"))));
            serving.setMeasurementDescription(cursor.getString(cursor.getColumnIndex("measurementDescription")));
            serving.setNumberOfUnits(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("numberOfUnits"))));
            serving.setMetricServingUnit(cursor.getString(cursor.getColumnIndex("metricServingUnit")));
            serving.setMetricServingAmount(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("metricServingAmount"))));
            serving.setServingUrl(cursor.getString(cursor.getColumnIndex("servingUrl")));
            serving.setServingDescription(cursor.getString(cursor.getColumnIndex("servingDescription")));
            serving.setServingId(cursor.getLong(cursor.getColumnIndex("servingId")));
            list.add(serving);
        }
        cursor.close();
        return list;
    }

    public long insertDietRecord(String foodName){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",foodName);
        cv.put("date",System.currentTimeMillis());
        return database.insert(DIET_RECORD,null,cv);
    }

    public ArrayList<DietLog> batchQueryDietRecord(){
        ArrayList<DietLog> list=new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(
                DIET_RECORD,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            DietLog record=new DietLog();
            record.setName(
                    cursor.getString(cursor.getColumnIndex("name"))
            );
            record.setDate(
                    cursor.getLong(cursor.getColumnIndex("date"))
            );
            list.add(record);
        }
        cursor.close();
        return list;
    }

    public int deleteDietRecord(DietLog dietLog){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(
                DIET_RECORD,
                "name=?&date=?",
                new String[]{dietLog.getName(),String.valueOf(dietLog.getDate())}
        );
    }


    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
