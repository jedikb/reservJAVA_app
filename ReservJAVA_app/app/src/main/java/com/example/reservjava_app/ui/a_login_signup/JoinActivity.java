package com.example.reservjava_app.ui.a_login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.reservjava_app.R;

public class JoinActivity extends AppCompatActivity {

    String state;

    EditText Id_text, Pw_text, Pwchk_text, nick_text, Email_text, Phone_text;
    Button signupbtn, resetbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //취소버튼 누르면 로그인페이지로 넘어갑니다
 /*       resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/

    }
}