package com.example.reservjava_app.ui.f_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.ATask.BookingDelete;
import com.example.reservjava_app.ATask.StroInfo;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.activity_cancel;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ReviewDetailActivity extends AppCompatActivity {
  TextView info_BUSINESS_NAME, info_PRODUCT_NAME, info_bookingdate, info_DateReserv,
      info_PRODUCT_NAME2, info_price, info_BUSINESS_NAME2, info_BUSINESS_ADDR,
      info_BUSINESS_TEL , tv9;
  RatingBar info_avg_star;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_review_detail);

    info_BUSINESS_NAME = findViewById(R.id.info_BUSINESS_NAME);
    info_PRODUCT_NAME = findViewById(R.id.info_PRODUCT_NAME);
    info_bookingdate = findViewById(R.id.info_bookingdate);
    info_DateReserv = findViewById(R.id.info_DateReserv);
    info_PRODUCT_NAME2 = findViewById(R.id.info_PRODUCT_NAME2);
    info_price = findViewById(R.id.info_price);
    info_BUSINESS_NAME2 = findViewById(R.id.info_BUSINESS_NAME2);
    info_BUSINESS_ADDR = findViewById(R.id.info_BUSINESS_ADDR);
    info_BUSINESS_TEL = findViewById(R.id.info_BUSINESS_TEL);
    info_avg_star  = findViewById(R.id.info_avg_star);
    tv9 = findViewById(R.id.tv_9);
    final Intent getintent = getIntent();
    final ReviewDTO businessDTO = (ReviewDTO) getintent
        .getSerializableExtra("reviewDTO");

/*    Intent intent = getIntent();
    ReviewDTO reviewDTO = (ReviewDTO) intent.getSerializableExtra("reviewDTO");*/

    HashMap<String, String> info_Store = new HashMap<String, String>();
    StroInfo stroInfo = new StroInfo(info_Store, businessDTO.getBooking_code() + "");
    try {
      stroInfo.execute().get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    info_BUSINESS_NAME.setText(SetNull(info_Store.get("BUSINESS_NAME")));
    info_PRODUCT_NAME.setText(SetNull(info_Store.get("PRODUCT_NAME")));
    info_bookingdate.setText(SetNull(info_Store.get("booking_date")));
    info_DateReserv.setText(SetNull(info_Store.get("BOOKING_DATE_RESERVATION")));
    info_PRODUCT_NAME2.setText(SetNull(info_Store.get("PRODUCT_NAME")));
    info_price.setText(SetNull(info_Store.get("booking_price")));
    info_BUSINESS_NAME2.setText(SetNull(info_Store.get("BUSINESS_NAME")));
    info_BUSINESS_ADDR.setText(SetNull(info_Store.get("business_addr")));
    info_BUSINESS_TEL.setText(SetNull(info_Store.get("business_tel")));
    float istar_val = SetFloat(info_Store.get("avg_star")) / 20;
    String sstar_val = tv9.getText() + " : " + (istar_val + "");
    tv9.setText(sstar_val);
    info_avg_star.setRating(istar_val);
  }

  private String SetNull(String data) {
    if (data == null || data.length() < 1) {
      return "정보 없음";
    } else {
      return data;
    }
  }
  private Float SetFloat(String data) {
    if (data == null || data.length() < 1) {
      return 0f;
    } else {
      return Float.parseFloat(data);
    }
  }
}