package com.example.reservjava_app.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.reservjava_app.Common.CommonMethod;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.ui.f_profile.ProfileActivity;
import com.example.reservjava_app.ui.f_profile.ReviewActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.loginDTO;
import static com.example.reservjava_app.Common.CommonMethod.pServer;

public class ReviewInsert extends AsyncTask<Void, Void, String> {
    private static final String TAG = "main ReviewInsert";;
    String review;
    float ratingbar;
    ReviewDTO dto;
    public ReviewInsert(String review, float ratingbar , ReviewDTO dto) {
        this.review = review;
        this.ratingbar = ratingbar;
        this.dto = dto;
    }

    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    String state = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("review", review, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("ratingbar", ratingbar+"", ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_code", loginDTO.getMember_code()+"", ContentType.create("Multipart/related", "UTF-8"));
             builder.addTextBody("booking_code", dto.getBooking_code()+"", ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + pServer + "/anMemberReview";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginObject();
            while (reader.hasNext()) {
                String readStr = reader.nextName();
                if (readStr.equals("succ")) {
                    state = reader.nextString();
                } else {

                    reader.skipValue();
                }
            }
            reader.endObject();

            // 응답
           // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
           // StringBuilder stringBuilder = new StringBuilder();
          //  String line = null;
          //  while ((line = bufferedReader.readLine()) != null){
          //      stringBuilder.append(line + "\n");
          //  }
           // state = stringBuilder.toString();

            inputStream.close();

        }  catch (Exception e) {
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
        return state;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d(TAG, "onPostExecute: " + result);
    }
}

