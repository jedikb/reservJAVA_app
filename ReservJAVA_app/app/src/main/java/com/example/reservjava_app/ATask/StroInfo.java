package com.example.reservjava_app.ATask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.adapter.MyReviewAdapter;

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
import java.util.HashMap;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.loginDTO;
import static com.example.reservjava_app.Common.CommonMethod.pServer;

public class StroInfo extends AsyncTask<Void, Void, Void> {

  private static final String TAG = "main:MyReview";

  //검색한 리스트를 담을 변수
  private HashMap<String,String> info_Store;
  private String booking_code ;
  ProgressDialog progressDialog;

  public StroInfo(HashMap<String,String> info_Store , String booking_code) {
    this.info_Store = info_Store;
    this.booking_code = booking_code;
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
      builder.addTextBody("booking_code", booking_code, ContentType.create("Multipart/related", "UTF-8"));
      String postURL = ipConfig + pServer + "/MyStroInfo";

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
      Log.d(TAG, "MyReview: "+ e.getMessage());
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

    if(progressDialog != null){
      progressDialog.dismiss();
    }
    Log.d("MyReview", "MyReview Select Complete!!!");


  }

  public void readJsonStream(InputStream inputStream) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
    try {
      reader.beginObject();
      while(reader.hasNext()) {
        info_Store.put(reader.nextName(),reader.nextString());
      }
      reader.endObject();
    } finally {
      reader.close();
    }
  }


}
