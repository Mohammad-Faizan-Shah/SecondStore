package com.example.android.secondstore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.android.secondstore.Model.ProductsModel;
import com.example.android.secondstore.R;
import com.example.android.secondstore.ViewHolder.ProductViewHolderAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SearchFragment extends Fragment {

    String s;
    SearchView searchView;
    RecyclerView searchRecyclerView;
    ProductViewHolderAdapter adapter;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        searchRecyclerView = view.findViewById(R.id.search_recycler);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView= view.findViewById(R.id.searchbar);



        FirebaseRecyclerOptions<ProductsModel>options =
                new FirebaseRecyclerOptions.Builder<ProductsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").child("user").child("products"), ProductsModel.class)
                        .build();

        adapter = new ProductViewHolderAdapter(options);
        adapter.startListening();
        searchRecyclerView.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                processSearch(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                processSearch(s);

                return false;
            }
        });

        return view;
    }

    private void setSupportActionBar(Toolbar searchtoolbar) {

    }

    public void onBackPressed(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new HomeFragment()).addToBackStack(null).commit();
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





    private void processSearch(String s){


        FirebaseRecyclerOptions<ProductsModel> options =
                new FirebaseRecyclerOptions.Builder<ProductsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").child("user").child("products").orderByChild("pname").startAt(s).endAt(s+"\uf8ff"), ProductsModel.class)
                        .build();

        adapter = new ProductViewHolderAdapter(options);
        adapter.startListening();
        searchRecyclerView.setAdapter(adapter);
    }
}