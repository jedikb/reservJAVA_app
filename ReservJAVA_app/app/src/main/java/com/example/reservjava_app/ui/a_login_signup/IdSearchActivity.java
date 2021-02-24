package com.example.reservjava_app.ui.a_login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reservjava_app.R;

public class IdSearchActivity extends AppCompatActivity {

    Button backBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search);

        //백버튼 누르면 아이디 비번 찾기 메인화면으로 넘어감
        findViewById(R.id.idsearchbakbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IdSearchActivity.this,LogPwSearchActivity.class);
                startActivity(intent);
            }
        });
    }
}