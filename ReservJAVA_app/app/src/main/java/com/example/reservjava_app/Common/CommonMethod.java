package com.example.reservjava_app.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class CommonMethod {

  //각자 자신의 아이피를 넣으세요!!
  //봉선
  //public static String  ipConfig = "http://192.168.0.116:80";

  //광범
  public static String  ipConfig = "http://192.168.0.17:80";
  //public static String ipConfig = "http://192.168.0.34:80";

  //경선
  //public static String  ipConfig = "http://192.168.0.61:80";

  //민혁
  //public static String  ipConfig = "http://192.168.0.65:80";

  //종훈
  //public static String  ipConfig = "http://192.168.0.62:80";

  //서버 저장 경로
  //프로젝트 서버
  public static String  pServer = "/reservjava_app";

  //프로필 사진
  public static String  member_imgPath = "/resources/images/member/";
  public static String  member_upImgPath = "/resources/images/member";
  //회사로고
  public static String  logo_imgPath = "/resources/images/logo/";
  public static String  logo_upImgPath = "/resources/images/logo/";
  //회사 소개 이미지
  public static String  business_imgPath = "/resources/images/business/";
  public static String  business_upImgPath = "/resources/images/business/";
  //제품 소개 이미지
  public static String  product_imgPath = "/resources/images/product/";
  public static String  product_upImgPath = "/resources/images/product/";


  // 네트워크에 연결되어 있는가
  public static boolean isNetworkConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager)
        context.getSystemService( Context.CONNECTIVITY_SERVICE );
    NetworkInfo info = cm.getActiveNetworkInfo();
    if(info != null){
      if(info.getType() == ConnectivityManager.TYPE_WIFI){
        Log.d("isconnected : ", "WIFI 로 설정됨");
      }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
        Log.d("isconnected : ", "일반망으로 설정됨");
      }
      Log.d("isconnected : ", "OK => " + info.isConnected());
      return true;
    }else {
      Log.d("isconnected : ", "False => 데이터 통신 불가!!!" );
      return false;
    }

  }

  // 이미지 로테이트 및 사이즈 변경
  public static Bitmap imageRotateAndResize(String path){ // state 1:insert, 2:update
    BitmapFactory.Options options = new BitmapFactory.Options();
    //options.inSampleSize = 8;

    File file = new File(path);
    if (file != null) {
      // 돌아간 앵글각도 알기
      int rotateAngle = setImageOrientation(file.getAbsolutePath());
      Bitmap bitmapTmp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

      // 사진 바로 보이게 이미지 돌리기
      Bitmap bitmap = imgRotate(bitmapTmp, rotateAngle);

      return bitmap;
    }
    return null;
  }

  // 사진 찍을때 돌린 각도 알아보기 : 가로로 찍는게 기본임
  public static int setImageOrientation(String path){
    ExifInterface exif = null;
    try {
      exif = new ExifInterface(path);
    } catch (IOException e) {
      e.printStackTrace();
    }

    int oriention = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
    return oriention;
  }

  // 이미지 돌리기
  public static Bitmap imgRotate(Bitmap bitmap, int orientation){

    Matrix matrix = new Matrix();

    switch (orientation) {
      case ExifInterface.ORIENTATION_NORMAL:
        return bitmap;
      case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
        matrix.setScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_ROTATE_180:
        matrix.setRotate(180);
        break;
      case ExifInterface.ORIENTATION_FLIP_VERTICAL:
        matrix.setRotate(180);
        matrix.postScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_TRANSPOSE:
        matrix.setRotate(90);
        matrix.postScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_ROTATE_90:
        matrix.setRotate(90);
        break;
      case ExifInterface.ORIENTATION_TRANSVERSE:
        matrix.setRotate(-90);
        matrix.postScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_ROTATE_270:
        matrix.setRotate(-90);
        break;
      default:
        return bitmap;
    }
    try {
      Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
      bitmap.recycle();
      return bmRotated;
    }
    catch (OutOfMemoryError e) {
      e.printStackTrace();
      return null;
    }
  }
}