package com.example.android.secondstore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.secondstore.Fragments.CartFragment;
import com.example.android.secondstore.Prevalent.Prevalent;
import com.example.android.secondstore.Fragments.AddItemsFragment;
import com.example.android.secondstore.Fragments.CategoriesFragment;
import com.example.android.secondstore.Fragments.ChatFragment;
import com.example.android.secondstore.Fragments.OrdersFragment;
import com.example.android.secondstore.Fragments.SearchFragment;
import com.example.android.secondstore.Fragments.SettingsFragment;
import com.example.android.secondstore.Fragments.HomeFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    NavigationView  sideNavigationView;
    CoordinatorLayout coordinatorLayout;
    FragmentTransaction fragmentTransaction;
    Fragment temp;
    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


//        if (Build.VERSION.SDK_INT>=21){
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//            Window window=getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
        coordinatorLayout=findViewById(R.id.coordinatorLayout);
        bottomAppBar=findViewById(R.id.bottom_app_bar);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        sideNavigationView = findViewById(R.id.side_nav_bar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //  Navigation $$$$$$$$%%#########%^%&%#%$##$#$#$#$#$#$@@@
        sideNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.side_nav_privacy:
                        temp = new OrdersFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, temp).commit();
                        break;

                    case R.id.side_nav_categories:
                        temp = new AddItemsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, temp).commit();
                        break;

                    case R.id.side_nav_settings:
                        temp = new SettingsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, temp).commit();
                        break;

                    case R.id.side_nav_fav:
                        temp = new CartFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, temp).commit();
                        break;
//                    case R.id.side_nav_contactUs:
//
//                        break;

                    case R.id.side_nav_share:
                        shareLink();
                        break;

                }coordinatorLayout.setVisibility(View.INVISIBLE);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        bottomNavigationView.setBackgroundColor(Color.TRANSPARENT);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new HomeFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        temp = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, temp).commit();
                        break;

                    case R.id.bottom_nav_search:
                        temp = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, temp).commit();
                        break;

                    case R.id.bottom_nav_chat:
                        temp = new ChatFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, temp).commit();
                        break;

                    case R.id.bottom_nav_logout:
                        LogOut();

                        break;

                }

                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new AddItemsFragment()).commit();
            }
        });


        Paper.init(this);

        View headerView = sideNavigationView.getHeaderView(0);
        TextView userProfileName = headerView.findViewById(R.id.user_profile_name);
        CircleImageView userProfileImage = headerView.findViewById(R.id.user_profile_image);
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(userProfileImage);
        userProfileName.setText(Prevalent.currentOnlineUser.getName());





// BubbleNavigation Section==========|||||||||||||||||||$$$$$$$$$$$#############$$$$$$======

//        BubbleNavigationLinearView bubbleNavigationLinearView = findViewById(R.id.bottom_nav_bar);

        //For Badges manually (Optional)
//        bubbleNavigationLinearView.setBadgeValue(0,"5");


//        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.container_main,new HomeFragment());
//        fragmentTransaction.commit();
//        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
//            @Override
//            public void onNavigationChanged(View view, int position) {
//
//                switch (position)
//                {
//                    case 0:
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.container_main,new HomeFragment());
//                        fragmentTransaction.commit();
//                        break;
////
//                    case 1:
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.container_main,new SearchFragment());
//                        fragmentTransaction.commit();
//                        break;
//
//                    case 2:
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.container_main,new AddItemsFragment());
//                        fragmentTransaction.commit();
//                        break;
//
//                    case 3:
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.container_main,new ChatFragment());
//                        fragmentTransaction.commit();
//                        break;
//                }
//
//            }
//        });
//========================***************===========*****************=============================


//        NavController navController = Navigation.findNavController(this, R.id.container_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(sideNavigationView, navController);
//        NavigationUI.setupWithNavController(toolbar, navController);

//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.bottom_nav_home,R.id.bottom_nav_chat,R.id.side_nav_orders)
//                .setDrawerLayout(drawer)
//                .build();


//        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener);
//        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment());
//


        // Recycler view section======****======================================
//            ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
//            recyclerView = findViewById(R.id.recycler_menu);
//            recyclerView.setHasFixedSize(true);
//            layoutManager  = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(layoutManager);
        //=============*********=============================


    }

    private void shareLink() {

        Intent intent  = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String sharebody = "Thank you for your support, This feature will be available soon.";
        String sahresub = "Second Store ";

        intent.putExtra(Intent.EXTRA_SUBJECT,sahresub);
        intent.putExtra(Intent.EXTRA_TEXT, sharebody);
        startActivity(Intent.createChooser(intent, "Share Using"));

    }

    private void LogOut() {

        AlertDialog.Builder exit = new AlertDialog.Builder(HomeActivity.this);
        exit.setTitle("Confirm LogOut...!");
        exit.setIcon(R.drawable.logout);
        exit.setMessage("Are you sure You want to LogOut");
        exit.setCancelable(false);
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                Paper.book().destroy();

            }
        });
        exit.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
                Toast.makeText(HomeActivity.this,"Canceled",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = exit.create();
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            if (temp instanceof HomeFragment){
                super.onBackPressed();
            }else {
                coordinatorLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new HomeFragment()).commit();
                bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
            }
        }

    }



    // Recycler View further Section===========*************************==================================
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<ProductsModel> options =
//                new FirebaseRecyclerOptions.Builder<ProductsModel>()
//            .setQuery(ProductsRef, ProductsModel.class)
//            .build();
//
//        FirebaseRecyclerAdapter<ProductsModel, ProductViewHolderAdapter> adapter =
//                new FirebaseRecyclerAdapter<ProductsModel, ProductViewHolderAdapter>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull ProductViewHolderAdapter holder, int position, @NonNull ProductsModel model) {
//
//                        holder.productName.setText(model.getPname());
//                        holder.productPrice.setText("Price = " + model.getPrice());
//                        holder.productDescription.setText(model.getDescription());
//                        Picasso.get().load(model.getImage()).into(holder.productImage);
//
//                    }
//
//                    @NonNull
//                    @Override
//                    public ProductViewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_itmes, parent, false);
//                        ProductViewHolderAdapter holder = new ProductViewHolderAdapter(view);
//                        return holder;
//                    }
//                };
//
//             recyclerView.setAdapter(adapter);
//             adapter.startListening();
//
//    }


    //  BottomNavigation section using menu without nav controller   =**********=====================

    //    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//
//            Fragment selectedFragment=null;
//            switch (item.getItemId())
//            {
//                case R.id.bottom_nav_home:
//                    selectedFragment = new HomeFragment();
//                    break;
//
//                case R.id.bottom_nav_search:
//                    selectedFragment = new SearchFragment();
//                    break;
//
//                case R.id.bottom_nav_add_items:
//                    selectedFragment = new AddItemsFragment();
//                    break;
//
//                case R.id.bottom_nav_chat:
//                    selectedFragment = new ChatFragment();
//                    break;
//
//                case R.id.bottom_nav_manage_account:
//                    selectedFragment = new AccountSettiongsFragment();
//                    break;
//            }
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,selectedFragment).commit();
//            return true;
//        }
//    };
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//
//
//        return true;
//    }


//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}

