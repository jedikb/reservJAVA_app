package com.example.reservjava_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.reservjava_app.fragment.HomeFragment;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.a_login_signup.JoinActivity;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.a_login_signup.QnAMainActivity;
import com.example.reservjava_app.ui.b_where.SearchActivity;
import com.example.reservjava_app.ui.d_bongsun.BookingViewActivity;
import com.example.reservjava_app.ui.d_bongsun.MemberCancelActivity;
import com.example.reservjava_app.ui.d_bongsun.PaymentActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class ListActivity extends AppCompatActivity {

    MainActivity activity;

    //HomeFragment homeFragment;
    ListFragment listFragment;
    QnAFragment qnAFragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        EditText addSearch = (EditText) findViewById(R.id.addrSearch);
        addSearch.setText("activity_list.xml");

        //1. 액티비티 화면이 A, B, C 를 만들어야 한다면
        //  액티비티 화면을 이름만 주어서 만든다.

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   //상단액션바를 툴바로 교체

        //햄버거, 액션바 내용 변경
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);   //햄버거 아이콘 변경
        //getSupportActionBar().setTitle("주소");   //상단액션바(default: app_name @res.values.strings.xml)

        //햄버거 버튼과 Navigation Drawer( 바로가기 메뉴)연결
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
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else if(id == R.id.nav_signupbtn){
                    Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                    startActivity(intent);
                }else if(id == R.id.nav_qna){
                    Intent intent = new Intent(getApplicationContext(), QnAMainActivity.class);
                    startActivity(intent);
                }
                return false;
            }



        });

        //하단바 처리
        //homeFragment = new HomeFragment();
        listFragment = new ListFragment();
        qnAFragment = new QnAFragment();

        //getSupportFragmentManager().beginTransaction()
        //        .replace(R.id.container, homeFragment).commit();    //기본 첫화면 띄우기
        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottom_navigation);

        //하단바 아이템 클릭 이벤트 처리
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent; //액티비티 콜을 위한 지역변수 선언
                switch (item.getItemId()){
                    case R.id.homeItem:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.searchItem:
                        intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.listItem:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, listFragment).commit();
                        return true;

                    //테스트 페이지(임시) 시작
                    case R.id.testItem:
                        intent = new Intent(getApplicationContext(), ListActivity.class);
                        startActivity(intent);
                        return true;
                    //테스트 페이지(임시) 끝

                }//switch
                return false;
            }//onNavigationItemSelected()
        });

        //로그인 이동(임시)
        findViewById(R.id.tologinBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //예약 이동(임시)
        findViewById(R.id.moveBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookingViewActivity.class);
                startActivity(intent);
            }
        });

        //회원탈퇴 이동(임시)
        findViewById(R.id.moveBtn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MemberCancelActivity.class);
                startActivity(intent);
            }
        });

        //결제 이동(임시)
        findViewById(R.id.moveBtn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        //여분(임시)
        findViewById(R.id.moveBtn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.onFragmentChange(7);
            }
        });

    }//onCreat()

    // 프래그먼트 이동 메소드
    public void onFragmentChange(int state){
        Intent intent; //액티비티 콜을 위한 지역변수 선언
        if (state == 1) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (state == 2) {
            intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        } else if (state == 3) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, listFragment).commit();
        } else if (state == 4) {    //테스트 페이지(임시) state = 4
            intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        } else if (state == 7) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, qnAFragment).commit();
        }
    }

}