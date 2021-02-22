package com.example.reservjava_app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.reservation.Reservation;

import java.util.ArrayList;

public class TimeListAdapter extends
        RecyclerView.Adapter<TimeListAdapter.ViewHolder>{

    OnItemClickListener listener;

    Context context;
    ArrayList<String> timeList;
    private TextView time_text_view;
    private int position;


    public TimeListAdapter(Context context, ArrayList<String> timeList) {
        this.context = context;
        this.timeList = timeList;
    }


    //화면(xml)연결
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_timelist, parent, false);

        return new ViewHolder(itemView);
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        this.position = position;
        holder.onBind(timeList.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_text_view.setText(timeList.get(position));
            }
        });
    }

    public String getTime(){
        String time = timeList.get(position);
        return time;
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public void addItem(String time){
        timeList.add(time);
    }

    //메인에서 접근하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time_text;

        public LinearLayout parentLayout;
        public TextView time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time_text = itemView.findViewById(R.id.time_text);
            parentLayout = itemView.findViewById(R.id.time_list_lay);




        }

        public void onBind(String time){
            time_text.setText(time);
        }
    }

    public String getItem(int position){
        return timeList.get(position);
    }

    public void settime_Text(TextView time_text_view){
        this.time_text_view = time_text_view;
    }

}
