package com.example.reservjava_app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.ui.b_where.WhereListActivity;

import java.util.ArrayList;
import static com.example.reservjava_app.MainActivity.busiList;

public class HomeFragment extends Fragment {
    ArrayList<BusinessDTO> searchBusiList;

    ImageView hospital, restaurant, accommodation, exercise,
        button5, button6, button7, button8;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);


        hospital = rootView.findViewById(R.id.imageButton1);
        restaurant = rootView.findViewById(R.id.imageButton2);
        accommodation = rootView.findViewById(R.id.imageButton3);
        exercise = rootView.findViewById(R.id.imageButton4);
        button5 = rootView.findViewById(R.id.imageButton5);
        button6 = rootView.findViewById(R.id.imageButton6);
        button7 = rootView.findViewById(R.id.imageButton7);
        button8 = rootView.findViewById(R.id.imageButton8);

//병원 카테고리
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBusiList = new ArrayList<>();
                Intent intent = new Intent(getActivity(), WhereListActivity.class);
                for(BusinessDTO dto : busiList) {
                    if(dto.getBusiness_hashtag().indexOf("병원") >-1 ){
                        searchBusiList.add(dto);
                    }
                }
                intent.putExtra("searchBusiList", searchBusiList);
                startActivity(intent);
            }
        });

        //식당 카테고리
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBusiList = new ArrayList<>();
                Intent intent = new Intent(getActivity(), WhereListActivity.class);
                for(BusinessDTO dto : busiList) {
                    if(dto.getBusiness_hashtag().indexOf("음식점") >-1 ){
                        searchBusiList.add(dto);
                    }
                }
                intent.putExtra("searchBusiList", searchBusiList);
                startActivity(intent);
            }
        });

        //숙박시설 카테고리
        accommodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBusiList = new ArrayList<>();
                Intent intent = new Intent(getActivity(), WhereListActivity.class);
                for(BusinessDTO dto : busiList) {
                    if(dto.getBusiness_hashtag().indexOf("숙박") >-1 ){
                        searchBusiList.add(dto);
                    }
                }
                intent.putExtra("searchBusiList", searchBusiList);
                startActivity(intent);
            }
        });

        //미용 카테고리
        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBusiList = new ArrayList<>();
                Intent intent = new Intent(getActivity(), WhereListActivity.class);
                for(BusinessDTO dto : busiList) {
                    if(dto.getBusiness_hashtag().indexOf("미용") >-1 ){
                        searchBusiList.add(dto);
                    }
                }
                intent.putExtra("searchBusiList", searchBusiList);
                startActivity(intent);
            }
        });


        return rootView;
    }
}