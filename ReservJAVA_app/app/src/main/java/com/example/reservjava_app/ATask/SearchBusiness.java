package com.example.reservjava_app.ATask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.adapter.SearchBusinessAdapter;

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

public class SearchBusiness extends AsyncTask<Void, Void, Void> {

  private static final String TAG = "main:SearchBusiness";

  //검색한 리스트를 담을 변수
  ArrayList<BusinessDTO> busiList;
  SearchBusinessAdapter Adapter;
  ProgressDialog progressDialog;
  String searchText;

  public SearchBusiness(ArrayList<BusinessDTO> busiList, String searchText, ProgressDialog progressDialog, SearchBusinessAdapter adapter) {
    this.busiList = busiList;
    this.searchText = searchText;
    this.progressDialog = progressDialog;
    this.Adapter = adapter;
  }

  public SearchBusiness(String searchText) {
    this.searchText = searchText;
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

    //businessList.clear();

    try {
      // MultipartEntityBuild 생성
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.setCharset(Charset.forName("UTF-8"));

      // 문자열 및 데이터 추가
      builder.addTextBody("searchText", searchText, ContentType.create("Multipart/related", "UTF-8"));
      Log.d(TAG, "doInBackground: searchText    " + searchText);
      String postURL = ipConfig + pServer + "/anSearchBusiness";

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
      Log.d("SearchBusiness", e.getMessage());
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
    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);

    if(progressDialog != null){
      progressDialog.dismiss();
    }

    Log.d("SearchBusiness", "SearchBusiness Select Complete!!!");

    //여기부터 다시 작업하자
    //searchBusinessAdapter.notifyDataSetChanged();
  }

  public void readJsonStream(InputStream inputStream) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
    try {
      reader.beginArray();
      while (reader.hasNext()) {
        busiList.add(readMessage(reader));
      }
      reader.endArray();
    } finally {
      reader.close();
    }
  }

  public BusinessDTO readMessage(JsonReader reader) throws IOException {
    int business_code = 0;
    String business_name = "";
    int business_member_code= 0, business_category_parent_code =0, business_category_code =0;
    String business_addr= "", business_tel="", business_image = "", business_info="", business_hashtag="";
    double business_star_avg = 0;

    reader.beginObject();
    while (reader.hasNext()){
      String readStr = reader.nextName();
      if(readStr.equals("business_code")) {
        business_code = reader.nextInt();
      } else if(readStr.equals("business_name")) {
        business_name = reader.nextString();
      } else if(readStr.equals("business_member_code")) {
        business_member_code = reader.nextInt();
      } else if(readStr.equals("business_category_parent_code")) {
        business_category_parent_code = reader.nextInt();
      } else if(readStr.equals("business_category_code")) {
        business_category_code = reader.nextInt();
      } else if(readStr.equals("business_addr")) {
        business_addr = reader.nextString();
      } else if(readStr.equals("business_tel")) {
        business_tel = reader.nextString();
      } else if(readStr.equals("business_image")) {
        business_image = reader.nextString();
      } else if(readStr.equals("business_info")) {
        business_info = reader.nextString();
      } else if(readStr.equals("business_star_avg")) {
        business_star_avg = reader.nextDouble();
      } else if(readStr.equals("business_hashtag")) {
        business_hashtag = reader.nextString();
      } else {
        reader.skipValue();
      }
      // 추가로 리뷰 개수를 불러와야 하는데
      // booking에서 count Booking_appraisal where Business_code 로 검색하면 될 듯
    }
      reader.endObject();
      //Log.d(TAG, "SearchBusiness:" + business_name + ", " + business_image +", " + business_star_avg);
      return new BusinessDTO(business_code, business_name, business_member_code, business_category_parent_code, business_category_code,
          business_addr, business_tel, business_image, business_info, business_star_avg, business_hashtag);
  }

}