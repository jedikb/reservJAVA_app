package com.example.reservjava_app.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.StoreListAdapter;
import com.example.reservjava_app.adapter.StoreMenuAdapter;

import java.util.ArrayList;

public class Accommodation extends AppCompatActivity {

    StoreListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<BusinessDTO> businessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);

        businessList = new ArrayList<>();

        //리사이클러뷰어 설정
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new StoreListAdapter(Accommodation.this, businessList);
        
        //adapter 추가 db연동해야됨!
        BusinessDTO dto;


        adapter.addItem(new BusinessDTO());




        recyclerView.setAdapter(adapter);
    }
}