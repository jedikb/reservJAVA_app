package com.example.reservjava_app.fragment.d_bongsun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.reservjava_app.R;

public class QnAMainFragment extends AppCompatActivity {

    Button qnabtn, drawalbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_qnamain);

        //문의하기 메인화면에서 문의하기 화면띄우기
        qnabtn = findViewById(R.id.qnabtn);
        qnabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAMainFragment.this, QnAFragment.class);
                startActivity(intent);
            }
        });

        //문의하기 메인화면에서 회원탈퇴 화면띄우기
        drawalbtn = findViewById(R.id.drawalbtn);
        drawalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAMainFragment.this, MemberCancelFragment.class);
                startActivity(intent);
            }
        });
    }
}
