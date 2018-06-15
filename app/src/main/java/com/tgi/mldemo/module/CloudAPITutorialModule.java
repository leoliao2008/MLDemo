package com.tgi.mldemo.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.WebDetection;
import com.google.api.services.vision.v1.model.WebEntity;
import com.google.api.services.vision.v1.model.WebLabel;
import com.tgi.mldemo.callback.VisionRequestModuleCallBack;
import com.tgi.mldemo.data.Static;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CloudAPITutorialModule {


    private Context mContext;
    private VisionRequestModuleCallBack mCallBack;

    public CloudAPITutorialModule(Context context, VisionRequestModuleCallBack callBack) {
        mContext = context;
        mCallBack=callBack;
    }

    public void identifyBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            // scale the image to save on bandwidth
            bitmap = scaleBitmapDown(bitmap, 1200);
            callCloudVision(bitmap);
        } else {
            showLog("error executing uploadImage(Bitmap bitmap): bitmap is null.");
        }
    }

    private void callCloudVision(final Bitmap bitmap) {

        // Do the real work in an async task, because we need to use the network anyway
        try {
            LabelDetectionTask labelDetectionTask = new LabelDetectionTask(prepareAnnotationRequest(bitmap),mCallBack);
            labelDetectionTask.execute();
        } catch (IOException e) {
           showLog(e.getMessage());
        }
    }

    private Vision.Images.Annotate prepareAnnotationRequest(final Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(Static.GOOGLE_CLOUD_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

//                        String packageName = mContext.getPackageName();
//                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);
//
//                        String sig = PackageManagerUtils.getSignature(mContext.getPackageManager(), packageName);
//
//                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature f1 = new Feature();
                f1.setType("WEB_DETECTION");
                f1.setMaxResults(10);
                add(f1);

                Feature f2 = new Feature();
                f2.setType("LABEL_DETECTION");
                f2.setMaxResults(10);
                add(f2);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
//        Log.d(TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }


    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private void showLog(String msg){
        Log.e(getClass().getName(),msg);
    }

    private static class LabelDetectionTask extends AsyncTask<Void, Void, BatchAnnotateImagesResponse> {
        private Vision.Images.Annotate mRequest;
        private VisionRequestModuleCallBack mCallBack;

        LabelDetectionTask(Vision.Images.Annotate annotate, VisionRequestModuleCallBack callBack) {
            mRequest = annotate;
            mCallBack=callBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCallBack.onPreExecute();
        }

        @Override
        protected BatchAnnotateImagesResponse doInBackground(Void... params) {
            try {
                Log.e("LabelDetectionTask", "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return response;
            } catch (GoogleJsonResponseException e) {
                mCallBack.onError(e);
                Log.e("LabelDetectionTask", "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                mCallBack.onError(e);
                Log.e("LabelDetectionTask", "failed to make API request because of other IOException " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(BatchAnnotateImagesResponse response) {
            mCallBack.onGetImageResponse(response);
            AnnotateImageResponse imageResponse = response.getResponses().get(0);
            List<EntityAnnotation> labelAnnotations = imageResponse.getLabelAnnotations();
            WebDetection detection = imageResponse.getWebDetection();
            ArrayList<String> list=new ArrayList<>();
            List<WebLabel> bestGuesses=null;
            List<WebEntity> webEntities=null;
            if(detection!=null){
                bestGuesses = detection.getBestGuessLabels();
                webEntities = detection.getWebEntities();
            }
            if(webEntities!=null){
                for(WebEntity entity:webEntities){
                    list.add(entity.getDescription());
                }
            }else if(bestGuesses!=null){
                for(WebLabel label:bestGuesses){
                    list.add(label.getLabel());
                }
            }else if(labelAnnotations!=null){
                for(EntityAnnotation annotation:labelAnnotations){
                    list.add(annotation.getDescription());
                }
            }
            if(list.size()>0){
                mCallBack.onGetResults(list);
            }else {
                mCallBack.onNoResult();
            }

        }
    }
}
