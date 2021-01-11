package com.example.reservjava_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;

public class SearchFragment extends Fragment {

    MainActivity activity;

    public SearchFragment() {}
    //지워도 되나;;

    public static com.example.reservjava_app.fragment.SearchFragment newInstance(String param1, String param2) {
        com.example.reservjava_app.fragment.SearchFragment fragment = new com.example.reservjava_app.fragment.SearchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_search, container,false);

        Button searchBtn = viewGroup.findViewById(R.id.searchBtn);
        Button backBtn = viewGroup.findViewById(R.id.backBtn);
        Button setAddrBtn = viewGroup.findViewById(R.id.setAddrBtn);

        EditText addrSearch = viewGroup.findViewById(R.id.addrSearch);
        TextView tvAddr = viewGroup.findViewById(R.id.tvAddr);

        //검색 버튼
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //주소 추가(확정) 버튼
        setAddrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}