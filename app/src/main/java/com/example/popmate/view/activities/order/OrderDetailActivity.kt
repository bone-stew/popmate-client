package com.example.popmate.view.activities.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.popmate.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        if (intent.hasExtra("data")) {
            val hashMap = intent.getSerializableExtra("data") as? HashMap<Int, Int>

            if (hashMap != null) {

                for ((key, value) in hashMap) {
                    Log.d("popmate", hashMap.size.toString())
                    Log.d("popmate", key.toString())
                    Log.d("popmate", value.toString())
                }
            }
        }

        setContentView(binding.root)
    }
}