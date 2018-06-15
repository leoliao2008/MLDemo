package com.tgi.mldemo.module;

import android.text.TextUtils;
import android.util.Log;

import com.tgi.mldemo.bean.CloudVisionResponse;
import com.tgi.mldemo.bean.CloudVisionResponse.Response;
import com.tgi.mldemo.bean.CloudVisionResponse.Response.LabelAnnotations;
import com.tgi.mldemo.bean.CloudVisionResponse.Response.WebDetection.BestGuessLabels;
import com.tgi.mldemo.bean.CloudVisionResponse.Response.WebDetection.WebEntities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResultHelper {
    /**
     * try to arrow down the search results to pinpoint the correct food name
     * using the following pattern:
     * @param responseBean response from google server
     */
    public static String findBestResult(CloudVisionResponse responseBean){
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

        //then we can begin the narrow down.
        //first we look to the web entity list, because it tend to be more accurate than the other two
        if(webEntities.size()>0){
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
        if(labelList.size()>0){
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
        for(BestGuessLabels bean:bestGuessList){
            String guess = bean.getLabel().toLowerCase();
            if(labelDescription.equals(guess)||labelDescription.contains(guess)||guess.contains(labelDescription)){
                return labelDescription;
            }
        }
        return null;
    }


    private static String onMediumScoreEntity(
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
    private static String onHighScoreEntity(WebEntities entity,
                                            ArrayList<LabelAnnotations> labelList,
                                            ArrayList<BestGuessLabels> bestGuessList) {


        String description = entity.getDescription();
        if(TextUtils.isEmpty(description)){
            return null;
        }
        description = description.toLowerCase();
        //we go to the best guess list, if their results are close, then ignore label list, return the entityDescription;
        for(BestGuessLabels bean:bestGuessList){
            String guess = bean.getLabel().toLowerCase();
            if(description.equals(guess)||description.contains(guess)||guess.contains(description)){
                return description;
            }
        }
        //if does not match, we go to the label list to do the same thing;
        for(LabelAnnotations bean:labelList){
            String label = bean.getDescription().toLowerCase();
            if(description.equals(label)||description.contains(label)||label.contains(description)){
                return description;
            }
        }

        return null;
    }

    private static void showLog(String msg){
        Log.e("ResultHelper",msg);
    }

    /**
     * get a list of possible food names with reference to a food image
     * @param responseBean
     * @return
     */
    public static ArrayList<String> pickPossibleResults(CloudVisionResponse responseBean) {
        List<Response> responses = responseBean.getResponses();
        if(responses==null||responses.size()<1){
            return null;
        }
        Response response= responses.get(0);
        ArrayList<String> list=new ArrayList<>();
        List<LabelAnnotations> labelAnnotations = response.getLabelAnnotations();
        for(LabelAnnotations lb:labelAnnotations){
            String description = lb.getDescription();
            if(TextUtils.isEmpty(description)){
                continue;
            }
            //add those with at least 50% confidence
            if(lb.getScore()>=0.5){
                list.add(description);
            }
        }
        List<WebEntities> webEntities = response.getWebDetection().getWebEntities();
        for(WebEntities entities:webEntities){
            String description = entities.getDescription();
            if(TextUtils.isEmpty(description)){
                continue;
            }
            if(entities.getScore()>=0.5){
                list.add(description);
            }
        }
        List<BestGuessLabels> bestGuessLabels = response.getWebDetection().getBestGuessLabels();
        for(BestGuessLabels guess:bestGuessLabels){
            String label = guess.getLabel();
            if(TextUtils.isEmpty(label)){
                continue;
            }
            list.add(label);
        }
        return list;
    }
}
