package com.tgi.mldemo.callback;

import android.support.annotation.NonNull;

import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;

import java.util.List;

/**
 * 创建者     $Author$
 * 创建时间   2018/6/2 18:34
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class ImageLabelerModuleCallBack {
    public void onGetOfflineResult(List<FirebaseVisionLabel> results){

    }

    public void onFailure(@NonNull Exception e){

    }

    public void onGetOnlineResult(List<FirebaseVisionCloudLabel> results) {

    }
}
