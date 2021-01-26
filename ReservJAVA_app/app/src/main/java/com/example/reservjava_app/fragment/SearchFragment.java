package com.example.reservjava_app.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.reservjava_app.Common.GpsTracker;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.b_where.WhereListActivity;
import com.example.reservjava_app.ui.f_profile.ProfileActivity;
import com.example.reservjava_app.ui.f_profile.ReviewActivity;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;


public class SearchFragment extends Fragment implements OnMapReadyCallback {

  MainActivity activity;
  private static final String TAG = "main::SearchFragment";

  private GpsTracker gpsTracker;
  private static final int PERMISSION_REQUEST_CODE = 100;
  private FusedLocationSource mLocationSource;
  private NaverMap mNaverMap;

  EditText addrSearch;

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    activity = (MainActivity) getActivity();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    activity = null;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    ViewGroup viewGroup = (ViewGroup) inflater
        .inflate(R.layout.fragment_search, container, false);

    final TextView tvAddr = (TextView)viewGroup.findViewById(R.id.tvAddr);

    addrSearch = viewGroup.findViewById(R.id.addrSearch);

    // 지도 객체 띄우기
    FragmentManager fm = getChildFragmentManager();
    MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
    if (mapFragment == null) {
      mapFragment = MapFragment.newInstance();
      fm.beginTransaction().add(R.id.map, mapFragment).commit();
    }
    // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
    // onMapReady에서 NaverMap 객체를 받음
    mapFragment.getMapAsync(this);

    // 위치를 반환하는 구현체인 FusedLocationSource 생성
    mLocationSource =
        new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

    //(임시) 누르면 현재 위치 찾는 것으로 구현해보자
    viewGroup.findViewById(R.id.setAddrBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gpsTracker = new GpsTracker(activity);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String address = activity.getCurrentAddress(latitude, longitude);
        tvAddr.setText(address);
        Toast.makeText(activity, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude))
            .animate(CameraAnimation.Easing, 2000);

        mNaverMap.moveCamera(cameraUpdate);

      }
    });


 /*   //사이드바(member)
    viewGroup.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });*/

    //검색버튼(whereList로 이동)
    viewGroup.findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(activity.getApplicationContext(), WhereListActivity.class);
        startActivity(intent);
      }
    });

/*    //주소 확정버튼(주소가 저장되었습니다 메시지 띄움)
    viewGroup.findViewById(R.id.setAddrBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getActivity(), "주소가 성공적으로 저장되었습니다", Toast.LENGTH_SHORT).show();
        activity.onFragmentChange(1);
      }
    });*/

    // (임시) 리뷰 등록 화면으로 이동
    viewGroup.findViewById(R.id.moveToReview).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(activity.getApplicationContext(), ReviewActivity.class);
        startActivity(intent);
      }
    });

    // (임시) 프로필 화면으로 이동
    viewGroup.findViewById(R.id.moveToProfile).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(loginDTO == null) {
          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
          builder.setTitle("알림");
          builder.setMessage("로그인이 필요한 페이지 입니다");
          builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              dialogInterface.dismiss();
              Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
              startActivity(intent);
            }
          });
          builder.show();

        } else {
        Intent intent = new Intent(activity.getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        }
      }
    });

    return viewGroup;
  }


  @Override
  public void onMapReady(@NonNull NaverMap naverMap) {
    Log.d( TAG, "onMapReady");

    // 초기 위치(현재 위치로 로딩)
    NaverMapOptions options = new NaverMapOptions()
        .camera(new CameraPosition(new LatLng(37.5666102, 126.9783881), 8))
        .mapType(NaverMap.MapType.Terrain);
    MapFragment mapFragment = MapFragment.newInstance(options);
    mapFragment.getMapAsync(this);

    // NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
    mNaverMap = naverMap;
    mNaverMap.setLocationSource(mLocationSource);

    gpsTracker = new GpsTracker(activity);

    double latitude = gpsTracker.getLatitude();
    double longitude = gpsTracker.getLongitude();




    Log.d(TAG, "onMapReady: " + latitude +" : " +longitude );
    // 지도상에 마커 표시
    //Marker marker = new Marker();
    //marker.setPosition(new LatLng(latitude, longitude));
    //marker.setMap(naverMap);

  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    // request code와 권한획득 여부 확인
    if (requestCode == PERMISSION_REQUEST_CODE) {
      if (grantResults.length > 0
          && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
      }
    }
  }
}