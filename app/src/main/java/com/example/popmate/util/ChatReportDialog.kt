package com.example.popmate.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.popmate.databinding.DialogChatReportBinding
import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatReportDialog(private val context: AppCompatActivity) {
    private lateinit var binding: DialogChatReportBinding
    private val dialog = Dialog(context)

    fun show(chat: Chat) {
        binding = DialogChatReportBinding.inflate(context.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.apply {
            chatMessage.chatMessage.text = chat.message
            chatMessage.sender.text = chat.name
            chatMessage.timeStamp.text = chat.createdAt?.substring(11, 16)
            reportBtn.setOnClickListener {
                ApiClient.chatService.report(chat)
                    .enqueue(object : Callback<ApiResponse<String>> {
                        override fun onResponse(
                            call: Call<ApiResponse<String>>,
                            response: Response<ApiResponse<String>>
                        ) {
                            Toast.makeText(context, response.body()?.data, Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                        override fun onFailure(call: Call<ApiResponse<String>>, t: Throwable) {
                            dialog.dismiss()
                        }
                    })
            }
        }
        dialog.show()
    }
}