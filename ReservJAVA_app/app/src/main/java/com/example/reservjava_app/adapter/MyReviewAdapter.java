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

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;

import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.busiList;

public class MyReviewAdapter extends
    RecyclerView.Adapter<MyReviewAdapter.ItemViewHolder> {
  private static final String TAG = "main:MyReviewAdapter";

  Context mContext;
  ArrayList<ReviewDTO> reviewDTOS, reviewList;
  LinearLayout parentLayout;

  public MyReviewAdapter(Context mContext, ArrayList<ReviewDTO> reviewDTOS) {
    this.mContext = mContext;

    reviewList = new ArrayList<>();
    //리뷰의 경우 리뷰작성을 하지 않은 것에 대해서는 리스트에 나타나지 않게 해야 한다
    for (ReviewDTO dto : reviewDTOS) {
      if(dto.getBooking_appraisal() != null) {
        reviewList.add(dto);
      }
    }
    this.reviewDTOS = reviewList;
  }

  //화면 연결
  @NonNull
  @Override
  public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.review_view, parent, false);

    return new ItemViewHolder(itemView);
  }

  //데이터 연결
  @Override
  public void onBindViewHolder(@NonNull MyReviewAdapter.ItemViewHolder holder, final int position) {
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
    public ImageView review_category;
    public TextView review_name;
    public RatingBar review_ratingBar;
    public TextView review_contents;

    public ItemViewHolder(@NonNull View itemView) {
      super(itemView);

      parentLayout = itemView.findViewById(R.id.parentLay);
      //카테고리 로고 넣을 곳
      review_category = itemView.findViewById(R.id.review_category);
      review_name = itemView.findViewById(R.id.review_name);
      review_ratingBar = itemView.findViewById(R.id.review_ratingBar);
      review_contents = itemView.findViewById(R.id.review_contents);
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
        review_category.setImageResource(R.drawable.main_img1);
      } else if (hashtag.indexOf("음식점") > -1) {
        review_category.setImageResource(R.drawable.main_img2);
      } else if (hashtag.indexOf("숙박") > -1) {
        review_category.setImageResource(R.drawable.main_img3);
      } else if (hashtag.indexOf("미용") > -1) {
        review_category.setImageResource(R.drawable.main_img4);
      } else if (hashtag.indexOf("카센터") > -1) {
        review_category.setImageResource(R.drawable.main_img5);
      } else if (hashtag.indexOf("놀이공원") > -1) {
        review_category.setImageResource(R.drawable.main_img6);
      } else if (hashtag.indexOf("박물관") > -1) {
        review_category.setImageResource(R.drawable.main_img7);
      } else if (hashtag.indexOf("항공권") > -1) {
        review_category.setImageResource(R.drawable.main_img8);
      }else if (hashtag.indexOf("카페") > -1) {
        review_category.setImageResource(R.drawable.main_img9);
      }else if (hashtag.indexOf("노래방") > -1) {
        review_category.setImageResource(R.drawable.main_img10);
      }else if (hashtag.indexOf("당구") > -1) {
        review_category.setImageResource(R.drawable.main_img11);
      }else if (hashtag.indexOf("PC") > -1) {
        review_category.setImageResource(R.drawable.main_img12);
      }else{
        review_category.setImageResource(R.drawable.intro2);
      }

      //임시
      review_name.setText(reviewDTO.getBusiness_name());
      review_ratingBar.setRating(reviewDTO.getBooking_appraisal_star());
      review_contents.setText(reviewDTO.getBooking_appraisal());
    }
  }
}
