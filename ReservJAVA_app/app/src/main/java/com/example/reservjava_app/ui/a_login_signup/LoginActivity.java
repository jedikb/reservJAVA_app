package com.example.reservjava_app.ui.a_login_signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.reservjava_app.ATask.LoginSelect;
import com.example.reservjava_app.ATask.MyReview;
import com.example.reservjava_app.DTO.MemberDTO;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.MyReviewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.reservjava_app.Common.CommonMethod.isNetworkConnected;

public class LoginActivity extends AppCompatActivity {
  private static final String TAG = "main:LoginActivity";
  //앞으로는 자동 로그인 기능이 이걸 대체할 것임
  public static MemberDTO loginDTO = null;

  public static ArrayList<ReviewDTO> reviewDTOS = null;
  private boolean saveLoginData;
  private SharedPreferences appData;
  private String member_id, member_pw;
  private MemberDTO loginData;

  CheckBox autoLogin;
  Button signupBtn, loginBtn;
  EditText editID, editPW;
  TextView idpw;
  Toolbar toolbar;
  ImageView backLoginBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    backLoginBtn = findViewById(R.id.backLoginBtn);
    backLoginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });

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


    //(임시: 작업하는 동안 아이디, 비번 치는게 귀찮음)
    //editID.setText("aaa");
    //editPW.setText("aaa");

    //아이디 입력하는 곳에 포커스 주기
    editID.requestFocus();
    //자동으로 키보드 띄우기(귀찮으니 주석처리;;)
    //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    // 로그인하고 전에 있던 화면으로 이동
    loginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loginData = new MemberDTO();

        if(editID.getText().toString().length() != 0 && editPW.getText().toString().length() != 0){
          member_id = editID.getText().toString();
          member_pw = editPW.getText().toString();

          LoginSelect loginSelect = new LoginSelect(member_id, member_pw);
          try {
            loginData = loginSelect.execute().get();
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

        if(loginData != null){
          Toast.makeText(LoginActivity.this, "로그인 되었습니다 !!!", Toast.LENGTH_SHORT).show();
          Log.d("main:login", loginData.getMember_name() + "님 로그인 되었습니다 !!!");

          // 로그인에 성공하면 값을 SharedPreference에 저장한다
          loginDTOSave();
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          //intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP|intent.FLAG_ACTIVITY_CLEAR_TOP);
          //intent.putExtra("login", loginData);
          //startActivityForResult(intent, 10000);
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
    editor.putInt("member_code", loginData.getMember_code());
    editor.putInt("member_kind", loginData.getMember_kind());
    editor.putString("member_name", loginData.getMember_name());
    editor.putString("member_nick", loginData.getMember_nick());
    editor.putString("member_tel", loginData.getMember_tel());
    editor.putString("member_email", loginData.getMember_email());
    editor.putString("member_addr", loginData.getMember_addr());
    editor.putString("member_image", loginData.getMember_image());
    //editor.putString("member_date", String.valueOf(loginData.getMember_date()));

    // apply, commit 을 안하면 변경된 내용이 저장되지 않음
    editor.apply();

    // 저장한 것을 loginDTO로 바로 담기 위해 실행함
    loginDTOLoad();

  }

  //로그인 정보 불러오기
  private void loginDTOLoad() {

    saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);

    int member_code=-1, member_kind=-1;
    String member_name = "", member_nick = "", member_tel = "", member_email = "", member_addr = "", member_image="";
    Date member_date = null;
    double member_lat = 0, member_lng= 0;

    member_code = appData.getInt("member_code", -1);
    member_id = appData.getString("member_id", "");
    member_pw = appData.getString("member_pw", "");
    member_kind = appData.getInt("member_kind", -1);
    member_name = appData.getString("member_name", "");
    member_nick = appData.getString("member_nick", "");
    member_tel = appData.getString("member_tel", "");
    member_email = appData.getString("member_email", "");
    member_addr = appData.getString("member_addr", "");
    member_image = appData.getString("member_image", "");
    SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      member_date = fm.parse(appData.getString("member_date", ""));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Log.d(TAG, "loginDTOLoad: member_date : " + member_date);
    member_lat = appData.getFloat("member_lat", 0);
    member_lng = appData.getFloat("member_lat", 0);

    member_lat = Double.parseDouble(String.valueOf(member_lat));
    member_lng = Double.parseDouble(String.valueOf(member_lng));

    loginDTO = new MemberDTO(member_code, member_id, member_pw, member_kind, member_name, member_nick
              , member_tel, member_email, member_addr, member_image, member_lat, member_lng, member_date);

    // 리뷰 정보 조회
    selectDate ();
  }

  //개인 정보 불러오기
  public void selectDate () {
    ArrayList<ReviewDTO> reviewDTOS;
    MyReviewAdapter rAdapter;
    ProgressDialog progressDialog;
    reviewDTOS = new ArrayList<>();
    rAdapter = new MyReviewAdapter(LoginActivity.this, reviewDTOS);

    progressDialog = new ProgressDialog(LoginActivity.this);
    progressDialog.setTitle("데이터 업로딩");
    progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();

    if(isNetworkConnected(LoginActivity.this) == true) {
      MyReview myReview = new MyReview(reviewDTOS, progressDialog, rAdapter);
      myReview.execute();
    } else {
      Toast.makeText(LoginActivity.this, "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
    }
  }
}