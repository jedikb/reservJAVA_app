package com.example.reservjava_app.ui.a_login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.reservjava_app.R;

public class PwSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_search);

        //백버튼 누르면 아이디 비번 찾기 메인화면으로 넘어감
        findViewById(R.id.pwsearchbakbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwSearchActivity.this,LogPwSearchActivity.class);
                startActivity(intent);
            }
        });
    }
}