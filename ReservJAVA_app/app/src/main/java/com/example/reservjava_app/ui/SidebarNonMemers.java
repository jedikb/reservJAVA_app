package com.example.reservjava_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.reservjava_app.R;
import com.example.reservjava_app.fragment.a_login_signup.Fragment3;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.a_login_signup.SignupActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class SidebarNonMemers extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  Toolbar toolbar;
  LoginActivity loginActivity;
  SignupActivity signupActivity;
  Fragment3 fragment3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar,
        // 이부분이 다름. 임시로 넣어놓았지만 확인 필요함
        R.string.nav_app_bar_open_drawer_description,
        R.string.nav_app_bar_navigate_up_description);
    //
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    loginActivity = new LoginActivity();
    signupActivity = new SignupActivity();
    fragment3 = new Fragment3();

 /*   getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, fragment1).commit();*/

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);


    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action",
            Snackbar.LENGTH_LONG).setAction("Action", null).show();
      }
    });

  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.nav_home) {
      Toast.makeText(this,
          "로그인 메뉴 선택", Toast.LENGTH_SHORT).show();
      Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
      startActivity(intent);
    } else if (id == R.id.nav_gallery) {
      Toast.makeText(this,
          "회원가입 메뉴 선택", Toast.LENGTH_SHORT).show();
      Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
      startActivity(intent);
    } else if (id == R.id.nav_slideshow) {
      Toast.makeText(this,
          "문의하기 메뉴 선택", Toast.LENGTH_SHORT).show();
      /*onFragmentSelected(2, null);*/
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);

    return true;
  }

  // 메뉴 선택될때 프레그먼트 바꾸기
  /*public void onFragmentSelected(int position, Bundle bundle) {
    Fragment curFragment = null;

    if (position == 0) {
      curFragment = fragment1;
      toolbar.setTitle("로그인 화면");
    } else if (position == 1) {
      curFragment = fragment2;
      toolbar.setTitle("회원가입 화면");
    } else if (position == 2) {
      curFragment = fragment3;
      toolbar.setTitle("문의하기 화면");
    }

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, curFragment).commit();

  }*/

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }
}
