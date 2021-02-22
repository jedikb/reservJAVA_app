package com.example.reservjava_app.ATask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.ProductDTO;
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

public class ProductSelect extends AsyncTask<Void, Void, Void> {

    ProductDTO productDTO;
    ArrayList<ProductDTO> productList;
    ProductAdapter adapter;
    ProgressDialog progressDialog;
    int business_code;
    String time;

    public ProductSelect(ProductAdapter adapter, ArrayList<ProductDTO> productList, ProgressDialog progressDialog, int business_code, String time) {
        this.adapter = adapter;
        this.productList = productList;
        this.progressDialog = progressDialog;
        this.business_code = business_code;
        this.time = time;
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
            builder.addTextBody("set_time", time, ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + pServer + "/anProductSelect";

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

        Log.d("ProductSelect", "ProductSelect Select Complete!!!");

        adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                productList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public ProductDTO readMessage(JsonReader reader) throws IOException {
        int product_code = 0, product_business_code = 0;
        String product_name = "";
        int product_price =0, product_price_deposit =0, product_stock=0;
        String product_image ="", product_info="", product_time="";

        reader.beginObject();
        while (reader.hasNext()){
            String readStr = reader.nextName();
            if(readStr.equals("product_code")){
                product_code = reader.nextInt();
            }else if(readStr.equals("product_business_code")){
                product_business_code = reader.nextInt();
            }else if(readStr.equals("product_name")){
                product_name = reader.nextString();
            }else if(readStr.equals("product_price")){
                product_price = reader.nextInt();
            }else if(readStr.equals("product_price_deposit")){
                product_price_deposit = reader.nextInt();
            }else if(readStr.equals("product_stock")){
                product_stock = reader.nextInt();
            }else if(readStr.equals("product_image")){
                product_image = reader.nextString();
            }else if(readStr.equals("product_info")){
                product_info = reader.nextString();
            }else if(readStr.equals("product_time")){
                product_time = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ProductDTO(product_code, product_business_code, product_name, product_price, product_price_deposit
                , product_stock, product_image, product_info, product_time);
    }
}
