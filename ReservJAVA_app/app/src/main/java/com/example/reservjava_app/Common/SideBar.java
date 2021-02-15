package com.example.reservjava_app.Common;

import android.content.Context;

public class SideBar {

  public static void sideBar(final Context context){

    /*NavigationView navigationView = findViewById(R.id.loginnavigation);
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
          Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
          context.startActivity(intent);
        }else if(id == R.id.nav_signupbtn){
          Intent intent = new Intent(context.getApplicationContext(), JoinActivity.class);
          context.startActivity(intent);
        }else if(id == R.id.nav_qna){
          Intent intent = new Intent(context.getApplicationContext(), QnAMainActivity.class);
          context.startActivity(intent);
        }
        return false;
      }

    });*/


  }


}
