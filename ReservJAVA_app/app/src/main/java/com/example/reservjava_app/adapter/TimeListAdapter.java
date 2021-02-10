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
import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;

import java.util.ArrayList;

public class TimeListAdapter extends
        RecyclerView.Adapter<TimeListAdapter.ViewHolder>
        implements  OnItemClickListener{

    OnItemClickListener listener;

    Context context;
    ArrayList<ProductDTO> productList;


    public TimeListAdapter(Context context, ArrayList<ProductDTO> productList) {
        this.context = context;
        this.productList = productList;
    }


    //화면(xml)연결
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_timelist, parent, false);

        return new ViewHolder(itemView, listener);
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDTO productDTO = productList.get(position);
        holder.onBind(productDTO);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void addItem(ProductDTO productDTO){
        productList.add(productDTO);
    }

    //메인에서 접근하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(StoreListAdapter.ViewHolder holderm, View view, int position) {
        if(listener != null){
            listener.onItemClick(holderm, view, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time_text;


        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            time_text = itemView.findViewById(R.id.time_text);


/*            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });*/
        }

        public void onBind(ProductDTO productDTO){
            time_text.setText(productDTO.getProduct_time());
        }
    }

    public ProductDTO getItem(int position){
        return productList.get(position);
    }

}
