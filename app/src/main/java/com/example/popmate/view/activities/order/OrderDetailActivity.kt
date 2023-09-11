package com.example.popmate.view.activities.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.ActivityOrderDetailBinding
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.order.OrderResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.view.adapters.order.OrderDetailAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDetailBinding
    private lateinit var adapter: OrderDetailAdapter
    private var totalAmount = 0
    private lateinit var data : ArrayList<PopupStoreItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = ArrayList<PopupStoreItem>()
        //        값 받아 왔을때 설계한 것
        if (intent.hasExtra("data")) {
            val hashMap = intent.getSerializableExtra("data") as? HashMap<Int, PopupStoreItem>
            if (hashMap != null) {
                for ((key, value) in hashMap) {
                    if(value.totalQuantity == 0) value.totalQuantity=1
                    data.add(value)
                    totalAmount += value.amount
                }
            }
        }


        // 여기서 리사이클러뷰 콜백처리
        adapter = OrderDetailAdapter(
            onAmountChanged = {position, totalQuantity , sign->
                val item = data[position]
                item.totalQuantity = totalQuantity

                updateTotalAmount(item, sign)
            },
            onItemRemoved = {position, item ->
                updateTotalAmount(item,"delete")
            }
        )
        // 리사이클러뷰
        adapter.listData = data
        binding.orderDetailRecyclerview.adapter = adapter
        binding.orderDetailRecyclerview.layoutManager = LinearLayoutManager(this)

        //여기서 초기 화면 설정
        binding.orderCnt.text = data.size.toString()
        binding.orderDetailTotalPrice.text = totalAmount.toString()
        binding.orderDetailTotalPrice1.text = totalAmount.toString()




        // 뒤로가기 처리
        binding.imgActivityOrderDetailBefore.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.orderDetailLinearLayout1.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.orderDetailBottom.setOnClickListener {
            Log.d("jjyaf",data.toString())
            //stockCheck(data)
            val intent = Intent(this,OrderPaymentActivity::class.java)
            intent.putExtra("item",data)
            startActivity(intent)
        }

    }




    private fun updateTotalAmount(item: PopupStoreItem, sign : String) {
        if(sign == "plus"){
            if(item.totalQuantity == item.orderLimit){
                Toast.makeText(this, "주문 가능한 최대 수량입니다", Toast.LENGTH_SHORT).show()
            }
            totalAmount += item.amount
            binding.orderDetailTotalPrice.text = totalAmount.toString()
            binding.orderDetailTotalPrice1.text = totalAmount.toString()
        }else if(sign == "minus"){
            totalAmount -= item.amount
            binding.orderDetailTotalPrice.text = totalAmount.toString()
            binding.orderDetailTotalPrice1.text = totalAmount.toString()
        }else{
            val cnt = binding.orderCnt.text.toString().toInt()
            totalAmount -= (item.amount * item.totalQuantity)
            binding.orderDetailTotalPrice.text = totalAmount.toString()
            binding.orderDetailTotalPrice1.text = totalAmount.toString()
            binding.orderCnt.text = (cnt-1).toString()
            if(data.size==0){
                val constraintLayout = findViewById<ConstraintLayout>(R.id.order_detail_bottom)
                constraintLayout.visibility = View.GONE
            }
        }

    }
}

