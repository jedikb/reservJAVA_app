package com.example.reservjava_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.ATask.Send_Store;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.activity_cancel;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.reservation.Reservation;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.reservjava_app.Common.CommonMethod.busiList;
import static com.example.reservjava_app.ATask.MyReview.reviewDTOS;

public class ReserveListAdapter extends
    RecyclerView.Adapter<ReserveListAdapter.ItemViewHolder> {
  private static final String TAG = "main:MyReviewAdapter";

  Context mContext;
  ArrayList<ReviewDTO> adapterDTOS;
  LinearLayout parentLayout;
  Button  viewStore, viewOrder , send_store;
  int position;
  ListFragment listFragment;
  public ReserveListAdapter(Context mContext, ArrayList<ReviewDTO> adapterDTOS , ListFragment listFragment) {
    this.mContext = mContext;
    this.adapterDTOS = adapterDTOS;
    this.listFragment = listFragment;
  }
  public ReserveListAdapter(Context mContext, ArrayList<ReviewDTO> adapterDTOS ) {
    this.mContext = mContext;
    this.adapterDTOS = adapterDTOS;
  }

  public void reFresh(){
    listFragment.Refresh();
  }

  //화면 연결
  @NonNull
  @Override
  public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.list_flgment_view, parent, false);

      viewStore = itemView.findViewById(R.id.viewStore);
      viewOrder = itemView.findViewById(R.id.viewOrder);
      send_store = itemView.findViewById(R.id.store_send_btn);
      viewOrder.setText("예약 상세 정보");

    return new ItemViewHolder(itemView);
  }


  //데이터 연결
  @Override
  public void onBindViewHolder(@NonNull ReserveListAdapter.ItemViewHolder holder, final int position) {
    Log.d(TAG, "onBindViewHolder: " + position);

    final ReviewDTO reviewDTO = adapterDTOS.get(position);
    holder.setItem(reviewDTO);
    viewStore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int code = reviewDTO.getBooking_business_code();
        BusinessDTO busidto = null;

        for (BusinessDTO dto: busiList) {
          if(code == dto.getBusiness_code()) {
              busidto = dto;
            break;
          }
        }

        //Toast.makeText(mContext, busidto.getBusiness_name(), Toast.LENGTH_SHORT).show();
      }
    });

    viewOrder.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final ReviewDTO reviewDTO = adapterDTOS.get(position);
        Intent intent = new Intent(v.getContext(), activity_cancel.class);
        intent.putExtra("reviewDTO", reviewDTO);
        v.getContext().startActivity(intent);
      }
    });

    send_store.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final ReviewDTO reviewDTO = adapterDTOS.get(position);

        String state = "";
        Send_Store send_store = new Send_Store(reviewDTO.getBooking_code());
        try {
          state = send_store.execute().get().trim();
          if (state.equals("1")) {
            Toast.makeText(mContext, "신호전송이 정상적으로 되었습니다", Toast.LENGTH_SHORT).show();
            Log.d("bookingInsert:", "신호전송이 정상적으로 되었습니다");
          } else {
            Toast.makeText(mContext, "신호전송이 실패하였습니다", Toast.LENGTH_SHORT).show();
            Log.d("bookingInsert", "신호전송이 실패하였습니다");
          }
          reFresh();
        } catch (ExecutionException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });


  }

  //버튼 연결

  @Override
  public int getItemCount() {
    return adapterDTOS.size();
  }

  //어댑터에 메소드 만들기
  //내용 추가하기
  public void addItem(ReviewDTO reviewDTO){
    adapterDTOS.add(reviewDTO);
  }

  public static class  ItemViewHolder extends  RecyclerView.ViewHolder {

    public LinearLayout parentLayout;
    public ImageView visit_category;
    public TextView visit_name, visit_date, visit_addr , list_status;
    public Button writeReview , send_store;


    public ItemViewHolder(@NonNull View itemView) {
      super(itemView);

      parentLayout = itemView.findViewById(R.id.parentLay);
      //카테고리 로고 넣을 곳
      visit_category = itemView.findViewById(R.id.visit_category);
      visit_name = itemView.findViewById(R.id.visit_name);
      visit_date = itemView.findViewById(R.id.visit_date);
      visit_addr = itemView.findViewById(R.id.visit_addr);
      list_status = itemView.findViewById(R.id.txt_status);
      send_store = itemView.findViewById(R.id.store_send_btn);
     // writeReview = itemView.findViewById(R.id.writeReview);
    }

    public void setItem(ReviewDTO reviewDTO) {
      //임시
      String hashtag = null;
      int status = reviewDTO.getBooking_kind();
      for (BusinessDTO dto: busiList
           ) {
          if (dto.getBusiness_code() == reviewDTO.getBooking_business_code()){
            hashtag = dto.getBusiness_hashtag();

          }

      }

        if (hashtag.indexOf("병원") > -1) {
          visit_category.setImageResource(R.drawable.main_img1);
        } else if (hashtag.indexOf("음식점") > -1) {
          visit_category.setImageResource(R.drawable.main_img2);
        } else if (hashtag.indexOf("숙박") > -1) {
          visit_category.setImageResource(R.drawable.main_img3);
        } else if (hashtag.indexOf("미용") > -1) {
          visit_category.setImageResource(R.drawable.main_img4);
        } else if (hashtag.indexOf("카센터") > -1) {
          visit_category.setImageResource(R.drawable.main_img5);
        } else if (hashtag.indexOf("놀이공원") > -1) {
          visit_category.setImageResource(R.drawable.main_img6);
        } else if (hashtag.indexOf("박물관") > -1) {
          visit_category.setImageResource(R.drawable.main_img7);
        } else if (hashtag.indexOf("항공권") > -1) {
          visit_category.setImageResource(R.drawable.main_img8);
        }else if (hashtag.indexOf("카페") > -1) {
          visit_category.setImageResource(R.drawable.main_img9);
        }else if (hashtag.indexOf("노래방") > -1) {
          visit_category.setImageResource(R.drawable.main_img10);
        }else if (hashtag.indexOf("당구") > -1) {
          visit_category.setImageResource(R.drawable.main_img11);
        }else if (hashtag.indexOf("PC") > -1) {
          visit_category.setImageResource(R.drawable.main_img12);
        }else{
          visit_category.setImageResource(R.drawable.intro2);
        }
        send_store.setVisibility(View.GONE);
        if(status == 1)list_status.setText("예약상태 "+"("+"찜"+")");
        if(status == 2){
          list_status.setText("예약상태 "+"("+"예약확인중"+")");
          list_status.setTextColor(Color.parseColor("#1DDB16"));
          send_store.setVisibility(View.VISIBLE);
        }
        if(status == 3){
          list_status.setText("예약상태 "+"("+"예약완료"+")");
          list_status.setTextColor(Color.parseColor("#1DDB16"));
          send_store.setVisibility(View.VISIBLE);
        }
        if(status == 4){
          list_status.setText("예약상태 "+"("+"방문완료"+")");
          list_status.setTextColor(Color.parseColor("#4374D9"));
        }
        if(status == 7){
          list_status.setText("예약상태 "+"("+"방문알림전송"+")");
          list_status.setTextColor(Color.parseColor("#FAED7D"));

        }
        if(status == 9){
          list_status.setText("예약상태 "+"("+"예약취소"+")");
          list_status.setTextColor(Color.parseColor("#FF0000"));
        }
      visit_name.setText(reviewDTO.getBusiness_name());
      visit_date.setText(reviewDTO.getBooking_date_reservation());
      visit_addr.setText(reviewDTO.getBusiness_addr());

    }
  }
}