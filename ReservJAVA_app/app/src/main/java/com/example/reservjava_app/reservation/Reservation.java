package com.example.reservjava_app.reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;

import java.util.Calendar;


public class Reservation extends AppCompatActivity {

    CalendarView calendar;
    ProductDTO productDTO;
    TextView calendar_text, time_text, product_text, person_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        //Intent getintent = getIntent();
        //int business_code = Integer.parseInt(getintent.getStringExtra("business_Code"));



        //캘린더에서 날짜 값 뽑기 및 지난 날짜 회색 처리
        calendar_text = findViewById(R.id.calendar_text);
        calendar = findViewById(R.id.calendarView);

        Calendar cal = Calendar.getInstance();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                calendar_text.setText(String.format("%d년 %d월 %d일", year, month, dayOfMonth));
            }
        });



        //product → List로 담기 꽉찬 물건 색처리해야됨




        //product DB연동



        //인원 textView 숫자 처리 및 인원 제한 걸어 보기



    }
}