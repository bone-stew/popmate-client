package com.example.popmate.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.popmate.R


class QrDialog(context: Context, private val qrImgUrl: String) {
    lateinit var listener: LessonOkDialogClickedListener
    lateinit var btnOk: Button
    lateinit var timerTextView: TextView
    private val dlg = Dialog(context)
    private var countDownTimer: CountDownTimer? = null

    interface LessonOkDialogClickedListener {
        fun onOkClicked()
    }

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.setContentView(R.layout.dialog_lesson_qr)

        val imageView = dlg.findViewById<ImageView>(R.id.img_mydetail_qr)

        Glide.with(dlg.context)
            .load(qrImgUrl)
            .into(imageView)

        btnOk = dlg.findViewById(R.id.btn_qr_ok)
        btnOk.setOnClickListener {
            listener.onOkClicked()
            dlg.dismiss()
        }

        startCountdown()
        dlg.show()
    }

    private fun startCountdown() {
        timerTextView = dlg.findViewById(R.id.timer)
        val totalTime = 30000L // 15초 (밀리초 단위)

        countDownTimer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // 1초마다 호출되며, 남은 시간을 표시합니다.
                val secondsRemaining = millisUntilFinished / 1000
                timerTextView.text = "$secondsRemaining"
            }

            override fun onFinish() {
                // 카운트다운 종료 후 다이얼로그를 닫습니다.
                dlg.dismiss()
            }
        }
        countDownTimer?.start()
    }

    fun cancelCountdown() {
        countDownTimer?.cancel()
    }
}
