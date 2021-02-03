package com.example.reservjava_app.ui.b_where;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.ATask.SearchBusiness;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.SearchBusinessAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.isNetworkConnected;

public class WhereListActivity extends AppCompatActivity {
  //public static BusinessDTO busiSetItem = null;
  private static final String TAG = "main:WhereListActivity";
  SearchBusiness searchBusiness;

  ArrayList<BusinessDTO> busiList;
  RecyclerView recyclerView;
  SearchBusinessAdapter adapter;
  ProgressDialog progressDialog;
  String searchText;
  EditText addrSearch;
  Toolbar toolbar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_where_list);

//-------------------------툴바
 /*   toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //햄버거
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);
    getSupportActionBar().setTitle("주소");

    //측면메뉴
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    //측면메뉴 버튼 작업
    NavigationView navigationView = findViewById(R.id.navigation_drawer);
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        return false;
      }
    });
*/
//--------------------- 툴바 ::: 일단 안되네  나중에 하자


    Intent intent = getIntent();

    searchText = intent.getStringExtra("searchText");
    Log.d(TAG, "onCreate searchText : " + searchText);

    //리사이클러 뷰 시작
    busiList = new ArrayList<>();
    adapter = new SearchBusinessAdapter(this, busiList);
    recyclerView = findViewById(R.id.searchRecyclerView);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this,
        RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    recyclerView.setAdapter(adapter);

    if(isNetworkConnected(this) == true){
      searchBusiness = new SearchBusiness(busiList, searchText, progressDialog, adapter);
      searchBusiness.execute();
    } else {
      Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
              Toast.LENGTH_SHORT).show();
    }

    for (BusinessDTO dto : busiList){
      Log.d(TAG, "onCreate: " + dto.getBusiness_name());
      adapter.addItem(new BusinessDTO(dto.getBusiness_name(), dto.getBusiness_addr(), dto.getBusiness_star_avg()));
    }

    // 상단 검색버튼
    findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addrSearch = findViewById(R.id.addrSearch);
        searchText = "";
        searchText = addrSearch.getText().toString();
        Toast.makeText(WhereListActivity.this, searchText + "를 검색합니다", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "onClick searchText : " + searchText);

        //화면 갱신시 옆으로 이동하는 것 없애려고 했는데 일단 너무 시간이 걸려 나중으로 넘김
        Intent intent = new Intent(WhereListActivity.this, WhereListActivity.class);
        intent.putExtra("searchText", searchText);
        finish();
        startActivity(intent);

      }
    });

    findViewById(R.id.addrSearch).setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
          switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
              searchText = "";
              searchText = addrSearch.getText().toString();
              Toast.makeText(WhereListActivity.this, searchText + "를 검색합니다", Toast.LENGTH_SHORT).show();
              //Log.d(TAG, "onClick searchText : " + searchText);

              //화면 갱신시 옆으로 이동하는 것 없애려고 했는데 일단 너무 시간이 걸려 나중으로 넘김
              Intent intent = new Intent(WhereListActivity.this, WhereListActivity.class);
              intent.putExtra("searchText", searchText);
              //finish();
              startActivity(intent);
              break;
          }
          return true;
        }
        return false;
      }
    });

  }

    // 이미 화면이 있을 때 받는 곳
    @Override
    protected void onNewIntent(Intent intent) {
      super.onNewIntent(intent);
      Log.d("Sub1", "onNewIntent() 호출됨");

      // 새로고침하면서 이미지가 겹치는 현상 없애기 위해...
      adapter.removeAllItem();

      progressDialog = new ProgressDialog(this);
      progressDialog.setTitle("데이터 업로딩");
      progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
      progressDialog.setCanceledOnTouchOutside(false);
      progressDialog.show();
      processIntent(intent);
    }

    private void processIntent(Intent intent){
      if(intent != null){
        searchBusiness = new SearchBusiness(busiList, searchText, progressDialog, adapter);
        searchBusiness.execute();
      }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
    }
}