package com.example.popmate.view.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.popmate.R
import com.example.popmate.model.data.local.Item
import com.example.popmate.view.fragments.HomeFragment
import com.example.popmate.view.fragments.MyPageFragment
import com.example.popmate.view.fragments.PopupStoreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val items = MutableLiveData<ArrayList<Item>>()
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val firstFragment=HomeFragment()
        val secondFragment=PopupStoreFragment()
        val thirdFragment=MyPageFragment()

        setCurrentFragment(firstFragment)

        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_home->setCurrentFragment(firstFragment)
                R.id.page_popupstore->setCurrentFragment(secondFragment)
                R.id.page_mypage->setCurrentFragment(thirdFragment)

            }
            true
        }

//        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, com.example.popmate.R.layout.activity_main)
////        binding.recyclerView.setHasFixedSize(true)
//
//        val itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
//        binding.viewModel = itemViewModel
//        binding.lifecycleOwner = this
//
////        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.recyclerView.adapter = ItemAdapter(itemViewModel.itemList)
//        binding.recyclerView.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//
////        binding.listView.layoutManager = GridLayoutManager(this, 1)
//        binding.listView.layoutManager = LinearLayoutManager(this)
//        binding.listView.adapter = ItemAdapter(itemViewModel.itemList)
//
//        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
//        indicator.setViewPager(binding.recyclerView)
//
//        binding.horizontalView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.horizontalView.adapter = ItemAdapter(itemViewModel.itemList)
//
//        val dataObserver: Observer<ArrayList<Item>> = Observer {
//            items.value = it
//            val adapter = ItemAdapter(items)
//            binding.recyclerView.adapter = adapter
//        }
//
//        itemViewModel.itemList.observe(this, dataObserver)
    }


 }
