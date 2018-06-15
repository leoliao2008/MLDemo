package com.tgi.mldemo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertDialogUtils {

    private static AlertDialog alertDialog;

    public static ProgressDialog showProgressDialog(Context context, String title, String msg, boolean cancellable){
        return ProgressDialog.show(
                context,
                title,
                msg,
                true,
                cancellable
        );
    }

    public static void confirmFoodAnnotation(
           Context context,
            String title,
            String msg,
            Runnable onConfirm,
            Runnable onDisagree) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        alertDialog = builder.setTitle(title).setMessage(msg).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onConfirm.run();
            }
        }).setNeutralButton("Disagree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDisagree.run();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        }).setCancelable(false).create();
        alertDialog.show();
    }
}
