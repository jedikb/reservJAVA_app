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
import static com.example.reservjava_app.Common.CommonMethod.pServer;
import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class ReviewSelect extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "main:ReviewSelect";

  String BOOKING_APPRAISAL_STAR, BOOKING_APPRAISAL;

  // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패

  HttpClient httpClient;
  HttpPost httpPost;
  HttpResponse httpResponse;
  HttpEntity httpEntity;

  public ReviewSelect(String BOOKING_APPRAISAL_STAR, String BOOKING_APPRAISAL) {
    this.BOOKING_APPRAISAL_STAR = BOOKING_APPRAISAL_STAR;
    this.BOOKING_APPRAISAL = BOOKING_APPRAISAL;
  }


  @Override
  protected Void doInBackground(Void... voids) {

    try {
      // MultipartEntityBuild 생성
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.setCharset(Charset.forName("UTF-8"));

      // 문자열 및 데이터 추가
      builder.addTextBody("BOOKING_APPRAISAL_STAR", BOOKING_APPRAISAL_STAR, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("BOOKING_APPRAISAL", BOOKING_APPRAISAL, ContentType.create("Multipart/related", "UTF-8"));

      Log.d(TAG, "doInBackground: "+ BOOKING_APPRAISAL_STAR + ", " + BOOKING_APPRAISAL);

      //접속 경로
      String postURL = ipConfig + pServer + "/anMemberReview";

      //Log.d(TAG, "doInBackground: "+ipConfig);
      // 전송
      InputStream inputStream = null;
      httpClient = AndroidHttpClient.newInstance("Android");
      httpPost = new HttpPost(postURL);
      httpPost.setEntity(builder.build());
      httpResponse = httpClient.execute(httpPost);
      httpEntity = httpResponse.getEntity();
      inputStream = httpEntity.getContent();

      // 하나의 오브젝트 가져올때 응답받기.
      loginDTO = readMessage(inputStream);
      Log.d(TAG, "doInBackground: " + loginDTO.getMember_id());

      inputStream.close();

    } catch (Exception e) {
      Log.d("main:Reviewselect", e.getMessage());
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


  protected void onPostExecute(String result) {
    //super.onPostExecute(result);
    //Log.d(TAG, "onPostExecute: " + result);
  }



  public MemberDTO readMessage(InputStream inputStream) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
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
    Log.d("main:Reviewselect : ", member_code + ", " + member_id + "," + member_name + "," + member_nick + "," + member_tel + "," + member_email);
    return new MemberDTO(member_code, member_id, member_kind, member_name, member_nick, member_tel, member_email, member_addr, member_image, member_date);
  }
}