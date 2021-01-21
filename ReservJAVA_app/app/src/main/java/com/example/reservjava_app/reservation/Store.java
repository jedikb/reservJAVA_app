package com.example.reservjava_app.reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.StoreMenuAdapter;

import java.util.ArrayList;

public class Store extends AppCompatActivity {

    StoreMenuAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ProductDTO> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        productList = new ArrayList<>();

        recyclerView = findViewById(R.id.store_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new StoreMenuAdapter();
        recyclerView.setAdapter(adapter);
    }
}