package com.example.popmate.view.activities.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.config.PopmateApplication
import com.example.popmate.databinding.ActivityPopupDetailBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.util.LessonLoginDialog
import com.example.popmate.view.activities.chat.ChatActivity
import com.example.popmate.view.activities.login.LoginActivity
import com.example.popmate.view.activities.order.OrderActivity
import com.example.popmate.view.activities.reservation.ReservationWaitActivity
import com.google.android.material.tabs.TabLayout
import java.util.LinkedList


class PopupDetailActivity :
    BaseActivity<ActivityPopupDetailBinding>(R.layout.activity_popup_detail) {
    val model: PopupDetailViewModel by viewModels()

    private val wifiPermissionCode = 1000 // 권한 요청 코드
    private var popupStoreId: Long = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        popupStoreId = intent.getLongExtra("id", -1)
        
        val model: PopupDetailViewModel by viewModels()

        binding.run {
            backBtn.setOnClickListener { finish() }
            infoChatTab.run {
                addTab(newTab().setText("상세"))
                addTab(newTab().setText("채팅"))
            }
            infoChatTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.d("kww", "onTabSelected: ${tab?.position}")
                    if (tab?.position == 0) setInfoFragment()
                    else setChatFragment()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
            reserveBtn.setOnClickListener {
                if (ApiClient.getJwtToken() == null) {
                    /**
                     * dialog_lesson_login 다이얼로그 띄우기
                     */
                    val dialog = LessonLoginDialog(this@PopupDetailActivity)
                    dialog.listener = object : LessonLoginDialog.LessonOkDialogClickedListener{
                        override fun onOkClicked() {
                            val intent = Intent(this@PopupDetailActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    dialog.start()
                    return@setOnClickListener
                }
                /**
                 * 와이파이 권한 요청
                 */
                if (hasWifiScanPermission()) {
                    Log.d("smh", "와이파이 권한이 허용되어 있음")
                    val intent = Intent(applicationContext, ReservationWaitActivity::class.java)
                    intent.putExtra("id", popupStoreId)
                    startActivity(intent)
                } else {
                    Log.d("smh", "와이파이 권한이 허용되어 있지 않음")
                    requestWifiScanPermission()
                }
            }
            orderLayout.orderBtnPost.setOnClickListener {
                val intent = Intent(applicationContext, OrderActivity::class.java)
                intent.putExtra("id", popupStoreId)
                startActivity(intent)
            }
            orderLayout.reserveBtnPost.setOnClickListener {
                /**
                 * 와이파이 권한 요청
                 */
                if (hasWifiScanPermission()) {
                    val intent = Intent(applicationContext, ReservationWaitActivity::class.java)
                    intent.putExtra("id", popupStoreId)
                    startActivity(intent)
                } else {
                    requestWifiScanPermission()
                }
            }
            chatEnterBtnClick.setOnClickListener {
                if (ApiClient.loginCheck()) {
                    val intent = Intent(applicationContext, ChatActivity::class.java)
                    intent.putExtra("storeId", popupStoreId)
                    intent.putExtra("storeName", model.store.value?.title)
                    startActivity(intent)
                } else {
                    val dialog = LessonLoginDialog(this@PopupDetailActivity)
                    dialog.listener = object : LessonLoginDialog.LessonOkDialogClickedListener{
                        override fun onOkClicked() {
                            val intent = Intent(this@PopupDetailActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    dialog.start()
                }
            }
        }

        model.loadStore(popupStoreId)
        model.loadChatThumbnail(popupStoreId)
        model.store.observe(this) {
            Glide.with(this).load(it.bannerImgUrl).into(binding.bannerImage)
            binding.run {
                store = it
            }
            setInfoFragment()
            saveToRecentlyViewedSharedPrefs(it)
        }

        model.status.observe(this) {
            binding.run {
                if (it == true) {
                    reserveBtn.visibility = View.GONE
                    orderLayout.orderBtnPost.visibility = View.VISIBLE
                    orderLayout.reserveBtnPost.visibility = View.VISIBLE
                } else {
                    reserveBtn.visibility = View.VISIBLE
                    orderLayout.orderBtnPost.visibility = View.GONE
                    orderLayout.reserveBtnPost.visibility = View.GONE
                }
            }
        }

        model.loading.observe(this) { isLoading ->
            binding.run {
                if (isLoading) {
                    reserveBtn.visibility = View.GONE
                    orderLayout.orderBtnPost.visibility = View.GONE
                    chatEnterBtn.visibility = View.GONE
                } else {
                    reserveBtn.visibility = View.VISIBLE
                    orderLayout.orderBtnPost.visibility = View.VISIBLE
                    chatEnterBtn.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun hasWifiScanPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestWifiScanPermission() {
        Log.d("smh", "와이파이 권한을 요청함")
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            wifiPermissionCode
        )
    }

    /**
     * 권한 요청 결과 처리
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == wifiPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Log.d("smh", "와이파이 권한이 승인됨")
                Log.d("smh", grantResults.toString())
                val intent = Intent(applicationContext, ReservationWaitActivity::class.java)
                intent.putExtra("id", popupStoreId)
                startActivity(intent)
            } else {
                Log.d("smh", grantResults.toString())
                Toast.makeText(this, "와이파이 스캔 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
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
            chatEnterBtn.visibility = View.GONE
            if (model.status.value == true) {
                orderLayout.orderBtnPost.visibility = View.VISIBLE
                orderLayout.reserveBtnPost.visibility = View.VISIBLE
            } else {
                reserveBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun setChatFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.detailMainFrame.id, PopupDetailChat.newInstance()).commit()
        binding.run {
            chatEnterBtn.visibility = View.VISIBLE
            if (model.status.value == true) {
                orderLayout.orderBtnPost.visibility = View.GONE
                orderLayout.reserveBtnPost.visibility = View.GONE
            } else {
                reserveBtn.visibility = View.GONE
            }
        }
    }
}
