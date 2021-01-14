package com.example.reservjava_app.ui.f_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.R;

public class ModProfileActivity extends AppCompatActivity {

  TextView mod_tv_name, mod_tv_pw, mod_tv_pw2, mod_tv_nic, mod_tv_phoneNum;
  EditText editPW, editPW2, editNicName, editPhoneNum;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mod_profile);

    findViewById(R.id.mod_tv_name);
    findViewById(R.id.mod_tv_pw);
    findViewById(R.id.mod_tv_pw2);
    findViewById(R.id.mod_tv_nic);
    findViewById(R.id.mod_tv_phoneNum);
    findViewById(R.id.mod_editPW);
    findViewById(R.id.mod_editPW2);
    findViewById(R.id.mod_editNicName);
    findViewById(R.id.mod_editPhoneNum);

    //저장버튼(수정한 정보를 저장한다)
    findViewById(R.id.mod_saveBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //수정한 정보를 저장하고 프로필 화면으로 이동
        Toast.makeText(ModProfileActivity.this, "수정한 내용이 저장되었습니다", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
      }
    });

    //취소 버튼(프로필 화면으로 돌아간다)
    findViewById(R.id.mod_cancelBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ModProfileActivity.this, "취소되었습니다", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
      }
    });

    //뒤로가기 버튼(프로필 화면으로 돌아간다)
    findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
      }
    });

  }
}