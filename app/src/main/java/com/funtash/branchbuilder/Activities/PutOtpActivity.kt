package com.funtash.branchbuilder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.funtash.branchbuilder.R
import com.funtash.branchbuilder.databinding.ActivityPutOtpBinding

class PutOtpActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityPutOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = resources.getColor(R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding= ActivityPutOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmitOtp.setOnClickListener { startActivity(Intent(applicationContext,ResetPasswordActivity::class.java)) }
    }
}