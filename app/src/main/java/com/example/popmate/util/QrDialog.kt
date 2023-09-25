package com.example.popmate.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.popmate.R


class QrDialog(context: Context, private val qrImgUrl: String) {
    lateinit var listener: LessonOkDialogClickedListener
    lateinit var btnOk: Button

    private val dlg = Dialog(context)

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
        dlg.show()
    }
}