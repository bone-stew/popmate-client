package com.example.popmate.view.activities.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popmate.databinding.ActivityMyPageLoginBinding
import com.example.popmate.util.LessonLoginDialog
import com.example.popmate.view.activities.login.LoginActivity

class MyPageLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.mypageDescription.setOnClickListener {
            val dialog = LessonLoginDialog(this)
            dialog.listener = object : LessonLoginDialog.LessonOkDialogClickedListener{
                override fun onOkClicked() {
                    val intent = Intent(this@MyPageLoginActivity,LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            dialog.start()
        }
    }
}
