package com.example.popmate.view.activities.order


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.ActivityOrderBinding
import com.example.popmate.model.data.remote.order.StoreItem
import com.example.popmate.view.adapters.order.OnItemClick
import com.example.popmate.view.adapters.order.OrderAdapter
import com.example.popmate.view.framents.order.OrderBottomFragment
import java.util.Date

class OrderActivity : AppCompatActivity(), OnItemClick {

    private lateinit var binding:ActivityOrderBinding
    private lateinit var fragment: OrderBottomFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data:MutableList<StoreItem> = loadData()
        var adapter = OrderAdapter(this)
        adapter.listData = data
        binding.orderRecyclerView.adapter = adapter

        fragment = OrderBottomFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.order_bottom,fragment)
            .hide(fragment)
            .commit()

        binding.orderRecyclerView.layoutManager = GridLayoutManager(this,2)
    }

    private fun loadData(): MutableList<StoreItem> {
        val data:MutableList<StoreItem> = mutableListOf()
        for(no in 1..100){
            val price = 10000 * no
            val name = "테스트 ${no}"
            var store = StoreItem(tbItemId = no, storeId = 0, name = name, price = price, imgUrl = "", stock = 0, order_limit = 0, createdAt = Date())
            data.add(store)
        }
        return data
    }

    override fun onClick(value: StoreItem) {
        supportFragmentManager.beginTransaction()
            .show(fragment)
            .commit()
        fragment.update(value)
    }


}