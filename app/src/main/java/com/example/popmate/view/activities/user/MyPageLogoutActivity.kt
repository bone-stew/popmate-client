package com.example.popmate.view.activities.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popmate.databinding.ActivityMyPageLogoutBinding

class MyPageLogoutActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyPageLogoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mypageLogin.setOnClickListener {
            var intent = Intent(this,MyPageLoginActivity::class.java)
            startActivity(intent)
        }

    }
}