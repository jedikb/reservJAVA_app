package com.example.reservjava_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.reservjava_app.fragment.HomeFragment;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.fragment.d_bongsun.BookingViewFragment;
import com.example.reservjava_app.fragment.d_bongsun.MemberCancelFragment;
import com.example.reservjava_app.fragment.d_bongsun.PaymentFragment;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.b_where.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class MainActivity extends AppCompatActivity {

  HomeFragment homeFragment;
  ListFragment listFragment;
/*
  BookingViewFragment bookingViewFragment;
  MemberCancelFragment memberCancelFragment;
  PaymentFragment paymentFragment;
*/
  QnAFragment qnAFragment;
  Toolbar toolbar;
  int member_kind=0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //위험권한 실행
    checkDangerousPermissions();

    //1. 액티비티 화면이 A, B, C 를 만들어야 한다면
    //  액티비티 화면을 이름만 주어서 만든다.

    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //햄버거
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);
    getSupportActionBar().setTitle("");

    //측면메뉴
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    //로그인 정보 표시하기



    //측면메뉴 버튼 작업
    NavigationView navigationView = findViewById(R.id.navigation_drawer);
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

    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        return false;
      }



    });


    //NavigationBar Setting
    homeFragment = new HomeFragment();
    listFragment = new ListFragment();
/*
    bookingViewFragment = new BookingViewFragment();
    memberCancelFragment = new MemberCancelFragment();
    paymentFragment = new PaymentFragment();
*/
    qnAFragment = new QnAFragment();

    getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, homeFragment).commit();
    BottomNavigationView bottomNavigationView =
            findViewById(R.id.bottom_navigation);

    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
          case R.id.homeItem:
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment).commit();
            return true;

          case R.id.searchItem:
           Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
            return true;

          case R.id.listItem:
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, listFragment).commit();
            return true;
        }//switch
        return false;
      }//onNavigationItemSelected()
    });


  }//onCreat()

  // 주요 프래그먼트로 이동
  public void onFragmentChange(int state){
    if (state == 1) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, homeFragment).commit();
    } else if (state == 2) {
      Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
      startActivity(intent);
      finish();
    } else if (state == 3) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, listFragment).commit();
/*
    } else if (state == 4) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, bookingViewFragment).commit();
    } else if (state == 5) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, memberCancelFragment).commit();
    } else if (state == 6) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, paymentFragment).commit();
*/
    } else if (state == 7) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, qnAFragment).commit();
    }
  }

  // 위험권한 설정(세부적인 것은 나중에 바꾸자)
  private void checkDangerousPermissions() {
    String[] permissions = {
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    };

    int permissionCheck = PackageManager.PERMISSION_GRANTED;
    for (int i = 0; i < permissions.length; i++) {
      permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
      if (permissionCheck == PackageManager.PERMISSION_DENIED) {
        break;
      }
    }

    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
        Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
      } else {
        ActivityCompat.requestPermissions(this, permissions, 1);
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == 1) {
      for (int i = 0; i < permissions.length; i++) {
        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
          Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
        } else {
          Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
        }
      }
    }
  }

  public String getCurrentAddress(double latitude, double longitude) {
    return "";
  }
}