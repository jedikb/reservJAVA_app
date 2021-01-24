package com.example.reservjava_app.ui.f_profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.reservjava_app.ATask.MemberUpdate;
import com.example.reservjava_app.Common.CommonMethod;
import com.example.reservjava_app.R;

import java.io.File;
import java.sql.Date;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.isNetworkConnected;
import static com.example.reservjava_app.Common.CommonMethod.member_imgPath;
import static com.example.reservjava_app.Common.CommonMethod.pServer;
import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class ModProfileActivity extends AppCompatActivity {
  private static final String TAG = "main:ModProfileActivity";

  EditText mod_editPW, mod_editPW2, mod_editNick, mod_editTel, mod_editEmail;
  TextView mod_tv_name, mod_tv_id;
  ImageView mod_faceImg;

  //사진 경로 지정을 위한 변수
  public String member_image;
  public String pImgDbPathU;    // 기존 DB에 있는 경로
  public String imageRealPathU = "", imageDbPathU = ""; //새로운 DB에 저장할 경로

  final int CAMERA_REQUEST = 1010;
  final int LOAD_IMAGE = 1011;

  File file = null;
  long fileSize = 0;
  java.text.SimpleDateFormat tmpDateFormat;

  // 여기까지 사진 관련


  String member_id, member_name, member_pw, member_pw2, member_nick, member_tel, member_email;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mod_profile);

    //사진
    //만약 사진 필드가 비어 있다면... 디폴트 이미지

    member_image = loginDTO.getMember_image();
    Log.d(TAG, "onCreate: 1" + member_image);
    mod_faceImg = findViewById(R.id.mod_faceImg);
    mod_faceImg.setVisibility(View.VISIBLE);
    //선택된 이미지 보여주기(움직이는 그림도 됨)
    Glide.with(ModProfileActivity.this).load(member_image).into(mod_faceImg);

    //loginDTO사용하여 정보 가지고 옴,,
    //비번은 가지고 오지 않는다.
    //단 값이 입력되어 있다면 두 값을 비교해서 같으면 저장, 아니면 다르다고 메시지 띄운다
    //수정 시 확인 메시지 띄움
    //폰이라 정보 수정에는 비번 필요 x,  결제 정보 변경에는 비번이 필요

    // ** 이미 저장 된 값을 불러와서 자동으로 표시해 줌
    //화면상에 표시할 곳을 지정해주고
    mod_tv_id = findViewById(R.id.mod_tv_id);
    mod_tv_name = findViewById(R.id.mod_tv_name);
    mod_editPW = findViewById(R.id.mod_editPW);
    mod_editPW2 = findViewById(R.id.mod_editPW2);
    mod_editNick = findViewById(R.id.mod_editNick);
    mod_editTel = findViewById(R.id.mod_editTel);
    mod_editEmail = findViewById(R.id.mod_editEmail);
    //값을 불러와서
    member_id = loginDTO.getMember_id();
    member_name = loginDTO.getMember_name();
    member_nick = loginDTO.getMember_nick();
    member_tel = loginDTO.getMember_tel();
    member_email = loginDTO.getMember_email();
    pImgDbPathU = member_image;
    //저장 된 값 화면에 표시
    mod_tv_id.setText(member_id);
    mod_tv_name.setText("소중한 " + member_name + "님");
    mod_editNick.setText(member_nick);
    mod_editTel.setText(member_tel);
    mod_editEmail.setText(member_email);

    //이미지를 클릭하면 갤러리창을 띄워 사진을 변경할 수 있게 한다
    //카메라 띄워서 바로 저장하는 건 나중에~~
    //그리고 서버에 그림 파일을 저장할 때,, 그림 파일명이 겹치지 않게 해줘야 한다.
    //**


    mod_faceImg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mod_faceImg.setVisibility(View.VISIBLE);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
    }
    });


  //이건 사진찍는 것
/*  mod_faceImg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try{
          try{
            file = createFile();
            Log.d("ModProfile:FilePath ", file.getAbsolutePath());
          }catch(Exception e){
            Log.d("ModProfile:error1", "Something Wrong", e);
          }

          mod_faceImg.setVisibility(View.VISIBLE);

          Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                FileProvider.getUriForFile(getApplicationContext(),
                    getApplicationContext().getPackageName() + ".fileprovider", file));
            Log.d("ModPro:appId", getApplicationContext().getPackageName());
          }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
          }
          if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST);
          }
        }catch(Exception e){
          Log.d("ModProfile:error2", "Something Wrong", e);
        }
      }
    }); */

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

  //사진찍기 기능의 파일 생성 메소드
/*  private File createFile() throws IOException {
    java.text.SimpleDateFormat tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

    String imageFileName = member_id + tmpDateFormat.format(new Date()) + ".jpg";
    File storageDir = Environment.getExternalStorageDirectory();
    File curFile = new File(storageDir, imageFileName);

    return curFile;
  }*/

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
      //여기는 카메라 기능이 없으니 현재 작동안함
      try {
        // 이미지 돌리기 및 리사이즈
        Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
        if(newBitmap != null){
          mod_faceImg.setImageBitmap(newBitmap);
        }else{
          Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
        }

        imageRealPathU = file.getAbsolutePath();
        String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
        imageDbPathU = ipConfig + pServer + member_image + uploadFileName;

        ImageView mod_faceImg = findViewById(R.id.mod_faceImg);
        mod_faceImg.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

        Log.d("ModProfile:picPath", file.getAbsolutePath());

      } catch (Exception e){
        e.printStackTrace();
      }
    }else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {

      try {
        String path = "";
        // Get the url from data
        Uri selectedImageUri = data.getData();
        if (selectedImageUri != null) {
          // Get the path from the Uri
          path = getPathFromURI(selectedImageUri);
        }
          Log.d(TAG, "onActivityResult: path  : " + path);

        // 이미지 돌리기 및 리사이즈
        Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
        if(newBitmap != null){
          mod_faceImg.setImageBitmap(newBitmap);
        }else{
          Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
        }

        imageRealPathU = path;
        Log.d(TAG, "onActivityResult: imageRealPathU   " + imageRealPathU);
        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        String uploadFileName = loginDTO.getMember_id() + tmpDateFormat.format(new Date(System.currentTimeMillis())) + ".jpg";
        //String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
        imageDbPathU = ipConfig + pServer + member_imgPath + uploadFileName;
        Log.d(TAG, "onActivityResult: uploadFile   " + imageDbPathU);

      } catch (Exception e){
        e.printStackTrace();
      }
    }

  }

  public String getPathFromURI(Uri contentUri) {
    String res = null;
    String[] proj = {MediaStore.Images.Media.DATA};
    Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
    if (cursor.moveToFirst()) {
      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      res = cursor.getString(column_index);
    }
    cursor.close();
    return res;
  }

  public void mod_saveBtnClicked(View view){
    if(isNetworkConnected(this) == true){
      member_pw = mod_editPW.getText().toString();
      member_pw2 = mod_editPW2.getText().toString();
      //입력한 두 비밀번호가 맞는지 확인
      if(!(member_pw.equals(member_pw2))) {
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

      } else {
        if(fileSize <= 5000000) {  // 파일크기가 5메가 보다 작아야 업로드 할수 있음

          // ** 수정 된 값을 변수에 저장
          member_id = mod_tv_id.getText().toString();
          member_pw = mod_editPW.getText().toString();
          member_nick = mod_editNick.getText().toString();
          member_tel = mod_editTel.getText().toString();
          member_email = mod_editEmail.getText().toString();
          member_image = imageDbPathU;
          loginDTO.setMember_image(member_image);
          Log.d(TAG, "mod_saveBtnClicked: image: " + member_image);

          MemberUpdate memberUpdate = new MemberUpdate(member_id, member_pw, member_nick, member_tel, member_email, member_image, pImgDbPathU, imageDbPathU, imageRealPathU);
          memberUpdate.execute();

          Toast.makeText(getApplicationContext(), "수정한 내용이 저장되었습니다", Toast.LENGTH_LONG).show();

          Intent showIntent = new Intent(getApplicationContext(), ProfileActivity.class);
          //showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
          //    Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
          //    Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
          startActivity(showIntent);

          finish();
        }else{
          // 알림창 띄움
          final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
          builder.setTitle("알림");
          builder.setMessage("파일 크기가 5MB초과하는 파일은 업로드가 제한되어 있습니다.\n5MB이하 파일로 선택해 주십시요!!!");
          builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              dialogInterface.dismiss();
            }
          });
          builder.show();
        }
      }

    } else {
      Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
          Toast.LENGTH_SHORT).show();
    }
  }

  //취소 버튼(프로필 화면으로 돌아간다)
  public void mod_cancelBtnClicked(View view){
    Toast.makeText(ModProfileActivity.this, "취소되었습니다", Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
    startActivity(intent);
    finish();
  }
}