package com.example.reservjava_app.ui.a_login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.ATask.JoinInsert;
import com.example.reservjava_app.R;
import com.google.android.material.navigation.NavigationView;
import com.naver.maps.map.Symbol;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class JoinActivity extends AppCompatActivity {

    String state;
    Button resetbtn, signupbtn, Idchk_btn;
    Toolbar toolbar;
    TextView Id_text, nick_text, Pw_text, Pwchk_text, Email_text, Phone_text;
    boolean vaildate = false;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //스피너 객체 선언 및 리소스를 가져오는 부분

        //findViewById(R.id.signupbt);
        resetbtn = findViewById(R.id.resetbtn);
        Pwchk_text = findViewById(R.id.Pwchk_text);
        Pw_text = findViewById(R.id.Pw_text);
        Id_text = findViewById(R.id.Id_text);
        Email_text = findViewById(R.id.Email_text);
        Phone_text = findViewById(R.id.Phone_text);
        nick_text = findViewById(R.id.nick_text);


        //회원가입 등록
        findViewById(R.id.signupbtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Id_text.getText().toString();
                String passwd = Pw_text.getText().toString();
                String passwdchk = Pwchk_text.getText().toString();
                String name = nick_text.getText().toString();
                String email = Email_text.getText().toString();
                String phonenumber =  Phone_text.getText().toString();
                //아이디 중복체크 확인


                if (Id_text.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this,"아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    Id_text.requestFocus();
                    return;
                }
                if (nick_text.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this,"닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
                    nick_text.requestFocus();
                    return;
                }
                if (Pw_text.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this,"비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    Pw_text.requestFocus();
                    return;
                }
                if (Pwchk_text.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this,"비밀번호확인을 입력하세요", Toast.LENGTH_SHORT).show();
                    Pwchk_text.requestFocus();
                    return;
                }
                //비밀번호 일치하는지 확인
                if (!Pw_text.getText().toString().equals(Pwchk_text.getText().toString())){
                    Toast.makeText(JoinActivity.this,"비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    Pw_text.setText("");
                    Pwchk_text.setText("");
                    Pw_text.requestFocus();
                    return;

                }
                if (Email_text.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this,"이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    Email_text.requestFocus();
                    return;
                }
                if (Phone_text.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this,"전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    Phone_text.requestFocus();
                    return;
                }

                setResult(RESULT_OK);


                JoinInsert joinInsert = new JoinInsert(id, passwd, passwdchk, name, email, phonenumber);
                try {
                    state = joinInsert.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this, "회원가입성공 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "회원가입성공 !!!");
                    finish();
                }else{
                    Toast.makeText(JoinActivity.this, "회원가입실패 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "회원가입실패 !!!");
                    finish();
                }
            }
        });


        findViewById(R.id.resetbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //위에 툴바 생기게 해서 네비게이션드로어 띄우고 닫을수 있게 하는 기능!
        toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(JoinActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        resetbtn = findViewById(R.id.resetbtn);
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);

        NavigationView navigationView = findViewById(R.id.loginnavigation);
        if (loginDTO == null) {  //로그인 안했을 때
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
        }

        //Navigation Drawer(바로가기 메뉴) 아이템 클릭 이벤트 처리 내용
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                //햄버거바 메뉴 누르면 이동
                if (id == R.id.nav_loginbtn) {
                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_signupbtn) {
                    Intent intent = new Intent(JoinActivity.this, JoinActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_qna) {
                    Intent intent = new Intent(JoinActivity.this, QnAMainActivity.class);
                    startActivity(intent);
                }
                return false;
            }


        });


        //비밀번호 다를 시 텍스트바 색상 변화
        Pwchk_text = findViewById(R.id.Pwchk_text);
        Pwchk_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = Pw_text.getText().toString();
                String confirm = Pwchk_text.getText().toString();

                if (password.equals(confirm)){
                    Pw_text.setBackgroundColor(Color.WHITE);
                    Pwchk_text.setBackgroundColor(Color.WHITE);
                }
                else {
                    Pw_text.setBackgroundColor(Color.GRAY);
                    Pwchk_text.setBackgroundColor(Color.GRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches())
//        {
//            Toast.makeText(JoinActivity.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
//            dialog.dismiss();
//            return;
//        }
//
//        //핸드폰번호 유효성
//        if(!Pattern.matches("^01(?:0|1|[6-9]) - (?:\\d{3}|\\d{4}) - \\d{4}$", Phone_num))
//        {
//            Toast.makeText(JoinActivity.this,"올바른 핸드폰 번호가 아닙니다.",Toast.LENGTH_SHORT).show();
//            dialog.dismiss();
//            return;
//        }
//
//        //비밀번호 유효성
//        if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", PW))
//        {
//            Toast.makeText(JoinActivity.this,"비밀번호 형식을 지켜주세요.",Toast.LENGTH_SHORT).show();
//            dialog.dismiss();
//            return;
//        }

//        //회원가입시 미입력구간 입력하라고 띄우기

    }
}