package com.funtash.branchbuilder.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.funtash.branchbuilder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         val preference=getSharedPreferences("Details", Context.MODE_PRIVATE)
        var token: String? =null

        token=preference.getString("ApiToken",null)

        Handler(Looper.getMainLooper()).postDelayed({
            if (token !=null){
                startActivity(Intent(this@MainActivity,HomeScreenActivity::class.java))
                finish()
            }else {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }, 700)


    }
}