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
import com.example.reservjava_app.R;
import com.example.reservjava_app.ui.f_profile.ModProfileActivity;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
  private static final String TAG = "main:LoginActivity";
  // 로그인이 성공하면 static 로그인DTO 변수에 담아서
  // 어느곳에서나 접근할 수 있게 한다
  // 적용할 곳에서 import만 해주면 된다.(다시 선언하면 안 됨)
  public static MemberDTO loginDTO = null;

  Button signupBtn, loginBtn;
  EditText editID, editPW;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    loginBtn = findViewById(R.id.loginBtn);
    signupBtn = findViewById(R.id.signupBtn);
    editID = findViewById(R.id.editID);
    editPW = findViewById(R.id.editPW);

    //(임시: 작업하는 동안 아이디, 비번 치는게 귀찮음)
    editID.setText("aaa");
    editPW.setText("aaa");
    //아이디 입력하는 곳에 포커스 주기
    editID.requestFocus();
    //자동으로 키보드 띄우기(귀찮으니 주석처리;;)
    //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    // 로그인하고 전에 있던 화면으로 이동
    loginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(editID.getText().toString().length() != 0 && editPW.getText().toString().length() != 0){
          String member_id = editID.getText().toString();
          String member_pw = editPW.getText().toString();
          //Log.d(TAG, "onClick: " + member_id);
          //Log.d(TAG, "onClick: " + member_pw);

          LoginSelect loginSelect = new LoginSelect(member_id, member_pw);
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

          // 로그인 정보에 값이 있으면 로그인이 되었다는 것이므로 화면을 종료시킨다
          Intent intent = new Intent(getApplicationContext(), ModProfileActivity.class);
          startActivity(intent);
          finish();

        }else {
          Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치안함 !!!", Toast.LENGTH_SHORT).show();
          Log.d("main:login", "아이디나 비밀번호가 일치안함 !!!");
          editID.setText(""); editPW.setText("");
          editID.requestFocus();
        }
      }
    });

    // 회원가입 화면으로 이동
    signupBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
        startActivity(intent);
      }
    });
  }
}