package com.example.reservjava_app.ui.f_profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.MemberDTO;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

  RecyclerView recyclerView;
  TextView mod_tv_name;

  ArrayList<BusinessDTO> businessDTOS;
  ArrayList<ReviewDTO> ReviewDTOS;

  LoginActivity loginActivity;
  ImageView faceImg;
  String member_name;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    Intent intent = new Intent();
    // 이름
    mod_tv_name = findViewById(R.id.mod_tv_name);
    mod_tv_name.setText(loginDTO.getMember_name());

    // 사진
    faceImg = findViewById(R.id.faceImg);

    //리스트를 담을 화면(방문, 리뷰 둘다 담을 예정)
    recyclerView = findViewById(R.id.ListView);
    LinearLayoutManager layoutManager = new LinearLayoutManager
        (this, RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    //프로필 수정 화면으로 이동
    findViewById(R.id.editProfileBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //수정할 때는 아이디만 가지고 새로 불러오게 함
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