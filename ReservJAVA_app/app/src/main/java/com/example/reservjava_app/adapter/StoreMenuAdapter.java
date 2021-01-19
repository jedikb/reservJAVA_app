package com.example.reservjava_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;

import java.util.ArrayList;

public class StoreMenuAdapter extends RecyclerView.Adapter<StoreMenuAdapter.ViewHolder> {

    //adapter에 들어갈 list
    private ArrayList<ProductDTO> productList = new ArrayList<>();

    //아이템 뷰를 정장하는 ViewHolder.Class
    public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView product_name;
            private TextView product_info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_info = itemView.findViewById(R.id.product_info);

        }

        public void onBind(ProductDTO productDTO){
            product_name.setText(productDTO.getProduct_name());
            product_info.setText(productDTO.getProduct_info());
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //item_product.xml 을 inflate
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //item  bind 함수
        holder.onBind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView 개수
        return productList.size();
    }

    //아이템 추가 메소드
    public void addItem(ProductDTO productDTO){
        productList.add(productDTO);
    }

}
