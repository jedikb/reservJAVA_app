package com.example.reservjava_app.ui.a_login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.d_bongsun.MemberCancelActivity;
import static com.example.reservjava_app.Common.CommonMethod.loginDTO;

public class QnAMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_a_main);

      //  qnabtn = findViewById(R.id.qnabtn);
       /* qnAFragment =new QnAFragment();
        //문의하기 화면띄우기
        qnabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  *//*              getSupportFragmentManager().beginTransaction()
                    .replace(MainActivity, qnAFragment).commit();*//*
            }
        });*/

        //회원탈퇴 화면띄우기
/*        drawalbtn = findViewById(R.id.drawalbtn);
        drawalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAMainActivity.this, MemberCancelActivity.class);
                startActivity(intent);
            }
        });*/

        //백버튼 누르면 메인으로
        //backQnAbtn = findViewById(R.id.backQnAbtn);
/*        backQnAbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

    }

}