package com.example.lab7_20203554.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab7_20203554.MainActivity;
import com.example.lab7_20203554.R;
import com.example.lab7_20203554.databinding.FragmentHeaderLogoutBinding;
import com.google.firebase.auth.FirebaseAuth;

public class HeaderLogoutFragment extends Fragment {
    FragmentHeaderLogoutBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHeaderLogoutBinding.inflate(inflater, container, false);

        binding.buttonLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}