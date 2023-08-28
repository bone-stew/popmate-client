package com.example.popmate.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.databinding.ActivityMainBinding
import com.example.popmate.model.data.local.Item
import com.example.popmate.view.adapters.ItemAdapter
import com.example.popmate.viewmodel.ItemViewModel


class MainActivity : AppCompatActivity() {
    private val items = MutableLiveData<ArrayList<Item>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, com.example.popmate.R.layout.activity_main)
//        binding.recyclerView.setHasFixedSize(true)

        val itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        binding.viewModel = itemViewModel
        binding.lifecycleOwner = this

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = ItemAdapter(itemViewModel.itemList)

        binding.listView.layoutManager = LinearLayoutManager(this)
        binding.listView.adapter = ItemAdapter(itemViewModel.itemList)

        binding.horizontalView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalView.adapter = ItemAdapter(itemViewModel.itemList)

        val dataObserver: Observer<ArrayList<Item>> = Observer {
            items.value = it
            val adapter = ItemAdapter(items)
            binding.recyclerView.adapter = adapter
        }

        itemViewModel.itemList.observe(this, dataObserver)
    }
}
