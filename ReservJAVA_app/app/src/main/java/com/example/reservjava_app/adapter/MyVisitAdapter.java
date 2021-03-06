package com.example.reservjava_app.adapter;

import android.content.Context;
import android.content.Intent;
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

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.activity_cancel;
import com.example.reservjava_app.reservation.Store;
import com.example.reservjava_app.ui.f_profile.Profile_MyVisit_flag;
import com.example.reservjava_app.ui.f_profile.ReviewActivity;

import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.busiList;
import static com.example.reservjava_app.ui.f_profile.ProfileActivity.reviewSetItem;

public class MyVisitAdapter extends
    RecyclerView.Adapter<MyVisitAdapter.ItemViewHolder> {
  private static final String TAG = "main:MyReviewAdapter";

  Context mContext;
  ArrayList<ReviewDTO> adapterDTOS;
  LinearLayout parentLayout;
  Button writeReview, viewStore, viewOrder;
  int position;
  Profile_MyVisit_flag profile_myVisit_flag;
  public MyVisitAdapter(Context mContext, ArrayList<ReviewDTO> adapterDTOS ,  Profile_MyVisit_flag profile_myVisit_flag) {
    this.mContext = mContext;
    this.adapterDTOS = adapterDTOS;
    this.profile_myVisit_flag = profile_myVisit_flag;
  }
  public void refresh(){profile_myVisit_flag.Refresh();}

  //화면 연결
  @NonNull
  @Override
  public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.visit_view, parent, false);

    writeReview = itemView.findViewById(R.id.writeReview);
    viewStore = itemView.findViewById(R.id.viewStore);
    viewOrder = itemView.findViewById(R.id.viewOrder);

    return new ItemViewHolder(itemView);
  }

  //데이터 연결
  @Override
  public void onBindViewHolder(@NonNull MyVisitAdapter.ItemViewHolder holder, final int position) {
    Log.d(TAG, "onBindViewHolder: " + position);

    final ReviewDTO reviewDTO = adapterDTOS.get(position);
    holder.setItem(reviewDTO);

    //리뷰 화면으로 연결
    writeReview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        reviewSetItem = adapterDTOS.get(position);

        Toast.makeText(mContext, adapterDTOS.get(position).getBusiness_name(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(v.getContext(), ReviewActivity.class);
        intent.putExtra("reviewDTO", reviewSetItem);
        v.getContext().startActivity(intent);
        refresh();

      }
    });

    //매장 화면 연결
    viewStore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        reviewSetItem = adapterDTOS.get(position);
        reviewSetItem.getBooking_business_code();

        int code = reviewDTO.getBooking_business_code();
        BusinessDTO busidto = null;

        for (BusinessDTO dto: busiList) {
          if(code == dto.getBusiness_code()) {
              busidto = dto;
            break;
          }
        }

        Intent intent = new Intent(v.getContext(), Store.class);
        intent.putExtra("businessdto", busidto);
        v.getContext().startActivity(intent);
      }
    });

    //아직 화면 구성이 없다
    viewOrder.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        final ReviewDTO reviewDTO = adapterDTOS.get(position);
        Intent intent = new Intent(v.getContext(), activity_cancel.class);
        intent.putExtra("reviewDTO", reviewDTO);
        v.getContext().startActivity(intent);

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
    public TextView visit_name, visit_date, visit_addr;
    public Button writeReview;

    public ItemViewHolder(@NonNull View itemView) {
      super(itemView);

      parentLayout = itemView.findViewById(R.id.parentLay);
      //카테고리 로고 넣을 곳
      visit_category = itemView.findViewById(R.id.visit_category);
      visit_name = itemView.findViewById(R.id.visit_name);
      visit_date = itemView.findViewById(R.id.visit_date);
      visit_addr = itemView.findViewById(R.id.visit_addr);
      writeReview = itemView.findViewById(R.id.writeReview);
    }

    public void setItem(ReviewDTO reviewDTO) {
      //임시
      String hashtag = null;
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


      visit_name.setText(reviewDTO.getBusiness_name());
      visit_date.setText(reviewDTO.getBooking_date_reservation());
      visit_addr.setText(reviewDTO.getBusiness_addr());
      if(reviewDTO.getBooking_appraisal() == null) {
        writeReview.setText("리뷰작성");
      } else {
        writeReview.setText("리뷰수정");
      }

    }
  }
}