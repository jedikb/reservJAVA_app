package com.example.reservjava_app.ui.f_profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.ATask.MemberUpdate;
import com.example.reservjava_app.DTO.MemberDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;

import java.io.File;

import static com.example.reservjava_app.Common.CommonMethod.isNetworkConnected;

public class ModProfileActivity extends AppCompatActivity {
  private static final String TAG = "main:ModProfileActivity";
  EditText mod_editPW, mod_editPW2, mod_editNick, mod_editTel, mod_editEmail;
  //Button mod_saveBtn, mod_cancelBtn;
  TextView mod_tv_name, mod_tv_id;
  ImageView faceImg;

  public String imagePath;
  public String pImgDbPathU;
  public String imageRealPathU = "", imageDbPathU = "";

  final int CAMERA_REQUEST = 1010;
  final int LOAD_IMAGE = 1011;

  File file = null;
  long fileSize = 0;


  // 사진 정보 추가
  String member_id, member_name, member_pw, member_pw2, member_nick, member_tel, member_email;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mod_profile);
    Log.d(TAG, "onCreate: 1");
    findViewById(R.id.mod_tv_id);
    findViewById(R.id.mod_tv_name);
    findViewById(R.id.mod_editPW);
    findViewById(R.id.mod_editPW2);
    findViewById(R.id.mod_editNick);
    findViewById(R.id.mod_editTel);
    findViewById(R.id.mod_editEmail);

    findViewById(R.id.faceImg);

    //보내온 값 파싱
    Intent intent = getIntent();
    MemberDTO updateMDTO = (MemberDTO) intent.getSerializableExtra("updateMDTO");

    Log.d(TAG, "onCreate: 2"+ updateMDTO.getMember_email());
    member_id = updateMDTO.getMember_id();
    member_pw = updateMDTO.getMember_pw();
    member_name = updateMDTO.getMember_name();
    member_nick = updateMDTO.getMember_nick();
    member_tel = updateMDTO.getMember_tel();
    member_email = updateMDTO.getMember_email();
    Log.d(TAG, "onCreate: 3" + member_email);

    mod_tv_id.setText(member_id);
    mod_editPW.setText(member_pw);
    mod_editPW2.setText(member_pw);
    mod_tv_name.setText("소중한 " + member_name + "님");
    mod_editNick.setText(member_nick);
    mod_editTel.setText(member_tel);
    mod_editEmail.setText(member_email);

/*    //저장버튼(수정한 정보를 저장한다)
    findViewById(R.id.mod_saveBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {



        //수정한 정보를 저장하고 프로필 화면으로 이동
        Toast.makeText(ModProfileActivity.this, "수정한 내용이 저장되었습니다", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
      }
    });*/

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
/*
    findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
      }
    });
*/

  }
  //서장
  public void mod_saveBtnClicked(View view){
    if(isNetworkConnected(this) == true){
      if(fileSize <= 5000000) {  // 파일크기가 5메가 보다 작아야 업로드 할수 있음
        member_id = mod_tv_id.getText().toString();
        member_pw = mod_editPW.getText().toString();
        member_name = mod_tv_name.getText().toString();
        member_nick = mod_editNick.getText().toString();
        member_tel = mod_editTel.getText().toString();
        member_email = mod_editEmail.getText().toString();

        MemberUpdate memberUpdate = new MemberUpdate(member_id, member_pw, member_name, member_nick, member_tel, member_email);
        memberUpdate.execute();

        Toast.makeText(getApplicationContext(), "수정성공", Toast.LENGTH_LONG).show();

        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
            Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
            Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
        startActivity(showIntent);

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
    }
  }
}