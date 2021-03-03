package com.example.reservjava_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.reservjava_app.ATask.SearchBusiness;

import com.example.reservjava_app.adapter.SearchBusinessAdapter;

import static com.example.reservjava_app.Common.CommonMethod.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class IntroActivity extends AppCompatActivity {
    //통신해서 값을 넘겨주기 위해
    SearchBusinessAdapter adapter;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        try {
            //CommonMethod로 많은 부분을 이동시킴
            //import static com.example.reservjava_app.Common.CommonMethod.*; 관련 메소드 들은 시켜서 사용하면됨

            //1.로그인 정보 불러오기
            LoginInfo(IntroActivity.this);

            //2. 전체 매장 정보를 불러오기
            busiList = new ArrayList<>();
            SearchBusiness searchBusiness = new SearchBusiness(busiList, "", progressDialog, adapter);
            searchBusiness.execute().get();


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1500);    //1000 = 1초, 인트로 화면 지속 시간
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}