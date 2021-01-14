package com.example.reservjava_app.ui.a_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.R;

public class LoginActivity extends AppCompatActivity {

  Button signup, login;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    login = findViewById(R.id.login);
    signup = findViewById(R.id.signup);

    // 로그인하고 메인화면으로 이동(임시로 그냥 메인으로 이동
    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    // 회원가입 화면으로 이동
    signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
      }
    });

  }
}