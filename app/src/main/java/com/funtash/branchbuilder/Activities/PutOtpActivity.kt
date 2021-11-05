package com.funtash.branchbuilder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.funtash.branchbuilder.databinding.ActivityPutOtpBinding

class PutOtpActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityPutOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPutOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmitOtp.setOnClickListener { startActivity(Intent(applicationContext,ResetPasswordActivity::class.java)) }
    }
}