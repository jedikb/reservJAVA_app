package com.example.reservjava_app.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.reservjava_app.DTO.MemberDTO;

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

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class LoginSelect extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "main:LoginSelect";
  String member_id, member_pw;

  HttpClient httpClient;
  HttpPost httpPost;
  HttpResponse httpResponse;
  HttpEntity httpEntity;

  public LoginSelect(String member_id, String member_pw) {
    this.member_id = member_id;
    this.member_pw = member_pw;
  }


    /*@Override  // 없어도 됨
    protected void onPreExecute() {

    }*/

  @Override
  protected Void doInBackground(Void... voids) {

    try {
      // MultipartEntityBuild 생성
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.setCharset(Charset.forName("UTF-8"));

      // 문자열 및 데이터 추가
      builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_pw", member_pw, ContentType.create("Multipart/related", "UTF-8"));

      Log.d(TAG, "doInBackground: "+ member_id + ", " + member_pw);
      String postURL = ipConfig + "/reservjava_app/anLogin";

      //Log.d(TAG, "doInBackground: "+ipConfig);
      // 전송
      InputStream inputStream = null;
      httpClient = AndroidHttpClient.newInstance("Android");
      httpPost = new HttpPost(postURL);
      httpPost.setEntity(builder.build());
      httpResponse = httpClient.execute(httpPost);
      httpEntity = httpResponse.getEntity();
      inputStream = httpEntity.getContent();

      Log.d(TAG, "doInBackground: 1");
      // 하나의 오브젝트 가져올때
      loginDTO = readMessage(inputStream);

      Log.d(TAG, "doInBackground: 2" + loginDTO.getMember_id());
      inputStream.close();
      Log.d(TAG, "doInBackground: 3");

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

  }

  public MemberDTO readMessage(InputStream inputStream) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
    Log.d(TAG, "readMessage: 12");
    String member_id = "", member_name = "", member_nick = "", member_tel = "";

    reader.beginObject();
    while (reader.hasNext()) {
      String readStr = reader.nextName();
      if (readStr.equals("member_id")) {
        member_id = reader.nextString();
        Log.d(TAG, "readMessage: " + member_id);
      } else if (readStr.equals("member_name")) {
        member_name = reader.nextString();
      } else if (readStr.equals("member_nick")) {
        member_nick = reader.nextString();
      } else if (readStr.equals("member_tel")) {
        member_tel = reader.nextString();
      }else {
        reader.skipValue();
      }
    }
    reader.endObject();
    Log.d("main:loginselect : ", member_id + "," + member_name + "," + member_nick + "," + member_tel);
    return new MemberDTO(member_id, member_name, member_nick, member_tel);
  }
}