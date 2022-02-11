package com.example.android.secondstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private TextInputLayout til_InputName, til_InputPhoneNumber;
    private TextInputEditText tedit_InputName, tedit_InputPhoneNumber;
    private TextInputLayout til_InputPassword, til_InputConfirmPassword;
    private TextInputEditText tedit_InputPassword,tedit_InputConfirmPassword;
    private TextView AdminLink, NotAdminLink;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CreateAccountButton = (Button)findViewById(R.id.register_btn);
        til_InputName = (TextInputLayout)findViewById(R.id.til_register_username_input);
        til_InputPhoneNumber = (TextInputLayout)findViewById(R.id.til_register_phone_number_input);
        til_InputPassword = (TextInputLayout)findViewById(R.id.til_register_password_input);
        tedit_InputName = (TextInputEditText)findViewById(R.id.tedit_register_username_input);
        tedit_InputPhoneNumber = (TextInputEditText)findViewById(R.id.tedit_register_phone_number_input);
        til_InputConfirmPassword = (TextInputLayout)findViewById(R.id.til_register_confirm_password_input);
        tedit_InputPassword = (TextInputEditText) findViewById(R.id.tedit_register_password_input);
        tedit_InputConfirmPassword = ( TextInputEditText) findViewById(R.id.tedit_register_confirm_password_input);
        AdminLink =  (TextView)findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView)findViewById(R.id.not_admin_panel_link);

        loadingBar = new ProgressDialog(this);


//        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    InputConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    InputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                }
//                else {
//                    InputConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    InputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccountButton.setText("Create admin account");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
                Log.d("Change parentDbName", parentDbName);
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccountButton.setText("Create account");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
                Log.d("Change parentDbName", parentDbName);
            }
        });


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

    }
    public void requestFocus(View view){
        if (view.requestFocus()){
            getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean CreateAccount() {
        String name = tedit_InputName.getText().toString();
        String phone = tedit_InputPhoneNumber.getText().toString();
        String password = tedit_InputConfirmPassword.getText().toString();
        String confirmPassword = tedit_InputConfirmPassword.getText().toString();


        if (tedit_InputName.getText().toString().isEmpty()){
            tedit_InputName.setError("Enter Name.");
            return false;
//            Toast.makeText(this,"Please write your name...", Toast.LENGTH_SHORT).show();
        }
            else{
                if (tedit_InputName.getText().toString().trim().length()<3){
                    til_InputName.setError("Minimum 3 Character Required.");
                    requestFocus(tedit_InputName);
                    return false;
                }
        } if (tedit_InputPhoneNumber.getText().toString().isEmpty()){
                        tedit_InputPhoneNumber.setError("Enter Phone Number.");
                        return false;
//                Toast.makeText(this,"Please write your phone number...", Toast.LENGTH_SHORT).show();
            }else{
                if (tedit_InputPhoneNumber.getText().toString().trim().length()<10){
                    til_InputPhoneNumber.setError("Enter a Valid Phone Number");
                   requestFocus(tedit_InputPhoneNumber);
                    return false;
                }
        } if (tedit_InputPassword.getText().toString().isEmpty()){
                    til_InputPassword.setError("Please create password...");
                    requestFocus(tedit_InputPassword);
                    return false;
//                    Toast.makeText(this,"Please create password...", Toast.LENGTH_SHORT).show();
                }else if (!tedit_InputPassword.equals(tedit_InputConfirmPassword)) {
                    tedit_InputConfirmPassword.setError("Password doesn't match.");
                    return false;
//                    Toast.makeText(this, "Password doesn't match.", Toast.LENGTH_SHORT).show();;
        }else {
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            ValidatephoneNumber(name, phone, password);
        }

        return true;
    }

    private void ValidatephoneNumber(String name, String phone, String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child(parentDbName).child(phone).exists())
                    {
                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put("phone", phone);
                        userdataMap.put("password", password);
                        userdataMap.put("name", name);
                        Log.d("Change parentDbName", parentDbName);

                        RootRef.child(parentDbName).child(phone).updateChildren(userdataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(RegisterActivity.this, "Congratulations, your account has bee created.",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                       Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                       startActivity(intent);
                                   }
                                   else {
                                       loadingBar.dismiss();
                                       Toast.makeText(RegisterActivity.this,"Network Error: Try again after some time...", Toast.LENGTH_SHORT).show();
                                   }
                                    }
                                });
                    }
                else
                    {
                        Toast.makeText(RegisterActivity.this,"An account is already exist with "+phone+" number.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();


                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}