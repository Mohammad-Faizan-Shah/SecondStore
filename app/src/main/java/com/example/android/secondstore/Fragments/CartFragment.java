package com.example.android.secondstore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.secondstore.Model.CartModel;
import com.example.android.secondstore.Prevalent.Prevalent;
import com.example.android.secondstore.R;
import com.example.android.secondstore.ViewHolder.CartViewHolderAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private Button cartNextButton;
    private TextView cartTotalAmount;
    CartViewHolderAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);

        cartRecyclerView =(RecyclerView) view.findViewById(R.id.cart_recycler);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartNextButton = view.findViewById(R.id.cart_next_btn);
        cartTotalAmount = view.findViewById(R.id.cart_top_textview);

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products"), CartModel.class)
                        .build();


        adapter = new CartViewHolderAdapter(options);
        adapter.startListening();
        cartRecyclerView.setAdapter(adapter);
        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");
//
//
//
//
//        FirebaseRecyclerAdapter<CartModel, CartViewHolder>adapter
//                =new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartModel model) {
//
//                holder.cartProductname.setText(model.getPname());
//                holder.cartProductPrice.setText(model.getPrice());
//                Picasso.get().load(model.getImage()).into(holder.cartProductImage);
//
//            }
//
//            @NonNull
//            @Override
//            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
//                CartViewHolder holder = new CartViewHolder(view);
//                return holder;
//            }
//        };
//        cartRecyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }


}