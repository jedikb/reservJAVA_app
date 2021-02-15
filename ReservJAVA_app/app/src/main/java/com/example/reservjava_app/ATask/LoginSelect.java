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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.pServer;

public class LoginSelect extends AsyncTask<Void, Void, MemberDTO> {
  private static final String TAG = "main:LoginSelect";
  String member_id, member_pw;
  MemberDTO loginData = new MemberDTO();

  HttpClient httpClient;
  HttpPost httpPost;
  HttpResponse httpResponse;
  HttpEntity httpEntity;

  public LoginSelect(String member_id, String member_pw) {
    this.member_id = member_id;
    this.member_pw = member_pw;
  }


  @Override
  protected MemberDTO doInBackground(Void... voids) {

    try {
      // MultipartEntityBuild 생성
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.setCharset(Charset.forName("UTF-8"));

      // 문자열 및 데이터 추가
      builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_pw", member_pw, ContentType.create("Multipart/related", "UTF-8"));

      Log.d(TAG, "doInBackground: "+ member_id + ", " + member_pw);

      //접속 경로
      String postURL = ipConfig + pServer + "/anMemberLogin";

      //Log.d(TAG, "doInBackground: "+ipConfig);
      // 전송
      InputStream inputStream = null;
      httpClient = AndroidHttpClient.newInstance("Android");
      httpPost = new HttpPost(postURL);
      httpPost.setEntity(builder.build());
      httpResponse = httpClient.execute(httpPost);
      httpEntity = httpResponse.getEntity();
      inputStream = httpEntity.getContent();

      // 하나의 오브젝트 가져올때
      loginData = readMessage(inputStream);

      inputStream.close();

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
    return loginData;
  }

  @Override
  protected void onPostExecute(MemberDTO aVoid) {

  }

  public MemberDTO readMessage(InputStream inputStream) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
    int member_code = -1, member_kind = -1;
    String member_id = "", member_name = "", member_nick = "", member_tel = "", member_email = "", member_addr = "", member_image="";
    Date member_date = null;
    //String member_date = "";
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
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
          member_date = fm.parse(reader.nextString());
        } catch (ParseException e) {
          e.printStackTrace();
        }
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();
    Log.d("main:loginselect : ", member_id + "," + member_name + "," + member_addr + "," + member_email);
    return new MemberDTO(member_code, member_id, member_kind, member_name, member_nick, member_tel, member_email, member_addr, member_image, member_date);
  }
}