package com.example.popmate.view.activities.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.popmate.databinding.ActivityLoginTestBinding
import com.example.popmate.model.data.remote.login.LoginTokenVO
import com.example.popmate.model.repository.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginTestBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(ApiClient.getJwtToken() != null){
            Log.d("ddd","로그인되어있습니다.")
        }
        binding.loginTestBtn.setOnClickListener {
            val apiService = ApiClient.getTokenService
            val call: Call<LoginTokenVO> = apiService.loginTest()
            call.enqueue(object : Callback<LoginTokenVO>{
                override fun onResponse(call: Call<LoginTokenVO>, response: Response<LoginTokenVO>) {
                    Log.d("ddd","${response.body()}")
                }

                override fun onFailure(call: Call<LoginTokenVO>, t: Throwable) {
                    Log.d("ddd","오류야")
                    Log.d("ddd","$t")
                }

            })
        }

    }
}