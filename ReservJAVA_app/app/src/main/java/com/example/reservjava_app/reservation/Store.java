package com.example.reservjava_app.reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.StoreMenuAdapter;

import java.util.ArrayList;

public class Store extends AppCompatActivity {

    StoreMenuAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ProductDTO> productList;
    TextView name, info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        productList = new ArrayList<>();

        Intent intent = getIntent();
        BusinessDTO businessDTO = (BusinessDTO) intent
                .getSerializableExtra("businessdto");

        name = findViewById(R.id.business_name);
        info = findViewById(R.id.business_info);

        name.setText(businessDTO.getBusiness_name());
        info.setText(businessDTO.getBusiness_info());

        //Toast.makeText(this, "name : " +businessDTO.getBusiness_name() +
        //        "info" + businessDTO.getBusiness_info(), Toast.LENGTH_SHORT).show();

        /*recyclerView = findViewById(R.id.store_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new StoreMenuAdapter();
        recyclerView.setAdapter(adapter);*/


    }
}