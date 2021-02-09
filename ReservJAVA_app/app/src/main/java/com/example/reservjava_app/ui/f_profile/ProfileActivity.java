package com.example.reservjava_app.ui.f_profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reservjava_app.ATask.MyReview;
import com.example.reservjava_app.ATask.MyVisit;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.MyReviewAdapter;
import com.example.reservjava_app.adapter.MyVisitAdapter;

import java.util.ArrayList;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class ProfileActivity extends AppCompatActivity {
  private static final String TAG = "main:ProfileActivity";
  RecyclerView recyclerView;
  TextView pro_tv_name;

  ImageView faceImg;

  //리사이클러뷰
  ArrayList<ReviewDTO> reviewDTOS, visitDTOS;
  MyReview myReview;
  MyReviewAdapter rAdapter;
  MyVisitAdapter vAdapter;
  ProgressDialog progressDialog;
  public static ReviewDTO reviewSetItem = null;

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
    vAdapter = new MyVisitAdapter(this, visitDTOS);
    recyclerView = findViewById(R.id.recyclerView);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this,
        RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    // 리뷰 정보 조회
    visitDTOS = new ArrayList<>();
    progressDialog = new ProgressDialog(ProfileActivity.this);
    progressDialog.setTitle("데이터 업로딩");
    progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();

    MyVisit myVisit = new MyVisit(visitDTOS, progressDialog, vAdapter);
    myVisit.execute();

    vAdapter = new MyVisitAdapter(this, visitDTOS);
    for(ReviewDTO dto : visitDTOS) {
      vAdapter.addItem(new ReviewDTO(dto.getBooking_code(), dto.getBusiness_category_code(), dto.getBusiness_name(), dto.getBooking_appraisal_star(), dto.getBooking_appraisal()));
    }

    recyclerView.setAdapter(vAdapter);

    //방문한! 리스트 보여줌
    findViewById(R.id.visitListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        visitDTOS = new ArrayList<>();
        rAdapter = new MyReviewAdapter(ProfileActivity.this, visitDTOS);

        vAdapter.removeAllItem();
        rAdapter.removeAllItem();

        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setTitle("데이터 업로딩");
        progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        vAdapter = new MyVisitAdapter(ProfileActivity.this, visitDTOS);
        MyVisit myVisit = new MyVisit(visitDTOS, progressDialog, vAdapter);
        myVisit.execute();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            for(ReviewDTO dto : visitDTOS) {
              vAdapter.addItem(new ReviewDTO(dto.getBooking_code(), dto.getBusiness_category_code(), dto.getBusiness_name(), dto.getBooking_appraisal_star(), dto.getBooking_appraisal()));
            }
            recyclerView.setAdapter(vAdapter);
          }
        }, 500);
      }
    });

    //내가 작성한 리뷰들을 보여줌
    findViewById(R.id.reviewListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        reviewDTOS = new ArrayList<>();
        rAdapter = new MyReviewAdapter(ProfileActivity.this, visitDTOS);

        vAdapter.removeAllItem();
        rAdapter.removeAllItem();

        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setTitle("데이터 업로딩");
        progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        MyReview myReview = new MyReview(reviewDTOS, progressDialog, rAdapter);
        myReview.execute();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            for(ReviewDTO dto : reviewDTOS) {
              rAdapter.addItem(new ReviewDTO(dto.getBooking_code(), dto.getBusiness_category_code(), dto.getBusiness_name(), dto.getBooking_appraisal_star(), dto.getBooking_appraisal()));
            }
            recyclerView.setAdapter(rAdapter);
          }
        }, 500);
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