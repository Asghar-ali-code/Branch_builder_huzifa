package com.funtash.branchbuilder.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Scroller;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.funtash.branchbuilder.Model.AppValues;
import com.funtash.branchbuilder.Model.CreateTruth;
import com.funtash.branchbuilder.Model.DeleteTruth;
import com.funtash.branchbuilder.Model.UpdateTruth;
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
    String category;
    String selectedCategory;
    int truthId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTruthDeatilsBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(binding.getRoot());
        intent = getIntent();
        scenario = intent.getIntExtra("scenario", 0);
        if (scenario==1) {
            category = intent.getStringExtra("truth");
            truthId=intent.getIntExtra("id",0);
            String title=AppValues.getTruthTitle();
            String body=AppValues.getTruthBody();
            binding.title.setText(title);
            binding.body.setText(body);
            binding.delete.setVisibility(View.VISIBLE);
        }
        binding.title.setScroller(new Scroller(this));
        binding.title.setMaxLines(1);

        binding.title.setVerticalScrollBarEnabled(true);
        binding.title.setMovementMethod(new ScrollingMovementMethod());
        initSpinner(scenario);
        countNumberOfCharacter();
        binding.backBtn.setOnClickListener(view -> onBackPressed());
        binding.editTruthBtn.setOnClickListener(view -> {
            if (scenario !=1){
            binding.spinKit.setVisibility(View.VISIBLE);
            binding.editTruthBtn.setVisibility(View.INVISIBLE);
            addTruth(selectedCategory,binding.title.getText().toString(),binding.body.getText().toString(),formattedAuthorizationToken());
            }else {
                binding.spinKit.setVisibility(View.VISIBLE);
                binding.editTruthBtn.setVisibility(View.INVISIBLE);
                updateTruth(formattedAuthorizationToken(),String.valueOf(truthId),binding.title.getText().toString(),binding.body.getText().toString());
            }


        });
        binding.delete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(TruthDeatilsActivity.this,R.style.TimePicker)
                    .setTitle("Delete Truth")
                    .setMessage("Are you sure you want delete this Truth")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            binding.spinKit.setVisibility(View.VISIBLE);
                            binding.editTruthBtn.setVisibility(View.INVISIBLE);
                            removeTruth(formattedAuthorizationToken(),String.valueOf(truthId));
                        }
                    })
                    .setNegativeButton("NO", null);
            builder.show();


        });



    }

    private void countNumberOfCharacter() {
        binding.body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int character=binding.body.length();
                binding.count.setVisibility(View.VISIBLE);
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
        Log.d("dhdhdh", "initSpinner: "+scen);
        if (scen == 1) {
            provinceList=AppValues.getArrayList();
            if (provinceList.contains(category)){
                ;
                int index=provinceList.indexOf(category);
                spProvince.setSelection(index);
                Log.d("Hhdhdhdh", "initSpinner: "+category);

            }

        } else {
            binding.textTop.setText("Add Truth");
            binding.editTruthBtn.setText("Add Truth");

            provinceList= AppValues.getArrayList();
        }
        if (!provinceList.isEmpty()) {
            spProvince.setItem(provinceList);
            spProvince.setAutoSelectable(true);
        }
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedCategory=provinceList.get(position);
             //   Toast.makeText(TruthDeatilsActivity.this, provinceList.get(position), Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(TruthDeatilsActivity.this,HomeScreenActivity.class));
                    finish();
                }else {
                    Toast.makeText(TruthDeatilsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    binding.spinKit.setVisibility(View.INVISIBLE);
                    binding.editTruthBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CreateTruth> call, Throwable t) {
                Toast.makeText(TruthDeatilsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.spinKit.setVisibility(View.INVISIBLE);
                binding.editTruthBtn.setVisibility(View.VISIBLE);



            }
        });
    }
    private String formattedAuthorizationToken(){
        String token;
        SharedPreferences preference=getSharedPreferences("Details", Context.MODE_PRIVATE);
        token=preference.getString("ApiToken",null);
        if (token !=null){
            String apiToken="Token "+token;
            return apiToken;
        }
        return null;
    }
    public void removeTruth(String Authorization, String id ) {
        Log.d("dhdhhdhd", "removeTruth: "+id+"id"+Authorization);
        Call<DeleteTruth> call = ResponseApis.getInstance().getApiAllPorts().deleteTruth(Authorization, id);
        call.enqueue(new Callback<DeleteTruth>() {
            @Override
            public void onResponse(Call<DeleteTruth> call, Response<DeleteTruth> response) {
                if (response.isSuccessful() && !response.body().equals(null)) {
                    if (response.body().yup.equals("Congrats!")) {
                        startActivity(new Intent(TruthDeatilsActivity.this,HomeScreenActivity.class));
                        finish();


                    }else {
                        binding.spinKit.setVisibility(View.INVISIBLE);
                        binding.editTruthBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(TruthDeatilsActivity.this, "else of yup", Toast.LENGTH_SHORT).show();
                    }
                }else{ Toast.makeText(TruthDeatilsActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    binding.spinKit.setVisibility(View.INVISIBLE);}
            }

            @Override
            public void onFailure(Call<DeleteTruth> call, Throwable t) {
                Toast.makeText(TruthDeatilsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.spinKit.setVisibility(View.INVISIBLE);
                binding.editTruthBtn.setVisibility(View.VISIBLE);

            }
        });
    }
    public  void  updateTruth(String Authorization, String id,String title,String body ){
        Call<UpdateTruth> call=ResponseApis.getInstance().getApiAllPorts().updateTruth(Authorization,id,title,body);
        call.enqueue(new Callback<UpdateTruth>() {
            @Override
            public void onResponse(Call<UpdateTruth> call, Response<UpdateTruth> response) {
                if (response.isSuccessful() && !response.body().equals(null)){
                    Toast.makeText(TruthDeatilsActivity.this, "Truth Updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TruthDeatilsActivity.this,HomeScreenActivity.class));
                    finish();
                }else {
                    Toast.makeText(TruthDeatilsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    binding.spinKit.setVisibility(View.INVISIBLE);
                    binding.editTruthBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<UpdateTruth> call, Throwable t) {
                Toast.makeText(TruthDeatilsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.spinKit.setVisibility(View.INVISIBLE);
                binding.editTruthBtn.setVisibility(View.VISIBLE);

            }
        });

    }
}