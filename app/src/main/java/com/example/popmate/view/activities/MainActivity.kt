package com.example.popmate.view.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityMainBinding
import com.example.popmate.viewmodel.TestViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var viewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        binding.lifecycleOwner = this
        binding.counter = viewModel
    }
}
