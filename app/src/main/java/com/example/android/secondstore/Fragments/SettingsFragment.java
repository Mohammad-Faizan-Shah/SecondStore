package com.example.android.secondstore.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.secondstore.HomeActivity;
import com.example.android.secondstore.Prevalent.Prevalent;
import com.example.android.secondstore.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {
    LinearLayout linearLayout;
    private TextInputLayout til_settingsName, til_settingsPhone, til_settingsAddress;
    private TextInputEditText tedit_settingsName, tedit_settingsPhone, tedit_settingsAddress;
    CircleImageView userProfileImage;
    private Button settingsSubmitButton;
    private TextView settingsRegisteredPhone;
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureRef;
    private String checker ="";
    private int GalleryPick =1;


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.settings_fragment, container, false);

       storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

       linearLayout=view.findViewById(R.id.backLayout);
       settingsSubmitButton = view.findViewById(R.id.settings_submit_btn);
        userProfileImage =view.findViewById(R.id.settings_profile);
        til_settingsName=view.findViewById(R.id.til_settings_name);
        til_settingsPhone=view.findViewById(R.id.til_settings_phone);
        til_settingsAddress=view.findViewById(R.id.til_settings_address);
        tedit_settingsName=view.findViewById(R.id.tedit_settings_name);
        tedit_settingsPhone=view.findViewById(R.id.tedit_settings_phone);
        tedit_settingsAddress=view.findViewById(R.id.tedit_settings_address);
        settingsRegisteredPhone = view.findViewById(R.id.settings_registerd_no);

        userInfoDisplay(userProfileImage,  tedit_settingsName, tedit_settingsPhone, tedit_settingsAddress);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment()).commit();
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.findViewById(R.id.coordinatorLayout).setVisibility(View.VISIBLE);
            }
        });

        settingsSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked")){
                    
                    userInfoSaved();
                }else {
                    
                    updateOnlyUserInfo();
                }
            }
        });

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                OpenGallery();
            }
        });


        
    return view;
    }
    private void OpenGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), GalleryPick);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("APP_DEBUG", String.valueOf(requestCode));

        try {
            // When an Image is picked
            if (requestCode == GalleryPick && resultCode == Activity.RESULT_OK
                    && null != data) {
                imageUri = data.getData();

                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                       .setAspectRatio(1,1)
                        .start(getContext(), this);
            }
            // when image is cropped
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Log.d("APP_DEBUG",result.toString());
                if (resultCode == Activity.RESULT_OK) {
                    imageUri = result.getUri();
                    Log.d("APP_DEBUG",imageUri.toString());
//                    Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    userProfileImage.setImageURI(imageUri);

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
            else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong"+e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }


    private void updateOnlyUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object>userMap = new HashMap<>();
        userMap.put("name", tedit_settingsName.getText().toString());
        userMap.put("phoneOrder", tedit_settingsPhone.getText().toString());
        userMap.put("address", tedit_settingsAddress.getText().toString());

        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment()).commit();
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.findViewById(R.id.coordinatorLayout).setVisibility(View.VISIBLE);
        Toast.makeText(getContext(),"Profile Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean userInfoSaved() {
        if (tedit_settingsName.getText().toString().isEmpty()){
            til_settingsName.setError("Enter Name.");
            return false;
        }else if(tedit_settingsPhone.getText().toString().isEmpty()){
            til_settingsPhone.setError("Enter Phone Number.");
            return false;
        }else if (tedit_settingsAddress.getText().toString().isEmpty()){
            til_settingsAddress.setError("Enter Address.");
            return false;
        }else if (checker.equals("clicked")){
            uploadImage();
        }
        return true;
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating Profile");
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri!=null){
            final StorageReference fileRef = storageProfilePictureRef
                    .child(Prevalent.currentOnlineUser.getPhone()+".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Uri downloadUrl = (Uri) task.getResult();
                        myUrl =downloadUrl.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        HashMap<String, Object>userMap = new HashMap<>();
                        userMap.put("name", tedit_settingsName.getText().toString());
                        userMap.put("phoneOrder", tedit_settingsPhone.getText().toString());
                        userMap.put("address", tedit_settingsAddress.getText().toString());
                        userMap.put("image", myUrl);
                        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                        progressDialog.dismiss();

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment()).commit();
                        AppCompatActivity activity=(AppCompatActivity)getContext();
                        activity.findViewById(R.id.coordinatorLayout).setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(),"Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                    }else progressDialog.dismiss();
                    Toast.makeText(getContext(),"Error", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getContext(),"Image is not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(CircleImageView userProfileImage, TextInputEditText tedit_settingsName, TextInputEditText tedit_settingsPhone, TextInputEditText tedit_settingsAddress) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("image").exists()){
                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String registeredphone = snapshot.child("phone").getValue().toString();
                        String phoneOrder = snapshot.child("phoneOrder").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(userProfileImage);
                        tedit_settingsName.setText(name);
                        tedit_settingsPhone.setText(phoneOrder);
                        settingsRegisteredPhone.setText(registeredphone);
                        tedit_settingsAddress.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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


}