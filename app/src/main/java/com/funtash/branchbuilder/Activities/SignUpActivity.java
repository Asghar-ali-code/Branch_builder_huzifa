package com.funtash.branchbuilder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.funtash.branchbuilder.R;
import com.funtash.branchbuilder.databinding.ActivitySignUpBinding;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    String password, email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginTxt.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
        binding.seePassword.setOnClickListener(view -> {
            binding.password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            binding.hidePassword.setVisibility(View.VISIBLE);
            binding.seePassword.setVisibility(View.INVISIBLE);
        });
        binding.hidePassword.setOnClickListener(view -> {
            binding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.hidePassword.setVisibility(View.INVISIBLE);
            binding.seePassword.setVisibility(View.VISIBLE);

        });
        binding.seePasswordRT.setOnClickListener(view -> {
            binding.retypePasswordSignup.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            binding.seePasswordRT.setVisibility(View.INVISIBLE);
            binding.hidePasswordRT.setVisibility(View.VISIBLE);
        });
        binding.hidePasswordRT.setOnClickListener(view -> {
            binding.retypePasswordSignup.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.seePasswordRT.setVisibility(View.VISIBLE);
            binding.hidePasswordRT.setVisibility(View.INVISIBLE);


        });
        binding.signUpBtn.setOnClickListener(view -> {
            if (emailPatternCheck() == true) {
                startActivity(new Intent(this, HomeScreenActivity.class));
            }
        });


    }

    private boolean emailPatternCheck() {

        email = binding.emailSignup.getText().toString().trim();
        Log.d("hddhd", "emailPatternCheck: " + email);
        if (email.isEmpty()) {
            binding.emailLayout.setError("Field can't be empty");

            Log.d("hddhd", "i am in if:field is emty ");
            return false;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            binding.emailLayout.setError("email Pattern is not valid");
            return false;
        }

    }

}