package com.example.popmate.view.activities.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popmate.R
import com.example.popmate.databinding.ActivityOrderPaymentBinding

class OrderPaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOrderPaymentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnOrderPayment.setOnClickListener {
            val intent = Intent(this,OrderPaymentCompleteActivity::class.java)
            startActivity(intent)
        }
    }
}