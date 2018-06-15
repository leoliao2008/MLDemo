package com.tgi.mldemo.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.gson.Gson;
import com.tgi.mldemo.bean.CloudVisionResponse;
import com.tgi.mldemo.callback.JavaHttpUrlRequestCallBack;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * This module use java generic api to make a HttpUrlRequest to Google Cloud server
 * to acquire the annotation info of an image. That is, use a bitmap as param to identify
 * the bitmap's content. For example to identify apple from a bitmap extracted from an apple image.
 * This module is with reference to <a href="https://cloud.google.com/community/tutorials/make-an-
 * http-request-to-the-cloud-vision-api-from-java" target="_blank">Google Cloud Vision Tutorial</a>.
 */
public class JavaHttpUrlRequestModule {
    private Context mContext;
    private JavaHttpUrlRequestCallBack mCallBack;
    private String mGoogleKey;
    private Handler mHandler;


    public JavaHttpUrlRequestModule(Context context, JavaHttpUrlRequestCallBack callBack, String googleKey) {
        mContext = context;
        mCallBack = callBack;
        mGoogleKey = googleKey;
        if(Looper.getMainLooper()==null){
            Looper.loop();
        }
        mHandler=new Handler(Looper.getMainLooper());
    }

    public void identifyBitmap(Bitmap bitmap){
        CloudVisionTask task=new CloudVisionTask(bitmap);
        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    /**
     * transform the bitmap into a Base64 encoded string in order to upload it as part of the
     * jason string.
     * @param bitmap the target bitmap which is later to upload to google sever.
     * @return a base64 encoded string generate from the bitmap.
     */
    private String bitmap2String(Bitmap bitmap) {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        String result = Base64.encodeBase64String(out.toByteArray());
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * in case the size of bitmap is too large, we need to scale it to an acceptable level.
     * @param bitmap the origin bitmap, its size may be very large.
     * @return the resized bitmap, easy to upload.
     */
    private Bitmap scaleBitmapDown(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int resizeW=1200;//this value is considered suitable because google demo uses the same value.
        int resizeH=1200;
        if(h>w){
            resizeW=resizeH*w/h;
        }else if(w>h){
            resizeH=resizeW*h/w;
        }
        return Bitmap.createScaledBitmap(bitmap,resizeW,resizeH,false);
    }


    private class CloudVisionTask extends AsyncTask<Void,Void,CloudVisionResponse>{
        private Bitmap mBitmap;

        public CloudVisionTask(Bitmap bitmap) {
            mBitmap = bitmap;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCallBack.onPreExecute();
        }

        @Override
        protected CloudVisionResponse doInBackground(Void... voids) {
            try {
                mBitmap=scaleBitmapDown(mBitmap);
                String bitmapString = bitmap2String(mBitmap);
                URL url=new URL("https://vision.googleapis.com/v1/images:annotate?key="+mGoogleKey);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                //start to connect here by calling "connection.getOutputStream()"
                BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                String jsonPost="{\n" +
                        "  \"requests\":[\n" +
                        "    {\n" +
                        "      \"image\":{\n" +
                        "        \"content\":\""+bitmapString+"\"\n" +//here is where we insert the bit map
                        "      },\n" +
                        "      \"features\":[\n" +
                        "        {\n" +
                        "          \"type\":\"Label_DETECTION\",\n" +//here we decide which type of detection
                        "          \"maxResults\":10\n" +//here we decide max result count
                        "        }," +
                        "        {\n" +
                        "          \"type\":\"WEB_DETECTION\",\n" +//here we decide which type of detection
                        "          \"maxResults\":10\n" +//here we decide max result count
                        "        }\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
                bw.write(jsonPost);
                bw.close();
                String responseMessage = connection.getResponseMessage();
                if(responseMessage.equals("OK")){
                    Scanner scanner=new Scanner(connection.getInputStream());
                    StringBuffer sb=new StringBuffer();
                    while (scanner.hasNextLine()){
                        sb.append(scanner.nextLine());
                    }
                    return decipherJasonResponse(sb.toString());
                }else {
                    handleException(new Exception("Response message from google cloud server is abnormal: "+responseMessage));
                }
            } catch (Exception e) {
                e.printStackTrace();
                handleException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(CloudVisionResponse bean) {
            super.onPostExecute(bean);
            if(bean!=null){
                mCallBack.onPostExecute(bean);
//                checkResponse(bean);
            }
        }

        private void handleException(final Exception e){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallBack.onError(e);
                }
            });
        }
    }

    private void checkResponse(CloudVisionResponse bean) {
        showLog("begin to check result:");
        List<CloudVisionResponse.Response> response = bean.getResponses();
        if(response==null){
            showLog("response == null");
            return;
        }
        if(response.size()<=0){
            showLog("response list size =0");
            return;
        }
        CloudVisionResponse.Response responseBean = response.get(0);
        if(responseBean==null){
            showLog("response at index 0 is null");
            return;
        }
        CloudVisionResponse.Response.WebDetection webDetection = responseBean.getWebDetection();
        if(webDetection==null){
            showLog("web detection do not exist.");
            return;
        }
        List<CloudVisionResponse.Response.WebDetection.WebEntities> entities = webDetection.getWebEntities();
        if(entities==null){
            showLog("web entity lis is null");
            return;
        }
        int size = entities.size();
        showLog("web entity list size ="+size);
        for(CloudVisionResponse.Response.WebDetection.WebEntities temp:entities){
            showLog(temp.toString());
        }
    }

    private CloudVisionResponse decipherJasonResponse(String jsonString) {
        return new Gson().fromJson(jsonString, CloudVisionResponse.class);
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
