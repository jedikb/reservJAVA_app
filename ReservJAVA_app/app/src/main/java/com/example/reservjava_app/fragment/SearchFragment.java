package com.example.reservjava_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.ui.b_where.WhereListActivity;
import com.example.reservjava_app.ui.f_profile.ProfileActivity;
import com.example.reservjava_app.ui.f_profile.ReviewActivity;


public class SearchFragment extends Fragment {

  MainActivity activity;

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

    tvAddr = viewGroup.findViewById(R.id.tvAddr);
    addrSearch = viewGroup.findViewById(R.id.addrSearch);

 /*   //사이드바(member)
    viewGroup.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });*/

    //검색버튼(whereList로 이동)
    viewGroup.findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(activity.getApplicationContext(), WhereListActivity.class);
        startActivity(intent);
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
        Intent intent = new Intent(activity.getApplicationContext(), ReviewActivity.class);
        startActivity(intent);
      }
    });

    // (임시) 프로필 화면으로 이동
    viewGroup.findViewById(R.id.moveToProfile).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(activity.getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
      }
    });

    return viewGroup;
  }
}