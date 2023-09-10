package com.example.popmate.view.activities.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.bumptech.glide.Glide
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.config.PopmateApplication
import com.example.popmate.databinding.ActivityPopupDetailBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.activities.chat.ChatActivity
import com.example.popmate.view.activities.order.OrderActivity
import com.example.popmate.view.activities.reservation.ReservationWaitActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import java.util.LinkedList

class PopupDetailActivity :
    BaseActivity<ActivityPopupDetailBinding>(R.layout.activity_popup_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val popupStoreId = intent.getLongExtra("id", -1)
        val model: PopupDetailViewModel by viewModels()

        binding.run {
            backBtn.setOnClickListener { finish() }
            infoChatTab.run {
                addTab(newTab().setText("상세"))
                addTab(newTab().setText("채팅"))
            }
            infoChatTab.addOnTabSelectedListener(object : OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.d("kww", "onTabSelected: ${tab?.position}")
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    TODO("Not yet implemented")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    TODO("Not yet implemented")
                }
            })
            reserveBtn.setOnClickListener {
                val intent = Intent(applicationContext, ReservationWaitActivity::class.java)
                startActivity(intent)
            }
            orderLayout.orderBtnPost.setOnClickListener {
                val intent = Intent(applicationContext, OrderActivity::class.java)
                startActivity(intent)
            }
            orderLayout.reserveBtnPost.setOnClickListener {
                val intent = Intent(applicationContext, ReservationWaitActivity::class.java)
                startActivity(intent)
            }
            chatEnterBtn.setOnClickListener {
                val intent = Intent(applicationContext, ChatActivity::class.java)
                intent.putExtra("storeId", popupStoreId)
                startActivity(intent)
            }
        }

        model.loadStore(popupStoreId)
        model.store.observe(this) {
            Glide.with(this).load(it.bannerImgUrl).into(binding.bannerImage)
            binding.run {
                store = it
                if (it.status == 1) {
                    reserveBtn.visibility = View.GONE
                    orderLayout.orderBtnPost.visibility = View.VISIBLE
                    orderLayout.reserveBtnPost.visibility = View.VISIBLE
                } else {
                    reserveBtn.visibility = View.VISIBLE
                    orderLayout.orderBtnPost.visibility = View.GONE
                    orderLayout.reserveBtnPost.visibility = View.GONE
                }
            }
            setInfoFragment()
            saveToRecentlyViewedSharedPrefs(it)
        }

    }

    private fun saveToRecentlyViewedSharedPrefs(store: PopupStore?) {
        val storeList = PopmateApplication.prefs.getList()
        var storeLinkedList: LinkedList<PopupStore>? = null
        storeLinkedList = if (storeList == null) {
            LinkedList<PopupStore>()
        } else {
            LinkedList(storeList)
        }
        if (storeLinkedList.contains(store)) {
            storeLinkedList.remove(store)
        }
        storeLinkedList.addFirst(store)
        if (storeLinkedList.size > 5) {
            storeLinkedList.removeLast()
        }
        PopmateApplication.prefs.setList("popmate", storeLinkedList.toList())
    }


    private fun setInfoFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.detailMainFrame.id, PopupDetailInfo.newInstance()).commit()
        binding.run {
            reserveBtn.visibility = View.VISIBLE
            chatEnterBtn.visibility = View.GONE
        }
    }


    private fun setChatFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.detailMainFrame.id, PopupDetailChat.newInstance()).commit()
        binding.run {
            reserveBtn.visibility = View.GONE
            chatEnterBtn.visibility = View.VISIBLE
        }
    }
}
