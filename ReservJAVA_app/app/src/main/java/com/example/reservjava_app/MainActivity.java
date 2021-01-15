package com.example.reservjava_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.reservjava_app.fragment.HomeFragment;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
  HomeFragment homeFragment;
  SearchFragment searchFragment;
  ListFragment listFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //위험권한 실행
    checkDangerousPermissions();

    //1. 액티비티 화면이 A, B, C 를 만들어야 한다면
    //  액티비티 화면을 이름만 주어서 만든다.

    //사이드바(member) - 안됨;;
    //toolbar = (android.support.v7.widget.toolbar) findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);

    //NavigationBar Setting
    homeFragment = new HomeFragment();
    searchFragment = new SearchFragment();
    listFragment = new ListFragment();

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
        Manifest.permission.CAMERA
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

}