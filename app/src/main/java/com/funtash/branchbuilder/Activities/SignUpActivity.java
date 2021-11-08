package com.funtash.branchbuilder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.funtash.branchbuilder.Model.ApiToken;
import com.funtash.branchbuilder.Model.Register;
import com.funtash.branchbuilder.Response.ResponseApis;
import com.funtash.branchbuilder.databinding.ActivitySignUpBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    String  email,val;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginTxt.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
        binding.signUpBtn.setOnClickListener(view -> {

            if (emailPatternCheck() && validatePassword()) {
                registerUser(email,val);

            }
        });


    }

    private boolean emailPatternCheck() {

        email = binding.emailSignup.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
            binding.emailSignup.setError("Field can't be empty");
            return false;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            Toast.makeText(this, "email Pattern is not valid", Toast.LENGTH_SHORT).show();
            binding.emailSignup.setError("email Pattern is not valid");
            return false;
        }


    }

    private boolean validatePassword() {
         val = binding.password.getText().toString().trim();
        String val1 = binding.retypePasswordSignup.getText().toString().trim();
        String regex = "(?=.*[0-9])(?=.*[a-z]).{8,}";
        if (val.isEmpty()) {
            binding.password.setError("This field cannot be empty");
            return false;
        } else if (val.length() < 8) {
            binding.password.setError("Password Must be greater or equal to 8 character");
            return false;
        } else if (!val.matches(regex)) {
            binding.password.setError("password Must contain digits and Alphabets Lower Case");
            return false;

        } else if (val1.isEmpty()) {
            binding.retypePasswordSignup.setError("This field cannot be empty");
            return false;
        } else if (!val.equals(val1)) {
            binding.retypePasswordSignup.setError("Password not match!");
            return false;
        } else {

            binding.password.setError(null);
            binding.retypePasswordSignup.setError(null);
            return true;
        }

    }
    private void registerUser(String email, String password){
        binding.signUpBtn.setVisibility(View.INVISIBLE);
        binding.spinKit.setVisibility(View.VISIBLE);
        Call<Register> call= ResponseApis.getInstance().getApiAllPorts().registerUser(email,password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                try {


                if (response.isSuccessful() && !response.body().equals(null)){
                    try {
                        if (response.body().status.equals("ok")){
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                            Log.d("hdhdd", "onResponse: "+response.body().status);
                        }else {
                            Toast.makeText(getApplicationContext(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
                            binding.signUpBtn.setVisibility(View.VISIBLE);
                            binding.spinKit.setVisibility(View.INVISIBLE);
                        }
                    }catch (NullPointerException e){}


                }else {
                    Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                    binding.signUpBtn.setVisibility(View.VISIBLE);
                    binding.spinKit.setVisibility(View.INVISIBLE);
                }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.signUpBtn.setVisibility(View.VISIBLE);
                binding.spinKit.setVisibility(View.INVISIBLE);

            }
        });

    }


}