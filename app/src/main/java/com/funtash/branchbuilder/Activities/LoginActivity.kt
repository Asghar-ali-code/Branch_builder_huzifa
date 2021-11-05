package com.funtash.branchbuilder.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import com.funtash.branchbuilder.Model.ApiToken
import com.funtash.branchbuilder.Response.ResponseApis
import com.funtash.branchbuilder.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signupTxt.setOnClickListener { startActivity(Intent(applicationContext,SignUpActivity::class.java))
        }
        binding.forgetPassword.setOnClickListener { startActivity(Intent(applicationContext,ForgetPasswordActivity::class.java))
        }
        binding.seePassword.setOnClickListener(View.OnClickListener {
            binding.passwordLogin.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.seePassword.visibility = View.INVISIBLE
            binding.hidePassword.visibility = View.VISIBLE
        })

        binding.hidePassword.setOnClickListener {
            binding.passwordLogin.inputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            binding.seePassword.visibility = View.VISIBLE
            binding.hidePassword.visibility = View.INVISIBLE

        }
        binding.LoginBtn.setOnClickListener {
            var email:kotlin.String=binding.emailLogin.text.toString()
            var password:kotlin.String=binding.passwordLogin.text.toString()
            Log.d("jdjdd", "onCreate: "+email+password)
            login(email,password)
            }


    }
    fun login(email:kotlin.String,password:kotlin.String){
        Log.d("jdjdd", "InMehtod ")


        val call: Call<ApiToken> = ResponseApis.getInstance()
            .apiAllPorts
            .getApiToken(password,email)
        call.enqueue(object : Callback<ApiToken> {
            override fun onResponse(
                call: Call<ApiToken>,
                response: Response<ApiToken>
            ) {
                try {
                    Log.d("jdjdd", "in succesful ")
                    if (response.isSuccessful && response.body() !=null) {
                        var token: kotlin.String? =null
                        token=response.body()!!.token
                       if (token !=null){
                           SavingKeyValuesInShPref(token.toString())

                        startActivity(Intent(this@LoginActivity,HomeScreenActivity::class.java))
                        finish()}else
                           Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show()


                    } else {
                        Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show()

                    }
                } catch (e: NullPointerException) {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiToken>, t: Throwable) {
                Log.d("jdjdd", "Infailuer ")
                Toast.makeText(applicationContext, "Something Want Wrong", Toast.LENGTH_SHORT)
                    .show()


            }
        })
    }
    fun SavingKeyValuesInShPref(ApiToken: kotlin.String){
        val preference=getSharedPreferences("Details", Context.MODE_PRIVATE)
        val editor=preference.edit()
        editor.putString(ApiToken.toString(),"ApiToken")
        editor.commit()
    }
}