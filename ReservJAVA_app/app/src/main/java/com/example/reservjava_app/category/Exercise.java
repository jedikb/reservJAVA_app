package com.example.reservjava_app.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.reservjava_app.ATask.SearchBusiness;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.OnItemClickListener;
import com.example.reservjava_app.adapter.StoreListAdapter;
import com.example.reservjava_app.reservation.Store;

import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.isNetworkConnected;

public class Exercise extends AppCompatActivity {

    StoreListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<BusinessDTO> businessList;
    ProgressDialog progressDialog;

    SearchBusiness listSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        businessList = new ArrayList<>();

        //리사이클러뷰어 설정
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new StoreListAdapter(Exercise.this, businessList);

        //adapter 추가 db연동해야됨!
//        if(isNetworkConnected(this) == true){
//            listSelect = new SearchBusiness(businessList, adapter, progressDialog);
//            listSelect.execute();
//        }else {
//            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
//                    Toast.LENGTH_SHORT).show();
//        }


        adapter.addItem(new BusinessDTO("test", "testInfo"));
        adapter.addItem(new BusinessDTO("test2", "testInfo"));




        recyclerView.setAdapter(adapter);

        //Item 클릭시 Store 페이지로 전환
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(StoreListAdapter.ViewHolder holderm, View view, int position) {
                BusinessDTO dto = adapter.getItem(position);

                //Toast.makeText(Exercise.this, "Choice Item : " + dto.getBusiness_name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Exercise.this, Store.class);
                intent.putExtra("businessdto", dto);
                startActivity(intent);
            }
        });
    }
}