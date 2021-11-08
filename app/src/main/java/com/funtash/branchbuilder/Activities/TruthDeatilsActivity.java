package com.funtash.branchbuilder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.funtash.branchbuilder.databinding.ActivityTruthDeatilsBinding;

public class TruthDeatilsActivity extends AppCompatActivity {
    ActivityTruthDeatilsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTruthDeatilsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}