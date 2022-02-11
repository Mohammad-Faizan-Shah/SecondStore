package com.example.android.secondstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.secondstore.Model.Users;
import com.example.android.secondstore.Prevalent.Prevalent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

import static android.content.SharedPreferences.*;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout til_InputPassword,til_InputPhoneNumber;
    private TextInputEditText tedit_InputPassword, tedit_InputPhoneNumber;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink, loginRegisterBtn;


    private String parentDbName = "Users";
    private com.rey.material.widget.CheckBox chkBoxRemeberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton = (Button)findViewById(R.id.login_btn);
        loginRegisterBtn = findViewById(R.id.login_register_btn);
        til_InputPhoneNumber = (TextInputLayout)findViewById(R.id.til_login_phone_number_input);
        tedit_InputPhoneNumber = (TextInputEditText) findViewById(R.id.tedit_login_phone_number_input);
        til_InputPassword = (TextInputLayout)findViewById(R.id.til_login_passaword_input);
        tedit_InputPassword = (TextInputEditText)findViewById(R.id.tedit_login_password_input);
        AdminLink =  (TextView)findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView)findViewById(R.id.not_admin_panel_link);
        loadingBar = new ProgressDialog(this);


        chkBoxRemeberMe = (com.rey.material.widget.CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


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
    }
    public void requestFocus(View view){
        if (view.requestFocus()){
            getWindow()
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

      }return true;
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
                               Toast.makeText(LoginActivity.this,"Logged in Successfully...",Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent = new Intent(LoginActivity.this,AdminAddNewProductActivity.class);
                               startActivity(intent);
                           }
                           else if (parentDbName.equals("Users")){

                               Toast.makeText(LoginActivity.this,"Logged in Successfully...",Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                               Prevalent.currentOnlineUser = usersData;
                               startActivity(intent);
                           }
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this,"Password is incorrect.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this,"Account with this "+phone+" number doesn't exist.",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}