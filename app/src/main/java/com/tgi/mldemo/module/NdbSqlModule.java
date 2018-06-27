package com.tgi.mldemo.module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.tgi.mldemo.bean.DietLog;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport.FoodMatadataDesc;
import com.tgi.mldemo.ndb_package.bean.FoodReportResponses.SearchResult.FoodReport.Nutrient;
import com.tgi.mldemo.utils.LocalStorageUtils;

import java.util.ArrayList;
import java.util.List;

public class NdbSqlModule extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME="LOCAL_NDB";
    private static final String PRIME_SHEET="FOOD_LOG";
    private static final String DIET_LOG="DIET_LOG";
    private static final String NDBNO_SHEET="NDBNO_SHEET";
    private static final int DATA_BASE_VER=3;
    private Context mContext;

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
                "food text,date long,thumb_nail text)";
        db.execSQL(cmd2);
        //this is the table that records a key value set for all the locally available ndbno and its corresponding search item.
        //this table is necessary because the food name from the image annotations must be matched to a ndbno so that we don't need
        //to search every time for the potential food names math with that of the image annotation.
//        String cmd3="create table if not exists '"+NDBNO_SHEET+"' (_id integer primary key autoincrement," +
//                "ndbno text,search_item text)";
//        db.execSQL(cmd3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String cmd1="drop table if exists '"+PRIME_SHEET+"'";
        db.execSQL(cmd1);
        String cmd2="drop table if exists '"+DIET_LOG+"'";
        db.execSQL(cmd2);
        String cmd3="drop table if exists '"+NDBNO_SHEET+"'";
        db.execSQL(cmd3);
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

    public long insertNewFoodReport(FoodReport report, String search_item,Bitmap bitmap){
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
            String path="";
            try {
                path=LocalStorageUtils.saveThumbNail(mContext,bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cv.put("thumb_nail", path);
            cv.put("nutri",tableName);
            cv.put("date",System.currentTimeMillis());
            cv.put("search_item",search_item);
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
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            Nutrient nutrient=new Nutrient();
            nutrient.setDerivationInfo(cursor.getString(cursor.getColumnIndex("derivation")));
            nutrient.setGroup(cursor.getString(cursor.getColumnIndex("_group")));
            nutrient.setNutrientId(cursor.getString(cursor.getColumnIndex("id")));
            nutrient.setNutrientName(cursor.getString(cursor.getColumnIndex("name")));
            nutrient.setUnit(cursor.getString(cursor.getColumnIndex("unit")));
            nutrient.setValue(cursor.getString(cursor.getColumnIndex("value")));
            list.add(nutrient);
        }
        cursor.close();
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
        report.setReleaseVersion(cursor.getString(cursor.getColumnIndex("sr")));
        report.setReportType(cursor.getString(cursor.getColumnIndex("type")));
        report.setThumbNailPath(cursor.getString(cursor.getColumnIndex("thumb_nail")));
        FoodMatadataDesc desc=new FoodMatadataDesc();
        desc.setNdbNo(cursor.getString(cursor.getColumnIndex("ndbno")));
        desc.setDataSource(cursor.getString(cursor.getColumnIndex("ds")));
        desc.setFoodName(cursor.getString(cursor.getColumnIndex("name")));
        desc.setManufacturer(cursor.getString(cursor.getColumnIndex("manu")));
        desc.setUnit(cursor.getString(cursor.getColumnIndex("ru")));
        report.setDesc(desc);
        String table = cursor.getString(cursor.getColumnIndex("nutri"));
        report.setNutrients(queryNutrients(table));
        report.setDate(cursor.getLong(cursor.getColumnIndex("date")));
        report.setSearchItem(cursor.getString(cursor.getColumnIndex("search_item")));
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
            String thumbNail = cursor.getString(cursor.getColumnIndex("thumb_nail"));
            if(!TextUtils.isEmpty(thumbNail)){
                LocalStorageUtils.deleteThumbNail(thumbNail);
            }
            String table = cursor.getString(cursor.getColumnIndex("nutri"));
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
        ContentValues cv=new ContentValues();
        cv.put("food",dietLog.getName());
        cv.put("date",System.currentTimeMillis());
        cv.put("thumb_nail",dietLog.getThumbNail());
        return getWritableDatabase().insert(DIET_LOG,null,cv);
    }

    public int deleteDietLog(String foodName,long date){
        //first we need to delete the thumb nail
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(
                DIET_LOG,
                new String[]{"thumb_nail"},
                "name=? and date=?",
                new String[]{foodName, String.valueOf(date)},
                null,
                null,
                null);
        while (cursor.moveToNext()){
            String path = cursor.getString(cursor.getColumnIndex("thumb_nail"));
            if(!TextUtils.isEmpty(path)){
                LocalStorageUtils.deleteThumbNail(path);
            }
        }
        cursor.close();
        return db.delete(DIET_LOG,"name=? and date=?",new String[]{foodName,String.valueOf(date)});
    }

    public ArrayList<DietLog> batchQueryDietLogs(){
        ArrayList<DietLog> list=new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(
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
            record.setDate(cursor.getLong(cursor.getColumnIndex("date")));
            record.setName(cursor.getString(cursor.getColumnIndex("food")));
            record.setThumbNail(cursor.getString(cursor.getColumnIndex("thumb_nail")));
            list.add(record);
        }
        cursor.close();
        return list;
    }

//    public long insertNdbKvPair(String searchItem,String ndbNo){
//        ContentValues cv=new ContentValues();
//        cv.put("search_item",searchItem.toLowerCase());
//        cv.put("ndbno",ndbNo);
//        return getWritableDatabase().insert(
//                NDBNO_SHEET,
//                null,
//                cv);
//    }
//
//    public String queryNdbNo(String searchItem){
//        String ndbno=null;
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.query(
//                NDBNO_SHEET,
//                new String[]{"ndbno", "search_item"},
//                "search_item=?",
//                new String[]{searchItem.toLowerCase()},
//                null,
//                null,
//                null
//        );
//        while (cursor.moveToNext()){
//            ndbno=cursor.getString(cursor.getColumnIndex("ndbno"));
//        }
//        cursor.close();
//        return ndbno;
//    }
//
//    public long deleteNdbKvPair(String searchItem){
//        return getWritableDatabase().delete(
//                NDBNO_SHEET,
//                "search_item=?",
//                new String[]{searchItem.toLowerCase()}
//        );
//    }
}
