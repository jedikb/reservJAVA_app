package com.example.reservjava_app.ui.f_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.ATask.ReviewInsert;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;

import java.util.concurrent.ExecutionException;

public class ReviewActivity extends AppCompatActivity {

  private static final String TAG = "main ReviewActivity";

  String state;

  EditText editReview;
  RatingBar ratingBar;
  Button submitBtn, cancelBtn;
  TextView reviewName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_review);

    editReview = findViewById(R.id.editReview);
    ratingBar = findViewById(R.id.ratingBar);
    submitBtn = findViewById(R.id.submitBtn);
    reviewName = findViewById(R.id.reviewName);

   /* // 뒤로가기 버튼(리스트 프래그먼트로 이동)
    findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {

    // 뒤로가기 버튼(리스트 프래그먼트로 이동)
    findViewById(R.id.backQnABtn).setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        finish();
      }
    });
*/

    // 고객 아이디, 매장 코드와 연동// 예약한 항목과 시간)
   /* findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //정보 가지고 간뒤
        finish();
        Toast.makeText(ReviewActivity.this, "리뷰가 등록되었습니다", Toast.LENGTH_SHORT).show();
      }
    });*/
    // 취소버튼(리스트 프래그먼트로 이동)
    findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    // 리뷰 등록 버튼(ratingbar 점수와 editReview 내용을 담고 간다
    submitBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String review = editReview.getText().toString();
        float ratingbar = ratingBar.getRating()*20;

        ReviewInsert ReviewInsert = new ReviewInsert(review, ratingbar);
        try {
          state = ReviewInsert.execute().get().trim();   //.get() : 데이터가 도착하기 전에 조회하는 것을 방지
          Log.d(TAG, "onClick: " + state);

        } catch (ExecutionException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        if(state.equals("1")) {
          Log.d(TAG, "onClick: 삽입 성공");
          Toast.makeText(ReviewActivity.this, "리뷰가 등록되었습니다", Toast.LENGTH_LONG).show();
          finish();

        } else {
          Log.d(TAG, "onClick: 삽입 실패");
          finish();
        }
      }
    });

    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

      @Override

      public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        // 저는 0개를 주기싫어서, 만약 1개미만이면 강제로 1개를 넣었습니다.

        if (ratingBar.getRating()<1.0f){

          ratingBar.setRating(1);

        }

      }

    });

    //매장 이름 입력
    ReviewDTO reviewDTO = null;
    Intent intent = getIntent();
    reviewDTO = (ReviewDTO) intent.getSerializableExtra("reviewDTO");
    reviewName.setText(reviewDTO.getBusiness_name());

    //수정으로 들어 왔을 경우
    if(reviewDTO.getBooking_appraisal() != null) {
      editReview.setText(reviewDTO.getBooking_appraisal());
      ratingBar.setRating(reviewDTO.getBooking_appraisal_star());
    }
  }
}