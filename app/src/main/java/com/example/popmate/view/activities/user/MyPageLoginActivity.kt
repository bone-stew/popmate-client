package com.example.popmate.view.activities.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popmate.databinding.ActivityMyPageLoginBinding

class MyPageLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}