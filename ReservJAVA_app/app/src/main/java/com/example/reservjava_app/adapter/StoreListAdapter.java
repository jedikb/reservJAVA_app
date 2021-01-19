package com.example.reservjava_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.R;

import java.util.ArrayList;

public class StoreListAdapter extends
        RecyclerView.Adapter<StoreListAdapter.ViewHolder>
        implements  OnItemClickListener{

    OnItemClickListener listener;

    Context context;
    ArrayList<BusinessDTO> businessList;


    public StoreListAdapter(Context context, ArrayList<BusinessDTO> businessList) {
        this.context = context;
        this.businessList = businessList;
    }


    //화면(xml)연결
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_business, parent, false);

        return new ViewHolder(itemView, listener);
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusinessDTO businessDTO = businessList.get(position);
        holder.onBind(businessDTO);
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public void addItem(BusinessDTO businessDTO){
        businessList.add(businessDTO);
    }

    //메인에서 접근하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holderm, View view, int position) {
        if(listener != null){
            listener.onItemClick(holderm, view, position);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView business_name;
        TextView business_info;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            business_name = itemView.findViewById(R.id.business_name);
            business_info = itemView.findViewById(R.id.business_info);
            imageView = itemView.findViewById(R.id.imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void onBind(BusinessDTO businessDTO){
            business_name.setText(businessDTO.getBusiness_name());
            business_info.setText(businessDTO.getBusiness_info());
            imageView.setImageResource(R.drawable.singer1);
        }
    }

}
