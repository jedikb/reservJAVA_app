package com.example.reservjava_app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;

import java.util.ArrayList;

import static com.example.reservjava_app.MainActivity.busiList;
import static com.example.reservjava_app.ui.f_profile.ProfileActivity.reviewSetItem;

public class MyVisitAdapter extends
    RecyclerView.Adapter<MyVisitAdapter.ItemViewHolder> {
  private static final String TAG = "main:MyReviewAdapter";

  Context mContext;
  ArrayList<ReviewDTO> reviewDTOS;
  LinearLayout parentLayout;

  public MyVisitAdapter(Context mContext, ArrayList<ReviewDTO> reviewDTOS) {
    this.mContext = mContext;
    this.reviewDTOS = reviewDTOS;
  }

  //화면 연결
  @NonNull
  @Override
  public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.visit_view, parent, false);

    return new ItemViewHolder(itemView);
  }

  //데이터 연결
  @Override
  public void onBindViewHolder(@NonNull MyVisitAdapter.ItemViewHolder holder, final int position) {
    Log.d(TAG, "onBindViewHolder: " + position);

    final ReviewDTO reviewDTO = reviewDTOS.get(position);
    holder.setItem(reviewDTO);

    holder.parentLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //우선 데이터 가져와서 작업하자.
        Toast.makeText(mContext, reviewDTOS.get(position).getBooking_business_code() + "가 클릭되었습니다", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public int getItemCount() {
    return reviewDTOS.size();
  }

  //어댑터에 메소드 만들기
  //내용 추가하기
  public void addItem(ReviewDTO reviewDTO){
    reviewDTOS.add(reviewDTO);
  }

  //리사이클러뷰 내용 모두 지우기
  public void removeAllItem() { reviewDTOS.clear();}

  // 특정 인덱스 항목 가져오기
  public ReviewDTO getItem(int position) { return reviewDTOS.get(position);}

  //특정 인덱스 항목 세팅하기
  public void setItem(int position, ReviewDTO reviewDTO) {reviewDTOS.set(position, reviewDTO);}

  //ArrayList 통째로 세팅하기
  public void setItems(ArrayList<ReviewDTO> reviewDTOS) { this.reviewDTOS = reviewDTOS;}

  public static class  ItemViewHolder extends  RecyclerView.ViewHolder {

    public LinearLayout parentLayout;
    public ImageView visit_category;
    public TextView visit_name;
    public TextView visit_date;
    public TextView visit_addr;

    public ItemViewHolder(@NonNull View itemView) {
      super(itemView);

      parentLayout = itemView.findViewById(R.id.parentLay);
      //카테고리 로고 넣을 곳
      visit_category = itemView.findViewById(R.id.visit_category);
      visit_name = itemView.findViewById(R.id.visit_name);
      visit_date = itemView.findViewById(R.id.visit_date);
      visit_addr = itemView.findViewById(R.id.visit_addr);

    }

    public void setItem(ReviewDTO reviewDTO) {
      //임시
      visit_category.setImageResource(R.drawable.fitness);
      visit_name.setText(reviewDTO.getBusiness_name());
      visit_date.setText(reviewDTO.getBooking_date_reservation());
      visit_addr.setText(reviewDTO.getBusiness_addr());
    }
  }
}