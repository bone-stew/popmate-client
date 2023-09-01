package com.example.popmate.view.activities.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityPopupDetailBinding

class PopupDetailActivity :
    BaseActivity<ActivityPopupDetailBinding>(R.layout.activity_popup_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
            .replace(binding.detailMainFrame.id, PopupDetailInfo.newInstance()).commit()
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