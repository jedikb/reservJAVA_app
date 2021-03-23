package com.example.reservjava_app;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Handler;

import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;

import com.example.reservjava_app.Common.CommonMethod;
import com.example.reservjava_app.fragment.HomeFragment;
import com.example.reservjava_app.fragment.ListFragment;

import com.example.reservjava_app.ui.a_login_signup.JoinActivity;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.a_login_signup.QnAMainActivity;
import com.example.reservjava_app.ui.b_where.SearchActivity;
import com.example.reservjava_app.ui.f_profile.ProfileActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static com.example.reservjava_app.Common.CommonMethod.loginDTO;
import static com.example.reservjava_app.Common.CommonMethod.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main:MainActivity";

    //뒤로가기 버튼
    private long backKeyPressedTime = 0;
    private Toast toast;

    HomeFragment homeFragment;
    ListFragment listFragment;
    double latitude = 0, longitude = 0;
    BottomNavigationView bottomNavigationView ;
    Toolbar toolbar;
    int member_kind = 0;

    //사이드바
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위험권한 실행
        checkDangerousPermissions(MainActivity.this);
        //위치권한 실행
        //위치 추적 권한 체크 -> CommonMethod로
        if (!checkLocationServicesStatus(MainActivity.this)) {
            showDialogForLocationServiceSetting(MainActivity.this);
        } else {
            checkRunTimePermission(MainActivity.this);
        }
        //로그인 정보에 관한 메소드

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        SupporAction();

        // //(튕김 방지 및 속도 개선을 위해) 로딩 시 매장 정보 및 현재 위치 미리 불러오기
        // 2021.02.28 영문 -> Main -> Intro  에서 가져오게 변경 Main->CommonMethod로 변경
        //    //1. 전체 매장 정보를 불러오기

        //2. 현재 위치 불러오기
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCurAddress(MainActivity.this, latitude, longitude);
            }
        }, 1200);

        //NavigationBar Setting
        //플래그먼트 세팅????
        SetFlagMents();
        //바텀 네비게이션 세팅
        SetBottomNav();

    }//onCreat()

    //뒤로가기 버튼
    public void onBackPressed() {
        //super.onBackPressed();
        if (drawer.isOpen()) {
            drawer.close();
        } else {
            // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
            // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
            // 2500 milliseconds = 2.5 seconds
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis();
                toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
            // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
            if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
                finish();
                toast.cancel();
                toast = Toast.makeText(this, "이용해 주셔서 감사합니다.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    // 프래그먼트 이동
    public void onFragmentChange(int state) {
        Intent intent; //액티비티 콜을 위한 지역변수 선언 ??
        if (state == 1) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment).commit();
        } else if (state == 3) {
            if (loginDTO != null) {
                bottomNavigationView.setSelectedItemId(R.id.listItem);
            }else{
                Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
              //  CommonMethod.LoginPageCall(this);
            }
        }
    }

    //액티비티가 다시 활성화 되었을 때(로그인)
    @Override
    protected void onResume() {
        super.onResume();
        drawer.close();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String address = currentAddress;
                TextView addrSearch = findViewById(R.id.addrSearch);
                address = address.substring(address.indexOf(" "));
                addrSearch.setText(address);
            }
        }, 1500);

        SupporAction();
    }

    //햄버거, 액션바
    private void SupporAction() {
        toolbar = findViewById(R.id.backJoinBtn);
        setSupportActionBar(toolbar);
        //햄버거, 액션바 내용 변경
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);   //햄버거 아이콘 변경
        //getSupportActionBar().setTitle("");   //상단액션바(default: app_name @res.values.strings.xml)
        //측면메뉴
        //햄버거 버튼과 Navigation Drawer( 바로가기 메뉴)연결
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //측면메뉴 버튼 작업
        //Navigation Drawer(바로가기 메뉴) 아이템 클릭 이벤트 처리
        NavigationView navigationView = findViewById(R.id.loginnavigation);
        // 헤드드로어에 로그인 정보 표시하기
        View headerView = navigationView.getHeaderView(0);
        ImageView imageView = headerView.findViewById(R.id.header_user_pro_img);
        TextView navLoginID = headerView.findViewById(R.id.header_user_id);
        if (loginDTO != null) {
            Glide.with(this).load(loginDTO.getMember_image()).error(R.drawable.user).into(imageView);
            navLoginID.setText("반갑습니다 " + loginDTO.getMember_nick() + "님");
        }

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
                drawer.close();
                //햄버거바 메뉴 누르면 이동
                if (id == R.id.nav_loginbtn) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                   // finish();
                } else if (id == R.id.nav_signupbtn) {
                    Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_qna) {
                    /*Intent intent = new Intent(getApplicationContext(), QnAMainActivity.class);
                    startActivity(intent);*/
                } else if (id == R.id.nav_membershipbtn) {
                    if (loginDTO == null) {
                        Toast.makeText(MainActivity.this, "로그인 되지 않았습니다", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }
                } else if (id == R.id.nav_logout) {
                    //로그아웃 시 Intro 화면으로 다시 return
                    loginDTO = null;
                    SharedPreferences.Editor editor = appData.edit();
                    editor.remove("SAVE_LOGIN_DATA");
                    editor.commit();
                    Intent itent = new Intent(MainActivity.this, IntroActivity.class);
                    itent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(itent);

                } else if (id == R.id.nav_listchk) {
                    if (loginDTO != null) {
                        bottomNavigationView.setSelectedItemId(R.id.listItem);
                        //Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                    }else{
                        CommonMethod.LoginPageCall(MainActivity.this);
                    }
                }
                return false;
            }

        });
    }

    //플래그먼트 세팅
    private void SetFlagMents() {
        homeFragment = new HomeFragment();
        listFragment = new ListFragment();  //ListFragment를 ListActivity로 변경함(임시)이봉선
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, homeFragment).commit();    //기본 첫화면(homeFragment) 띄우기
    }

    //바텀 네비게이션 관련 처리
    private void SetBottomNav() {
        //하단바 아이템 클릭 이벤트 처리
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent; //액티비티 콜을 위한 지역변수 선언
                switch (item.getItemId()) {
                    case R.id.homeItem:
                        findViewById(R.id.main_app_bar).setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, homeFragment).commit();
                        return true;

                    case R.id.searchItem:
                        if (curAddr.latitude == 0.0) {
                            setCurAddress(MainActivity.this, latitude, longitude);
                        }
                        intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.listItem:
                        if (loginDTO != null) {

                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, listFragment).commit();
                        }else{
                            Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                            CommonMethod.LoginPageCall(MainActivity.this);
                        }
                        return true;
                }//switch
                return false;
            }//onNavigationItemSelected()

        });
    }
}