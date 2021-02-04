package com.example.reservjava_app.ui.a_login_signup;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.d_bongsun.MemberCancelActivity;

public class QnAMainActivity extends AppCompatActivity {

    Button qnabtn, drawalbtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_a_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //위에 툴바 생기게 해서 네비게이션드로어 띄우고 닫을수 있게 하는 기능!
        toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        qnabtn = findViewById(R.id.qnabtn);
        //문의하기 화면띄우기
        qnabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAMainActivity.this, QnAFragment.class);
                startActivity(intent);
            }
        });

        //회원탈퇴 화면띄우기
        drawalbtn = findViewById(R.id.drawalbtn);
        drawalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAMainActivity.this, MemberCancelActivity.class);
                startActivity(intent);
            }
        });

        //백버튼 누르면 메인으로
        findViewById(R.id.backBtn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}