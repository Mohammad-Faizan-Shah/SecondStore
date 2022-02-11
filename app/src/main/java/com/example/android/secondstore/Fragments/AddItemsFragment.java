package com.example.android.secondstore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.secondstore.AdminAddNewProductActivity;
import com.example.android.secondstore.R;

public class AddItemsFragment extends Fragment {

    private Button motherBoard, ram, cpu, psu;
    private Button hdd, cpuFan, gpu, expansionCard;
    private Button keyBoard, mouse, monitor, laptop;
    private Button headPhones, cables;


    public static AddItemsFragment newInstance() {
        return new AddItemsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_admin_category, container, false);


//        Intent intent = new Intent(getContext(), AdminCategoryActivity.class);
//        startActivity(intent);


        motherBoard = view.findViewById(R.id.motherboard);
        ram = view.findViewById(R.id.ram);
        cpu = view.findViewById(R.id.cpu);
        psu = view.findViewById(R.id.psu);
        hdd = view.findViewById(R.id.hdd);
        cpuFan = view.findViewById(R.id.cpufan);
        gpu = view.findViewById(R.id.gpu);
        expansionCard = view.findViewById(R.id.expansion_card);
        keyBoard = view.findViewById(R.id.keyboard);
        mouse = view.findViewById(R.id.mouse);
        monitor = view.findViewById(R.id.monitor);
        laptop = view.findViewById(R.id.laptop_pc);
        headPhones = view.findViewById(R.id.headphones);
        cables = view.findViewById(R.id.cables);


        motherBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "motherBoard");
                startActivity(intent);
            }
        });

        ram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "ram");
                startActivity(intent);
            }
        });

        cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "cpu");
                startActivity(intent);
            }
        });

        psu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "psu");
                startActivity(intent);
            }
        });

        hdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "hdd");
                startActivity(intent);
            }
        });

        cpuFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "cpuFan");
                startActivity(intent);
            }
        });

        gpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "gpu");
                startActivity(intent);
            }
        });

        expansionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AdminAddNewProductActivity.class);
                intent.putExtra("category", "expansionCard");
                startActivity(intent);
            }
        });

        keyBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "keyBoard");
                startActivity(intent);
            }
        });

        mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "mouse");
                startActivity(intent);
            }
        });

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "monitor");
                startActivity(intent);
            }
        });

        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "laptop");
                startActivity(intent);
            }
        });

        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "headPhones");
                startActivity(intent);
            }
        });

        cables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                intent.putExtra("category", "cables");
                startActivity(intent);
            }
        });


        return view;
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