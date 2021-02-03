package com.example.reservjava_app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.reservjava_app.R;
import com.example.reservjava_app.category.Accommodation;
import com.example.reservjava_app.category.Exercise;
import com.example.reservjava_app.category.Hospital;
import com.example.reservjava_app.category.Restaurant;
import com.google.android.material.navigation.NavigationView;


public class HomeFragment extends Fragment {


    ImageButton hospital, restaurant, accommodation, exercise,
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
                Intent intent = new Intent(getActivity(), Hospital.class);
                startActivity(intent);
            }
        });

        //식당 카테고리
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Restaurant.class);
                startActivity(intent);
            }
        });

        //숙박시설 카테고리
        accommodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Accommodation.class);
                startActivity(intent);
            }
        });

        //운동 카테고리
        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Exercise.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}