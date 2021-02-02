package com.example.reservjava_app.reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

public class Reservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

    }
}