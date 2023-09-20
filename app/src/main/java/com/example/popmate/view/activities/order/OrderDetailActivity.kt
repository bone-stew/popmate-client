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
import com.example.popmate.model.data.remote.order.OrderPlaceDetailResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.model.data.remote.order.StockCheckItemsResponse
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.view.adapters.order.OrderDetailAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDetailBinding
    private lateinit var adapter: OrderDetailAdapter
    private var totalAmount = 0
    private lateinit var data : ArrayList<PopupStoreItem>
    private var placeDetail: OrderPlaceDetailResponse? = null
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
        if(intent.hasExtra("placeDetail")){
            placeDetail = intent.getParcelableExtra("placeDetail") as? OrderPlaceDetailResponse
            if (placeDetail != null) {
                binding.txtPopupStoreName.text = placeDetail?.title.toString()
            }
        }


        // 여기서 리사이클러뷰 콜백처리
        adapter = placeDetail?.let {
            OrderDetailAdapter(
                this,
                it,
                onAmountChanged = {position, totalQuantity , sign->
                    val item = data[position]
                    item.totalQuantity = totalQuantity

                    updateTotalAmount(item, sign)
                }
            ) { position, item ->
                updateTotalAmount(item, "delete")
            }
        }!!
        // 리사이클러뷰
        adapter.listData = data
        binding.orderDetailRecyclerview.adapter = adapter
        binding.orderDetailRecyclerview.layoutManager = LinearLayoutManager(this)

        //여기서 초기 화면 설정
        binding.orderCnt.text = data.size.toString()
        val amount = NumberFormat.getNumberInstance(Locale.KOREA).format(totalAmount)
        binding.orderDetailTotalPrice.text = amount
        binding.orderDetailTotalPrice1.text = amount

        // 뒤로가기 처리
        binding.imgActivityOrderDetailBefore.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.orderDetailLinearLayout1.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.orderDetailBottom.setOnClickListener {
            Log.d("jjra",data.toString())
            stockCheck(data , placeDetail!!)
        }

    }

    private fun stockCheck(data: ArrayList<PopupStoreItem>, placeDetail: OrderPlaceDetailResponse) {
        val call : Call<ApiResponse<StockCheckItemsResponse>> = ApiClient.orderService.checkOrderItemsStock(data)

        call.enqueue(object : Callback<ApiResponse<StockCheckItemsResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<StockCheckItemsResponse>>,
                response: Response<ApiResponse<StockCheckItemsResponse>>
            ) {
                var check : Boolean  = true;
                var message: String = ""
                val datae = response.body()?.data?.stockCheckItemResponse
                datae?.forEach { storeCheckItem ->
                    if(!storeCheckItem.check){
                        check = false
                        message += "${storeCheckItem.itemId} 의 재고가 부족합니다\n"
                    }
                }
                if(!check){
                    Toast.makeText(this@OrderDetailActivity, message, Toast.LENGTH_SHORT).show()
                }else{
                    val intent = Intent(this@OrderDetailActivity,OrderPaymentActivity::class.java)
                    intent.putExtra("item",data)
                    intent.putExtra("placeDetail",placeDetail)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ApiResponse<StockCheckItemsResponse>>, t: Throwable) {
                Log.d("ddd", t.message.toString())
            }

        })
    }


    private fun updateTotalAmount(item: PopupStoreItem, sign : String) {
        if(sign == "plus"){
            totalAmount += item.amount
            val amount = NumberFormat.getNumberInstance(Locale.KOREA).format(totalAmount)
            binding.orderDetailTotalPrice.text = amount
            binding.orderDetailTotalPrice1.text = amount
        }else if(sign == "minus"){
            totalAmount -= item.amount
            val amount = NumberFormat.getNumberInstance(Locale.KOREA).format(totalAmount)
            binding.orderDetailTotalPrice.text = amount
            binding.orderDetailTotalPrice1.text = amount
        }else{
            val cnt = binding.orderCnt.text.toString().toInt()
            totalAmount -= (item.amount * item.totalQuantity)
            val amount = NumberFormat.getNumberInstance(Locale.KOREA).format(totalAmount)
            binding.orderDetailTotalPrice.text = amount
            binding.orderDetailTotalPrice1.text = amount
            binding.orderCnt.text = (cnt-1).toString()
            if(data.size==0){
                val constraintLayout = findViewById<ConstraintLayout>(R.id.order_detail_bottom)
                constraintLayout.visibility = View.GONE
            }
        }

    }
}