package com.example.reservjava_app.ui.b_where;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.SearchBusinessAdapter;

import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.isNetworkConnected;

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

    searchText = intent.getStringExtra("searchText");
    Log.d(TAG, "onCreate searchText : " + searchText);

    //리사이클러 뷰 시작
    busiList = new ArrayList<>();
    adapter = new SearchBusinessAdapter(this, busiList);
    recyclerView = findViewById(R.id.searchRecyclerView);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this,
        RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    // 리사이클러뷰 새로고침
      final ProgressDialog progressDialog = new ProgressDialog(this);
      progressDialog.setMessage("Carimento dati...");
      progressDialog.show();

    if(isNetworkConnected(this) == true){
        searchBusiness = new SearchBusiness(busiList, searchText, progressDialog, adapter);
        searchBusiness.execute();
        adapter.notifyDataSetChanged();
    } else {
      Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
              Toast.LENGTH_SHORT).show();
    }

    for (BusinessDTO dto : busiList){
      Log.d(TAG, "onCreate: " + dto.getBusiness_name());
      adapter.addItem(new BusinessDTO(dto.getBusiness_name(), dto.getBusiness_addr(), dto.getBusiness_star_avg(), dto.getBusiness_lat(), dto.getBusiness_lng() ));
    }

    recyclerView.setAdapter(adapter);
    progressDialog.dismiss();

    // 상단 검색버튼
    addrSearch = findViewById(R.id.addrSearch);
    findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        searchText = "";
        searchText = addrSearch.getText().toString();
        Toast.makeText(WhereListActivity.this, searchText + "를 검색합니다", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onClick searchText : " + searchText);

        adapter.removeAllItem();

        if(isNetworkConnected(WhereListActivity.this) == true){
          searchBusiness = new SearchBusiness(busiList, searchText, progressDialog, adapter);
          searchBusiness.execute();
          adapter.notifyDataSetChanged();
        } else {
          Toast.makeText(WhereListActivity.this, "인터넷이 연결되어 있지 않습니다.",
              Toast.LENGTH_SHORT).show();
        }

        for (BusinessDTO dto : busiList){
          Log.d(TAG, "onCreate: " + dto.getBusiness_name());
          adapter.addItem(new BusinessDTO(dto.getBusiness_name(), dto.getBusiness_addr(), dto.getBusiness_star_avg(), dto.getBusiness_lat(), dto.getBusiness_lng()));
        }
        recyclerView.setAdapter(adapter);
      }
    });

    addrSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        searchText = "";
        searchText = addrSearch.getText().toString();
        Toast.makeText(WhereListActivity.this, searchText + "를 검색합니다", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "onClick searchText : " + searchText);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            Intent intent = new Intent(WhereListActivity.this, WhereListActivity.class);
            intent.putExtra("searchText", searchText);
            startActivity(intent);
            finish();
          }
        }, 2000);
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