package com.example.reservjava_app.ui.a_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.ATask.LoginSelect;
import com.example.reservjava_app.DTO.MemberDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

  // 로그인이 성공하면 static 로그인DTO 변수에 담아서
  // 어느곳에서나 접근할 수 있게 한다
  public static MemberDTO loginDTO = null;

  Button signup, login;
  EditText editID, editPW;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    login = findViewById(R.id.login);
    signup = findViewById(R.id.signup);
    editID = findViewById(R.id.editID);
    editPW = findViewById(R.id.editPW);

    // 로그인하고 메인화면으로 이동
    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(editID.getText().toString().length() != 0 && editPW.getText().toString().length() != 0){
          String id = editID.getText().toString();
          String passwd = editPW.getText().toString();

          LoginSelect loginSelect = new LoginSelect(id, passwd);
          try {
            loginSelect.execute().get();
          } catch (ExecutionException e) {
            e.getMessage();
          } catch (InterruptedException e) {
            e.getMessage();
          }

        } else {
          Toast.makeText(LoginActivity.this, "아이디와 암호를 모두 입력하세요", Toast.LENGTH_SHORT).show();
          Log.d("main:login", "아이디와 암호를 모두 입력하세요 !!!");
          return;
        }

        if(loginDTO != null){
          Toast.makeText(LoginActivity.this, "로그인 되었습니다 !!!", Toast.LENGTH_SHORT).show();
          Log.d("main:login", loginDTO.getMember_name() + "님 로그인 되었습니다 !!!");

          // 로그인 정보에 값이 있으면 로그인이 되었으므로 메인화면으로 이동
          if(loginDTO != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
          }

        }else {
          Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치안함 !!!", Toast.LENGTH_SHORT).show();
          Log.d("main:login", "아이디나 비밀번호가 일치안함 !!!");
          editID.setText(""); editPW.setText("");
          editID.requestFocus();
        }

        Toast.makeText(LoginActivity.this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
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