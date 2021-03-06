package com.example.reservjava_app.ui.b_where;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.ATask.SearchBusiness;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.SearchBusinessAdapter;

import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.busiList;

public class WhereListActivity extends AppCompatActivity {
  private static final String TAG = "main:WhereListActivity";
  SearchBusiness searchBusiness;
  ArrayList<BusinessDTO> searchBusiList;
  RecyclerView recyclerView;
  SearchBusinessAdapter adapter;
  ProgressDialog progressDialog;
  String searchText;
  EditText wl_addrSearch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_where_list);

    Intent intent = getIntent();

    //리사이클러 뷰 시작
    searchBusiList = new ArrayList<>();
    ArrayList<BusinessDTO> searchBusiList = (ArrayList<BusinessDTO>) intent.getSerializableExtra("searchBusiList");

    adapter = new SearchBusinessAdapter(this, searchBusiList);
    recyclerView = findViewById(R.id.searchRecyclerView);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this,
        RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    recyclerView.setAdapter(adapter);

    // 상단 검색버튼
    wl_addrSearch = findViewById(R.id.wl_addrSearch);
    findViewById(R.id.wl_searchBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      adapter.removeAllItem();

      searchText = wl_addrSearch.getText().toString();

      for(BusinessDTO dto : busiList) {
        if( dto.getBusiness_name().indexOf(searchText) >-1 || dto.getBusiness_hashtag().indexOf(searchText) >-1) {
          adapter.addItem(new BusinessDTO(dto.getBusiness_code(), dto.getBusiness_name(), dto.getBusiness_hashtag(), dto.getBusiness_addr(), dto.getBusiness_star_avg(), dto.getBusiness_lat(), dto.getBusiness_lng() ));
        }
      }

      Toast.makeText(WhereListActivity.this, searchText + " 매장을 검색합니다", Toast.LENGTH_SHORT).show();
      recyclerView.setAdapter(adapter);
        wl_addrSearch.setText(null);
      }
    });

    // 엔터로 검색
    wl_addrSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
      adapter.removeAllItem();

      //String searchText = null;
      searchText = wl_addrSearch.getText().toString();
      //trim, replace"[\\n\\r]", null, "",  다 안되네;
      //  \\n, \n,
      //searchText.replace("\\n", "");
      //searchText.substring(3); // 앤 튕김..... '\n'을 문자로 인식하지 않는거 같음
      //android:singleLine="true" 임시로 이걸 적용해서 문제 해결하자.

      for(BusinessDTO dto : busiList) {
        if( dto.getBusiness_name().indexOf(searchText) >-1 || dto.getBusiness_hashtag().indexOf(searchText) >-1) {
          adapter.addItem(new BusinessDTO(dto.getBusiness_code(), dto.getBusiness_name(), dto.getBusiness_hashtag(), dto.getBusiness_addr(), dto.getBusiness_star_avg(), dto.getBusiness_lat(), dto.getBusiness_lng() ));
        }
      }

      Toast.makeText(WhereListActivity.this, searchText + " 매장을 검색합니다", Toast.LENGTH_SHORT).show();
      recyclerView.setAdapter(adapter);
      wl_addrSearch.setText(null);
      wl_addrSearch.requestFocus();
      return true;
      }
    });

    //백버튼
    findViewById(R.id.wl_backBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
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

  //뒤로가기 버튼
  public void onBackPressed() {
    super.onBackPressed();
  }
}