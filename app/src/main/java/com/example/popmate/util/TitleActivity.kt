package com.example.popmate.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.popmate.R
import com.example.popmate.view.activities.MainActivity

class TitleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        moveMain(1)
    }

    private fun moveMain(sec: Int) {
        Handler().postDelayed({
            // Intent(현재 context, 이동할 activity)
            val intent = Intent(applicationContext, MainActivity::class.java)

            startActivity(intent) // intent 에 명시된 액티비티로 이동

            finish() // 현재 액티비티 종료
        }, 1000 * sec.toLong()) // sec초 정도 딜레이를 준 후 시작
    }
}