package com.tgi.mldemo.module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.tgi.mldemo.bean.DietLog;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport.FoodMatadataDesc;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport.Nutrient;
import com.tgi.mldemo.utils.LocalStorageUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class NdbSqlModule extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME="LOCAL_NDB";
    private static final String PRIME_SHEET="FOOD_LOG";
    private static final String DIET_LOG="DIET_LOG";
    private static final int DATA_BASE_VER=4;
    private Context mContext;
    private int mCurrentVer=-1;
    private int mUpgradeVer=-1;
    private ArrayList<DietLog> mDietLogs;

    public NdbSqlModule(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VER);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //this is the primary table that records all the food reports
        String cmd1="create table if not exists '"+PRIME_SHEET+"' (_id integer primary key autoincrement, " +
                "sr text,type text,ndbno text,name text,ds text,manu text,ru text,thumb_nail text, nutri text,date long,search_item text)";
        db.execSQL(cmd1);
        //this is the table that records user's diet record
        String cmd2="create table if not exists '"+DIET_LOG+"' (_id integer primary key autoincrement," +
                "food text,date long,thumb_nail text,weight double)";
        db.execSQL(cmd2);
        switch (mUpgradeVer){
            case 4:
                if(mDietLogs!=null){
                    for(DietLog dietLog:mDietLogs){
                        insertNewDietLog(dietLog,db);
                    }
                }
                break;
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mCurrentVer=oldVersion;
        mUpgradeVer=newVersion;
        switch (oldVersion){
            case 1:
            case 2:
            case 3:
                mDietLogs = batchQueryDietLogs(db);
                break;
        }
        String cmd1="drop table if exists '"+PRIME_SHEET+"'";
        db.execSQL(cmd1);
        String cmd2="drop table if exists '"+DIET_LOG+"'";
        db.execSQL(cmd2);
        onCreate(db);
    }

    public FoodReport queryLocalFoodReport(String searchItem){
        searchItem=searchItem.toLowerCase();
        FoodReport foodReport=null;
        Cursor cursor = getReadableDatabase().query(
                PRIME_SHEET,
                null,
                "search_item=?",
                new String[]{searchItem},
                null,
                null,
                null
        );
        if (cursor.moveToNext()){
            foodReport= genFoodReport(cursor);
        }
        cursor.close();
        return foodReport;
    }

    public long insertNewFoodReport(FoodReport report){
        long row=-1;
        String ndbNo=null;
        try {
            ndbNo=report.getDesc().getNdbNo();
        }catch (NullPointerException e){
            return row;
        }
        if(!TextUtils.isEmpty(ndbNo)){
            //meaning the food dose not exist in the data base
            SQLiteDatabase db = getWritableDatabase();
            List<Nutrient> nutrients = report.getNutrients();
            String tableName="";
            if(nutrients!=null&&nutrients.size()>0){
                if(insertNutrients(ndbNo,nutrients)!=-1) tableName=convertNdbNoToTableName(ndbNo);
            }
            ContentValues cv=new ContentValues();
            cv.put("sr",report.getReleaseVersion());
            cv.put("type",report.getReportType());
            cv.put("ndbno",report.getDesc().getNdbNo());
            cv.put("name",report.getDesc().getFoodName());
            cv.put("ds",report.getDesc().getDataSource());
            cv.put("manu",report.getDesc().getManufacturer());
            cv.put("ru",report.getDesc().getUnit());
            cv.put("thumb_nail", report.getThumbNailPath());
            cv.put("nutri",tableName);
            cv.put("date",report.getDate());
            cv.put("search_item",report.getSearchItem());
            row= db.insert(PRIME_SHEET, null, cv);
        }
        return row;
    }



    private long insertNutrients(String ndbNo, List<Nutrient> nutrients) {
        ndbNo= convertNdbNoToTableName(ndbNo);
        long result=-1;
        deleteNutrients(ndbNo);
        SQLiteDatabase db = getWritableDatabase();
        //ndbNo serves as a table name
        String create="create table if not exists '"+ndbNo+"' (_id integer primary key autoincrement," +
                "id text,name text,derivation text,_group text,unit text,value text)";
        db.execSQL(create);
        for(Nutrient temp:nutrients){
            ContentValues cv=new ContentValues();
            cv.put("id",temp.getNutrientId());
            cv.put("name",temp.getNutrientName());
            cv.put("derivation",temp.getDerivationInfo());
            cv.put("_group",temp.getGroup());
            cv.put("unit",temp.getUnit());
            cv.put("value",temp.getValue());
            result=db.insert(
                    ndbNo,
                    null,
                    cv
            );
            if(result<0){
                return -1;
            }
        }
        return result;
    }

    private String convertNdbNoToTableName(String ndbNo) {
        return "NdbNo"+ndbNo+"ed";
    }

    private List<Nutrient> queryNutrients(String tableName){
        ArrayList<Nutrient> list=new ArrayList<>();
        if(!TextUtils.isEmpty(tableName)){
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(tableName, null, null, null, null, null, null);
            while (cursor.moveToNext()){
                Nutrient nutrient=new Nutrient();
                nutrient.setDerivationInfo(getColumnValue(String.class,cursor,"derivation","null"));
                nutrient.setGroup(getColumnValue(String.class,cursor,"_group","null"));
                nutrient.setNutrientId(getColumnValue(String.class,cursor,"id","0"));
                nutrient.setNutrientName(getColumnValue(String.class,cursor,"name","null"));
                nutrient.setUnit(getColumnValue(String.class,cursor,"unit",""));
                nutrient.setValue(getColumnValue(String.class,cursor,"value","0"));
                list.add(nutrient);
            }
            cursor.close();
        }
        return list;
    }

    private void deleteNutrients(String tableName){
        SQLiteDatabase db = getWritableDatabase();
        String drop="drop table if exists '"+tableName+"'";
        db.execSQL(drop);
    }

    private boolean isReportExist(String ndbNo){
        boolean result=false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                PRIME_SHEET,
                new String[]{"ndbno"},
                "ndbno=?",
                new String[]{ndbNo},
                null,
                null,
                null);
        if(cursor.moveToNext()){
            result=true;
        }
        cursor.close();
        return result;
    }

    public ArrayList<FoodReport> batchQueryFoodReports(){
        ArrayList<FoodReport> list=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PRIME_SHEET, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            list.add(genFoodReport(cursor));
        }
        cursor.close();
        return list;
    }

    private FoodReport genFoodReport(Cursor cursor) {
        FoodReport report=new FoodReport();
        report.setReleaseVersion(getColumnValue(String.class,cursor,"sr","null"));
        report.setReportType(getColumnValue(String.class,cursor,"type","null"));
        report.setThumbNailPath(getColumnValue(String.class,cursor,"thumb_nail",""));
        FoodMatadataDesc desc=new FoodMatadataDesc();
        desc.setNdbNo(getColumnValue(String.class,cursor,"ndbno",""));
        desc.setDataSource(getColumnValue(String.class,cursor,"ds","null"));
        desc.setFoodName(getColumnValue(String.class,cursor,"name","null"));
        desc.setManufacturer(getColumnValue(String.class,cursor,"manu","null"));
        desc.setUnit(getColumnValue(String.class,cursor,"ru","null"));
        report.setDesc(desc);
        report.setNutrients(queryNutrients(getColumnValue(String.class,cursor,"nutri","")));
        report.setDate(getColumnValue(Long.class,cursor,"date",0L));
        report.setSearchItem(getColumnValue(String.class,cursor,"search_item",""));
        return report;
    }

    public int deleteFoodReport(String ndbNo){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(
                PRIME_SHEET,
                new String[]{"ndbno,thumb_nail,nutri"},
                "ndbno=?",
                new String[]{ndbNo},
                null,
                null,
                null
        );
        while (cursor.moveToNext()){
            String thumbNail = getColumnValue(String.class,cursor,"thumb_nail","");
            if(!TextUtils.isEmpty(thumbNail)){
                LocalStorageUtils.deleteThumbNail(thumbNail);
            }

            String table = getColumnValue(String.class,cursor,"nutri","");
            if(!TextUtils.isEmpty(table)){
                deleteNutrients(table);
            }
        }
        cursor.close();
        return db.delete(
                PRIME_SHEET,
                "ndbno=?",
                new String[]{ndbNo}
        );
    }

    public long insertNewDietLog(DietLog dietLog){
        return insertNewDietLog(dietLog,null);
    }

    private long insertNewDietLog(DietLog dietLog,@Nullable SQLiteDatabase database){
        if(database==null){
            database= getWritableDatabase();
        }
        ContentValues cv=new ContentValues();
        cv.put("food",dietLog.getName());
        cv.put("date",System.currentTimeMillis());
        cv.put("thumb_nail",dietLog.getThumbNail());
        cv.put("weight",dietLog.getWeight());
        return database.insert(DIET_LOG,null,cv);
    }

    public int deleteDietLog(String foodName,long date){
        //first we need to delete the thumb nail
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(
                DIET_LOG,
                new String[]{"thumb_nail"},
                "food=? and date=?",
                new String[]{foodName, String.valueOf(date)},
                null,
                null,
                null);
        while (cursor.moveToNext()){
            String path = getColumnValue(String.class,cursor,"thumb_nail","");
            if(!TextUtils.isEmpty(path)){
                LocalStorageUtils.deleteThumbNail(path);
            }
        }
        cursor.close();
        return db.delete(DIET_LOG,"food=? and date=?",new String[]{foodName,String.valueOf(date)});
    }

    public ArrayList<DietLog> batchQueryDietLogs(){
        return batchQueryDietLogs(null);
    }

    private ArrayList<DietLog> batchQueryDietLogs(@Nullable SQLiteDatabase database){
        if(database==null){
            database=getReadableDatabase();
        }
        ArrayList<DietLog> list=new ArrayList<>();
        Cursor cursor = database.query(
                DIET_LOG,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()){
            DietLog record=new DietLog();
            record.setDate(getColumnValue(Long.class,cursor,"date",0L));
            record.setWeight(getColumnValue(Double.class,cursor,"weight",0.d));
            record.setThumbNail(getColumnValue(String.class,cursor,"thumb_nail",""));
            record.setName(getColumnValue(String.class,cursor,"food",""));
            list.add(record);
        }
        cursor.close();
        return list;
    }

    private <T > T getColumnValue(Class<T> valueType,Cursor cursor,String columnName,T def){
        T result=null;
        try {
            if (valueType.equals(long.class)||valueType.equals(Long.class)) {
                result=valueType.cast(cursor.getLong(cursor.getColumnIndex(columnName)));
            }else if( valueType.equals(String.class)){
                result=valueType.cast(cursor.getString(cursor.getColumnIndex(columnName)));
            }else if( valueType.equals(double.class)||valueType.equals(Double.class)){
                result=valueType.cast(cursor.getDouble(cursor.getColumnIndex(columnName)));
            }else if(valueType.equals(int.class)||valueType.equals(Integer.class)){
                result=valueType.cast(cursor.getInt(cursor.getColumnIndex(columnName)));
            }
        }catch(Exception e){
           e.printStackTrace();
        }
        if(result!=null){
            return result;
        }
        return def;
    }

}
