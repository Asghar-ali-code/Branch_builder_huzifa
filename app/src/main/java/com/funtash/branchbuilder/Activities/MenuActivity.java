package com.funtash.branchbuilder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.funtash.branchbuilder.Adapter.menuAdapter;
import com.funtash.branchbuilder.Model.AppValues;
import com.funtash.branchbuilder.Model.UpdateNotifications;
import com.funtash.branchbuilder.Response.ResponseApis;
import com.funtash.branchbuilder.databinding.ActivityMenuBinding;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> arr;
        arr = AppValues.getArrayList();

        binding.recycleView.setAdapter(new menuAdapter(this, arr));

    }

    public void updateNotification(String id, Boolean noti, String starthour, String endhour, String delay) {
        Call<UpdateNotifications> call = ResponseApis.getInstance().getApiAllPorts().updateNoti(id, noti, starthour, endhour, delay);
        call.enqueue(new Callback<UpdateNotifications>() {
            @Override
            public void onResponse(Call<UpdateNotifications> call, Response<UpdateNotifications> response) {
                try {


                    if (response.isSuccessful() && !response.body().equals(null)) {
                        Log.d("hdhdhd", "onResponse: " + response.body().yup);
                        if (!response.body().yup.equals(null)) {
                            Toast.makeText(MenuActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(MenuActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MenuActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<UpdateNotifications> call, Throwable t) {
                Toast.makeText(MenuActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private int getNotificationId() {
        int id = -1;
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("NotifactionPrf", MODE_PRIVATE);
        id = sharedPreferences.getInt("NotificationId", -1);
        return id;

    }
}