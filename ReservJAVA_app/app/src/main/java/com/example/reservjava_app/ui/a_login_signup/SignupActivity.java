package com.example.reservjava_app.ui.a_login_signup;

import android.content.Intent;
import android.net.http.RequestQueue;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

  Button Idchk_btn;
  TextView pwchkbar;
  EditText Pw_text, Pwchk_text;
  Object object;

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

    //비밀번호 일치 여부 확인하기
    //Pwchk_text.addTextChangedListener(TextWatcher {

    // EditText에 문자 입력 전
    //override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    // EditText에 변화가 있을 경우
    //override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    // EditText 입력이 끝난 후
    //override fun afterTextChanged(p0: Editable?) {
    /*  if(Pw_text.getText().toString().equals(Pwchk_text.getText().toString())){
        pwchkbar.setText("일치합니다.")
      }
      else
        pwchkbar.setText("일치하지 않습니다.")*/
    }
}