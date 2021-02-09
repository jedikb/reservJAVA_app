package com.example.reservjava_app.ATask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.adapter.MyVisitAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.pServer;
import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class MyVisit extends AsyncTask<Void, Void, Void> {

  private static final String TAG = "main:MyVisit";

  //검색한 리스트를 담을 변수
  //ReviewDTO와 거의 비슷한 자료를 불러오기 때문에 같은 것을 쓴다
  ArrayList<ReviewDTO> visitDTOS;
  ProgressDialog progressDialog;
  MyVisitAdapter adapter;

  public MyVisit(ArrayList<ReviewDTO> visitDTOS, ProgressDialog progressDialog, MyVisitAdapter adapter) {
    this.visitDTOS = visitDTOS;
    this.adapter = adapter;
    this.progressDialog = progressDialog;
  }

  HttpClient httpClient;
  HttpPost httpPost;
  HttpResponse httpResponse;
  HttpEntity httpEntity;

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected Void doInBackground(Void... voids) {

    int member_code = loginDTO.getMember_code();

    try {
      // MultipartEntityBuild 생성
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.setCharset(Charset.forName("UTF-8"));

      // 문자열 및 데이터 추가
      builder.addTextBody("member_code", String.valueOf(member_code), ContentType.create("Multipart/related", "UTF-8"));
      String postURL = ipConfig + pServer + "/anMyReview";

      // 전송
      InputStream inputStream = null;
      httpClient = AndroidHttpClient.newInstance("Android");
      httpPost = new HttpPost(postURL);
      httpPost.setEntity(builder.build());
      httpResponse = httpClient.execute(httpPost);
      httpEntity = httpResponse.getEntity();
      inputStream = httpEntity.getContent();

      readJsonStream(inputStream);
    } catch (Exception e) {
      Log.d(TAG, "MyVisit: "+ e.getMessage());
      e.printStackTrace();
    } finally {
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
    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);

/*    if(progressDialog != null){
      progressDialog.dismiss();
    }*/

    Log.d("MyVisit", "MyVisit Select Complete!!!");

    adapter.notifyDataSetChanged();
  }

  public void readJsonStream(InputStream inputStream) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
    try {
      reader.beginArray();
      while (reader.hasNext()) {
        visitDTOS.add(readMessage(reader));
      }
      reader.endArray();
    } finally {
      reader.close();
    }
  }

  public ReviewDTO readMessage(JsonReader reader) throws IOException {
    int booking_code = 0, booking_kind = 0, booking_member_code= 0, booking_business_code= 0, booking_product_code= 0;
    String booking_date_reservation = null;
    float booking_appraisal_star = 0;
    String booking_appraisal = null, business_name = null, business_addr = null;
    int business_category_code = 0;

    reader.beginObject();
    while(reader.hasNext()) {
      String readStr = reader.nextName();
      if(readStr.equals("booking_code")) {
        booking_code = reader.nextInt();
      } else if(readStr.equals("booking_kind")) {
        booking_kind = reader.nextInt();
      } else if(readStr.equals("booking_member_code")) {
        booking_member_code = reader.nextInt();
      } else if(readStr.equals("booking_business_code")) {
        booking_business_code = reader.nextInt();
      } else if(readStr.equals("booking_product_code")) {
        booking_product_code = reader.nextInt();
      } else if(readStr.equals("booking_appraisal_star")) {
        booking_appraisal_star = (reader.nextInt()/20);
      } else if(readStr.equals("booking_appraisal")) {
        booking_appraisal = reader.nextString();
      } else if(readStr.equals("business_name")) {
        business_name = reader.nextString();
      } else if(readStr.equals("business_category_code")) {
        business_category_code = reader.nextInt();
      } else if(readStr.equals("booking_date_reservation")) {
        booking_date_reservation = reader.nextString();
      } else if(readStr.equals("business_addr")) {
        business_addr = reader.nextString();
      } else {
        reader.skipValue();
      }
    }

    reader.endObject();
    return new ReviewDTO(booking_code, booking_kind, booking_member_code, booking_business_code,
        booking_product_code, booking_date_reservation, booking_appraisal_star, booking_appraisal
        , business_name, business_category_code, business_addr);
  }
}