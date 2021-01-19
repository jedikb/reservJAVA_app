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

        recyclerView = findViewById(R.id.store_recycler);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StoreListAdapter(Accommodation.this, businessList);

        //adapter 추가 db연동해야됨!
        BusinessDTO dto = new BusinessDTO();
        dto.setBusiness_name("test");
        dto.setBusiness_info("testSet");
        adapter.addItem(dto);




        recyclerView.setAdapter(adapter);
    }
}