package com.example.reservjava_app.reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.Common.CommonMethod;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.loginDTO;

public class Store extends AppCompatActivity {


    ArrayList<ProductDTO> productList;
    TextView name, info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        productList = new ArrayList<>();

        final Intent getintent = getIntent();
        final BusinessDTO businessDTO = (BusinessDTO) getintent
                .getSerializableExtra("businessdto");

        name = findViewById(R.id.business_name);
        info = findViewById(R.id.business_info);

        name.setText(businessDTO.getBusiness_name());
        info.setText(businessDTO.getBusiness_info());

        //테스트용 버튼 입니다.
        Button testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO == null) {
                    CommonMethod.LoginPageCall(Store.this);
                }else {
                    Intent intent = new Intent(Store.this, Reservation.class);
                    intent.putExtra("business_code", businessDTO.getBusiness_code());
                    startActivity(intent);
                    finish();
                }
            }
        });



    }
}