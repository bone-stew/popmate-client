package com.example.popmate.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import com.example.popmate.R
import com.github.ybq.android.spinkit.style.Circle

class ProgressDialog(context: Context) : Dialog(context) {
    init {
        /*타이틀바 제거*/
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        /*커스텀 다이얼로그 radius 적용*/
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.dialog_progress)
        val progressBar = findViewById<ProgressBar>(R.id.spin_kit)
        val doubleBounce = Circle()
        progressBar.indeterminateDrawable = doubleBounce
    }

    fun start() {
        show()
    }
}
