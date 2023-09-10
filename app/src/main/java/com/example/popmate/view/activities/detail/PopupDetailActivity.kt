package com.example.popmate.view.activities.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.config.PopmateApplication
import com.example.popmate.databinding.ActivityPopupDetailBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.activities.order.OrderActivity
import com.example.popmate.view.activities.reservation.ReservationWaitActivity
import java.util.LinkedList

class PopupDetailActivity :
    BaseActivity<ActivityPopupDetailBinding>(R.layout.activity_popup_detail) {

    var popupStoreId: Long? = null
    var hasVisited: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.popupStoreId = intent.getLongExtra("id", -1)

        binding.backBtn.setOnClickListener {
            finish()
        }


        val model: PopupDetailViewModel by viewModels()
        model.getStore(popupStoreId!!).observe(this) {
            binding.store = it
            Glide.with(this)
                .load(it.bannerImgUrl)
                .into(binding.bannerImage)

            hasVisited = it.status==1
            Log.i("HASVISITED", "status: "+it.status.toString())
            if (hasVisited){
                Log.i("HASVISITED", "status: TRUE"+it.status.toString())
                binding.reserveBtn.visibility = View.GONE
                binding.orderLayout.orderBtnPost.visibility = View.VISIBLE
                binding.orderLayout.reserveBtnPost.visibility = View.VISIBLE
            } else {
                binding.reserveBtn.visibility = View.VISIBLE
                binding.orderLayout.orderBtnPost.visibility = View.GONE
                binding.orderLayout.reserveBtnPost.visibility = View.GONE
                Log.i("HASVISITED", "status: FALSE"+it.status.toString())

            }
            binding.run {
                reserveBtn.setOnClickListener{
                    val intent = Intent(applicationContext, ReservationWaitActivity::class.java)
//                intent.putExtra("id", popupStore.popupStoreId)
                    startActivity(intent)
                }
                orderLayout.orderBtnPost.setOnClickListener{
                    val intent = Intent(applicationContext, OrderActivity::class.java)
//                intent.putExtra("id", popupStore.popupStoreId)
                    startActivity(intent)
                }
                orderLayout.reserveBtnPost.setOnClickListener{
                    val intent = Intent(applicationContext, ReservationWaitActivity::class.java)
                    Log.d("Reservation", "put popupStoreId: $popupStoreId")
                    intent.putExtra("id", popupStoreId)
                    startActivity(intent)
                }
            }
            saveToRecentlyViewedSharedPrefs(it)
        }
        setInfoFragment()
        binding.run {
            infoBtn.setOnClickListener {
                setInfoFragment()
            }
            chatBtn.setOnClickListener {
                setChatFragment()
            }
        }
    }

    private fun saveToRecentlyViewedSharedPrefs(store: PopupStore?) {
        var storeList = PopmateApplication.prefs.getList()
        var storeLinkedList: LinkedList<PopupStore>? = null
        if (storeList == null) {
            storeLinkedList = LinkedList<PopupStore>()
        } else {
            storeLinkedList = LinkedList(storeList)
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
            .replace(binding.detailMainFrame.id, PopupDetailInfo.newInstance(popupStoreId!!)).commit()
    }


    private fun setChatFragment() {
        binding.reserveBtn.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(binding.detailMainFrame.id, PopupDetailChat.newInstance()).commit()
        binding.run {
            infoBtn.run {
                setTextColor(ContextCompat.getColor(context, R.color.tx_light_gray))
                setBackgroundResource(R.drawable.bottom_line_selected)
            }
            chatBtn.run {
                setTextColor(ContextCompat.getColor(context, R.color.black))
                setBackgroundResource(R.drawable.bottom_line)
            }

        }
    }
}
