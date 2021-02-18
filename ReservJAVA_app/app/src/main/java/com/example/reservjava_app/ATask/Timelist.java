package com.example.reservjava_app.ATask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.adapter.TimeListAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.pServer;

public class Timelist extends AsyncTask<Void, Void, Void> {

    ArrayList<String> timeList;
    TimeListAdapter adapter;
    ProgressDialog progressDialog;
    int business_code;
    String time;

    public Timelist(ArrayList<String> timeList, TimeListAdapter adapter, ProgressDialog progressDialog, int business_code) {
        this.timeList = timeList;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
        this.business_code = business_code;
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
            builder.addTextBody("business_code", String.valueOf(business_code), ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + pServer + "/anTimeList";

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
            time = stringBuilder.toString().trim(); // 7:00, 8:00
            String[] stime = time.split("\n");
            for (int i = 0; i < stime.length; i++){
                timeList.add(stime[i]);
            }
            Log.d("time: ", "doInBackground: " + timeList.get(0));

            inputStream.close();

            /*String line = String.valueOf(inputStream.read());
            while()
            inputStream.read();
            readJsonStream(inputStream);*/

            } catch (Exception e) {
            Log.d("TimeList", e.getMessage());
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

            Log.d("TimeList", "TimeList Select Complete!!!");

            adapter.notifyDataSetChanged();
            }

    public void readJsonStream(InputStream inputStream) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            try {
                reader.beginArray();
                while (reader.hasNext()) {
                    timeList.add(readMessage(reader));
                }
                reader.endArray();
            } finally {
                reader.close();
            }
    }

    public String readMessage(JsonReader reader) throws IOException {
            String product_time = "";

            reader.beginObject();
            while (reader.hasNext()){
                product_time = reader.nextString();
            }
            reader.endObject();
            return product_time;
            }

}
