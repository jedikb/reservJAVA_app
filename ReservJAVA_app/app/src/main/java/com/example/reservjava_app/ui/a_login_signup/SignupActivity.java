package com.example.reservjava_app.ui.a_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.R;

public class SignupActivity extends AppCompatActivity {



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);

    //가입 버튼 눌렀을 때 (두가지 경우가 있겠지만 일단 성공 화면으로 이동
    findViewById(R.id.signupbtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
        startActivity(intent);
      }
    });

    //취소 버튼 눌렀을 때(임시로 그냥 이전 화면으로 가게 함)
    findViewById(R.id.resetbtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });


  }
}