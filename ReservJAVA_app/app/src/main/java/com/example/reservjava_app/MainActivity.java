package com.example.reservjava_app;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.reservjava_app.ATask.MyReview;
import com.example.reservjava_app.ATask.SearchBusiness;
import com.example.reservjava_app.Common.GpsTracker;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.MemberDTO;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.adapter.MyReviewAdapter;
import com.example.reservjava_app.adapter.SearchBusinessAdapter;
import com.example.reservjava_app.fragment.HomeFragment;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.a_login_signup.JoinActivity;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.a_login_signup.QnAMainActivity;
import com.example.reservjava_app.ui.b_where.SearchActivity;
import com.example.reservjava_app.ui.f_profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.naver.maps.geometry.LatLng;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.reservjava_app.Common.CommonMethod.isNetworkConnected;
import static com.example.reservjava_app.Common.SideBar.sideBar;
import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;
import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.reviewDTOS;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "main:MainActivity";

  //뒤로가기 버튼
  private long backKeyPressedTime = 0;
  private Toast toast;
  //위치 추적
  private static final int GPS_ENABLE_REQUEST_CODE = 2001;
  String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
  public static final int PERMISSIONS_REQUEST_CODE = 100;

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
  //데이터 공유
  public static ArrayList<BusinessDTO> busiList= null;
  private SharedPreferences appData;

  //사이드바
  DrawerLayout drawer;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //위험권한 실행
    checkDangerousPermissions();
    //위치 추적 권한 체크
    if (!checkLocationServicesStatus()) {
      showDialogForLocationServiceSetting();
    } else {
      checkRunTimePermission();
    }

    //자동 로그인 정보 불러오기
    // 설정값 불러오기
    appData = getSharedPreferences("SAVE_LOGIN_DATA", MODE_PRIVATE);
    saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
    if (saveLoginData) {
      loginDTOLoad();
    }

    toolbar = findViewById(R.id.toolbar);
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

    //로그인 정보 표시하기


    sideBar(getApplicationContext());

    //측면메뉴 버튼 작업
    //Navigation Drawer(바로가기 메뉴) 아이템 클릭 이벤트 처리
    NavigationView navigationView = findViewById(R.id.loginnavigation);

//    // 헤드드로어에 로그인 정보 표시하기
//    int userLevel = 1; // 0:일반유저, 1:관리자
//    String loninID = "";
//    View headerView = navigationView.getHeaderView(0);
//    ImageView imageView = headerView.findViewById(R.id.loginImage);
//    TextView navLoginID = headerView.findViewById(R.id.loginID);
//    navLoginID.setText("반갑습니다 " + loninID);
//    // imageView.setImageResource(R.drawable.su);
//    Glide.with(this)
//            .load(R.drawable.susu)
//            .circleCrop()
//            .into(imageView);
//
//    if(userLevel == 1){
//      navigationView.getMenu().findItem(R.id.communi)
//              .setVisible(true);
//    }
//
//    FloatingActionButton fab = findViewById(R.id.fab);
//    fab.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        Snackbar.make(view, "Replace with your own action",
//                Snackbar.LENGTH_LONG).setAction("Action", null).show();
//      }
//    });

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
          drawer.close();
          Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
          startActivity(intent);
          finish();
        }else if(id == R.id.nav_signupbtn){
          drawer.close();
          Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
          startActivity(intent);
        }else if(id == R.id.nav_qna){
          drawer.close();
          Intent intent = new Intent(getApplicationContext(), QnAMainActivity.class);
          startActivity(intent);

        }else if(id == R.id.nav_membershipbtn){
          drawer.close();
          if(loginDTO == null) {
            Toast.makeText(MainActivity.this, "로그인 되지 않았습니다", Toast.LENGTH_SHORT).show();
          } else {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
          }
        }else if(id == R.id.nav_logout){
          drawer.close();
          SharedPreferences.Editor editor = appData.edit();
          editor.clear();
          editor.commit();
          loginDTO = null;
          reviewDTOS = null;
          Toast.makeText(MainActivity.this, "정상적으로 로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
          startActivity(getIntent());

        }else if(id == R.id.nav_listchk){
          drawer.close();
          getSupportFragmentManager().beginTransaction()
              .replace(R.id.container, listFragment).commit();
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
            if(curAddr.latitude == 0.0) {
              setCurAddress(latitude, longitude);
            }
            intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
            //finish();
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

  //뒤로가기 버튼
  public void onBackPressed() {
    //super.onBackPressed();
    if(drawer.isOpen()) {
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
        toast = Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG);
        toast.show();
      }
    }
  }


  // 프래그먼트 이동
  public void onFragmentChange(int state){
    Intent intent; //액티비티 콜을 위한 지역변수 선언
    if (state == 1) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, homeFragment).commit();
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
      //Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
        Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
      } else {
        ActivityCompat.requestPermissions(this, permissions, 1);
      }
    }
  }

  //--------------------- GPS 활성화를 위한 메소드들   ---------------------
  private void showDialogForLocationServiceSetting() {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("위치 서비스 비활성화");
    builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
        + "위치 설정을 수정하시겠습니까?");
    builder.setCancelable(true);
    builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int id) {
        Intent callGPSSettingIntent
            = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
      }
    });
    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });
    builder.create().show();
  }

/*  이건 밑에 것과 중복되므로 문제가 없다면 삭제 하자
  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == 1) {
      for (int i = 0; i < permissions.length; i++) {
        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
          //Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
        } else {
          Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
        }
      }
    }
  }
*/

  //권한관련해서는 이미 작업해놓은 것이 있으므로 중복
  /* ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드. */
  @Override
  public void onRequestPermissionsResult(int permsRequestCode,
                                         @NonNull String[] permissions,
                                         @NonNull int[] grandResults) {

    if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
      // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
      boolean check_result = true;

      // 모든 퍼미션을 허용했는지 체크.
      for (int result : grandResults) {
        if (result != PackageManager.PERMISSION_GRANTED) {
          check_result = false;
          break;
        }
      }

      if ( check_result ) {

      }
      else {
        // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료.

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
            || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

          Toast.makeText(this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
          finish(); // 액티비티가 아니라 종료하면 안될 거 같은데..
        }else {
          Toast.makeText(this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
        }
      }
    }
  }

  void checkRunTimePermission(){
    //런타임 퍼미션 처리
    // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
    int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION);
    int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_COARSE_LOCATION);

    if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
        hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

      // 2. 이미 퍼미션을 가지고 있다면
      // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
      // 3.  위치 값을 가져올 수 있음

    } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

      // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
        // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
        Toast.makeText(this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
        // 3-3. 사용자에게 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
            PERMISSIONS_REQUEST_CODE);

      } else {
        // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
            PERMISSIONS_REQUEST_CODE);
      }
    }
  }

  public boolean checkLocationServicesStatus() {
    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
  }
  //-------------------------------------------------------
  //------------------- 현재 위치 추적 ---------------------
  public static String currentAddress = null;
  Address address = null;
  public static LatLng curAddr = null;

  public void setCurAddress(Double latitude, Double longitude) {
    GpsTracker gpsTracker;
    gpsTracker = new GpsTracker(this);

    latitude = gpsTracker.getLatitude();
    longitude = gpsTracker.getLongitude();

    //주소가 미발견인 경우 preference에 저장된 주소를 불러온다
    appData = getSharedPreferences("SAVE_LOGIN_DATA", MODE_PRIVATE);
    SharedPreferences.Editor editor = appData.edit();
    if(saveLoginData) {
      if (latitude == 0.0 || longitude == 0.0) {
        latitude = Double.valueOf(appData.getString("member_lat", ""));
        longitude = Double.valueOf(appData.getString("member_lng", ""));
      } else {
        editor.putFloat("member_lat", Float.parseFloat(String.valueOf(latitude)));
        editor.putFloat("member_lng", Float.parseFloat(String.valueOf(longitude)));
        editor.apply();
      }
    }

    curAddr = new LatLng(latitude, longitude);
    currentAddress = getCurrentAddress(latitude, longitude);
  }

  //현재 주소를 한글로 변환(메인 화면 로딩할 때 미리 불러온다)
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

    address = addresses.get(0);
    return address.getAddressLine(0);
  }

  //로그인 정보 불러오기
  private boolean saveLoginData;
  private void loginDTOLoad() {

    saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);

    int member_code=-1, member_kind=-1;
    String member_id = "", member_pw = "",
        member_name = "", member_nick = "", member_tel = "", member_email = "", member_addr = "", member_image="";
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
    rAdapter = new MyReviewAdapter(this, reviewDTOS);

    progressDialog = new ProgressDialog(this);
    progressDialog.setTitle("데이터 업로딩");
    progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();

    if(isNetworkConnected(this) == true) {
      MyReview myReview = new MyReview(reviewDTOS, progressDialog, rAdapter);
      myReview.execute();
    } else {
      Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
    }
  }

  //액티비티가 다시 활성화 되었을 때(로그인)

  @Override
  protected void onResume() {
    super.onResume();
    drawer.close();

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

  }
}