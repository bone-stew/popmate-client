package com.example.popmate.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.view.activities.MainActivity

class TitleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = getSharedPreferences("autoLogin", 0)
        val jwtToken =pref.getString("JwtToken", "").toString()
        val pref1 = getSharedPreferences("onboarding", 0)
        val onboardingCheck =pref1.getString("Check", "").toString()
        Log.d("dddddd", jwtToken)
        Log.d("dddddd", ApiClient.getJwtToken().toString())
        ApiClient.setJwtToken(jwtToken)
        if(ApiClient.getJwtToken()==""){
            val intent = Intent(applicationContext, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        // setContentView(binding.root)
        //moveMain(1)
    }

    private fun moveMain(sec: Int) {
        Handler().postDelayed({
            // Intent(현재 context, 이동할 activity)
            val intent = Intent(applicationContext, OnBoardingActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }, 1 * sec.toLong()) // sec초 정도 딜레이를 준 후 시작
    }
}
