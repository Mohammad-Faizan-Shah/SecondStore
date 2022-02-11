package com.example.android.secondstore.Fragments;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.secondstore.AdminAddNewProductActivity;
import com.example.android.secondstore.HomeActivity;
import com.example.android.secondstore.LoginActivity;
import com.example.android.secondstore.MainActivity;
import com.example.android.secondstore.Model.Users;
import com.example.android.secondstore.Prevalent.Prevalent;
import com.example.android.secondstore.R;
import com.example.android.secondstore.RegisterActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class LoginFragment extends Fragment {

    private TextInputLayout til_InputPassword,til_InputPhoneNumber;
    private TextInputEditText tedit_InputPassword, tedit_InputPhoneNumber;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink, loginRegisterBtn;


    private String parentDbName = "Users";
    private com.rey.material.widget.CheckBox chkBoxRemeberMe;


    public LoginFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        LoginButton =view.findViewById(R.id.login_btn);
        loginRegisterBtn = view.findViewById(R.id.login_register_btn);
        til_InputPhoneNumber = view.findViewById(R.id.til_login_phone_number_input);
        tedit_InputPhoneNumber = view.findViewById(R.id.tedit_login_phone_number_input);
        til_InputPassword =view.findViewById(R.id.til_login_passaword_input);
        tedit_InputPassword =view.findViewById(R.id.tedit_login_password_input);
        AdminLink =view.findViewById(R.id.admin_panel_link);
        NotAdminLink =view.findViewById(R.id.not_admin_panel_link);
        loadingBar = new ProgressDialog(getContext());


        chkBoxRemeberMe =view.findViewById(R.id.remember_me_chkb);
        Paper.init(getContext());

        loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new RegisterFragment()).addToBackStack(null).commit();
            }
        });


        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey!=null && UserPasswordKey!=null){

            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){

                AllowAccess(UserPhoneKey, UserPasswordKey);

                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Wait a moment...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }
        }


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
                Log.d("Change parentDbName", parentDbName);
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
                Log.d("Change parentDbName", parentDbName);
            }
        });


        return view;
    }



    public void requestFocus(View view){
        if (view.requestFocus()){
            getActivity().getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean LoginUser() {
        String phone = tedit_InputPhoneNumber.getText().toString();
        String password = tedit_InputPassword.getText().toString();



        if (tedit_InputPhoneNumber.getText().toString().isEmpty()){
            tedit_InputPhoneNumber.setError("Enter Phone Number.");
            requestFocus(tedit_InputPhoneNumber);
            return false;
//            Toast.makeText(this,"Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else{
            if (tedit_InputPhoneNumber.getText().toString().trim().length()<10){
                til_InputPhoneNumber.setError("Enter a Valid Phone Number");
                requestFocus(tedit_InputPhoneNumber);
                return false;
            }
        } if (tedit_InputPassword.getText().toString().isEmpty()){
            til_InputPassword.setError("Enter Password.");
            requestFocus(tedit_InputPassword);
            return false;
//            Toast.makeText(this,"Please type password...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccesToAccount(phone, password);


        }


        return true;
    }

    private void AllowAccess(final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(phone).exists()){
                    Users usersData = snapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(password)){
                            Toast.makeText(getContext(),"Logged in Successfully...",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(getContext(),HomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(getContext(),"Password is incorrect.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getContext(),"Account with this "+phone+" number doesn't exist.",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void AllowAccesToAccount(String phone, String password) {

        if (chkBoxRemeberMe.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }



        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        Log.d("RootRef", FirebaseDatabase.getInstance().toString());




        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()){
                    Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)){

                        if (usersData.getPassword().equals(password)){

                            if (parentDbName.equals("Admins")){
                                Toast.makeText(getContext(),"Logged in Successfully...",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(getContext(), AdminAddNewProductActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Users")){

                                loadingBar.dismiss();
                                Toast.makeText(getContext(),"Logged in Successfully...",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(getContext(),"Password is incorrect.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getContext(),"Account with this "+phone+" number doesn't exist.",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}