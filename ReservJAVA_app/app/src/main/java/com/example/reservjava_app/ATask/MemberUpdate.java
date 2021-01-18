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

public class MemberUpdate extends AsyncTask<Void, Void, Void> {

  private static final String TAG = "main:MemberUpdate";

  HttpClient httpClient;
  HttpPost httpPost;
  HttpResponse httpResponse;
  HttpEntity httpEntity;

  // 사진 정보 추가
  String member_id, member_name, member_pw, member_pw2, member_nick, member_tel, member_email;


  public MemberUpdate(String member_id, String member_pw, String member_name, String member_nick, String member_tel, String member_email) {
    this.member_id = member_id;
    this.member_pw = member_pw;
    this.member_name = member_name;
    this.member_nick = member_nick;
    this.member_tel = member_tel;
    this.member_email = member_email;
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

      postURL = ipConfig + "/app/anUpdate";
      Log.d(TAG, "doInBackground: " + member_email);
      // 전송
      InputStream inputStream = null;
      httpClient = AndroidHttpClient.newInstance("Android");
      httpPost = new HttpPost(postURL);
      httpPost.setEntity(builder.build());
      httpResponse = httpClient.execute(httpPost);
      httpEntity = httpResponse.getEntity();
      //inputStream = httpEntity.getContent();
      //inputStream.close();

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

/*  public MemberDTO readMessage(InputStream inputStream) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

    String member_id = "", member_name = "", member_nick = "", member_tel = "";

    reader.beginObject();
    while (reader.hasNext()) {
      String readStr = reader.nextName();
      if (readStr.equals("member_id")) {
        member_id = reader.nextString();
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

  }*/
}
