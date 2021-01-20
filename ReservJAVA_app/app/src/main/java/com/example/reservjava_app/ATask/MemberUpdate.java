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

import java.io.InputStream;
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
  String member_id, member_name, member_pw, member_pw2, member_nick, member_tel, member_email, member_image, member_date;

  public MemberUpdate(String member_id, String member_pw, String member_name, String member_nick) {}

  public MemberUpdate(String member_id, String member_pw, String member_name, String member_nick, String member_tel, String member_email, String member_image, String member_date) {
    this.member_id = member_id;
    this.member_pw = member_pw;
    this.member_name = member_name;
    this.member_nick = member_nick;
    this.member_tel = member_tel;
    this.member_email = member_email;
    this.member_image = member_image;
    this.member_date = member_date;
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
      builder.addTextBody("member_name", member_name, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_nick", member_nick, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_tel", member_tel, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_email", member_email, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_image", member_image, ContentType.create("Multipart/related", "UTF-8"));
      builder.addTextBody("member_date", member_date, ContentType.create("Multipart/related", "UTF-8"));

      postURL = ipConfig + pServer + "/anUpdate";
      Log.d(TAG, "doInBackground: " + member_email);
      // 전송
      InputStream inputStream = null;
      httpClient = AndroidHttpClient.newInstance("Android");
      httpPost = new HttpPost(postURL);
      httpPost.setEntity(builder.build());
      httpResponse = httpClient.execute(httpPost);
      httpEntity = httpResponse.getEntity();
      inputStream = httpEntity.getContent();
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

    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {

  }
 /* public MemberDTO readMessage(InputStream inputStream) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
    Log.d(TAG, "readMessage: 12");
    int member_code = -1, member_kind = -1;
    String member_id = "", member_name = "", member_nick = "", member_tel = "", member_email = "", member_addr = "", member_image = "";
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
  }*/
}
