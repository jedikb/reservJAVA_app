package com.example.reservjava_app.ui.f_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.reservjava_app.R;

public class ReviewActivity extends AppCompatActivity {

  EditText editReview;
  RatingBar ratingBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_review);

    editReview = findViewById(R.id.editReview);
    ratingBar = findViewById(R.id.ratingBar);

    // 뒤로가기 버튼(리스트 프래그먼트로 이동)
    findViewById(R.id.backQnABtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    // 리뷰 등록 버튼(ratingbar 점수와 editReview 내용을 담고 간다
    // 고객 아이디, 매장 코드와 연동// 예약한 항목과 시간)
    findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        /*정보 가지고 간뒤 */
        finish();
        Toast.makeText(ReviewActivity.this, "리뷰가 등록되었습니다", Toast.LENGTH_SHORT).show();
      }
    });
    // 취소버튼(리스트 프래그먼트로 이동)
    findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });



  }
}