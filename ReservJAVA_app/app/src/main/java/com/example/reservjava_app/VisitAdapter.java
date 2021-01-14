package com.example.reservjava_app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.ReviewDTO;

import java.util.ArrayList;

public class VisitAdapter extends
    RecyclerView.Adapter<VisitAdapter.ViewHolder> {

  Context context;
  ArrayList<BusinessDTO> businessDTOS;
  ArrayList<ReviewDTO> ReviewDTOS;
  LinearLayout parentLay;

  public VisitAdapter(Context context, ArrayList<BusinessDTO> businessDTOS, ArrayList<ReviewDTO> ReviewDTOS){
    this.context = context;
    this.businessDTOS = businessDTOS;
    this.ReviewDTOS = ReviewDTOS;
  }


  @NonNull
  @Override
  public VisitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull VisitAdapter.ViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() { return businessDTOS.size(); }

  public void addDTO(BusinessDTO dto) { businessDTOS.add(dto); }

  public class ViewHolder extends RecyclerView.ViewHolder {
    LinearLayout parentLay;
    TextView name, date, order;
    ImageView imageView;


    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      parentLay = itemView.findViewById(R.id.parentLay);
      name = itemView.findViewById(R.id.name);
      date = itemView.findViewById(R.id.date);
      order = itemView.findViewById(R.id.order);
    }
    public void setItem(BusinessDTO dto, ReviewDTO reviewDTO){
      name.setText(dto.getBusiness_name());
      //나중에 다 바꿔야 할듯;;; 일이 커진다
      date.setText((CharSequence) reviewDTO.getBooking_date_reservation());
      order.setText(reviewDTO.getBooking_product_code());
    }
  }
}
