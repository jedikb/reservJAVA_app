package com.example.reservjava_app.ATask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.reservjava_app.DTO.CategoryDTO;
import com.example.reservjava_app.DTO.Category_SubDTO;
import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.adapter.ExpandableListAdapter;
import com.example.reservjava_app.adapter.ProductAdapter;

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

public class CategorySelect extends AsyncTask<Void, Void, Void> {

    ProductDTO productDTO;
    ArrayList<CategoryDTO> categoryDTOS;
    ArrayList<Category_SubDTO> categorysubDTOS;
    ExpandableListAdapter adapter;
    ProgressDialog progressDialog;

    public CategorySelect(ArrayList<CategoryDTO> categoryDTOS , ProgressDialog progressDialog , ExpandableListAdapter adapter) {
        this.adapter = adapter;
        this.categoryDTOS = categoryDTOS;
        this.progressDialog = progressDialog;
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
       //     builder.addTextBody("business_code", String.valueOf(business_code), ContentType.create("Multipart/related", "UTF-8"));
         //   builder.addTextBody("set_time", time, ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + pServer + "/anCategory";

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
            Log.d("ProductSelect", e.getMessage());
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

        Log.d("category", "category Select Complete!!!");

       // adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                categoryDTOS.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public CategoryDTO readMessage(JsonReader reader) throws IOException {
        int category_code = 0, category_parent_code = 0;
        String category_name = "" , category_info = "";
        int cnt = 0;
        reader.beginObject();
        while (reader.hasNext()){
            String readStr = reader.nextName();
            if(readStr.equals("category_code")){
                category_code = reader.nextInt();
            }else if(readStr.equals("category_parent_code")){
                category_parent_code = reader.nextInt();
            }else if(readStr.equals("category_name")){
                category_name = reader.nextString();
            }else if(readStr.equals("category_info")){
                category_info = reader.nextString();
            }else if(readStr.equals("cnt")){
                cnt = reader.nextInt();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new CategoryDTO(category_code,category_parent_code,category_name,category_info,cnt);
    }
}
