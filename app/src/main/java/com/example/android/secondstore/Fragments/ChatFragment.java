package com.example.android.secondstore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.secondstore.R;

public class ChatFragment extends Fragment {

        TextView name, profession, senMessage;
        ImageView imageView;
        CardView cardView;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_layout, container, false);
        senMessage = view.findViewById(R.id.chat_send_message);
        profession = view.findViewById(R.id.chat_item_prof);
        imageView = view.findViewById(R.id.chat_card_image);
        name = view.findViewById(R.id.chat_item_name);





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