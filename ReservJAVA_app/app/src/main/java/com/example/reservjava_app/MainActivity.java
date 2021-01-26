package com.example.reservjava_app;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.reservjava_app.Common.GpsTracker;
import com.example.reservjava_app.fragment.HomeFragment;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.fragment.SearchFragment;
import com.example.reservjava_app.fragment.d_bongsun.BookingViewFragment;
import com.example.reservjava_app.fragment.d_bongsun.MemberCancelFragment;
import com.example.reservjava_app.fragment.d_bongsun.PaymentFragment;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naver.maps.map.MapFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

  HomeFragment homeFragment;
  SearchFragment searchFragment;
  ListFragment listFragment;
  BookingViewFragment bookingViewFragment;
  MemberCancelFragment memberCancelFragment;
  PaymentFragment paymentFragment;
  QnAFragment qnAFragment;

  // GPS관련

  private GpsTracker gpsTracker;
  private static final int GPS_ENABLE_REQUEST_CODE = 2001;
  private static final int PERMISSIONS_REQUEST_CODE = 100;
  String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //위험권한 실행
    checkDangerousPermissions();

    //1. 액티비티 화면이 A, B, C 를 만들어야 한다면
    //  액티비티 화면을 이름만 주어서 만든다.

    //NavigationBar Setting
    homeFragment = new HomeFragment();
    searchFragment = new SearchFragment();
    listFragment = new ListFragment();
    bookingViewFragment = new BookingViewFragment();
    memberCancelFragment = new MemberCancelFragment();
    paymentFragment = new PaymentFragment();
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
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, searchFragment).commit();
            return true;

          case R.id.listItem:
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, listFragment).commit();
            return true;
        }//switch
        return false;
      }//onNavigationItemSelected()
    });

    //네이버 맵 권한 체크(중복)
/*    if (!checkLocationServicesStatus()) {
      showDialogForLocationServiceSetting();
    }else {
      checkRunTimePermission();
    }*/

/*    //네이버맵 생성
    FragmentManager fm = getSupportFragmentManager();
    MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
    if (mapFragment == null) {
      mapFragment = MapFragment.newInstance();
      fm.beginTransaction().add(R.id.map, mapFragment).commit();
    }*/

  }//onCreat()

  // 주요 프래그먼트로 이동
  public void onFragmentChange(int state){
    if (state == 1) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, homeFragment).commit();
    } else if (state == 2) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, searchFragment).commit();
    } else if (state == 3) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, listFragment).commit();
    } else if (state == 4) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, bookingViewFragment).commit();
    } else if (state == 5) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, memberCancelFragment).commit();
    } else if (state == 6) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, paymentFragment).commit();
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

  //네이버 맵 관련
  public String getCurrentAddress( double latitude, double longitude) {

    //지오코더... GPS를 주소로 변환
    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

    List<Address> addresses;

    try {
      addresses = geocoder.getFromLocation(latitude, longitude, 7);
    } catch (IOException ioException) {
      //네트워크 문제
      Toast.makeText(MainActivity.this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
      return "지오코더 서비스 사용불가";
    } catch (IllegalArgumentException illegalArgumentException) {
      Toast.makeText(MainActivity.this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
      return "잘못된 GPS 좌표";
    }

    if (addresses == null || addresses.size() == 0) {
      Toast.makeText(MainActivity.this, "주소 미발견", Toast.LENGTH_LONG).show();
      return "주소 미발견";
    }

    Address address = addresses.get(0);
    return address.getAddressLine(0).toString()+"\n";
  }

  //여기부터는 GPS 활성화를 위한 메소드들
  private void showDialogForLocationServiceSetting() {

    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {

      case GPS_ENABLE_REQUEST_CODE:
        //사용자가 GPS 활성 시켰는지 검사
        if (checkLocationServicesStatus()) {
          if (checkLocationServicesStatus()) {
            //@@@로 하면 이름이 나오는 건가;;
            Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
            //어짜피 어플 뜰 때 체크함
            //checkRunTimePermission();
            return;
          }
        }
        break;
    }
  }

  public boolean checkLocationServicesStatus() {
    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
  }
}