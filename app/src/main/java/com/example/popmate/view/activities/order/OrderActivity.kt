package com.example.popmate.view.activities.order

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityOrderBinding
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.model.data.remote.order.PopupStoreItemsResponse
import com.example.popmate.view.adapters.order.OnItemClick
import com.example.popmate.view.adapters.order.OrderAdapter
import com.example.popmate.view.fragments.order.OrderBottomFragment

import com.example.popmate.viewmodel.order.OrderViewModel


class OrderActivity : BaseActivity<ActivityOrderBinding>(R.layout.activity_order), OnItemClick {
    private lateinit var fragment: OrderBottomFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model : OrderViewModel by viewModels()
        model.getPopupStoreItems().observe(this){
            binding.popupstoreitem = it
            Log.d("jjra", it.toString())
            val data = (binding.popupstoreitem as? PopupStoreItemsResponse)?.popupStoreItemResponse?.toMutableList()
            var adapter = OrderAdapter(this)
            adapter.listData = data!!
            binding.orderRecyclerView.adapter = adapter
            fragment = OrderBottomFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.order_bottom,fragment)
                .hide(fragment)
                .commit()
            binding.orderRecyclerView.layoutManager = GridLayoutManager(this,2)
        }

        binding.orderBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onClick(value: PopupStoreItem) {
        if(value.stock==0){
            Toast.makeText(this, "재고가 없습니다.", Toast.LENGTH_SHORT).show()
        }else{
            supportFragmentManager.beginTransaction()
                .show(fragment)
                .commit()
            fragment.update(value)
        }
    }

}