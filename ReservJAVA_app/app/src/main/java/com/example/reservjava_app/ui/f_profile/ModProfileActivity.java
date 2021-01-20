package com.example.reservjava_app.ui.f_profile;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;

import java.io.File;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.pServer;
import static com.example.reservjava_app.Common.CommonMethod.imgPath;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class ModProfileActivity extends AppCompatActivity {
  private static final String TAG = "main:ModProfileActivity";

  MainActivity activity;
  EditText mod_editPW, mod_editPW2, mod_editNick, mod_editTel, mod_editEmail;
  //Button mod_saveBtn, mod_cancelBtn;
  TextView mod_tv_name, mod_tv_id;
  ImageView mod_faceImg;

  public String imagePath;

  final int CAMERA_REQUEST = 1010;
  final int LOAD_IMAGE = 1011;

  File file = null;
  long fileSize = 0;

  String member_id, member_name, member_pw, member_pw2, member_nick, member_tel, member_email;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mod_profile);
    findViewById(R.id.mod_tv_id);
    findViewById(R.id.mod_tv_name);
    findViewById(R.id.mod_editPW);
    findViewById(R.id.mod_editPW2);
    findViewById(R.id.mod_editNick);
    findViewById(R.id.mod_editTel);
    findViewById(R.id.mod_editEmail);

    //사진
    findViewById(R.id.mod_faceImg);


    imagePath = ipConfig + pServer + imgPath + loginDTO.getMember_image();
    //mod_faceImg.setVisibility(View.VISIBLE);
    //선택된 이미지 보여주기(움직이는 그림도 됨)
    //Glide.with(this).load(imagePath).into(mod_faceImg);

    //새로 불러오지 말고 loginDTO사용하자
    //수정시 확인 메시지 띄움
    //폰이라서 변경시 비번 입력할 필요는 없을 듯..
    //결제 정보 변경에는 비번이 필요
    Log.d(TAG, "onCreate: 2" +imagePath );

    member_id = loginDTO.getMember_id();
    member_pw = loginDTO.getMember_pw();
    member_name = loginDTO.getMember_name();
    member_nick = loginDTO.getMember_nick();
    member_tel = loginDTO.getMember_tel();
    member_email = loginDTO.getMember_email();
    Log.d(TAG, "onCreate: 3" + member_email);

    //아마 값을 비워야 하는 것 같음
    //mod_tv_id.setText(member_id);
    //mod_editPW.setText(member_pw);
    //mod_editPW2.setText(member_pw);
    mod_tv_name.setText("소중한 " + member_name + "님");
    /*mod_editNick.setText(member_nick);
    mod_editTel.setText(member_tel);
    mod_editEmail.setText(member_email);*/

 /*   //저장버튼(수정한 정보를 저장한다)
    findViewById(R.id.mod_saveBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if(mod_editPW == mod_editPW2) {
          member_pw = mod_editPW.getText().toString();
        } else {
          AlertDialog.Builder builder = new AlertDialog.Builder(ModProfileActivity.this);
          builder.setTitle("알림");
          builder.setMessage("비밀번호가 일치하지 않습니다");
          builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              dialogInterface.dismiss();
            }
          });
          builder.show();
          mod_editPW.requestFocus();
        }

        member_nick = mod_editNick.getText().toString();
        member_tel = mod_editTel.getText().toString();
        member_email = mod_editEmail.getText().toString();

        MemberUpdate memberUpdate = new MemberUpdate(member_id, member_pw, member_name, member_nick, member_tel, member_email);
        memberUpdate.execute();

        Toast.makeText(getApplicationContext(), "수정성공", Toast.LENGTH_LONG).show();

       *//*
        이건 이미지 변경에 들어가는게 맞을 듯?
       Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
            Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
            Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
        startActivity(showIntent);*//*

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

*//*  //이미지 변경
  public void mod_saveBtnClicked(View view){
    if(isNetworkConnected(this) == true){
      if(fileSize <= 5000000) {  // 파일크기가 5메가 보다 작아야 업로드 할수 있음


        finish();
      }else{
        // 알림창 띄움
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("파일 크기가 5MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
          }
        });
        builder.show();
      }

    }else {
      Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
          Toast.LENGTH_SHORT).show();
    }*/
  }
}