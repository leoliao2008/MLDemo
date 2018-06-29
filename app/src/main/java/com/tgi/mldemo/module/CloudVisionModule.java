package com.tgi.mldemo.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.gson.Gson;
import com.tgi.mldemo.bean.CloudVisionResponse;
import com.tgi.mldemo.bean.CloudVisionResponse.Response;
import com.tgi.mldemo.bean.CloudVisionResponse.Response.LabelAnnotations;
import com.tgi.mldemo.bean.CloudVisionResponse.Response.WebDetection.BestGuessLabels;
import com.tgi.mldemo.bean.CloudVisionResponse.Response.WebDetection.WebEntities;
import com.tgi.mldemo.callback.JavaHttpUrlRequestCallBack;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * This module use java generic api to make a HttpUrlRequest to Google Cloud server
 * to acquire the annotation info of an image. That is, use a bitmap as param to identify
 * the bitmap's content. For example to identify apple from a bitmap extracted from an apple image.
 * This module is with reference to <a href="https://cloud.google.com/community/tutorials/make-an-
 * http-request-to-the-cloud-vision-api-from-java" target="_blank">Google Cloud Vision Tutorial</a>.
 */
public class CloudVisionModule {
    private Context mContext;
    private JavaHttpUrlRequestCallBack mCallBack;
    private String mGoogleKey;
    private Handler mHandler;


    public CloudVisionModule(Context context, JavaHttpUrlRequestCallBack callBack, String googleKey) {
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
        int resizeW=300;//this value is 1200 in google demo, but we try to reduce it to 300 to shorten the time span
        int resizeH=300;
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
                    handleException(new Exception("FoodSearchResponses message from google cloud server is abnormal: "+responseMessage));
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
        List<Response> response = bean.getResponses();
        if(response==null){
            showLog("response == null");
            return;
        }
        if(response.size()<=0){
            showLog("response list size =0");
            return;
        }
        Response responseBean = response.get(0);
        if(responseBean==null){
            showLog("response at index 0 is null");
            return;
        }
        Response.WebDetection webDetection = responseBean.getWebDetection();
        if(webDetection==null){
            showLog("web detection do not exist.");
            return;
        }
        List<WebEntities> entities = webDetection.getWebEntities();
        if(entities==null){
            showLog("web entity lis is null");
            return;
        }
        int size = entities.size();
        showLog("web entity list size ="+size);
        for(WebEntities temp:entities){
            showLog(temp.toString());
        }
    }

    private CloudVisionResponse decipherJasonResponse(String jsonString) {
        return new Gson().fromJson(jsonString, CloudVisionResponse.class);
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }


    /**
     * try to arrow down the search results to pinpoint the correct food name
     * using the following pattern:
     * @param responseBean response from google server
     */
    public String findBestResult(CloudVisionResponse responseBean){
        //only one response exists.
        List<Response> responses = responseBean.getResponses();
        if(responses==null||responses.size()<1){
            return null;
        }
        Response rp = responses.get(0);
        //get all the resources available to help us identify the image:
        //get possible labels for this image
        ArrayList<LabelAnnotations> labelList = (ArrayList<LabelAnnotations>) rp.getLabelAnnotations();
        //get possible webEntities related to this image
        ArrayList<WebEntities> webEntities = (ArrayList<WebEntities>) rp.getWebDetection().getWebEntities();
        //get the best guess list of this image
        ArrayList<BestGuessLabels> bestGuessList = (ArrayList<BestGuessLabels>) rp.getWebDetection().getBestGuessLabels();

        //now we must use the 3 lists above to arrow down the search results to one.
        //before we proceed, we shall delete vague categories such as Food, Seafood, Dish, Recipe...because these results are useless.
        String [] noUseCategory={"Food","Seafood","Dish","Recipe","Vegetable","Meat","Cuisine",
                "Fish","Fruit","Produce","Diet","Health","Natural Food","Natural Foods","Local Food","Poultry","Fried Food",
                "Cooking","Leaf Vegetable","Leaf","Root Vegetable","Ingredient"};
        if(labelList!=null&&labelList.size()>0){
            Iterator<LabelAnnotations> it1 = labelList.iterator();
            while (it1.hasNext()){
                LabelAnnotations label = it1.next();
                String description = label.getDescription();
                if(TextUtils.isEmpty(description)){
                    it1.remove();
                    continue;
                }
                for(String s:noUseCategory){
                    if(description.compareToIgnoreCase(s)==0){
                        it1.remove();
                        break;
                    }
                }
            }
        }

        if(webEntities!=null&&webEntities.size()>0){
            Iterator<WebEntities> it2 = webEntities.iterator();
            while (it2.hasNext()){
                WebEntities webEntity = it2.next();
                String description = webEntity.getDescription();
                if(TextUtils.isEmpty(description)){
                    it2.remove();
                    continue;
                }
                for(String s:noUseCategory){
                    if(description.compareToIgnoreCase(s)==0){
                        it2.remove();
                        break;
                    }
                }
            }
        }



        if(bestGuessList!=null&&bestGuessList.size()>0){
            Iterator<BestGuessLabels> it3 = bestGuessList.iterator();
            while (it3.hasNext()){
                BestGuessLabels guess = it3.next();
                String guessLabel = guess.getLabel();
                if(TextUtils.isEmpty(guessLabel)){
                    it3.remove();
                    continue;
                }
                for(String s:noUseCategory){
                    if(guessLabel.compareToIgnoreCase(s)==0){
                        it3.remove();
                        break;
                    }
                }
            }
        }


        //then we can begin the narrow down.
        //first we look to the web entity list, because it tend to be more accurate than the other two
        if(webEntities!=null&&webEntities.size()>0){
            for(WebEntities temp:webEntities){
                if(temp.getScore()>=1){
                    //for those with high score which means it is quite likely to be the correct answer, we adapt the following logic
                    String result = onHighScoreEntity(temp, labelList, bestGuessList);
                    if(!TextUtils.isEmpty(result)){
                        return result;
                    }
                }else if(temp.getScore()>=0.4){
                    //for those with medium score, we adapt the following logic
                    String result=onMediumScoreEntity(temp,labelList,bestGuessList);
                    if(!TextUtils.isEmpty(result)){
                        return result;
                    }
                }
                //for those with score value of less then 0.4, we ignore it.
            }
        }

        //if no result fits the paten, we shall look to label list to try find the answer
        if(labelList!=null&&labelList.size()>0&&webEntities!=null){
            //this is the case where web entities find no confident answers and we have to ignore the web entities
            if(webEntities.size()>0&&webEntities.get(0).getScore()<0.7){
                for(LabelAnnotations label:labelList){
                    //only consider those with high score
                    if(label.getScore()>=0.9){
                        String result=compareLabelWithBestGuess(label,bestGuessList);
                        if(!TextUtils.isEmpty(result)){
                            return result;
                        }
                    }
                }
            }
            //If we still cannot find the result, then we'll use the web entity,even it is a less confident speculation.
            if(webEntities.size()>0){
                return webEntities.get(0).getDescription();
            }
        }
        //I would rather return null than return the best guess, because it sometimes returns very ridiculous answers.
        return null;
    }

    private static String compareLabelWithBestGuess(LabelAnnotations label, ArrayList<BestGuessLabels> bestGuessList) {
        String labelDescription = label.getDescription().toLowerCase();
        if(bestGuessList!=null){
            for(BestGuessLabels bean:bestGuessList){
                String guess = bean.getLabel().toLowerCase();
                if(labelDescription.equals(guess)||labelDescription.contains(guess)||guess.contains(labelDescription)){
                    return labelDescription;
                }
            }
        }
        return null;
    }


    private String onMediumScoreEntity(
            WebEntities entity,
            ArrayList<LabelAnnotations> labelList,
            ArrayList<BestGuessLabels> bestGuessList) {
        return onHighScoreEntity(entity,labelList,bestGuessList);
    }

    /**
     * what shall we do when we find a high score web entity
     * @param entity
     * @param labelList
     * @param bestGuessList
     * @return the name of the food or null if cannot decide.
     */
    private String onHighScoreEntity(WebEntities entity,
                                            ArrayList<LabelAnnotations> labelList,
                                            ArrayList<BestGuessLabels> bestGuessList) {


        String description = entity.getDescription();
        if(TextUtils.isEmpty(description)){
            return null;
        }
        description = description.toLowerCase();
        //we go to the best guess list, if their results are close, then ignore label list, return the entityDescription;
        if(bestGuessList!=null){
            for(BestGuessLabels bean:bestGuessList){
                String guess = bean.getLabel().toLowerCase();
                if(description.equals(guess)||description.contains(guess)||guess.contains(description)){
                    return description;
                }
            }
        }

        //if does not match, we go to the label list to do the same thing;
        if(labelList!=null){
            for(LabelAnnotations bean:labelList){
                String label = bean.getDescription().toLowerCase();
                if(description.equals(label)||description.contains(label)||label.contains(description)){
                    return description;
                }
            }
        }
        return null;
    }


    /**
     * get a list of possible food names with reference to a food image
     * @param responseBean
     * @return
     */
    public ArrayList<String> pickPossibleResults(CloudVisionResponse responseBean) {
        List<Response> responses = responseBean.getResponses();
        if(responses==null||responses.size()<1){
            return null;
        }
        Response response= responses.get(0);
        ArrayList<String> list=new ArrayList<>();
        List<LabelAnnotations> labelAnnotations = response.getLabelAnnotations();
        if(labelAnnotations!=null){
            for(LabelAnnotations lb:labelAnnotations){
                String description = lb.getDescription().toLowerCase();
                if(TextUtils.isEmpty(description)){
                    continue;
                }
                //add those with at least 50% confidence
                if(lb.getScore()>=0.5&&!list.contains(description)){
                    list.add(description);
                }
            }
        }

        List<WebEntities> webEntities = response.getWebDetection().getWebEntities();
        if(webEntities!=null){
            for(WebEntities entities:webEntities){
                String description = entities.getDescription().toLowerCase();
                if(TextUtils.isEmpty(description)){
                    continue;
                }
                if(entities.getScore()>=0.5&&!list.contains(description)){
                    list.add(description);
                }
            }
        }

        List<BestGuessLabels> bestGuessLabels = response.getWebDetection().getBestGuessLabels();
        if(bestGuessLabels!=null){
            for(BestGuessLabels guess:bestGuessLabels){
                String label = guess.getLabel().toLowerCase();
                if(TextUtils.isEmpty(label)){
                    continue;
                }
                if(!list.contains(label)){
                    list.add(label);
                }
            }
        }
        return list;
    }
}
