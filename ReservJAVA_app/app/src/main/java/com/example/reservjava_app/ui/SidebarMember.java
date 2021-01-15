package com.example.reservjava_app.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.reservjava_app.R;
import com.example.reservjava_app.fragment.a_login_signup.Fragment3;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.a_login_signup.SignupActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class SidebarMember extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    LoginActivity fragment1;
    SignupActivity fragment2;
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
          R.string.nav_app_bar_open_drawer_description,
          R.string.nav_app_bar_navigate_up_description);
      drawer.addDrawerListener(toggle);
      toggle.syncState();

/*      fragment1 = new LoginFragment();
      fragment2 = new SignupFragment();
      fragment3 = new Fragment3();

      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, fragment1).commit();*/

      NavigationView navigationView = findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);

      // 헤드드로어에 로그인 정보 표시하기
      int userLevel = 1; // 0:일반유저, 1:관리자
      String loninID = "문경선";
      View headerView = navigationView.getHeaderView(0);
      ImageView imageView = headerView.findViewById(R.id.loginImage);
      TextView navLoginID = headerView.findViewById(R.id.loginID);
      navLoginID.setText("반갑습니다 " + loninID + "님");
      // imageView.setImageResource(R.drawable.su);
      Glide.with(this)
          .load(R.drawable.susu)
          .circleCrop()
          .into(imageView);

      if(userLevel == 1){
        navigationView.getMenu().findItem(R.id.communi)
            .setVisible(true);
      }

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

/*      if (id == R.id.nav_home) {
        Toast.makeText(this,
            "회원정보 메뉴 선택", Toast.LENGTH_SHORT).show();
        onFragmentSelected(0, null);
      } else if (id == R.id.nav_gallery) {
        Toast.makeText(this,
            "예약확인 메뉴 선택", Toast.LENGTH_SHORT).show();
        onFragmentSelected(1, null);
      } else if (id == R.id.nav_slideshow) {
        Toast.makeText(this,
            "문의하기 메뉴 선택", Toast.LENGTH_SHORT).show();
        onFragmentSelected(2, null);
      } else if (id == R.id.nav_slideshow) {
        Toast.makeText(this,
            "로그아웃 메뉴 선택", Toast.LENGTH_SHORT).show();
        onFragmentSelected(3, null);
      }*/

      DrawerLayout drawer = findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);

      return true;
    }


/*    public void onFragmentSelected(int position, Bundle bundle) {
      Fragment curFragment = null;

      if (position == 0) {
        curFragment = fragment1;
        toolbar.setTitle("첫번째 화면");
      } else if (position == 1) {
        curFragment = fragment2;
        toolbar.setTitle("두번째 화면");
      } else if (position == 2) {
        curFragment = fragment3;
        toolbar.setTitle("세번째 화면");
      } else if (position == 3) {
        curFragment = fragment1;
        toolbar.setTitle("네번째 화면");
      } else if (position == 4) {
        curFragment = fragment2;
        toolbar.setTitle("다섯번째 화면");
      } else if (position == 5) {
        curFragment = fragment3;
        toolbar.setTitle("여섯번째 화면");
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
