package com.example.reservjava_app.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.pServer;

public class Payment extends AsyncTask<Void, Void, String> {
    private static final String TAG = "main:MemberCancel.java";

    //String member_code;
    String booking_code;

    public Payment(String booking_code) {

        //this.member_code = member_code;
        this.booking_code = booking_code;
    }

    // WEB(DB) Query 결과 0 보다 크면 성공, 같거나 작으면 실패
    String       state = "";

    HttpClient   httpClient     = null;
    HttpPost     httpPost       = null;
    HttpResponse httpResponse   = null;
    HttpEntity   httpEntity     = null;

    @Override
    protected String doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            String postURL = "";
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            //builder.addTextBody("member_code", member_code, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("booking_code", booking_code, ContentType.create("Multipart/related", "UTF-8"));

            Log.d(TAG, "doInBackground: booking_code: "+ booking_code + " 결재 처리 시작.");


            //스프링서버 응답 URL
            postURL = ipConfig + pServer + "/anPayment";
            Log.d(TAG, "doInBackground: " + postURL);

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            inputStream.close();

            //응답결과
            state = stringBuilder.toString().trim();
            Log.d(TAG, "doInBackground: state= " + state);

        } catch (Exception e) {
            Log.d(TAG, "doInBackground: " + e.getMessage() );
            e.printStackTrace();
        }finally {
            if(httpEntity   != null) httpEntity     = null;
            if(httpResponse != null) httpResponse   = null;
            if(httpPost     != null) httpPost       = null;
            if(httpClient   != null) httpClient     = null;
        }

        Log.d(TAG, "doInBackground.return: state= " + state);
        return state;
    }

}
