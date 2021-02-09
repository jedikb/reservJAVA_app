package com.example.reservjava_app;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.reservjava_app.ATask.SearchBusiness;
import com.example.reservjava_app.Common.GpsTracker;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.adapter.SearchBusinessAdapter;
import com.example.reservjava_app.fragment.HomeFragment;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.a_login_signup.JoinActivity;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.a_login_signup.QnAMainActivity;
import com.example.reservjava_app.ui.b_where.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.naver.maps.geometry.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "main:MainActivity";
  HomeFragment homeFragment;
  ListFragment listFragment;
  double latitude = 0, longitude= 0;
  //통신해서 값을 넘겨주기 위해
  SearchBusinessAdapter adapter;
  ProgressDialog progressDialog;
/*
  BookingViewFragment bookingViewFragment;
  MemberCancelFragment memberCancelFragment;
  PaymentFragment paymentFragment;
*/
  QnAFragment qnAFragment;
  Toolbar toolbar;
  int member_kind=0;
  public static String currentAddress = null;
  public static LatLng curAddr = null;
  public static ArrayList<BusinessDTO> busiList= null;


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

    //햄버거, 액션바 내용 변경
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);   //햄버거 아이콘 변경
    //getSupportActionBar().setTitle("");   //상단액션바(default: app_name @res.values.strings.xml)

    //측면메뉴
    //햄버거 버튼과 Navigation Drawer( 바로가기 메뉴)연결
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    //로그인 정보 표시하기



    //측면메뉴 버튼 작업
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

    //(튕김 방지 및 속도 개선을 위해) 로딩 시 매장 정보 및 현재 위치 미리 불러오기
    //1. 전체 매장 정보를 불러오기
    String searchText = "";
    busiList = new ArrayList<>();
    SearchBusiness searchBusiness = new SearchBusiness(busiList, searchText, progressDialog, adapter);
    searchBusiness.execute();

    //2. 현재 위치 불러오기
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        setCurAddress(latitude, longitude);
      }
    }, 1200);

    //3. 지도 미리 띄우기? 이것도 도움이 되려나?
    //4. 미리 불러오는 건 아니지만,, 메인 액티비티는 계속 띄워 놓는게 속도에 도움이 될까?
    //아니면 지금 미리 불러오는 자료들을 로딩하는 액티비티에 넣는 게 맞을까?
    //주소 검색창도 로딩?? 너무 많아 지는 것 아닌가?

    //NavigationBar Setting
    homeFragment = new HomeFragment();
    listFragment = new ListFragment();  //ListFragment를 ListActivity로 변경함(임시)이봉선
/*
    bookingViewFragment = new BookingViewFragment();
    memberCancelFragment = new MemberCancelFragment();
    paymentFragment = new PaymentFragment();
*/
    qnAFragment = new QnAFragment();

    getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, homeFragment).commit();    //기본 첫화면(homeFragment) 띄우기
    BottomNavigationView bottomNavigationView =
            findViewById(R.id.bottom_navigation);

    //하단바 아이템 클릭 이벤트 처리
    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent; //액티비티 콜을 위한 지역변수 선언
        switch (item.getItemId()){
          case R.id.homeItem:
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment).commit();
            return true;

          case R.id.searchItem:
                intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("busiList", busiList);
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

  }//onCreat()

  public void setCurAddress(Double latitude, Double longitude) {
    GpsTracker gpsTracker;
    gpsTracker = new GpsTracker(this);

    latitude = gpsTracker.getLatitude();
    longitude = gpsTracker.getLongitude();

    curAddr = new LatLng(latitude, longitude);
    currentAddress = getCurrentAddress(latitude, longitude);
  }

  // 주요 프래그먼트로 이동
  public void onFragmentChange(int state){
    Intent intent; //액티비티 콜을 위한 지역변수 선언
    if (state == 1) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, homeFragment).commit();
    } else if (state == 2) {
      intent = new Intent(getApplicationContext(), SearchActivity.class);
      startActivity(intent);
      finish();
    } else if (state == 3) {
      getSupportFragmentManager().beginTransaction()
              .replace(R.id.container, listFragment).commit();
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

  //현재 주소 불러오기(메인 화면 로딩할 때 미리 작업해놓는게 나은듯
  public String getCurrentAddress(double latitude, double longitude) {
    Geocoder geocoder = new Geocoder(this);
    //지오코더... GPS를 주소로 변환
    geocoder = new Geocoder(this, Locale.getDefault());
    List<Address> addresses;

    try {
      addresses = geocoder.getFromLocation(latitude, longitude, 9);
    } catch (IOException ioException) {
      //네트워크 문제
      Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
      return "지오코더 서비스 사용불가";
    } catch (IllegalArgumentException illegalArgumentException) {
      Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
      return "잘못된 GPS 좌표";
    }

    if (addresses == null || addresses.size() == 0) {
      Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
      return "주소 미발견";
    }

    Address address = addresses.get(0);
    return address.getAddressLine(0);
  }

}