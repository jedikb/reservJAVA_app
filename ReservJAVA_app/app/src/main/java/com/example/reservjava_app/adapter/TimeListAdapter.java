package com.example.reservjava_app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.R;
import com.example.reservjava_app.Listner.TimeItemClickListener;

import java.util.ArrayList;

public class TimeListAdapter extends
        RecyclerView.Adapter<TimeListAdapter.ViewHolder>
        implements TimeItemClickListener{

    static TimeItemClickListener listener;
    Context context;
    ArrayList<String> timeList;
    private TextView view_time_text;


    String time;


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

        return new ViewHolder(itemView,listener);
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.onBind(timeList.get(position));
        /*holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_text_view.setText(timeList.get(position));
                time = timeList.get(position);
            }
        });*/
    }

    public String getTime(){
        return time;
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public void addItem(String time){
        timeList.add(time);
    }


    public void setOnItemClickListener(TimeItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null){
            listener.onItemClick(holder, view, position);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public LinearLayout parentLayout;
        public TextView time_text;


        public ViewHolder(@NonNull final View itemView, final TimeItemClickListener listener) {
            super(itemView);

            view_time_text = itemView.findViewById(R.id.view_time_text);
            parentLayout = itemView.findViewById(R.id.time_list_lay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, itemView, position);
                    }
                }
            });

        }

        public void onBind(String time){
            view_time_text.setText(time);
        }
    }

    public String getItem(int position){
        return timeList.get(position);
    }

    public void settime_Text(TextView view_time_text ){
        this.view_time_text = view_time_text;
    }

}
