package com.funtash.branchbuilder.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.funtash.branchbuilder.Adapter.AdapterTruth;
import com.funtash.branchbuilder.Model.ApiToken;
import com.funtash.branchbuilder.Model.Branches;
import com.funtash.branchbuilder.R;
import com.funtash.branchbuilder.Response.ResponseApis;
import com.funtash.branchbuilder.databinding.ActivityHomeScreenBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreenActivity extends AppCompatActivity {
  private   ActivityHomeScreenBinding binding;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    String token=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawerLayout = findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        binding.truthsRec.setLayoutManager(new LinearLayoutManager(this));

        //setting drawer icon
        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_drawer, null);
            toolbar.setNavigationIcon(d);
        });
        //toggle the drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //
        SharedPreferences preference=getSharedPreferences("Details", Context.MODE_PRIVATE);
        token=preference.getString("ApiToken",null);
        if (token !=null){
            String apiToken="Token "+token;
            Log.d("dhdhdh", "onCreate: "+token);
            gettingTruths(apiToken);
        }

    }
    public  void  gettingTruths(String Authorization){
        Call<Branches> call= ResponseApis.getInstance().getApiAllPorts().gettingBranches(Authorization);
        call.enqueue(new Callback<Branches>() {
            @Override
            public void onResponse(Call<Branches> call, Response<Branches> response) {
                if (response.isSuccessful() && !response.body().equals(null)){
                    Log.d("dhdhdh", "onResponse: "+response.body().truths);
                    binding.truthsRec.setAdapter(new AdapterTruth(response.body(),HomeScreenActivity.this));


                }
            }

            @Override
            public void onFailure(Call<Branches> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}