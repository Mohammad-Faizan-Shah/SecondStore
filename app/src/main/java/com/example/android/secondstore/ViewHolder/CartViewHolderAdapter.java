package com.example.android.secondstore.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.secondstore.Model.CartModel;
import com.example.android.secondstore.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CartViewHolderAdapter extends FirebaseRecyclerAdapter<CartModel, CartViewHolderAdapter.cartViewHolder> {


    public CartViewHolderAdapter(@NonNull FirebaseRecyclerOptions<CartModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull cartViewHolder holder, int position, @NonNull CartModel model) {

        holder.cartProductName.setText(model.getPname());
        holder.cartProductPrice.setText("Price = ₹" + model.getPrice());
        Picasso.get().load(model.getImage()).into(holder.cartProductImage);

    }

    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new cartViewHolder(view);
    }

    public class cartViewHolder extends RecyclerView.ViewHolder {

        public TextView cartProductName,  cartProductPrice;
        public ImageView cartProductImage;

        public cartViewHolder(@NonNull View itemView) {
            super(itemView);

            cartProductName = (TextView)itemView.findViewById(R.id.cart_product_name);
            cartProductPrice = (TextView)itemView.findViewById(R.id.cart_product_price);
            cartProductImage = (ImageView)itemView.findViewById(R.id.cart_product_image);
        }
    }
}
