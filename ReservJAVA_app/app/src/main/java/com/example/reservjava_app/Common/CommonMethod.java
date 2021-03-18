package com.example.reservjava_app.Common;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.reservjava_app.ATask.MyReview;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.MemberDTO;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.adapter.MyReviewAdapter;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.naver.maps.geometry.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class CommonMethod {

  //각자 자신의 아이피를 넣으세요!!
  //봉선
  //public static String  ipConfig = "http://192.168.0.116:80";

  //광범
  public static String  ipConfig = "http://192.168.0.17:800";
  //public static String ipConfig = "http://192.168.0.63:80";
//192.168.219.103
  //경선
  //public static String  ipConfig = "http://192.168.0.61:80";

  //민혁
  //public static String  ipConfig = "http://192.168.0.63:80";

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

  // //(튕김 방지 및 속도 개선을 위해) 로딩 시 매장 정보 및 현재 위치 미리 불러오기
  // 2021.02.28 영문 -> Main -> Intro  에서 가져오게 변경 Main->CommonMethod로 변경
  public static MemberDTO loginDTO = null;
  public static ArrayList<BusinessDTO> busiList= null;
  public static SharedPreferences appData;
  private static boolean saveLoginData;
  public static String currentAddress = null;
  public static Address address = null;
  public static LatLng curAddr = null;

  public static final int PERMISSIONS_REQUEST_CODE = 100;
  private static final int GPS_ENABLE_REQUEST_CODE = 2001;
  private static String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

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

  // 위험권한 설정(세부적인 것은 나중에 바꾸자)2021.02.28 영문수정
  public static void checkDangerousPermissions(Activity context) {
    String[] permissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    int permissionCheck = PackageManager.PERMISSION_GRANTED;
    for (int i = 0; i < permissions.length; i++) {
      permissionCheck = ContextCompat.checkSelfPermission(context, permissions[i]);
      if (permissionCheck == PackageManager.PERMISSION_DENIED) {
        break;
      }
    }

    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
      //Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(context, "권한 없음", Toast.LENGTH_LONG).show();
      if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[0])) {
        Toast.makeText(context, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
      } else {
        ActivityCompat.requestPermissions(context, permissions, 1);
      }
    }
  }//checkDangerousPermissions

  public static void checkRunTimePermission(Activity context){
    //런타임 퍼미션 처리
    // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
    int hasFineLocationPermission = ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION);
    int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_COARSE_LOCATION);

    if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

      // 2. 이미 퍼미션을 가지고 있다면
      // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
      // 3.  위치 값을 가져올 수 있음

    } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

      // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
      if (ActivityCompat.shouldShowRequestPermissionRationale(context, REQUIRED_PERMISSIONS[0])) {
        // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
        Toast.makeText(context, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
        // 3-3. 사용자에게 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신
        ActivityCompat.requestPermissions(context, REQUIRED_PERMISSIONS,
                PERMISSIONS_REQUEST_CODE);

      } else {
        // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
        ActivityCompat.requestPermissions(context, REQUIRED_PERMISSIONS,
                PERMISSIONS_REQUEST_CODE);
      }
    }
  }//checkRunTimePermission()

  public static void showDialogForLocationServiceSetting(final Activity context) {

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("위치 서비스 비활성화");
    builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
            + "위치 설정을 수정하시겠습니까?");
    builder.setCancelable(true);
    builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int id) {
        Intent callGPSSettingIntent
                = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivityForResult(callGPSSettingIntent,GPS_ENABLE_REQUEST_CODE);
      }
    });
    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });
    builder.create().show();
  }//showDialogForLoacation

  public static void onRequestPermissionsResult(Activity context,
                                         int permsRequestCode,
                                         @NonNull String[] permissions,
                                         @NonNull int[] grandResults) {

    if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
      // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
      boolean check_result = true;

      // 모든 퍼미션을 허용했는지 체크.
      for (int result : grandResults) {
        if (result != PackageManager.PERMISSION_GRANTED) {
          check_result = false;
          break;
        }
      }

      if ( check_result ) {

      }
      else {
        // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료.

        if (ActivityCompat.shouldShowRequestPermissionRationale(context, REQUIRED_PERMISSIONS[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(context, REQUIRED_PERMISSIONS[1])) {

          Toast.makeText(context, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
  //        finish(); // 액티비티가 아니라 종료하면 안될 거 같은데..
        }else {
          Toast.makeText(context, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
        }
      }
    }
  }//onRequestPermissionResult



  public static boolean checkLocationServicesStatus(Context context) {
    LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
  }//CheckLocationServicesStutus


  public static void setCurAddress(Activity context , Double latitude, Double longitude) {
    GpsTracker gpsTracker;
    gpsTracker = new GpsTracker(context);

    latitude = gpsTracker.getLatitude();
    longitude = gpsTracker.getLongitude();

    //주소가 미발견인 경우 preference에 저장된 주소를 불러온다
    appData = context.getSharedPreferences("SAVE_LOGIN_DATA", context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appData.edit();

    if(saveLoginData) {
      if (latitude == 0.0 || longitude == 0.0) {
        latitude = Double.valueOf(appData.getString("member_lat", ""));
        longitude = Double.valueOf(appData.getString("member_lng", ""));
      } else {
        editor.putFloat("member_lat", Float.parseFloat(String.valueOf(latitude)));
        editor.putFloat("member_lng", Float.parseFloat(String.valueOf(longitude)));
        editor.apply();
      }
    }

    curAddr = new LatLng(latitude, longitude);
    currentAddress = getCurrentAddress(context , latitude, longitude);
  }

  public static String getCurrentAddress(Activity context, double latitude, double longitude) {
    Geocoder geocoder = new Geocoder(context);
    //지오코더... GPS를 주소로 변환
    geocoder = new Geocoder(context, Locale.getDefault());
    List<Address> addresses;

    try {
      addresses = geocoder.getFromLocation(latitude, longitude, 9);
    } catch (IOException ioException) {
      //네트워크 문제
      Toast.makeText(context, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
      return "지오코더 서비스 사용불가";
    } catch (IllegalArgumentException illegalArgumentException) {
      Toast.makeText(context, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
      return "잘못된 GPS 좌표";
    }

    if (addresses == null || addresses.size() == 0) {
      Toast.makeText(context, "주소 미발견", Toast.LENGTH_LONG).show();
      return "주소 미발견";
    }

    address = addresses.get(0);
    return address.getAddressLine(0);
  }


  public static void loginDTOLoad(Activity context) {

    saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);

    int member_code=-1, member_kind=-1;
    String member_id = "", member_pw = "",
            member_name = "", member_nick = "", member_tel = "", member_email = "", member_addr = "", member_image="";
    Date member_date = null;
    double member_lat = 0, member_lng= 0;

    member_code = appData.getInt("member_code", -1);
    member_id = appData.getString("member_id", "");
    member_pw = appData.getString("member_pw", "");
    member_kind = appData.getInt("member_kind", -1);
    member_name = appData.getString("member_name", "");
    member_nick = appData.getString("member_nick", "");
    member_tel = appData.getString("member_tel", "");
    member_email = appData.getString("member_email", "");
    member_addr = appData.getString("member_addr", "");
    member_image = appData.getString("member_image", "");
    SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      member_date = fm.parse(appData.getString("member_date", ""));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    member_lat = appData.getFloat("member_lat", 0);
    member_lng = appData.getFloat("member_lat", 0);

    member_lat = Double.parseDouble(String.valueOf(member_lat));
    member_lng = Double.parseDouble(String.valueOf(member_lng));

    loginDTO = new MemberDTO(member_code, member_id, member_pw, member_kind, member_name, member_nick
            , member_tel, member_email, member_addr, member_image, member_lat, member_lng, member_date);

    // 리뷰 정보 조회
    selectData(context);
  }

  public static void selectData(Activity context) {
    ArrayList<ReviewDTO> reviewDTOS;
    MyReviewAdapter rAdapter;
    ProgressDialog progressDialog;
    reviewDTOS = new ArrayList<>();
    rAdapter = new MyReviewAdapter(context, reviewDTOS);

    progressDialog = new ProgressDialog(context);
    progressDialog.setTitle("데이터 업로딩");
    progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();

    if(isNetworkConnected(context) == true) {
      MyReview myReview = new MyReview(reviewDTOS, progressDialog, rAdapter);
      try {
        myReview.execute().get();
      } catch (ExecutionException e) {
        e.printStackTrace();
        Toast.makeText(context, "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
      } catch (InterruptedException e) {
        e.printStackTrace();
        Toast.makeText(context, "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(context, "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
    }
  }


  public static void LoginInfo(Activity context){
    loginDTO = (MemberDTO)context.getIntent().getSerializableExtra("login");
    //자동 로그인 정보 불러오기
    // 설정값 불러오기
    appData = context.getSharedPreferences("SAVE_LOGIN_DATA", context.MODE_PRIVATE);
    saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
    //자동로그인 이거나 로그인 정보 있으면?
    if (saveLoginData) {
      loginDTOLoad(context);
    }
  }

  public static void LoginPageCall(final Activity context){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("알림");
    builder.setMessage("로그인이 필요한 페이지 입니다");
    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
        context.startActivity(intent);
      }
    });
    builder.show();
  }
}