package com.example.reservjava_app.ui.a_login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reservjava_app.R;

public class LogPwSearchActivity extends AppCompatActivity {

    Button IdSearchBtn, PassSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_pw_search);

        IdSearchBtn = findViewById(R.id.IdSearchBtn);
        IdSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogPwSearchActivity.this, IdSearchActivity.class);
                startActivity(intent);
            }
        });

        PassSearch = findViewById(R.id.PassSearch);
        PassSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogPwSearchActivity.this, PwSearchActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.backQnABtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogPwSearchActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}