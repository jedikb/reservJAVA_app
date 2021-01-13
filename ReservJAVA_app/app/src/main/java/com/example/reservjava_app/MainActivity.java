package com.example.reservjava_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.reservjava_app.fragment.HomeFragment;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.fragment.SearchFragment;
import com.example.reservjava_app.fragment.b_where.WhereListFragment;
import com.example.reservjava_app.ui.f_profile.ProfileActivity;
import com.example.reservjava_app.ui.f_profile.ReviewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

  HomeFragment homeFragment;
  SearchFragment searchFragment;
  ListFragment listFragment;


  //검색과 관련된 프래그먼트(광범)
  WhereListFragment whereListFragment;
  //프로필과 관련된 프래그먼트(광범)


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

      //where
      whereListFragment = new WhereListFragment();




  }//onCreat()

  //  SearchFragment 이동(이건 나중에 다 모아서 올려야 할듯...)-일단 광범
  public void onFragmentChange(int state){
    if (state == 1) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, homeFragment).commit();
    } else if (state == 2) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, searchFragment).commit();
    } else if (state == 3) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, whereListFragment).commit();
    }else if (state == 6) {
      Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
      startActivity(intent);

    }else if (state == 11) {
      Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
      startActivity(intent);
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