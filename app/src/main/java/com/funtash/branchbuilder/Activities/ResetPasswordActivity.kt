package com.funtash.branchbuilder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.funtash.branchbuilder.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitRetype.setOnClickListener {
            if (validatePassword())
            startActivity(Intent(applicationContext,LoginActivity::class.java )) }
    }
    private fun validatePassword(): Boolean {

        val value:String = binding.passwordForget.getText().toString().trim()
        val val1: String = binding.reTypePassword.getText().toString().trim()
        val regex = "(?=.*[0-9])(?=.*[a-z]).{8,}".toRegex()
        return if (value.isEmpty()) {
            binding.passwordForget.setError("This field cannot be empty")
            false
        } else if (value.length < 8) {
            binding.passwordForget.setError("Password Must be greater or equal to 8 character")
            false
        } else if (!value.matches(regex)) {
            binding.passwordForget.setError("password Must contain digits and Alphabets Lower Case")
            false
        } else if (val1.isEmpty()) {
            binding.reTypePassword.setError("This field cannot be empty")
            false
        } else if (value != val1) {
            binding.reTypePassword.setError("Password not match!")
            false
        } else {
            binding.reTypePassword.setError(null)
            binding.reTypePassword.setError(null)
            true
        }
    }
}


