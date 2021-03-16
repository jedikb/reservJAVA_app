package com.example.reservjava_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
            ImageView imageView1 = findViewById(R.id.image_logo1);
            Glide.with(this).load(R.drawable.logo_img2).into(imageView1);

            ImageView imageView2 = findViewById(R.id.image_logo2);
            Glide.with(this).load(R.drawable.move_).into(imageView2);

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
            },2500);    //1000 = 1초, 인트로 화면 지속 시간
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