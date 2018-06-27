package com.tgi.mldemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class LocalStorageUtils {
    public static String saveThumbNail(Context context,Bitmap bitmap) throws Exception {
        String thumbNailPath=null;
        if(checkSDCardAvailable()){
            File directory = Environment.getExternalStorageDirectory();
            String packageName = context.getPackageName();
            String destPath=directory.getAbsolutePath()+File.separator+packageName+File.separator+"thumbNails";
            File newDir=new File(destPath);
            if(!newDir.exists()){
                if(!newDir.mkdirs()){
                    return null;
                }
            }
            int w=255,h=255;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if(width>height){
                h=w*height/width;
            }else {
                w=h*width/height;
            }
            Bitmap thumbNail = Bitmap.createScaledBitmap(bitmap, w, h, false);
            File dest=new File(newDir,System.currentTimeMillis()+".png");
            if(dest.exists()){
                dest.delete();
            }
            if(!dest.createNewFile()){
                return null;
            }
            FileOutputStream out=new FileOutputStream(dest);
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            boolean compress = thumbNail.compress(Bitmap.CompressFormat.PNG, 100, bos);
            if(compress){
                byte[] bytes = bos.toByteArray();
                out.write(bytes);
                thumbNailPath= dest.getAbsolutePath();
            }
        }else {
            throw new Exception("SD card is not mounted or available space is less then 50M!");
        }
        return thumbNailPath;
    }

    public static boolean deleteThumbNail(String path){
        File file=new File(path);
        if(file.exists()){
            return file.delete();
        }
        return false;
    }

    private static boolean checkSDCardAvailable(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            File file = Environment.getExternalStorageDirectory();
            return file.getUsableSpace()>=1024*1024*50;
        }
        return false;
    }

    private static void showLog(String msg){
        Log.e("Local Storage Utils",msg);
    }
}
