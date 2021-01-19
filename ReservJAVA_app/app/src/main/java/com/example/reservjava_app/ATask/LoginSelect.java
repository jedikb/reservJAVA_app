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
import java.sql.Date;

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
    int member_code = -1, member_kind = -1;
    String member_id = "", member_name = "", member_nick = "", member_tel = "", member_email = "", member_addr = "", member_image="";
    Date member_date = null;
    //오류나면 여기 일 수 있음

    reader.beginObject();
    while (reader.hasNext()) {
      String readStr = reader.nextName();
      if (readStr.equals("member_code")) {
        member_code = reader.nextInt();
      } else if (readStr.equals("member_id")) {
        member_id = reader.nextString();
      } else if (readStr.equals("member_kind")) {
        member_kind = reader.nextInt();
      } else if (readStr.equals("member_name")) {
        member_name = reader.nextString();
      } else if (readStr.equals("member_nick")) {
        member_nick = reader.nextString();
      } else if (readStr.equals("member_tel")) {
        member_tel = reader.nextString();
      } else if (readStr.equals("member_email")) {
        member_email = reader.nextString();
      } else if (readStr.equals("member_addr")) {
        member_addr = reader.nextString();
      } else if (readStr.equals("member_image")) {
        member_image = reader.nextString();
      } else if (readStr.equals("member_date")) {
        // 이부분은 나중에 다시 하자
        //Log.d(TAG, "readMessage: date1");
        //String d = reader.nextString();
        //Log.d(TAG, "readMessage: date : " + d);
        member_date = Date.valueOf(reader.nextString());
        //Log.d(TAG, "readMessage: date2");
        //member_date = Date.valueOf("2021-01-01");
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();
    Log.d("main:loginselect : ", member_code + ", " + member_id + "," + member_name + "," + member_nick + "," + member_tel + "," + member_email);
    return new MemberDTO(member_code, member_id, member_kind, member_name, member_nick, member_tel, member_email, member_addr, member_image, member_date);
  }
}