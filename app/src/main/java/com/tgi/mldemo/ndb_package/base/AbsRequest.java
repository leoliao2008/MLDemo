package com.tgi.mldemo.ndb_package.base;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public abstract class AbsRequest<RESPONSE> {
    private String mStrRq;
    private Handler mHandler;
    private AbsRequestCallback<RESPONSE> mCallback;

    public AbsRequest(String strRq) {
        mStrRq = strRq;
        mHandler=new Handler(Looper.getMainLooper());
    }

    protected void postRequest(AbsRequestCallback<RESPONSE> callback){
        mCallback=callback;
        new Task().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private class Task extends AsyncTask<Void,Void,RESPONSE>{
        @Override
        protected void onPreExecute() {
            mCallback.onPreExecute();
            super.onPreExecute();
        }

        @Override
        protected RESPONSE doInBackground(Void... voids) {
            HttpURLConnection connection=null;
            BufferedInputStream in=null;
            try {
                URL url=new URL(mStrRq);
                connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                in=new BufferedInputStream(connection.getInputStream());
                Scanner scanner=new Scanner(in);
                StringBuilder sb=new StringBuilder();
                while (scanner.hasNextLine()){
                    sb.append(scanner.nextLine());
                }
                return genResponse(sb.toString());
            } catch (Exception e) {
                handleException(e);
            } finally {
                if(connection!=null){
                    connection.disconnect();
                    connection=null;
                }
                if(in!=null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        in=null;
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(RESPONSE responses) {
            super.onPostExecute(responses);
            if(responses!=null){
                mCallback.onPostExecute(responses);
            }
        }
    }

    protected abstract RESPONSE genResponse(String json);

    private void handleException(final Exception e){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError(e);
            }
        });
    }


}
