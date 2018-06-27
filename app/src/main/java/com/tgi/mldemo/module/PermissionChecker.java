package com.tgi.mldemo.module;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class PermissionChecker {

    public String[] checkPermissions(Context context,String[] permissions){
        ArrayList<String> rqstPms=new ArrayList<>();
        for(String per:permissions){
            if(context.checkSelfPermission(per)== PackageManager.PERMISSION_DENIED){
                rqstPms.add(per);
            }
        }
        String[] rsult=new String[rqstPms.size()];
        for(int i=0;i<rsult.length;i++){
            rsult[i]=rqstPms.get(i);
        }
        return rsult;
    }

    public void requestPermissions(Activity activity,String[] pers,int rqCode){
        activity.requestPermissions(pers,rqCode);
    }

    public void onRequestPermissionsResult(
            String[]pers,
            int[]results,
            @Nullable Runnable onPassAll,
            Runnable onDenied){
        for(int i=0;i<pers.length;i++){
            if(results[i]== PackageManager.PERMISSION_DENIED){
                onDenied.run();
                return;
            }
        }
        if(onPassAll!=null){
            onPassAll.run();
        }
    }
}
