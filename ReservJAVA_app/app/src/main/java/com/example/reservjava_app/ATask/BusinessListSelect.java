package com.example.reservjava_app.ATask;

import android.app.ProgressDialog;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.adapter.StoreListAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPostHC4;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpRetryException;
import java.util.ArrayList;

class ListSelect extends
        AsyncTask<Void, Void, Void> {
    //생성자
    ArrayList<BusinessDTO> businessList;
    StoreListAdapter adapter;
    ProgressDialog progressDialog;

    public ListSelect(ArrayList<BusinessDTO> businessList, StoreListAdapter adapter, ProgressDialog progressDialog){
        this.businessList = businessList;
        this.adapter = adapter;
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
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(progressDialog != null){
            progressDialog.dismiss();;
        }

        Log.d("StoreList", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while(reader.hasNext()){
                businessList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public BusinessDTO readMessage(JsonReader reader) {


        return new BusinessDTO();
    }
}
