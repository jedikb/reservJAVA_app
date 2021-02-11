package com.example.reservjava_app.reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.StoreListAdapter;
import com.example.reservjava_app.adapter.TimeListAdapter;
import com.example.reservjava_app.category.Exercise;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Reservation extends AppCompatActivity {

    TimeListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ProductDTO> arrayList;

    String date;
    TextView calendar_text, time_text, product_text, person_text, per;
    int person = 1, maxPerson = 5, minPerson = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        arrayList = new ArrayList<ProductDTO>();

        //Intent getintent = getIntent();
        //int business_code = Integer.parseInt(getintent.getStringExtra("business_Code"));



        //캘린더에서 날짜 값 뽑기 및 지난 날짜 회색 처리
        calendar_text = findViewById(R.id.calendar_text);

        final Calendar cal = Calendar.getInstance();

        findViewById(R.id.btn_date_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(Reservation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = String.format("%d년 %d월 %d일", year, month+1, dayOfMonth);
                        Toast.makeText(Reservation.this, date, Toast.LENGTH_SHORT).show();
                        calendar_text.setText(date);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();
            }
        });




        //시간  리사이클 뷰 처리
//        recyclerView = findViewById(R.id.recyclerView);
//        RecyclerView.LayoutManager layoutManager =
//                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new TimeListAdapter(Reservation.this, arrayList);











        //시간 하나 선택시 다른 시간 enable처리 색처리해야됨






        //product DB연동



        //인원 textView 숫자 처리 및 인원 제한 걸어 보기
        person_text = findViewById(R.id.person_text);
        per = findViewById(R.id.person);

        person_text.setText(Integer.toString(person));
        per.setText(Integer.toString(person));

        findViewById(R.id.person_Plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person++;
                if (person > maxPerson){
                    person = maxPerson;
                    Toast.makeText(Reservation.this, "최대인원수", Toast.LENGTH_SHORT).show();
                }else{
                    person_text.setText(Integer.toString(person));
                    per.setText(Integer.toString(person));
                }
            }
        });
        findViewById(R.id.person_Minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person--;
                if (person < minPerson) {
                    person = minPerson;
                    Toast.makeText(Reservation.this, "최소인원수", Toast.LENGTH_SHORT).show();
                } else {
                    person_text.setText(Integer.toString(person));
                    per.setText(Integer.toString(person));
                }
            }
        });


    }



}