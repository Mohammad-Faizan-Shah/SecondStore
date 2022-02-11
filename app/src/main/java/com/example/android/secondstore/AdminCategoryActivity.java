package com.example.android.secondstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView motherBoard, ram, cpu, psu;
    private ImageView hdd, cpuFan, gpu, expansionCard;
    private ImageView keyBoard, mouse, monitor, laptop;
    private ImageView headPhones, cables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_category);

        motherBoard = (ImageView)findViewById(R.id.motherboard);
        ram = (ImageView)findViewById(R.id.ram);
        cpu = (ImageView)findViewById(R.id.cpu);
        psu = (ImageView)findViewById(R.id.psu);
        hdd = (ImageView)findViewById(R.id.hdd);
        cpuFan = (ImageView)findViewById(R.id.cpufan);
        gpu = (ImageView)findViewById(R.id.gpu);
        expansionCard = (ImageView)findViewById(R.id.expansion_card);
        keyBoard = (ImageView)findViewById(R.id.keyboard);
        mouse = (ImageView)findViewById(R.id.mouse);
        monitor = (ImageView)findViewById(R.id.monitor);
        laptop = (ImageView)findViewById(R.id.laptop_pc);
        headPhones = (ImageView)findViewById(R.id.headphones);
        cables = (ImageView)findViewById(R.id.cables);




        motherBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "motherBoard");
                startActivity(intent);
            }
        });

        ram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "ram");
                startActivity(intent);
            }
        });

        cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "cpu");
                startActivity(intent);
            }
        });

        psu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "psu");
                startActivity(intent);
            }
        });

        hdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "hdd");
                startActivity(intent);
            }
        });

        cpuFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "cpuFan");
                startActivity(intent);
            }
        });

        gpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "gpu");
                startActivity(intent);
            }
        });

        expansionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "expansionCard");
                startActivity(intent);
            }
        });

        keyBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "keyBoard");
                startActivity(intent);
            }
        });

        mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "mouse");
                startActivity(intent);
            }
        });

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "monitor");
                startActivity(intent);
            }
        });

        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "laptop");
                startActivity(intent);
            }
        });

        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "headPhones");
                startActivity(intent);
            }
        });

        cables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "cables");
                startActivity(intent);
            }
        });
    }
}