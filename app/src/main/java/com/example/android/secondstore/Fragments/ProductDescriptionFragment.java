package com.example.android.secondstore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.secondstore.Prevalent.Prevalent;
import com.example.android.secondstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class ProductDescriptionFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    String category, date, description, image, pid, pname, price, time, posted;


    public ProductDescriptionFragment() {

    }

    public ProductDescriptionFragment( String image, String pname, String description, String price,String category,String date,  String pid, String time, String posted) {
        this.pname=pname;
        this.description= description;
        this.image=image;
        this.price=price;
        this.category=category;
        this.date=date;
        this.time=time;
        this.pid=pid;
        this.posted=posted;

    }

    public static ProductDescriptionFragment newInstance(String param1, String param2) {
        ProductDescriptionFragment fragment = new ProductDescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        ImageView descriptionProductImage = view.findViewById(R.id.description_product_image);
        TextView descriptionProductName = view.findViewById(R.id.description_product_name);
        TextView postedShowDescription = view.findViewById(R.id.postedshowDescription);
        TextView descriptionProductDescription = view.findViewById(R.id.description_product_description);
        TextView descriptionProductPrice = view.findViewById(R.id.description_product_price);
        Button descriptionAddToCart = view.findViewById(R.id.description_add_to_cart_btn);



        Picasso.get().load(image).into(descriptionProductImage);
        descriptionProductName.setText(pname);
        descriptionProductDescription.setText(description);
        descriptionProductPrice.setText("Price = ₹"+ price);
        postedShowDescription.setText(posted);
        
        
        descriptionAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });

       return view;
    }

    private void addingToCartList() {

        String  saveCurrentDate, saveCurrentTime;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM DD, YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
         final HashMap<String, Object>cartMap = new HashMap<>();
         cartMap.put("pid",pid);
         cartMap.put("pname",pname);
         cartMap.put("price",price);
         cartMap.put("image",image);
         cartMap.put("date", saveCurrentDate);
         cartMap.put("time", saveCurrentTime);

         cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
         .child(pid)
         .updateChildren(cartMap)
         .addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()){
                     cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                             .child(pid)
                             .updateChildren(cartMap)
                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if (task.isSuccessful()){
                                         Toast.makeText(getContext(),"Added to CartModel",Toast.LENGTH_SHORT).show();

                                     }
                                 }
                             });
                 }
             }
         });
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

//    public void onBackPressed(){
//        AppCompatActivity activity=(AppCompatActivity)getContext();
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new HomeFragment()).addToBackStack(null).commit();
//
//    }
}