package com.funtash.branchbuilder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Scroller;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.funtash.branchbuilder.Model.CreateTruth;
import com.funtash.branchbuilder.R;
import com.funtash.branchbuilder.Response.ResponseApis;
import com.funtash.branchbuilder.databinding.ActivityTruthDeatilsBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TruthDeatilsActivity extends AppCompatActivity {
    ActivityTruthDeatilsBinding binding;
    private SmartMaterialSpinner<String> spProvince;
    private SmartMaterialSpinner<String> spEmptyItem;
    private List<String> provinceList;
    int scenario = 0;
    Intent intent;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTruthDeatilsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        scenario = intent.getIntExtra("scenario", 0);
        title = intent.getStringExtra("truth");
        binding.title.setScroller(new Scroller(this));
        binding.title.setMaxLines(1);
        binding.title.setVerticalScrollBarEnabled(true);
        binding.title.setMovementMethod(new ScrollingMovementMethod());
        initSpinner(scenario);
        countNumberOfCharacter();


    }

    private void countNumberOfCharacter() {
        binding.body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int character=binding.body.length();
                binding.count.setText("Count:" +String.valueOf(character));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initSpinner(int scen) {
        spProvince = findViewById(R.id.spinner);
        // spEmptyItem = findViewById(R.id.sp_empty_item);
        provinceList = new ArrayList<>();
        if (scen == 1) {
            provinceList.add(title);
            provinceList.add("Kampong Thom");
            provinceList.add("Kampong Cham");
            provinceList.add("Kampong Chhnang");
            provinceList.add("Phnom Penh");
            provinceList.add("Kandal");
            provinceList.add("Kampot");

        } else {
            provinceList.add("Kampong Thom");
            provinceList.add("Kampong Cham");
            provinceList.add("Kampong Chhnang");
            provinceList.add("Phnom Penh");
            provinceList.add("Kandal");
            provinceList.add("Kampot");
        }
        spProvince.setItem(provinceList);

        spProvince.setAutoSelectable(true);

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(TruthDeatilsActivity.this, provinceList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void addTruth(String category,String title,String body,String Authorization){
        Call<CreateTruth> call= ResponseApis.getInstance().getApiAllPorts().createTruth(category,title,body,Authorization);
        call.enqueue(new Callback<CreateTruth>() {
            @Override
            public void onResponse(Call<CreateTruth> call, Response<CreateTruth> response) {
                if (response.isSuccessful() && !response.body().equals(null)){
                    Toast.makeText(TruthDeatilsActivity.this, "Truth added successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TruthDeatilsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateTruth> call, Throwable t) {
                Toast.makeText(TruthDeatilsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }
}