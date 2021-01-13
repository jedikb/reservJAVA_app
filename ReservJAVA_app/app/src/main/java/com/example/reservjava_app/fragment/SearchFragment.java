package com.example.reservjava_app.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;


public class SearchFragment extends Fragment {

  MainActivity activity;

  //Button backBtn, searchBtn, setAddrBtn;
  TextView tvAddr;
  EditText addrSearch;

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    activity = (MainActivity) getActivity();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    activity = null;
  }
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    ViewGroup viewGroup = (ViewGroup) inflater
        .inflate(R.layout.fragment_search, container, false);

    //backBtn = viewGroup.findViewById(R.id.backBtn); 왜 인지 모르겠지만 이렇게 하면 다운된다;;

    //백 버튼 (홈으로 돌아감)
    viewGroup.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        activity.onFragmentChange(1);
      }
    });

    //검색버튼(whereList로 이동)
    viewGroup.findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        activity.onFragmentChange(3);
      }
    });

    //주소 확정버튼(주소가 저장되었습니다 메시지 띄움)
    viewGroup.findViewById(R.id.setAddrBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getActivity(), "주소가 성공적으로 저장되었습니다", Toast.LENGTH_SHORT).show();
        activity.onFragmentChange(1);
      }
    });

    // (임시) 리뷰 등록 화면으로 이동
    viewGroup.findViewById(R.id.moveToReview).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        activity.onFragmentChange(11);
      }
    });

    // (임시) 프로필 화면으로 이동
    viewGroup.findViewById(R.id.moveToProfile).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        activity.onFragmentChange(6);
      }
    });




    tvAddr = viewGroup.findViewById(R.id.tvAddr);
    addrSearch = viewGroup.findViewById(R.id.addrSearch);



    return viewGroup;
  }


}