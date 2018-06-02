package com.tgi.mldemo.module;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.tgi.mldemo.callback.ImageLabelerModuleCallBack;

import java.util.List;

/**
 * 创建者     $Author$
 * 创建时间   2018/6/2 18:21
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class ImageLabelerModule {

    private final FirebaseVisionLabelDetector mDetector;
    private ImageLabelerModuleCallBack mCallBack;

    public ImageLabelerModule(){
        mDetector = FirebaseVision.getInstance().getVisionLabelDetector();
    }

    public void identify(Bitmap bitmap, final ImageLabelerModuleCallBack callBack){
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        mDetector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionLabel> firebaseVisionLabels) {
                callBack.onSuccess(firebaseVisionLabels);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e);
            }
        });
    }




}
