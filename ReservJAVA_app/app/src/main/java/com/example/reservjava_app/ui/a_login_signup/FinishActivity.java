package com.example.reservjava_app.ui.a_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;

public class FinishActivity extends AppCompatActivity {

  Button loginbtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_finish);

    loginbtn = findViewById(R.id.loginbtn);

    loginbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(FinishActivity.this, MainActivity.class);

        startActivity(intent);
      }
    });
  }
}