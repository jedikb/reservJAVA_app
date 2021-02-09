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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.ATask.SearchBusiness;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.SearchBusinessAdapter;

import java.util.ArrayList;

public class WhereListActivity extends AppCompatActivity {
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

    Intent intent = getIntent();

    //searchText = intent.getStringExtra("searchText");

    //리사이클러 뷰 시작
    busiList = new ArrayList<>();
    adapter = new SearchBusinessAdapter(this, busiList);
    recyclerView = findViewById(R.id.searchRecyclerView);

    ArrayList<BusinessDTO> busiList = (ArrayList<BusinessDTO>) intent.getSerializableExtra("busiList");

    LinearLayoutManager layoutManager = new LinearLayoutManager(this,
        RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    for (BusinessDTO dto : busiList){
      //Log.d(TAG, "onCreate: " + dto.getBusiness_name());
      adapter.addItem(new BusinessDTO(dto.getBusiness_name(), dto.getBusiness_addr(), dto.getBusiness_star_avg(), dto.getBusiness_lat(), dto.getBusiness_lng() ));
    }

    recyclerView.setAdapter(adapter);

    // 상단 검색버튼
    addrSearch = findViewById(R.id.addrSearch);
    findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        searchText = "";
        searchText = addrSearch.getText().toString();
        Toast.makeText(WhereListActivity.this, searchText + "를 검색합니다", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "onClick searchText : " + searchText);

        Intent intent = new Intent(WhereListActivity.this, WhereListActivity.class);
        intent.putExtra("searchText", searchText);
        startActivity(intent);
        finish();
      }
    });
    addrSearch.requestFocus();
    // 엔터로 검색
    addrSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        ArrayList<BusinessDTO> busiList = new ArrayList<>();
        busiList = MainActivity.busiList;
        adapter.removeAllItem();

        //String searchText = null;
        searchText = addrSearch.getText().toString();
        //trim, replace"[\\n\\r]", null, "",  다 안되네;
        //  \\n, \n,
        //searchText.replace("\\n", "");
        //searchText.substring(3); // 앤 튕김..... '\n'을 문자로 인식하지 않는거 같음
        //android:singleLine="true" 임시로 이걸 적용해서 문제 해결하자.

        for(BusinessDTO dto : busiList) {
          if( dto.getBusiness_name().indexOf(searchText) >-1 || dto.getBusiness_hashtag().indexOf(searchText) >-1) {
            adapter.addItem(new BusinessDTO(dto.getBusiness_name(), dto.getBusiness_addr(), dto.getBusiness_star_avg(), dto.getBusiness_lat(), dto.getBusiness_lng() ));
          }
        }

        Toast.makeText(WhereListActivity.this, searchText + "를 검색합니다", Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(adapter);
        addrSearch.setText(null);
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