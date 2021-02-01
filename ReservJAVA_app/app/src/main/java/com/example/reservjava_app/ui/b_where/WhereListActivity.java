package com.example.reservjava_app.ui.b_where;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.ATask.SearchBusiness;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.SearchBusinessAdapter;

import java.util.ArrayList;

public class WhereListActivity extends AppCompatActivity {
    public static BusinessDTO busiSetItem = null;

  SearchBusiness searchBusiness;

  ArrayList<BusinessDTO> busiList;
  RecyclerView recyclerView;
  SearchBusinessAdapter adapter;
  ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_where_list);

    //리사이클러 뷰 시작



  }
}