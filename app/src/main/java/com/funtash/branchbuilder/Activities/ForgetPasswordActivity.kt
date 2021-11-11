package com.funtash.branchbuilder.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.funtash.branchbuilder.R
import com.funtash.branchbuilder.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = resources.getColor(R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding= ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSendCode.setOnClickListener { startActivity(Intent(this,PutOtpActivity::class.java))
        }

    }
}