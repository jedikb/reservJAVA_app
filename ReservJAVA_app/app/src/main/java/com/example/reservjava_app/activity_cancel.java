package com.example.reservjava_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.ATask.BookingDelete;
import com.example.reservjava_app.ATask.StroInfo;
import com.example.reservjava_app.DTO.ReviewDTO;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class activity_cancel extends AppCompatActivity {
    TextView info_BUSINESS_NAME, info_PRODUCT_NAME, info_bookingdate, info_DateReserv,
            info_PRODUCT_NAME2, info_price, info_num, info_BUSINESS_NAME2, info_BUSINESS_ADDR,
            info_BUSINESS_TEL , tv9;
    Button btn_cancel , rev_detail_btn;
    LinearLayout rev_detail_rayout;
    RatingBar info_avg_star;
    Drawable img1;
    Drawable img2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ArrayList<ReviewDTO> reviewDTO =  reviewDTOS;
        setContentView(R.layout.activity_cancel);
        img1 =this.getResources().getDrawable( R.drawable.btn_plus );
        img2 =this.getResources().getDrawable( R.drawable.btn_plust );
        info_BUSINESS_NAME = findViewById(R.id.info_BUSINESS_NAME);
        info_PRODUCT_NAME = findViewById(R.id.info_PRODUCT_NAME);
        info_bookingdate = findViewById(R.id.info_bookingdate);
        info_DateReserv = findViewById(R.id.info_DateReserv);
        info_PRODUCT_NAME2 = findViewById(R.id.info_PRODUCT_NAME2);
        info_price = findViewById(R.id.info_price);
        info_BUSINESS_NAME2 = findViewById(R.id.info_BUSINESS_NAME2);
        info_BUSINESS_ADDR = findViewById(R.id.info_BUSINESS_ADDR);
        info_BUSINESS_TEL = findViewById(R.id.info_BUSINESS_TEL);
        btn_cancel  = findViewById(R.id.btn_cancel);
        rev_detail_btn = findViewById(R.id.rev_detail_btn);
        rev_detail_btn.setTag("N");
        rev_detail_rayout = findViewById(R.id.rev_detail_rayout);
        rev_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rev_detail_btn.getTag().toString().equals("N")){
                    rev_detail_btn.setTag("Y");
                    Animation animation = new AlphaAnimation(0, 1);
                    animation.setDuration(1000);
                    rev_detail_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img2, null);

                    rev_detail_rayout.setVisibility(View.VISIBLE);
                    rev_detail_rayout.setAnimation(animation);
                }else{
                    rev_detail_btn.setTag("N");
                    rev_detail_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);
                    findViewById(R.id.rev_detail_rayout).setVisibility(View.GONE);
                }
            }
        });
        info_avg_star  = findViewById(R.id.info_avg_star);

        tv9 = findViewById(R.id.tv_9);
        final Intent getintent = getIntent();
        final ReviewDTO businessDTO = (ReviewDTO) getintent
                .getSerializableExtra("reviewDTO");
        //2. 전체 매장 정보를 불러오기
        HashMap<String, String> info_Store = new HashMap<String, String>();
        StroInfo stroInfo = new StroInfo(info_Store, businessDTO.getBooking_code() + "");
        try {
            stroInfo.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            if(businessDTO.getBooking_kind() == 2 ||
                    businessDTO.getBooking_kind() == 3){
                btn_cancel.setVisibility(View.VISIBLE);
            }else{
                btn_cancel.setVisibility(View.GONE);
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
            String sstar_val = tv9.getText() + "(" + (istar_val + "") + ")";
             tv9.setText(sstar_val);
          //  int star_vv = Integer.parseInt( star_val+"" );
            info_avg_star.setRating(istar_val);

    /*    SearchBusiness searchBusiness = new SearchBusiness(busiList, "", progressDialog, adapter);
        searchBusiness.execute().get();*/
      /*  for (ReviewDTO dto : reviewDTOS) {
            if (dto.getBusiness_hashtag().indexOf(category) > -1) {
                searchBusiList.add(dto);
            }
        }*/

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_cancel.this);
                builder.setTitle("예약취소")
                        .setMessage("예약을 취소하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int whichButton){
                                String succ = "0";
                                BookingDelete bookingDelete = new BookingDelete(businessDTO.getBooking_code()+"",succ);
                                try {
                                    succ = bookingDelete.execute().get();
                                    if (succ.equals("1")){
                                        Toast.makeText(activity_cancel.this, "삭제완료 ", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(activity_cancel.this, "서버와 연결 실패.. 잠시후 시도해주세요. ", Toast.LENGTH_SHORT).show();

                                    }
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int whichButton){

                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기
            }
        });
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