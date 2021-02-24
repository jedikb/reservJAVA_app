package com.example.reservjava_app.ui.f_profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reservjava_app.ATask.MyReview;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.MyReviewAdapter;
import com.example.reservjava_app.adapter.MyVisitAdapter;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;
import static com.example.reservjava_app.ATask.MyReview.reviewDTOS;

public class ProfileActivity extends AppCompatActivity {
  private static final String TAG = "main:ProfileActivity";
  public static ReviewDTO reviewSetItem = null;
  RecyclerView recyclerView;
  TextView pro_tv_name;
  ImageView faceImg, profilebackBtn;

  //리사이클러뷰
  MyReview myReview;
  MyReviewAdapter rAdapter;
  MyVisitAdapter vAdapter;
  public String member_image;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    // 이름
    pro_tv_name = findViewById(R.id.pro_tv_name);
    pro_tv_name.setText("소중한 " + loginDTO.getMember_name() + "님");

    // 사진
    //만약 사진 필드가 비어 있다면... 디폴트 이미지
    faceImg = findViewById(R.id.faceImg);
    member_image = loginDTO.getMember_image();

    faceImg.setVisibility(View.VISIBLE);
    //선택된 이미지 보여주기(움직이는 그림도 됨)
    Glide.with(this).load(member_image)
        .error(R.drawable.user).into(faceImg);

    //리사클러 뷰 시작
    rAdapter = new MyReviewAdapter(this, reviewDTOS);
    vAdapter = new MyVisitAdapter(this, reviewDTOS);
    recyclerView = findViewById(R.id.recyclerView);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this,
        RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    recyclerView.setAdapter(vAdapter);

    //방문한! 리스트 보여줌
    findViewById(R.id.visitListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        recyclerView.setAdapter(vAdapter);
      }
    });

    //내가 작성한 리뷰들을 보여줌
    findViewById(R.id.reviewListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        recyclerView.setAdapter(rAdapter);
      }
    });

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

    //뒤로가기 버튼(메인화면으로 돌아감)
    profilebackBtn = findViewById(R.id.profilebackBtn);
    profilebackBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });

  }
}