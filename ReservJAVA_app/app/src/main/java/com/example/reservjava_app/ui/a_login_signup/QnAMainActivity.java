package com.example.reservjava_app.ui.a_login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.d_bongsun.MemberCancelActivity;
import com.example.reservjava_app.ui.f_profile.ModProfileActivity;
import com.example.reservjava_app.ui.f_profile.ProfileActivity;
import com.google.android.material.navigation.NavigationView;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class QnAMainActivity extends AppCompatActivity {

    Button qnabtn, drawalbtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_a_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //위에 툴바 생기게 해서 네비게이션드로어 띄우고 닫을수 있게 하는 기능!
        toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        qnabtn = findViewById(R.id.qnabtn);
        //문의하기 화면띄우기
        qnabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAMainActivity.this, QnAFragment.class);
                startActivity(intent);
            }
        });

        //회원탈퇴 화면띄우기
        drawalbtn = findViewById(R.id.drawalbtn);
        drawalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAMainActivity.this, MemberCancelActivity.class);
                startActivity(intent);
            }
        });

        //백버튼 누르면 메인으로
        findViewById(R.id.backBtn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //로그인 했을때와 안했을때 네비메뉴 다르게 뜨는것!
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
        }else {  //로그인 했을 때
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
                if (id == R.id.nav_loginbtn) {
                    Intent intent = new Intent(QnAMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_signupbtn) {
                    Intent intent = new Intent(QnAMainActivity.this, JoinActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_qna) {
                    Intent intent = new Intent(QnAMainActivity.this, QnAMainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_membershipbtn) {
                    Intent intent = new Intent(QnAMainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                return false;
            }


        });

    }

}