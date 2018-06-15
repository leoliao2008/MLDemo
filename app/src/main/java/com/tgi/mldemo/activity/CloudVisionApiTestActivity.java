package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tgi.mldemo.R;
import com.tgi.mldemo.data.Static;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class CloudVisionApiTestActivity extends AppCompatActivity {
    private TextView mTvResult;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            URL url = null;
            try {
                url = new URL(Static.GOOGLE_CLOUD_REQUEST_PREFIX_WITH_API_KEY);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                handleException(e);
                return;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                handleException(e);
                return;
            }
            try {
                connection.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
                handleException(e);
                return;
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                handleException(e);
                return;
            }
            try {
                bw.write("{\"requests\":  [{ \"features\":  [ {\"type\": \"WEB_DETECTION\""
                        + "}], \"image\": {\"source\": { \"gcsImageUri\":"
                        + " \"gs://vision-sample-images/4_Kittens.jpg\"}}}]}");
            } catch (IOException e) {
                e.printStackTrace();
                handleException(e);
                return;
            }
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
                handleException(e);
                return;
            }
            String responseMessage = null;
            try {
                responseMessage = connection.getResponseMessage();
            } catch (IOException e) {
                e.printStackTrace();
                handleException(e);
                return;
            }
            final String finalResponseMessage = responseMessage;
            final StringBuffer sb = new StringBuffer();
            sb.append(finalResponseMessage+"\n");
            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                handleException(e);
                return;
            }
            if (inputStream == null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvResult.setText("Connection Fails.");
                    }
                });
                return;
            }
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvResult.setText(sb.toString());
                }
            });
            scanner.close();
        }
    };




    public static void start(Context context) {
        Intent starter = new Intent(context, CloudVisionApiTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_vison_api_test);
        mTvResult=findViewById(R.id.tv_result);
    }

    public void sendRequest(View view) {
        new Thread(mRunnable).start();
    }

    private void handleException(final Exception e){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg=e.getMessage();
                if(TextUtils.isEmpty(msg)){
                    msg="Unknown Error";
                    e.printStackTrace();
                }
                mTvResult.setText(msg);
            }
        });
    }
}
