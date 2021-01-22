package com.example.reservjava_app.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.OnItemClickListener;
import com.example.reservjava_app.adapter.StoreListAdapter;
import com.example.reservjava_app.reservation.Store;

import java.util.ArrayList;

public class Restaurant extends AppCompatActivity {

    StoreListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<BusinessDTO> businessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        businessList = new ArrayList<>();

        //리사이클러뷰어 설정
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new StoreListAdapter(Restaurant.this, businessList);

        //adapter 추가 db연동해야됨!
        BusinessDTO dto;


        adapter.addItem(new BusinessDTO());




        recyclerView.setAdapter(adapter);

        //Item 클릭시 Store 페이지로 전환
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(StoreListAdapter.ViewHolder holderm, View view, int position) {
                BusinessDTO dto = adapter.getItem(position);

                //Toast.makeText(Exercise.this, "Choice Item : " + dto.getBusiness_name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Restaurant.this, Store.class);
                intent.putExtra("businessdto", dto);
                startActivity(intent);
            }
        });
    }
}