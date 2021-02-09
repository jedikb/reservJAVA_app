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
import org.apache.http.entity.mime.content.FileBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.pServer;

public class MemberUpdate extends AsyncTask<Void, Void, Void> {

  private static final String TAG = "main:MemberUpdate";

  HttpClient httpClient;
  HttpPost httpPost;
  HttpResponse httpResponse;
  HttpEntity httpEntity;

  // 사진 정보 추가
  String member_id, member_pw, member_nick, member_tel, member_email, member_image, pImgDbPathU, imageDbPathU, imageRealPathU;

  public MemberUpdate(String member_id, String member_pw, String member_nick, String member_tel, String member_email, String member_image, String pImgDbPathU, String imageDbPathU, String imageRealPathU) {
    this.member_id = member_id;
    this.member_pw = member_pw;
    this.member_nick = member_nick;
    this.member_tel = member_tel;
    this.member_email = member_email;
    this.member_image = member_image;
    this.pImgDbPathU = pImgDbPathU;
    this.imageDbPathU = imageDbPathU;
    this.imageRealPathU = imageRealPathU;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected Void doInBackground(Void... voids) {

    try {
      // MultipartEntityBuild 생성
      String postURL = "";
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.setCharset(Charset.forName("UTF-8"));

      // 문자열 및 데이터 추가
      builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_pw", member_pw, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_nick", member_nick, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_tel", member_tel, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_email", member_email, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_image", member_image, ContentType.create("Multipart/related", "UTF-8"));

      // 이미지를 새로 선택했으면 선택한 이미지와 기존에 이미지 경로를 같이 보낸다
      if(!imageRealPathU.equals("")){
        Log.d("memberUpdate:postURL", "1");
        // 기존에 있던 DB 경로
        builder.addTextBody("pDbImgPath", pImgDbPathU, ContentType.create("Multipart/related", "UTF-8"));
        // DB에 저장할 경로
        builder.addTextBody("dbImgPath", imageDbPathU, ContentType.create("Multipart/related", "UTF-8"));
        // 실제 이미지 파일
        builder.addPart("image", new FileBody(new File(imageRealPathU)));

        postURL = ipConfig + pServer + "/anMemberUpdate";

      }else if(imageRealPathU.equals("")){  // 이미지를 바꾸지 않았다면
        Log.d("memberUpdate:postURL", "3");
        postURL = ipConfig + pServer + "/anMemberUpdateNoimg";
      }else{
        Log.d("memberUpdate:postURL", "5 : error");
      }
      Log.d("memberUpdate:postURL", postURL);


      Log.d(TAG, "doInBackground: " + member_email);
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

      // 응답결과
      String result = stringBuilder.toString();
      Log.d("response", result);

    } catch (Exception e) {
      Log.d("main:loginselect", e.getMessage());
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
    //dialog.dismiss();
  }
}
