package com.example.popmate.view.activities.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityPopupDetailBinding

class PopupDetailActivity :
    BaseActivity<ActivityPopupDetailBinding>(R.layout.activity_popup_detail) {

//        val popupStoreId = intent.getStringExtra("id")?.toLong()
    var popupStoreId: Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.popupStoreId = intent.getLongExtra("id", -1)
        Log.i("DETAIL", intent.toString())
        Log.i("DETAIL", intent.extras.toString())
        Log.d("DETAIL", "Received intent: $intent")
        Log.i("DETAIL", intent.getLongExtra("id", -1).toString())

        if(intent.hasExtra("id")){
            Log.i("DETAIL", "HELLO")
            Log.i("DETAIL", intent.getLongExtra("id", -1).toString())

        }



    val model: PopupDetailViewModel by viewModels()
        model.getStore(popupStoreId!!).observe(this) {
            binding.store = it
            Glide.with(this)
                .load(it.bannerImgUrl)
                .into(binding.bannerImage)
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

    private fun setInfoFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.detailMainFrame.id, PopupDetailInfo.newInstance(popupStoreId!!)).commit()
        binding.reserveBtn.visibility = View.VISIBLE
        binding.run {
            infoBtn.run {
                setTextColor(ContextCompat.getColor(context, R.color.black))
                setBackgroundResource(R.drawable.bottom_line)
            }
            chatBtn.run {
                setTextColor(ContextCompat.getColor(context, R.color.tx_light_gray))
                setBackgroundResource(R.drawable.bottom_line_selected)
            }
        }
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
