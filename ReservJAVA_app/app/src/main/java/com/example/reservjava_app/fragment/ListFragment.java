package com.example.reservjava_app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.ATask.BookingInsert;
import com.example.reservjava_app.ATask.Send_Store;
import com.example.reservjava_app.Common.CommonMethod;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.ReserveListAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.reservjava_app.ATask.MyReview.reviewDTOS;
import static com.example.reservjava_app.Common.CommonMethod.currentAddress;
import static com.example.reservjava_app.Common.CommonMethod.loginDTO;

public class ListFragment extends Fragment {

    MainActivity activity;
    RecyclerView recyclerView;
    ReserveListAdapter vAdapter;
    ViewGroup viewGroup_b;
    Spinner spinner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity.findViewById(R.id.main_app_bar).setVisibility(View.VISIBLE);
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.fragment_list, container, false);
        viewGroup_b = viewGroup;
        activity.findViewById(R.id.main_app_bar).setVisibility(View.GONE);
        CommonMethod.selectData(activity);
        viewGroup.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Refresh();
            }
        });

        final String[] spitems = {
                "전체",
                "찜",
                "예약확인중",
                "예약완료",
                "방문완료",
                "방문알림전송",
                "예약취소"
        };

        spinner = viewGroup_b.findViewById(R.id.list_spiner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, spitems);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(activity, spitems[i].toString(), Toast.LENGTH_SHORT).show();
                int flag = 0;
                switch (spitems[i]) {
                    case "전체":
                        break;
                    case "찜":
                        flag = 1;
                        break;
                    case "예약확인중":
                        flag = 2;
                        break;
                    case "예약완료":
                        flag = 3;
                        break;
                    case "방문완료":
                        flag = 4;
                        break;
                    case "방문알림전송":
                        flag = 7;
                        break;
                    case "예약취소":
                        flag = 9;
                        break;
                }
                setvAdapter(viewGroup_b, flag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        setvAdapter(viewGroup, 0);

        return viewGroup;
    }

    public void Refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public void setvAdapter(ViewGroup viewGroup, int pos) {
        ArrayList<ReviewDTO> RevList = new ArrayList<>();
        if (pos == 0) {
            for (ReviewDTO dto : reviewDTOS) {
                //  if(dto.getBooking_kind() == 2 || dto.getBooking_kind() == 2){
                RevList.add(dto);
                //  }
            }
        } else {
            for (ReviewDTO dto : reviewDTOS) {
                  if(dto.getBooking_kind() == pos || dto.getBooking_kind() == pos){
                RevList.add(dto);
                  }
            }
        }
        vAdapter = new ReserveListAdapter(viewGroup.getContext(), RevList, this);
        recyclerView = viewGroup.findViewById(R.id.rc_frg_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewGroup.getContext(),
                RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(vAdapter);
    }
}