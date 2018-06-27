package com.tgi.mldemo.presenters;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.tgi.mldemo.R;
import com.tgi.mldemo.activity.ModulesComparisonActivity;
import com.tgi.mldemo.adapter.SearchResultAdapter;
import com.tgi.mldemo.bean.CloudVisionResponse;
import com.tgi.mldemo.callback.JavaHttpUrlRequestCallBack;
import com.tgi.mldemo.callback.VisionRequestModuleCallBack;
import com.tgi.mldemo.data.Static;
import com.tgi.mldemo.module.CloudAPIModule;
import com.tgi.mldemo.module.CloudAPITutorialModule;
import com.tgi.mldemo.module.CloudVisionModule;

import java.util.ArrayList;
import java.util.List;

public class ModulesCmpActivityPresenter {
    private ModulesComparisonActivity mActivity;
    private ListView mConsole;
    private ArrayList<String> mLogs =new ArrayList<>();
    private ArrayAdapter<String> mConsoleAdapter;
    private Handler mHandler;
    private RecyclerView mRcyResults;
    private LinearLayout.LayoutParams mResultsLayoutParams;
    private ArrayList<String> mResult =new ArrayList<>();
    private SearchResultAdapter mSearchResultAdapter;
    private CloudVisionModule mHttpUrlRequestModule;
    private CloudAPITutorialModule mTutorialModule;
    private CloudAPIModule mCloudAPIModule;
    private int mSearchEngine=-1;
    private static final int USE_GG_TUTORIAL_API=231;
    private static final int USE_JAVA_GENERIC_API=564;
    private static final int USE_GG_OFFICIAL_API=897;


    //public:
    public ModulesCmpActivityPresenter(ModulesComparisonActivity activity) {
        mActivity = activity;
        mHandler=new Handler(mActivity.getMainLooper());
    }

    public void init() {
        initLogConsole();
        initPickResultWindow();
        initRadioGroup();
        initModules();
    }

    public void identifyImage(Bitmap bitmap) {
        switch (mSearchEngine){
            case USE_GG_OFFICIAL_API:
//                mCloudAPIModule.identifyByBitmap(bitmap);
                break;
            case USE_GG_TUTORIAL_API:
                mTutorialModule.identifyBitmap(bitmap);
                break;
            case USE_JAVA_GENERIC_API:
                mHttpUrlRequestModule.identifyBitmap(bitmap);
                break;
        }
    }






    //private:
    private void initModules() {
        initMyGenericModule();
        initGGTutorialModule();
//        initGGOfficialApiModule();
    }

//    private void initGGOfficialApiModule() {
//        mCloudAPIModule=new CloudAPIModule(
//                mActivity,
//                new CloudAPIModuleCallback(){
//                    long mStart;
//                    long mEnd;
//                    @Override
//                    public void onPreExecute() {
//                        super.onPreExecute();
//                        mStart=System.currentTimeMillis();
//                        updateConsole("Searching with GG Official Api, please wait...");
//                    }
//
//                    @Override
//                    public void onPostExecute(CloudVisionResponseBeanLite response) {
//                        super.onPostExecute(response);
//                        mEnd=System.currentTimeMillis();
//                        long elapse=mEnd-mStart;
//                        WebDetection webDetection = response.getWebDetection();
//                        List<WebDetection.WebEntity> list = webDetection.getWebEntitiesList();
//                        ArrayList<String> results=new ArrayList<>();
//                        for(WebDetection.WebEntity temp:list){
//                            StringBuilder sb=new StringBuilder();
//                            sb.append("Desc:").append(temp.getDescription()).append('\n');
//                            sb.append("Entity ID:").append(temp.getEntityId()).append('\n');
//                            sb.append("Score:").append(temp.getScore());
//                            results.add(sb.toString());
//                        }
//                        updateResults(results);
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        super.onError(e);
//                        updateConsole("Searching with GG Official Api fails for exception:"+e.getMessage());
//                        handleException(e);
//                    }
//                }
//        );
//
//    }

    private void initGGTutorialModule() {
        mTutorialModule=new CloudAPITutorialModule(
                mActivity,
                new VisionRequestModuleCallBack(){
                    private long mBegin;
                    private long mEnd;

                    @Override
                    public void onPreExecute() {
                        super.onPreExecute();
                        mBegin=System.currentTimeMillis();
                        updateConsole("Searching by Google Tutorial API,please wait... ");
                    }

                    @Override
                    public void onGetResults(ArrayList<String> results) {
                        super.onGetResults(results);
                        mEnd=System.currentTimeMillis();
                        long elapse=mEnd-mBegin;
                        updateConsole("Searching by Google Tutorial API cost "+elapse+" millis.");
                        updateResults(results);
                    }

                    @Override
                    public void onNoResult() {
                        super.onNoResult();
                        updateConsole("Searching by Google Tutorial API with 0 result.");
                    }

                    @Override
                    public void onError(Exception e) {
                        super.onError(e);
                        handleException(e);
                        updateConsole("Searching by Google Tutorial API fails:"+e.getMessage());
                    }
                }
        );
    }

    private void initMyGenericModule() {
        mHttpUrlRequestModule=new CloudVisionModule(
                mActivity,
                new JavaHttpUrlRequestCallBack(){
                    private long mBegin;
                    private long mEnd;
                    @Override
                    public void onPreExecute() {
                        super.onPreExecute();
                        updateConsole("Searching by generic java api.Please Wait...");
                        mBegin=System.currentTimeMillis();
                    }

                    @Override
                    public void onPostExecute(CloudVisionResponse response) {
                        super.onPostExecute(response);
                        mEnd=System.currentTimeMillis();
                        long elapse = mEnd- mBegin;
                        updateConsole("Searching by generic java api cost "+elapse+" milliseconds.");
                        List<CloudVisionResponse.Response> list = response.getResponses();
                        if(list.size()<1){
                            updateConsole("FoodSearchResponses list is empty.");
                            return;
                        }
                        CloudVisionResponse.Response bean = list.get(0);
                        List<CloudVisionResponse.Response.WebDetection.WebEntities> entities = bean.getWebDetection().getWebEntities();
                        ArrayList<String> results=new ArrayList<>();
                        for(CloudVisionResponse.Response.WebDetection.WebEntities temp:entities){
                            StringBuilder sb=new StringBuilder();
                            sb.append("Desc:").append(temp.getDescription()).append('\n');
                            sb.append("Entity ID:").append(temp.getEntityId()).append('\n');
                            sb.append("Score:").append(temp.getScore()).append('\n');
                            results.add(sb.toString());
                        }
                        updateResults(results);
                    }

                    @Override
                    public void onError(Exception e) {
                        super.onError(e);
                        handleException(e);
                    }
                },
                Static.GOOGLE_CLOUD_API_KEY
        );
    }


    private void initRadioGroup() {
        mActivity.getGrpModules().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.activity_md_comp_rdbtn_gg_tutorial:
                        mSearchEngine=USE_GG_TUTORIAL_API;
                        break;
                    case R.id.activity_md_comp_rdbtn_generic_api:
                        mSearchEngine=USE_JAVA_GENERIC_API;
                        break;
                    case R.id.activity_md_comp_rdbtn_gg_official_api:
                        mSearchEngine=USE_GG_OFFICIAL_API;
                        break;
                }
            }
        });

    }

    private void initPickResultWindow() {
        mRcyResults = mActivity.getRcyResults();
        mResultsLayoutParams = (LinearLayout.LayoutParams) mRcyResults.getLayoutParams();
        LinearLayoutManager manager=new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false);
        mSearchResultAdapter = new SearchResultAdapter(mResult,mActivity);
        mRcyResults.setLayoutManager(manager);
        mRcyResults.setAdapter(mSearchResultAdapter);
    }

    private void updateResults(ArrayList<String>newResults){
        mResult.clear();
        mResult.addAll(newResults);
        mSearchResultAdapter.notifyDataSetChanged();
    }

    private void initLogConsole() {
        mConsole = mActivity.getLstvConsole();
        mConsoleAdapter = new ArrayAdapter<String>(
                mActivity,
                android.R.layout.simple_list_item_1,
                mLogs
        );
        mConsole.setAdapter(mConsoleAdapter);
    }

    private void updateConsole(final String log){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mLogs.size()>20){
                    mLogs.remove(0);
                }
                mLogs.add(log);
                mConsoleAdapter.notifyDataSetChanged();
                mConsole.smoothScrollToPosition(Integer.MAX_VALUE);
            }
        });
    }

    private void handleException(Exception e){
        String message = e.getMessage();
        if(TextUtils.isEmpty(message)){
            message="Error type: Unknown.";
        }
        updateConsole(message);
        log(message);
    }

    private void log(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }

}
