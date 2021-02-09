package com.example.reservjava_app.ui.a_login_signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.reservjava_app.ATask.LoginSelect;
import com.example.reservjava_app.ATask.MyReview;
import com.example.reservjava_app.ATask.MyVisit;
import com.example.reservjava_app.DTO.MemberDTO;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.ProfileActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.MyReviewAdapter;
import com.example.reservjava_app.adapter.MyVisitAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
  private static final String TAG = "main:LoginActivity";
  // 로그인이 성공하면 static 로그인DTO 변수에 담아서
  // 어느곳에서나 접근할 수 있게 한다
  // 적용할 곳에서 import만 해주면 된다.(다시 선언하면 안 됨)
  public static MemberDTO loginDTO = null;
  public static ArrayList<ReviewDTO> reviewDTOS = null;
  public static ArrayList<ReviewDTO> visitDTOS = null;

  Button signupBtn, loginBtn;
  EditText editID, editPW;
  TextView idpw;
  Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    loginBtn = findViewById(R.id.loginBtn);
    signupBtn = findViewById(R.id.signupBtn);
    editID = findViewById(R.id.editID);
    editPW = findViewById(R.id.editPW);
    idpw = (TextView) findViewById(R.id.idpw);


    //위에 툴바 생기게 해서 네비게이션드로어 띄우고 닫을수 있게 하는 기능!
    toolbar = findViewById(R.id.toolbar);
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();


    //Navigation Drawer(바로가기 메뉴) 아이템 클릭 이벤트 처리
    NavigationView navigationView = findViewById(R.id.loginnavigation);
    if(loginDTO == null) {  //로그인 안했을 때
      navigationView.getMenu().findItem(R.id.nav_membershipbtn)
              .setVisible(false);
      navigationView.getMenu().findItem(R.id.nav_logout)
              .setVisible(false);
      navigationView.getMenu().findItem(R.id.nav_listchk)
              .setVisible(false);
      navigationView.getMenu().findItem(R.id.nav_loginbtn)
              .setVisible(true);
      navigationView.getMenu().findItem(R.id.nav_signupbtn)
              .setVisible(true);
      navigationView.getMenu().findItem(R.id.nav_qna)
              .setVisible(true);
    } else {  //로그인 했을 때
      navigationView.getMenu().findItem(R.id.nav_membershipbtn)
              .setVisible(true);
      navigationView.getMenu().findItem(R.id.nav_logout)
              .setVisible(true);
      navigationView.getMenu().findItem(R.id.nav_listchk)
              .setVisible(true);
      navigationView.getMenu().findItem(R.id.nav_loginbtn)
              .setVisible(false);
      navigationView.getMenu().findItem(R.id.nav_signupbtn)
              .setVisible(false);
      navigationView.getMenu().findItem(R.id.nav_qna)
              .setVisible(true);
    }

    //Navigation Drawer(바로가기 메뉴) 아이템 클릭 이벤트 처리 내용
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //햄버거바 메뉴 누르면 이동
        if(id == R.id.nav_loginbtn){
          Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
          startActivity(intent);
        }else if(id == R.id.nav_signupbtn){
          Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
          startActivity(intent);
        }else if(id == R.id.nav_qna){
          Intent intent = new Intent(LoginActivity.this, QnAMainActivity.class);
          startActivity(intent);
        }
        return false;
      }



    });


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
          // 로그인이 되면 필요한 정보를 불러 온다.

          // 리뷰 정보 조회
          ArrayList<ReviewDTO> reviewDTOS, visitDTOS;
          MyReviewAdapter rAdapter;
          MyVisitAdapter vAdapter;
          ProgressDialog progressDialog;
          visitDTOS = new ArrayList<>();
          reviewDTOS = new ArrayList<>();
          vAdapter = new MyVisitAdapter(LoginActivity.this, visitDTOS);
          rAdapter = new MyReviewAdapter(LoginActivity.this, visitDTOS);
          progressDialog = new ProgressDialog(LoginActivity.this);
          progressDialog.setTitle("데이터 업로딩");
          progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
          progressDialog.setCanceledOnTouchOutside(false);
          progressDialog.show();

          MyVisit myVisit = new MyVisit(visitDTOS, progressDialog, vAdapter);
          myVisit.execute();
          MyReview myReview = new MyReview(reviewDTOS, progressDialog, rAdapter);
          myReview.execute();

          if(progressDialog != null){
            progressDialog.dismiss();
          }

          Log.d(TAG, "onClick: visit  " + visitDTOS.size());
          Log.d(TAG, "onClick: visit  " + reviewDTOS.size());
          Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
          intent.putExtra("visitDTOS", visitDTOS);
          intent.putExtra("reviewDTOS", reviewDTOS);
          //startActivity(intent);
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
}