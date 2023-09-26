package com.example.popmate.view.activities.order

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityOrderBinding
import com.example.popmate.model.data.remote.order.OrderPlaceDetailResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.model.data.remote.order.PopupStoreItemsResponse
import com.example.popmate.view.adapters.order.OnItemClick
import com.example.popmate.view.adapters.order.OrderAdapter
import com.example.popmate.view.fragments.order.OrderBottomFragment

import com.example.popmate.viewmodel.order.OrderViewModel


class OrderActivity : BaseActivity<ActivityOrderBinding>(R.layout.activity_order), OnItemClick {
    private lateinit var fragment: OrderBottomFragment
    private var popupStoreId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        popupStoreId = intent.getLongExtra("id", 0)

        val model : OrderViewModel by viewModels()

        // 나중에 popupStoreId로 바꾸면 된다.
        model.loadList(popupStoreId)
        model.loadPlaceDetail(popupStoreId)

        model.popupStoreItem.observe(this){
            binding.popupstoreitem = it
            val data = (binding.popupstoreitem as? PopupStoreItemsResponse)?.popupStoreItemResponse?.toMutableList()
            var adapter = OrderAdapter(this)
            adapter.listData = data!!
            binding.orderRecyclerView.adapter = adapter
            binding.orderRecyclerView.layoutManager = GridLayoutManager(this,2)
        }

        model.placeDetail.observe(this){
            binding.placedetail = it
            val placeDetailResponse = binding.placedetail as OrderPlaceDetailResponse
            binding.orderPopstoreName.text = placeDetailResponse.title
            Glide.with(this)
                .load(placeDetailResponse.bannerImgUrl)
                .into(binding.imageView)
            fragment = OrderBottomFragment(placeDetailResponse)
            supportFragmentManager.beginTransaction()
                .add(R.id.order_bottom,fragment)
                .hide(fragment)
                .commit()
        }

        binding.orderBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onClick(value: PopupStoreItem, orderGoodsCheck: View) {
        if(value.stock==0){
            Toast.makeText(this, "재고가 없습니다.", Toast.LENGTH_SHORT).show()
        }else{
            supportFragmentManager.beginTransaction()
                .show(fragment)
                .commit()
            fragment.update(value,orderGoodsCheck)
        }
    }

}