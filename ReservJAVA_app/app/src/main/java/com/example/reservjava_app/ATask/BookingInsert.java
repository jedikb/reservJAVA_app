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

public class BookingInsert extends AsyncTask<Void, Void, String> {

private static final String TAG = "main:MemberUpdate";

        HttpClient httpClient;
        HttpPost httpPost;
        HttpResponse httpResponse;
        HttpEntity httpEntity;

        String state = "";

        // 사진 정보 추가
        int member_code, business_code, product_code, product_price, price_deposit, booking_num;
        String booking_date_reservation;

        public BookingInsert(int member_code, int business_code, int product_code, int product_price, int price_deposit, int booking_num, String booking_date_reservation) {
                this.member_code = member_code;
                this.business_code = business_code;
                this.product_code = product_code;
                this.product_price = product_price;
                this.price_deposit = price_deposit;
                this.booking_num = booking_num;
                this.booking_date_reservation = booking_date_reservation;
        }

        @Override
protected void onPreExecute() {
        super.onPreExecute();
        }

@Override
protected String doInBackground(Void... voids) {

        try {
        // MultipartEntityBuild 생성
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Charset.forName("UTF-8"));

        // 문자열 및 데이터 추가
        builder.addTextBody("member_code", String.valueOf(member_code), ContentType.create("Multipart/related", "UTF-8"));
        builder.addTextBody("business_code", String.valueOf(business_code), ContentType.create("Multipart/related", "UTF-8"));
        builder.addTextBody("product_code", String.valueOf(product_code), ContentType.create("Multipart/related", "UTF-8"));
        builder.addTextBody("product_price", String.valueOf(product_price), ContentType.create("Multipart/related", "UTF-8"));
        builder.addTextBody("price_deposit", String.valueOf(price_deposit), ContentType.create("Multipart/related", "UTF-8"));
        builder.addTextBody("booking_num", String.valueOf(booking_num), ContentType.create("Multipart/related", "UTF-8"));
        builder.addTextBody("booking_date_reservation", booking_date_reservation, ContentType.create("Multipart/related", "UTF-8"));

        String postURL = ipConfig + pServer + "/anBookingInsert";



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

        state = stringBuilder.toString();

        inputStream.close();

        // 응답결과
        Log.d("response", state);

        } catch (Exception e) {
        Log.d("main:bookinginsert", e.getMessage());
        e.printStackTrace();
        }finally {
        if(httpEntity != null){
        httpEntity = null;
        }
        if(httpResponse != null){
        httpResponse = null;
        }
        if(httpPost != null){
        httpPost = null;
        }
        if(httpClient != null){
        httpClient = null;
        }
        }
        return state;
        }

@Override
protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        //dialog.dismiss();
        }


}
