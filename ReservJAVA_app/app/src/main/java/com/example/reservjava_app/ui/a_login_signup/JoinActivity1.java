package com.example.reservjava_app.ui.a_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.R;

public class JoinActivity1 extends AppCompatActivity {

    String state;

    EditText Id_text, Pw_text, Pwchk_text, nick_text, Email_text, Phone_text;
    Button signupbtn, resetbtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_signup, container, false);


        //취소버튼 누르면 로그인페이지로 넘어갑니다
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity1.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //회원가입

        return root;
    }
}
