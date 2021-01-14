package com.example.reservjava_app.ui.f_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.reviewDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

  RecyclerView recyclerView;

  ArrayList<BusinessDTO> businessDTOS;
  ArrayList<reviewDTO> reviewDTOS;
  ImageView faceImg;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);


    //리스트를 담을 화면(방문, 리뷰 둘다 담을 예정)
    recyclerView = findViewById(R.id.ListView);
    faceImg = findViewById(R.id.faceImg);
    LinearLayoutManager layoutManager = new LinearLayoutManager
        (this, RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    //프로필 수정 화면으로 이동
    findViewById(R.id.editProfileBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ModProfileActivity.class);
        startActivity(intent);
        finish();
      }
    });

    //방문한! 리스트 보여줌
    findViewById(R.id.visitListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

    //내가 작성한 리뷰들을 보여줌
    findViewById(R.id.reviewListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

    //뒤로가기 버튼(이전 화면으로 돌아간다-임시:Search)
    findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

  }
}