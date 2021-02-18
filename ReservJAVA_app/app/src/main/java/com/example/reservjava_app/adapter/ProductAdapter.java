package com.example.reservjava_app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.R;

import java.util.ArrayList;

public class ProductAdapter extends
        RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    OnItemClickListener listener;

    Context context;
    ArrayList<ProductDTO> productList;


    public ProductAdapter(Context context, ArrayList<ProductDTO> productList) {
        this.context = context;
        this.productList = productList;
    }


    //화면(xml)연결
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_product, parent, false);

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
//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.listener = listener;
//    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_name;
        TextView product_price;



        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);


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
            Log.d("main: ", "onBind: " + productDTO.getProduct_name() + productDTO.getProduct_price());
            String name = productDTO.getProduct_name();
            int price = productDTO.getProduct_price();
            product_name.setText(name);
            product_price.setText(price);
        }
    }

    public ProductDTO getItem(int position){
        return productList.get(position);
    }

}
