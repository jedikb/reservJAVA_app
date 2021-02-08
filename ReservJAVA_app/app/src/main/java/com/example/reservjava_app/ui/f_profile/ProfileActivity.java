package com.example.reservjava_app.ui.f_profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reservjava_app.ATask.MyReview;
import com.example.reservjava_app.ATask.ReviewSelect;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.MyReviewAdapter;

import java.util.ArrayList;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class ProfileActivity extends AppCompatActivity {
  private static final String TAG = "main:ProfileActivity";
  RecyclerView recyclerView;
  TextView pro_tv_name;

  ImageView faceImg;

  //리사이클러뷰
  ArrayList<ReviewDTO> reviewDTOS;
  MyReview myReview;
  MyReviewAdapter adapter;
  ProgressDialog progressDialog;

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
    reviewDTOS = new ArrayList<>();
    adapter = new MyReviewAdapter(this, reviewDTOS);
    recyclerView = findViewById(R.id.recyclerView);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this,
        RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);


    for(ReviewDTO dto : reviewDTOS) {
      adapter.addItem(new ReviewDTO(dto.getBooking_code(), dto.getBusiness_category_code(), dto.getBusiness_name(), dto.getBooking_appraisal_star(), dto.getBooking_appraisal()));
    }

    recyclerView.setAdapter(adapter);

    //방문한! 리스트 보여줌
    findViewById(R.id.visitListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

    //내가 작성한 리뷰들을 보여줌
    findViewById(R.id.reviewListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        for(ReviewDTO dto : reviewDTOS) {
          adapter.addItem(new ReviewDTO(dto.getBooking_code(), dto.getBusiness_category_code(), dto.getBusiness_name(), dto.getBooking_appraisal_star(), dto.getBooking_appraisal()));
        }

        recyclerView.setAdapter(adapter);
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

    //뒤로가기 버튼(이전 화면으로 돌아간다-임시:Search)
    findViewById(R.id.backQnABtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });


  }

}