package com.example.reservjava_app.ui.a_login_signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.ATask.LoginSelect;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.reservjava_app.Common.CommonMethod.*;

public class LoginActivity extends AppCompatActivity {
  private static final String TAG = "main:LoginActivity";
  //앞으로는 자동 로그인 기능이 이걸 대체할 것임

  //public static ArrayList<ReviewDTO> reviewDTOS = null;
  private boolean saveLoginData;
  private String member_id, member_pw;

  CheckBox autoLogin;
  Button signupBtn, loginBtn;
  EditText editID, editPW;
  TextView idpw;
  ImageView backLoginBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

  /*  backLoginBtn = findViewById(R.id.backLoginBtn);
    backLoginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });*/

    // 설정값 불러오기
    appData = getSharedPreferences("SAVE_LOGIN_DATA", MODE_PRIVATE);
    //loginDTOLoad();

    loginBtn = findViewById(R.id.loginBtn);
    signupBtn = findViewById(R.id.signupBtn);
    editID = findViewById(R.id.editID);
    editPW = findViewById(R.id.editPW);
    idpw = (TextView) findViewById(R.id.idpw);

    //자동 로그인
    autoLogin = findViewById(R.id.autoLogin);

    if(saveLoginData) {
      editID.setText(member_id);
      editPW.setText(member_pw);
      autoLogin.setChecked(saveLoginData);
    }

    //아이디 입력하는 곳에 포커스 주기
    editID.requestFocus();
    //자동으로 키보드 띄우기
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    // 로그인하고 전에 있던 화면으로 이동
    loginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(editID.getText().toString().length() != 0 && editPW.getText().toString().length() != 0){
          member_id = editID.getText().toString();
          member_pw = editPW.getText().toString();

          LoginSelect loginSelect = new LoginSelect(member_id, member_pw);
          try {
            loginDTO = loginSelect.execute().get();
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

          // 로그인에 성공하면 값을 SharedPreference에 저장한다
          loginDTOSave();
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          //intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP|intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(intent);
          finish();

        }else {
          Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
          Log.d("main:login", "아이디나 비밀번호가 일치하지 않습니다");
          editID.setText(""); editPW.setText("");
          editID.requestFocus();
        }
      }
    });

    // 회원가입 화면으로 이동
    signupBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
        startActivity(intent);
        finish();
      }
    });

    // 아이디 비밀번호 찾기 화면으로 넘어가는 이벤트
    idpw.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, LogPwSearchActivity.class);
        startActivity(intent);
      }
    });
  }

  //로그인 정보 저장
  private void loginDTOSave() {

    SharedPreferences.Editor editor = appData.edit();

    // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
    // 저장시킬 이름이 이미 존재하면 덮어씌움
    editor.putBoolean("SAVE_LOGIN_DATA", autoLogin.isChecked());
    editor.putString("member_id", editID.getText().toString().trim());
    editor.putString("member_pw", editPW.getText().toString().trim());

    //기타 정보들도 저장하자
    editor.putInt("member_code", loginDTO.getMember_code());
    editor.putInt("member_kind", loginDTO.getMember_kind());
    editor.putString("member_name", loginDTO.getMember_name());
    editor.putString("member_nick", loginDTO.getMember_nick());
    editor.putString("member_tel", loginDTO.getMember_tel());
    editor.putString("member_email", loginDTO.getMember_email());
    editor.putString("member_addr", loginDTO.getMember_addr());
    editor.putString("member_image", loginDTO.getMember_image());
    //editor.putString("member_date", String.valueOf(loginData.getMember_date()));

    // apply, commit 을 안하면 변경된 내용이 저장되지 않음
    editor.apply();

    // 저장한 것을 loginDTO로 바로 담기 위해 실행함
    loginDTOLoad(LoginActivity.this);
  }

}