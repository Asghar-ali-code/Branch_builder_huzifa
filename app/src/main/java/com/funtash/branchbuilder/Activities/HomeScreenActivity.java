package com.funtash.branchbuilder.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.funtash.branchbuilder.Adapter.AdapterTruth;
import com.funtash.branchbuilder.BuildConfig;
import com.funtash.branchbuilder.Model.ApiToken;
import com.funtash.branchbuilder.Model.AppValues;
import com.funtash.branchbuilder.Model.Branches;
import com.funtash.branchbuilder.Model.DefaultNotification;
import com.funtash.branchbuilder.Model.UpdateNotifications;
import com.funtash.branchbuilder.R;
import com.funtash.branchbuilder.Response.ResponseApis;
import com.funtash.branchbuilder.databinding.ActivityHomeScreenBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreenActivity extends AppCompatActivity {
  private   ActivityHomeScreenBinding binding;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    String token=null;
    String apiToken;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.TimePicker)
                .setTitle("Exit")
                .setMessage("Are you sure you wan to close this app")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("NO", null);
        builder.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        binding=ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawerLayout = findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clickListnerns(this);

        //setting drawer icon
        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_drawer, null);
            toolbar.setNavigationIcon(d);
        });
        //toggle the drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //getting api token from sharedfpref
        SharedPreferences preference=getSharedPreferences("Details", Context.MODE_PRIVATE);
        token=preference.getString("ApiToken",null);
        if (token !=null){
             apiToken="Token "+token;
            Log.d("dhdhdh", "onCreate: "+token);
            gettingTruths(apiToken);
        }
        binding.floatingButton.setOnClickListener(view -> {
            Intent intent=new Intent(HomeScreenActivity.this,TruthDeatilsActivity.class);
            intent.putExtra("scenario",2);
            startActivity(intent);
        });
        binding.truth.setOnClickListener(view -> {
            startActivity(new Intent(this,HomeScreenActivity.class));
        });
        if (preference.getBoolean("notification",true))
        binding.switchNotification.setChecked(true);
        binding.switchNotification.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.switchNotification.isChecked()){
                Toast.makeText(HomeScreenActivity.this, "Notification  on", Toast.LENGTH_SHORT).show();
                Boolean notification = true;
                SharedPreferences.Editor editor= preference.edit();
                editor.putBoolean("notification",notification);
                editor.commit();
                editor.apply();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("NotifactionPrf",MODE_PRIVATE);
          int notification_id=  sharedPreferences.getInt("NotificationId",-1);
                updateNotification(apiToken,String.valueOf(notification_id),true,"7","22","20");


            }else {
                Toast.makeText(HomeScreenActivity.this, "Notification  Off", Toast.LENGTH_SHORT).show();
                Boolean notification = false;
                SharedPreferences.Editor editor= preference.edit();
                editor.putBoolean("notification",notification);
                editor.commit();
                editor.apply();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("NotifactionPrf",MODE_PRIVATE);
                int notification_id=  sharedPreferences.getInt("NotificationId",-1);
                updateNotification(apiToken,String.valueOf(notification_id),false,"7","22","20");
            }
        });
        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preference=getSharedPreferences("Details", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preference.edit();
                editor.remove("ApiToken");
                editor.apply();
                startActivity(new Intent(HomeScreenActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    private void clickListnerns(Context context) {
        binding.share.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }

        });
        binding.rateUs.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialogCustom)
                    .setTitle("Rate Application")
                    .setMessage("Please, rate this app at PlayStore")
                    .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (context != null) {
                                String link = "market://details?id=";
                                try {
                                    // play market available
                                    context.getPackageManager()
                                            .getPackageInfo("com.android.vending", 0);
                                    // not available
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                    // should use browser
                                    link = "https://play.google.com/store/apps/details?id=";
                                }
                                // starts external action
                                context.startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(link + context.getPackageName())));
                            }
                        }
                    })
                    .setNegativeButton("CANCEL", null);
            builder.show();

        });
        binding.menu.setOnClickListener(view -> {
            startActivity(new Intent(this,MenuActivity.class));
        });
        binding.truthsRec.setLayoutManager(new LinearLayoutManager(this));

    }

    public  void  gettingTruths(String Authorization){
        Call<Branches> call= ResponseApis.getInstance().getApiAllPorts().gettingBranches(Authorization);
        call.enqueue(new Callback<Branches>() {
            @Override
            public void onResponse(Call<Branches> call, Response<Branches> response) {
                if (response.isSuccessful() && !response.body().equals(null)){
                    binding.spinKit.setVisibility(View.INVISIBLE);
                    if (checkNotificationId()){
                        defaultNotifictions(Authorization);
                    }
                    Log.d("dhdhdh", "onResponse: "+response.body().truths);
                    binding.truthsRec.setAdapter(new AdapterTruth(response.body(),HomeScreenActivity.this,Authorization));
                    List<String> list=new ArrayList<String>();

                    list=response.body().categories;
                    AppValues.setArrayList((ArrayList<String>) list);
                    int i;
                    for (i=0; i<=list.size();i++){
                        try {
                            Log.d("dhdhdhd", "onResponse: "+list.get(i));
                        }catch (IndexOutOfBoundsException e){}


                    }

                }
            }

            @Override
            public void onFailure(Call<Branches> call, Throwable t) {
                binding.spinKit.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public  void  defaultNotifictions (String Authorization){
        Call<DefaultNotification> call=ResponseApis.getInstance().getApiAllPorts().defaultNoti(Authorization);
        call.enqueue(new Callback<DefaultNotification>() {
            @Override
            public void onResponse(Call<DefaultNotification> call, Response<DefaultNotification> response) {
                if (response.isSuccessful() && !response.body().equals(null)){
                    savingNotificationId(response.body().noti.id);
                    Log.d("gettingdd", "onResponse: "+response.body().noti.id);

                }else {
                    Toast.makeText(HomeScreenActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultNotification> call, Throwable t) {
                Toast.makeText(HomeScreenActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    private boolean checkNotificationId(){
        int id=-1;
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("NotifactionPrf",MODE_PRIVATE);
      id=sharedPreferences.getInt("NotificationId",-1);
      if (id ==-1){
          Log.d("gettingddChk", "onResponse: "+String.valueOf(id));
          return  true;

      }
        Log.d("gettingddSavChk", "onResponse: "+String.valueOf(id));
        return  false;


    }
    private  void  savingNotificationId(int id){
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("NotifactionPrf",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("NotificationId",id);
        editor.commit();
        editor.apply();
        Log.d("gettingddSav", "onResponse: "+String.valueOf(id));
    }
    public void updateNotification(String Authorization, String id, Boolean noti, String starthour, String endhour, String delay) {
        Call<UpdateNotifications> call = ResponseApis.getInstance().getApiAllPorts().updateNoti(Authorization, id, noti, starthour, endhour, delay);
        call.enqueue(new Callback<UpdateNotifications>() {
            @Override
            public void onResponse(Call<UpdateNotifications> call, Response<UpdateNotifications> response) {
                try {


                    if (response.isSuccessful() && !response.body().equals(null)) {
                        Log.d("dhhdhdhdhd", "onResponse: " + response.body().yup);
                        if (!response.body().yup.equals(null)) {
                            Toast.makeText(HomeScreenActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(HomeScreenActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(HomeScreenActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<UpdateNotifications> call, Throwable t) {
                Toast.makeText(HomeScreenActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}