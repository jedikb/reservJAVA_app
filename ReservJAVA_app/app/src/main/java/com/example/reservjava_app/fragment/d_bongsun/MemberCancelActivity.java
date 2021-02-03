package com.example.reservjava_app.fragment.d_bongsun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reservjava_app.R;
import com.example.reservjava_app.ui.a_login_signup.QnAMainActivity;

public class MemberCancelActivity extends AppCompatActivity {

    Button backQnABtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_cancel);
         findViewById(R.id.backQnABtn).setOnClickListener(new View.OnClickListener() {
             @Override
             //백버튼 누르면 문의하기 화면으로 넘어가기
             public void onClick(View v) {
                 Intent intent = new Intent(MemberCancelActivity.this, QnAMainActivity.class);
                 startActivity(intent);
             }
         });
    }
}