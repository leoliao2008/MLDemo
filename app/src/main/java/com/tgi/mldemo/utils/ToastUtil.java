package com.tgi.mldemo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;
    private ToastUtil(){}
    public synchronized static void showToast(Context context,String msg){
        if(toast==null){
            toast=Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }
}
