package com.example.kpetshop.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kpetshop.R;
import com.example.kpetshop.Interface.ItemClickListener;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
public TextView txtProductName,txtProductPrice,txtProductQuantity;
private ItemClickListener itemClickListener; //import interface itemclick listener

public CartViewHolder(@NonNull View itemView){
        super(itemView);

        txtProductName=itemView.findViewById(R.id.cart_product_name);
        txtProductQuantity=itemView.findViewById(R.id.cart_product_quantity);
        txtProductPrice=itemView.findViewById(R.id.cart_product_price);
        }

@Override
public void onClick(View v){
        itemClickListener.onClick(v,getAdapterPosition(),false);
        }

public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
        }
        }
