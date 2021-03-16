package com.example.reservjava_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.Common.CommonMethod;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.reservation.Store;

import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.busiList;
import static com.example.reservjava_app.ui.b_where.SearchActivity.busiSetItem;

public class SearchBusinessAdapter extends
    RecyclerView.Adapter<SearchBusinessAdapter.ItemViewHolder> {
  private static final String TAG = "main:SBAdater";

  Context mContext;
  ArrayList<BusinessDTO> busiList;
  LinearLayout parentLayout;

  public SearchBusinessAdapter(Context mContext, ArrayList<BusinessDTO> busiList){
    this.mContext = mContext;
    this.busiList = busiList;
  }

  //화면(xml) 연결
  @NonNull
  @Override
  public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.business_view, parent, false);

    return  new ItemViewHolder(itemView);
  }

  //데이터 연결
  @Override
  public void onBindViewHolder(@NonNull SearchBusinessAdapter.ItemViewHolder holder, final int position) {

    final BusinessDTO busiDTO = busiList.get(position);
    holder.setItem(busiDTO);

    holder.parentLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        busiSetItem = busiList.get(position);

        Toast.makeText(mContext, busiList.get(position).getBusiness_name() + " 매장으로 이동합니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(v.getContext(), Store.class);
        intent.putExtra("businessdto", busiSetItem);
        v.getContext().startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() { return busiList.size(); }

  //어댑터에 메소드 만들기
  //내용 추가하기
  public void addItem(BusinessDTO businessDTO){
    busiList.add(businessDTO);
  }

  //리사이클러뷰 내용 모두 지우기
  public void removeAllItem() { busiList.clear();}

  // 특정 인덱스 항목 가져오기
  public BusinessDTO getItem(int position) { return busiList.get(position);}

  //특정 인덱스 항목 세팅하기
  public void setItem(int position, BusinessDTO busiDTO) {busiList.set(position, busiDTO);}

  //ArrayList 통째로 세팅하기
  public void setItems(ArrayList<BusinessDTO> busiList) { this.busiList = busiList;}

  public static class ItemViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout parentLayout;
    public ImageView search_bLogo;
    public TextView search_bName;
    public TextView search_bAddr;
    public TextView search_bSRateAvg;
    //이건 테이블 바꿔야 해서 우선은 보류
    // 이 부분은 따로 테이블 내에 필드가 없어도 카운트만으로도 가능할 것 같다.
    //public TextView search_bReviewNum;

    public ItemViewHolder(@NonNull View itemView) {
      super(itemView);

      parentLayout = itemView.findViewById(R.id.parentLay);
      // logo는 category 아이콘을 넣을 예정
      search_bLogo = itemView.findViewById(R.id.search_bLogo);
      search_bName = itemView.findViewById(R.id.search_bName);
      search_bAddr = itemView.findViewById(R.id.search_bAddr);
      search_bSRateAvg = itemView.findViewById(R.id.search_bSRateAvg);
      //search_bReviewNum = itemView.findViewById(R.id.search_bReviewNum);
    }

    public void setItem(BusinessDTO busiDTO) {

      String hashtag = null;
      for (BusinessDTO dto: CommonMethod.busiList
      ) {
        if (dto.getBusiness_code() == busiDTO.getBusiness_code()){
          hashtag = dto.getBusiness_hashtag();
        }

      }

      if (hashtag.indexOf("병원") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img1);
      } else if (hashtag.indexOf("음식점") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img2);
      } else if (hashtag.indexOf("숙박") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img3);
      } else if (hashtag.indexOf("미용") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img4);
      } else if (hashtag.indexOf("카센터") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img5);
      } else if (hashtag.indexOf("놀이공원") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img6);
      } else if (hashtag.indexOf("박물관") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img7);
      } else if (hashtag.indexOf("항공권") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img8);
      }else if (hashtag.indexOf("카페") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img9);
      }else if (hashtag.indexOf("노래방") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img10);
      }else if (hashtag.indexOf("당구") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img11);
      }else if (hashtag.indexOf("PC") > -1) {
        search_bLogo.setImageResource(R.drawable.main_img12);
      }else{
        search_bLogo.setImageResource(R.drawable.intro2);
      }

      search_bName.setText(busiDTO.getBusiness_name());
      search_bAddr.setText(busiDTO.getBusiness_addr());
      String bRateAvg = String.format("%.2f", busiDTO.getBusiness_star_avg()/20);
      Log.d(TAG, "setItem: " + bRateAvg);
      search_bSRateAvg.setText(bRateAvg);

      //카테고리 로고
      //Glide.with(itemView).load(busiDTO.getBusiness_image()).into(search_bLogo);
    }
  }
}
