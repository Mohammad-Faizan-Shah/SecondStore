package com.example.android.secondstore.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.secondstore.Model.ProductsModel;
import com.example.android.secondstore.R;
import com.example.android.secondstore.ViewHolder.ProductViewHolderAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ProductViewHolderAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView =(RecyclerView) root.findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseRecyclerOptions<ProductsModel>options =
                new FirebaseRecyclerOptions.Builder<ProductsModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").child("user").child("products"), ProductsModel.class)
                .build();

        adapter = new ProductViewHolderAdapter(options);
        recyclerView.setAdapter(adapter);

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}

