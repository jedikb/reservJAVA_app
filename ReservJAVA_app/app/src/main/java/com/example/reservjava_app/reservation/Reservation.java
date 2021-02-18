package com.example.reservjava_app.reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.ATask.ProductSelect;
import com.example.reservjava_app.ATask.Timelist;
import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.ProductAdapter;
import com.example.reservjava_app.adapter.TimeListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class Reservation extends AppCompatActivity {

    ProductSelect productSelect;
    Timelist timelistAsync;
    TimeListAdapter timeListAdapter;
    ProductAdapter productAdapter;
    ProgressDialog progressDialog;
    RecyclerView recyclerView_product, recyclerView_time;
    ArrayList<ProductDTO> productList;
    ArrayList<String> timeList;

    String date;
    TextView calendar_text, time_text, product_text, person_text, per;
    int person = 1, maxPerson = 5, minPerson = 0;
    int business_code;

    Button reservBtn, cancleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent getintent = getIntent();
        business_code = getintent.getIntExtra("business_code", -1);





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
        if(business_code != -1){
            timeList = new ArrayList<>();

            timeListAdapter = new TimeListAdapter(Reservation.this, timeList);

            recyclerView_time = findViewById(R.id.time_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            recyclerView_time.setLayoutManager(layoutManager);

            recyclerView_time.setAdapter(timeListAdapter);


            timelistAsync = new Timelist(timeList, timeListAdapter, progressDialog, business_code);
            try {
                timelistAsync.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }









        //product select 연결



        
        //상품리스트 표시


        if(business_code != -1){
            productList = new ArrayList<>();

            productAdapter = new ProductAdapter(this, productList);
            recyclerView_product = findViewById(R.id.product_list);

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerView_product.setLayoutManager(layoutManager1);



            recyclerView_product.setAdapter(productAdapter);

            productSelect = new ProductSelect(productAdapter, productList, progressDialog, business_code);
            try {
                productSelect.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }






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




        reservBtn = findViewById(R.id.rev_choice);
        reservBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancleBtn = findViewById(R.id.rev_cancle);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreat()




}