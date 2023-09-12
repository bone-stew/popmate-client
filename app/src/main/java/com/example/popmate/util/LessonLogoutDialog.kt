package com.example.popmate.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.example.popmate.R

// Dialog이고 연결 부분은 MyPageLogin에 간단히 넣어놨다
// 추후에 바꾸면 될 것 같다.

class LessonLogoutDialog(context: Context) {
    lateinit var listener: LessonOkDialogClickedListener
    lateinit var btnOk: Button
    lateinit var btnCancle: Button
    private val dlg = Dialog(context)

    interface LessonOkDialogClickedListener {
        fun onOkClicked()
        fun onCancelClicked()
    }

    fun start() {
        /*타이틀바 제거*/
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        /*커스텀 다이얼로그 radius 적용*/
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dlg.setContentView(R.layout.dialog_lesson_logout)

        btnCancle = dlg.findViewById(R.id.btn_logout_cancle)
        btnOk = dlg.findViewById(R.id.btn_logout_go)

        btnOk.setOnClickListener {
            listener.onOkClicked()
            dlg.dismiss()
        }

        btnCancle.setOnClickListener {
            listener.onCancelClicked()
            dlg.dismiss()
        }
        dlg.show()
    }
}