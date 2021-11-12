package com.funtash.branchbuilder.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.funtash.branchbuilder.Adapter.menuAdapter;
import com.funtash.branchbuilder.Model.AppValues;
import com.funtash.branchbuilder.Model.UpdateNotifications;
import com.funtash.branchbuilder.R;
import com.funtash.branchbuilder.Response.ResponseApis;
import com.funtash.branchbuilder.TimePicker.timePicker;
import com.funtash.branchbuilder.databinding.ActivityMenuBinding;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    ActivityMenuBinding binding;
    // AlertDialog.Builder dialogTime;
    LayoutInflater inflater;
    // View dialogView;
    int i;
    TextInputEditText time_start, time_end, delay_time;
    String startHour, endHour, delayHour = null;
    AppCompatButton confirm;
    String startTime, endTime;
    Calendar calendar = Calendar.getInstance();
    menuAdapter adapterObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(binding.getRoot());
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> arr;
        arr = AppValues.getArrayList();
        if (!arr.isEmpty())
        adapterObj = new menuAdapter(this, arr, i);
        binding.recycleView.setAdapter(adapterObj);
        binding.notiSet.setOnClickListener(view -> {
            i = AppValues.getI();
            if (i == 1) {

                selectNotificationDetails();

            } else {
                Toast.makeText(getApplicationContext(), "Please Checked atleast one Category", Toast.LENGTH_SHORT).show();

            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });


    }

    public void updateNotification(String Authorization, String id, Boolean noti, String starthour, String endhour, String delay) {
        Call<UpdateNotifications> call = ResponseApis.getInstance().getApiAllPorts().updateNoti(Authorization, id, noti, starthour, endhour, delay);
        call.enqueue(new Callback<UpdateNotifications>() {
            @Override
            public void onResponse(Call<UpdateNotifications> call, Response<UpdateNotifications> response) {
                try {

                    binding.spinKit.setVisibility(View.INVISIBLE);
                    if (response.isSuccessful() && !response.body().equals(null)) {
                        Log.d("dhhdhdhdhd", "onResponse: " + response.body().yup);
                        if (!response.body().yup.equals(null)) {
                            startActivity(new Intent(MenuActivity.this, HomeScreenActivity.class));
                            finish();
                            Toast.makeText(MenuActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(MenuActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        binding.spinKit.setVisibility(View.INVISIBLE);

                    } else
                        Toast.makeText(MenuActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    binding.spinKit.setVisibility(View.INVISIBLE);
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

    private void selectNotificationDetails() {
        Dialog dialogTime = new Dialog(this);
        dialogTime.setContentView(R.layout.time_picker_dialog);
        dialogTime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTime.setCancelable(true);
        time_start = (TextInputEditText) dialogTime.findViewById(R.id.time);
        time_end = (TextInputEditText) dialogTime.findViewById(R.id.time2);
        delay_time = (TextInputEditText) dialogTime.findViewById(R.id.delay);
        confirm = (AppCompatButton) dialogTime.findViewById(R.id.confirm);
        dialogTime.show();
        DialogFragment timePicObj = new timePicker();
        //time
        time_start.setOnClickListener(v -> {

            timePicObj.show(getSupportFragmentManager(), "time Picker");
        });
        time_end.setOnClickListener(view -> {

            new TimePickerDialog(MenuActivity.this, R.style.TimePicker, onStartTimeListener, calendar
                    .get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show();


        });

        confirm.setOnClickListener(view -> {
            delayHour = delay_time.getText().toString();
            if (!delayHour.equals("")) {
                int delay = Integer.parseInt(delayHour);
                if (delay > 10 && delay <= 60) {
                    binding.spinKit.setVisibility(View.VISIBLE);
                    Log.d("djddhd", "selectNotificationDetails: " + i);
                    updateNotification(apiToken(), String.valueOf(getNotificationId()), true, startTime, endTime, delayHour);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select delay time between 10 to 60 minutes", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private int getNotificationId() {
        int id = -1;
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("NotifactionPrf", MODE_PRIVATE);
        id = sharedPreferences.getInt("NotificationId", -1);
        return id;

    }

    private String apiToken() {
        SharedPreferences preference = getSharedPreferences("Details", Context.MODE_PRIVATE);
        String token = preference.getString("ApiToken", null);
        if (token != null) {
            String apiToken = "Token " + token;
            return apiToken;
        }
        return null;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        startHour = (i + " : " + i1);
        startTime = i + "";
        Log.d("dhdhdhdh", "onTimeSet: " + startTime);
        time_start.setText(startHour);


    }

    TimePickerDialog.OnTimeSetListener onStartTimeListener = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String AM_PM = null;
            int am_pm;

            time_end.setText(hourOfDay + ": " + minute);
            endTime = hourOfDay + "";
            Log.d("dhdhdhdh", "onTimeSet: " + endTime);
            calendar.set(Calendar.HOUR, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

        }
    };
}