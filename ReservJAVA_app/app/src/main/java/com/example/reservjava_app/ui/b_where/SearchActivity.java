package com.example.reservjava_app.ui.b_where;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.reservation.Store;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.f_profile.ProfileActivity;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.CompassView;
import com.naver.maps.map.widget.LocationButtonView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static com.example.reservjava_app.MainActivity.PERMISSIONS_REQUEST_CODE;
import static com.example.reservjava_app.MainActivity.busiList;
import static com.example.reservjava_app.MainActivity.curAddr;
import static com.example.reservjava_app.MainActivity.currentAddress;
import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class SearchActivity extends AppCompatActivity implements NaverMap.OnMapClickListener, Overlay.OnClickListener, OnMapReadyCallback, NaverMap.OnCameraChangeListener {

  private static final String TAG = "main::SearchActivity";
  public static BusinessDTO busiSetItem = null;

  //지도에 매장들 띄우기 위한 변수들
  ArrayList<BusinessDTO> searchBusiList;
  Double lat = null, lng = null;
  double latitude=0.0, longitude=0.0;

  private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
  private FusedLocationSource mLocationSource;
  private NaverMap mNaverMap;
  private String searchText=null, address;

  // 마커 정보 저장시킬 변수들 선언
  private ArrayList<Marker> markers = new ArrayList<>();
  Marker marker = new Marker();
  Address addr = null;
  LatLng markerPosition;
  private InfoWindow infoWindow;
  private Vector<Marker> activeMarkers;

  // 일단 Searchview는 힘드니 EditText로 기능을 구현하고 나서
  //Searchview 사용을 고민해보자..
  EditText search_addrSearch;
  TextView tvAddr;
  int newAddr = 0;
  Geocoder geocoder = new Geocoder(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    search_addrSearch = findViewById(R.id.search_addrSearch);
    tvAddr = findViewById(R.id.tvAddr);

    //(임시) 누르면 현재 위치 찾는 것으로 구현해보자(종료?)
    //자동으로 위치 찾기가 안되었을 때,, 이전 위치 띄워주었으면 좋겠다//
    //자동 refresh 되는 옵션 추가하자.

    // 누르면 새로운 주소 검색화면으로 연결하자
    findViewById(R.id.setAddrBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(SearchActivity.this, AddrListActivity.class);
        startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
      }
    });

    // 위치를 반환하는 구현체인 FusedLocationSource 생성
    mLocationSource =
        new FusedLocationSource(this, PERMISSIONS_REQUEST_CODE);

    //지도 객체 띄우기
    FragmentManager fm = getSupportFragmentManager();
    MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
    if (mapFragment == null) {   // 이거 왜 건너뛰는거지;;; null을 not null로 강제 실행 시키려 해도 안됨
      mapFragment = MapFragment.newInstance(new NaverMapOptions()
              .camera(new CameraPosition(new LatLng(curAddr.latitude, curAddr.longitude), 16, 0, 90))
          //기존에 접속 했던 곳의 위치 정보가 필요함
          );

      fm.beginTransaction().add(R.id.map, mapFragment).commit();
    }
    mapFragment.getMapAsync(this);


    //상단바 - 검색버튼(whereList로 이동)
    findViewById(R.id.search_searchBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        searchBusiList = new ArrayList<>();
        searchText = "";
        searchText = search_addrSearch.getText().toString();
        for(BusinessDTO dto : busiList) {
          if( dto.getBusiness_name().indexOf(searchText) >-1 || dto.getBusiness_hashtag().indexOf(searchText) >-1) {
            searchBusiList.add(dto);
          }
        }

        Toast.makeText(SearchActivity.this, searchText + " 매장을 검색합니다", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SearchActivity.this, WhereListActivity.class);
        intent.putExtra("searchBusiList",searchBusiList);
        startActivity(intent);
        search_addrSearch.setText(null);
        //finish();
      }
    });

    //검색명 입력하고 엔터키 입력시 검색으로 연결
    //엔터키의 경우 두번 작동하게 되는 문제가 있어 에디터 액션 이벤트를 이용한다
    search_addrSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        searchBusiList = new ArrayList<>();
        searchText = "";
        searchText = search_addrSearch.getText().toString();
        for(BusinessDTO dto : busiList) {
          if( dto.getBusiness_name().indexOf(searchText) >-1 || dto.getBusiness_hashtag().indexOf(searchText) >-1) {
            searchBusiList.add(dto);
    // (임시) 프로필 화면으로 이동
          }
        }

        Toast.makeText(SearchActivity.this, searchText + " 매장을 검색합니다", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SearchActivity.this, WhereListActivity.class);
        intent.putExtra("searchBusiList",searchBusiList);
        startActivity(intent);
        search_addrSearch.setText(null);
        //finish();
        return false;
      }
    });

    //(임시) 프로필로 이동
    findViewById(R.id.moveToProfile).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(loginDTO == null) {
          AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
          builder.setTitle("알림");
          builder.setMessage("로그인이 필요한 페이지 입니다");
          builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              dialogInterface.dismiss();
              Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
              startActivity(intent);
            }
          });
          builder.show();

        } else {
          Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
          startActivity(intent);
        }
      }
    });

    //백 버튼
    findViewById(R.id.search_backBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @UiThread
  @Override
  public void onMapReady(@NonNull NaverMap naverMap) {
    Log.d( TAG, "onMapReady");

    //이렇게 띄우면 위치 변경이 안 된다
    // NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
    mNaverMap = naverMap;
    mNaverMap.setLocationSource(mLocationSource);

    UiSettings uiSettings = naverMap.getUiSettings();
    uiSettings.setCompassEnabled(false);
    uiSettings.setLocationButtonEnabled(false);

    //나침반, 현재 위치 추적 버튼 활성화
    // 나침반 위치 변경해야 함
    CompassView compassView = findViewById(R.id.compassBtn);
    compassView.setMap(mNaverMap);
    LocationButtonView locationButtonView = findViewById(R.id.locationBtn);
    locationButtonView.setMap(mNaverMap);

    if(newAddr == 0) {    // 기본옵션(자동으로 현재 위치 불러옴)
      //주소 자동으로 입력하기
      address = currentAddress;
      address = address.substring(address.indexOf(" "));
      tvAddr.setText(address);

      // 지정된 위치로 이동
      CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(curAddr.latitude, curAddr.longitude), 16)
          .animate(CameraAnimation.Easing, 900);
      mNaverMap.setMinZoom(6.0);
      mNaverMap.setMaxZoom(18.0);
      mNaverMap.setExtent(new LatLngBounds(new LatLng(33.5, 126), new LatLng(39.35, 130)));
      mNaverMap.moveCamera(cameraUpdate);

    } else if(newAddr ==1) { //새로운 주소를 설정했을 경우
      //새로 지정한 주소로 이동 및 텍스트를 변경 한다
      // 지오코더를 이용하여 주소를 위도 경도로 변환
      List<Address> list = null;

      String str = tvAddr.getText().toString();
      try {
        list = geocoder.getFromLocationName(str, // 지역 이름
                10); // 읽을 개수
      } catch (IOException e) {
        e.printStackTrace();
        Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
      }

      if (list != null) {
        if (list.size() == 0) {
          tvAddr.setText("해당되는 주소 정보는 없습니다");
        } else {
          // 해당되는 주소로 인텐트 날리기
          Address addr = list.get(0);
          latitude = addr.getLatitude();
          longitude = addr.getLongitude();

          // 지정된 위치로 이동
          CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(latitude, longitude), 16)
              .animate(CameraAnimation.Easing, 900);
          mNaverMap.setMinZoom(6.0);
          mNaverMap.setMaxZoom(18.0);
          mNaverMap.setExtent(new LatLngBounds(new LatLng(33.5, 126), new LatLng(39.35, 130)));
          mNaverMap.moveCamera(cameraUpdate);

          //String sss = String.format("geo:%f,%f", latitude, longitude);
        }
      }
    }

    mNaverMap.addOnCameraChangeListener(SearchActivity.this);
    mNaverMap.setOnMapClickListener(SearchActivity.this);

    infoWindow = new InfoWindow();
    infoWindow.setAdapter(new InfoWindow.DefaultViewAdapter(this) {
      @NonNull
      @Override
      protected View getContentView(@NonNull InfoWindow infoWindow) {
        Marker marker = infoWindow.getMarker();
        BusinessDTO dto = (BusinessDTO) marker.getTag();

        String name = dto.getBusiness_name();
        String addr = dto.getBusiness_addr();
        String avg = String.valueOf((dto.getBusiness_star_avg())/20);
        String rNum = "123";
        int category = dto.getBusiness_category_code();

        View view = View.inflate(SearchActivity.this, R.layout.business_view_map, null);
        ImageView logo = view.findViewById(R.id.search_bLogo);

        if(category >=200 && category < 300) {
          logo.setImageResource(R.drawable.ramen);
        } else if (category == 301) {
          logo.setImageResource(R.drawable.salon);
        } else if (category >=400 && category < 500) {
          logo.setImageResource(R.drawable.beds);
        } else {    // 임시
          logo.setImageResource(R.drawable.fitness);
        }

        ((TextView) view.findViewById(R.id.search_bName)).setText(name);
        ((TextView) view.findViewById(R.id.search_bAddr)).setText(addr);
        ((TextView) view.findViewById(R.id.search_bSRateAvg)).setText(avg);
        ((TextView) view.findViewById(R.id.search_bReviewNum)).setText(rNum);
        return view;
      }
    });

    // 마커 클릭하면 매장 정보로 이동
    infoWindow.setOnClickListener(new Overlay.OnClickListener() {
      @Override
      public boolean onClick(@NonNull Overlay overlay) {
        Marker marker = infoWindow.getMarker();
        BusinessDTO dto = (BusinessDTO) marker.getTag();

        busiSetItem = dto;
        Toast.makeText(SearchActivity.this, busiSetItem.getBusiness_name(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SearchActivity.this, Store.class);
        intent.putExtra("businessdto", busiSetItem);
        startActivity(intent);
        return false;
      }
    });
  }

  //지도 위치 변경시 마커 새로 뿌림 - 처음 뿌릴 때도 이것으로 뿌리기 때문에 마커 정보를 저장 후 지도를 이동해 주어야 한다.
  @Override
  public void onCameraChange(int i, boolean b) {
    freeActiveMarkers();
    // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
    LatLng currentPosition = getCurrentPosition(mNaverMap);

    for (BusinessDTO dto: busiList) {
      markerPosition = new LatLng(dto.getBusiness_lat(), dto.getBusiness_lng());
      if (!withinSightMarker(currentPosition, markerPosition))
        continue;
      Marker marker = new Marker();
      marker.setTag(dto);
      marker.setWidth(100);
      marker.setHeight(100);
      marker.setPosition(markerPosition);
      // 마커 아이콘 설정
      if("200".equalsIgnoreCase(String.valueOf(dto.getBusiness_category_parent_code()))) {
        marker.setIcon(OverlayImage.fromResource(R.drawable.ramen));
      } else if("301".equalsIgnoreCase(String.valueOf(dto.getBusiness_category_code()))) {
        marker.setIcon(OverlayImage.fromResource(R.drawable.salon));
      } else {
        marker.setIcon(OverlayImage.fromResource(R.drawable.location));
      }
      marker.setAnchor(new PointF(0.5f, 1.1f));
      marker.setHideCollidedMarkers(true);  //겹치는 마커 자동으로 숨김
      marker.setMap(mNaverMap);
      marker.setOnClickListener(SearchActivity.this);
      activeMarkers.add(marker);
    }
  }

  //마커 클릭에 따른 이벤트
  @Override
  public boolean onClick(@NonNull Overlay overlay) {
    if(overlay instanceof Marker) {
      Marker marker = (Marker) overlay;
      if(marker.getInfoWindow() != null) {
        infoWindow.close();
      } else {
        infoWindow.open(marker);
      }
      return true;
    }
    //마커 위치로 이동  이동시키고 싶은데 그렇게 하면 마커를 새로 뿌리게 되서 인포윈도가 뜨지 않는다.
    //CameraUpdate cameraUpdate = CameraUpdate.scrollTo(marker.getPosition());
    //    .animate(CameraAnimation.Easing, 500);
    //mNaverMap.moveCamera(cameraUpdate);
    return false;
  }

  //지도 클릭했을 때 마커 사라지게
  @Override
  public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
    if(infoWindow.getMarker() != null) {
      infoWindow.close();
    }
  }

  // 현재 카메라가 보고있는 위치
  public LatLng getCurrentPosition(NaverMap naverMap) {
    CameraPosition cameraPosition = naverMap.getCameraPosition();
    return new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
  }

  // 선택한 마커의 위치가 가시거리(카메라가 보고있는 위치 반경 3km 내)에 있는지 확인
  public final static double REFERANCE_LAT = 1 / 109.958489129649955;
  public final static double REFERANCE_LNG = 1 / 88.74;
  public final static double REFERANCE_LAT_X3 = 3 / 109.958489129649955;
  public final static double REFERANCE_LNG_X3 = 3 / 88.74;
  public boolean withinSightMarker(LatLng currentPosition, LatLng markerPosition) {
    boolean withinSightMarkerLat = Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3;
    boolean withinSightMarkerLng = Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3;
    return withinSightMarkerLat && withinSightMarkerLng;
  }

  // 지도상에 표시되고있는 마커들 지도에서 삭제
  private void freeActiveMarkers() {
    if (activeMarkers == null) {
      activeMarkers = new Vector<Marker>();
      return;
    }
    for (Marker activeMarker: activeMarkers) {
      activeMarker.setMap(null);
    }
    activeMarkers = new Vector<Marker>();
  }


}