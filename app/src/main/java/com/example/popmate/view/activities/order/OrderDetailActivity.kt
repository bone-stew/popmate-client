package com.example.popmate.view.activities.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popmate.R
import com.example.popmate.databinding.ActivityOrderDetailBinding
import com.example.popmate.view.framents.order.OrderDetailFragment

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDetailBinding
    private lateinit var fragment : OrderDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        fragment = OrderDetailFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.order_detail_middle,fragment)
            .commit()

        setContentView(binding.root)
    }



}
//        값 받아 왔을때 설계한 것
//        if (intent.hasExtra("data")) {
//            val hashMap = intent.getSerializableExtra("data") as? HashMap<Int, Int>
//
//            if (hashMap != null) {
//
//                for ((key, value) in hashMap) {
//                    Log.d("popmate", hashMap.size.toString())
//                    Log.d("popmate", key.toString())
//                    Log.d("popmate", value.toString())
//                }
//            }
//        }
