package com.example.popmate.view.activities.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityMyPagePurchaseBinding
import com.example.popmate.model.data.remote.user.Orders
import com.example.popmate.view.activities.detail.PopupDetailActivity
import com.example.popmate.view.adapters.user.MyPageOrderAdapter
import com.example.popmate.viewmodel.user.OrderListViewModel

class MyPagePurchaseActivity : BaseActivity<ActivityMyPagePurchaseBinding>(R.layout.activity_my_page_purchase) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPagePurchaseBinding.inflate(layoutInflater)

        val model : OrderListViewModel by viewModels()
        model.getOrderListItems().observe(this){
            binding.orderlistitem = it
            val data: MutableList<Orders> = binding.orderlistitem?.orderListItemResponses?.toMutableList() ?: mutableListOf()
            val adapter = MyPageOrderAdapter()
            adapter.listData = data
            if (data.isEmpty()) {
                binding.txtNoguma.visibility = View.VISIBLE // 뷰를 화면에 보이게 설정
            } else {
                binding.txtNoguma.visibility = View.GONE // 뷰를 화면에서 숨김
            }
            binding.myPagePurchaseRecyclerview.adapter = adapter
            binding.myPagePurchaseRecyclerview.layoutManager = LinearLayoutManager(this)
            binding.layoutPageTitle.imgArrow.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            // 아이템 클릭 이벤트 처리
            adapter.setOnItemClickListener { clickedItem ->
                val intent = Intent(this, MyPagePurchaseDetailActivity::class.java)
                Log.d("jjrd",clickedItem.toString())
                intent.putExtra("id", clickedItem)
                startActivity(intent)
            }
            adapter.setOnImgClickListener {
                val intent = Intent(this, PopupDetailActivity::class.java)
                intent.putExtra("id", it)
                startActivity(intent)
            }
        }


        setContentView(binding.root)
    }
}

