package com.tgi.mldemo.callback;

import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;

import java.util.ArrayList;

public class VisionRequestModuleCallBack {

    public void onPreExecute() {

    }

    public void onGetResults(ArrayList<String> results){

    }

    public void onNoResult(){

    }

    public void onError(Exception e){

    }


    public void onGetImageResponse(BatchAnnotateImagesResponse response) {

    }
}
