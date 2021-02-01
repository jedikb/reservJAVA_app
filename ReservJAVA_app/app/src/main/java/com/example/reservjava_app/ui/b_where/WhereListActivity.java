package com.example.reservjava_app.ui.b_where;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.ATask.SearchBusiness;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.SearchBusinessAdapter;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_where_list);

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

    //평균 int로 되어 있는거 double로 바꾸자
    //adapter.addItem(new BusinessDTO("가계 이름", "주소", 4.4));
    //adapter.addItem(new BusinessDTO("가계 이름1", "주소1", 4.54));

    for (BusinessDTO dto : busiList){
      Log.d(TAG, "onCreate: " + dto.getBusiness_name());
      //튕김
      adapter.addItem(new BusinessDTO(dto.getBusiness_name(), dto.getBusiness_addr(), dto.getBusiness_star_avg()));
      //튕김
      //adapter.addItem(dto.getBusiness_name().toString(), dto.getBusiness_addr().toString(), dto.getBusiness_star_avg());
      //
    }



/*    if(isNetworkConnected(this) == true) {
      searchBusiness = new SearchBusiness(busiList, searchText, progressDialog, adapter);
      searchBusiness.execute();
    } else {
      Toast.makeText(this, "인터넷이 연결되어 있지 않습니다",
              Toast.LENGTH_SHORT).show();
    }*/

    // 상단 검색버튼


  }

    // 이미 화면이 있을 때 받는 곳
    @Override
    protected void onNewIntent(Intent intent) {
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