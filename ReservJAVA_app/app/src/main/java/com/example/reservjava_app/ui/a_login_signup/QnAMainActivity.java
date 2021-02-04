package com.example.reservjava_app.ui.a_login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reservjava_app.R;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.d_bongsun.MemberCancelActivity;

public class QnAMainActivity extends AppCompatActivity {

    Button qnabtn, drawalbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_a_main);

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

    }
}