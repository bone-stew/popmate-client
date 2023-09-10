package com.example.popmate.view.activities.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityChatBinding
import com.example.popmate.model.data.local.Chat
import com.google.gson.Gson
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

class ChatActivity : BaseActivity<ActivityChatBinding>(R.layout.activity_chat) {
  
    private var roomId: Long = 0
    private val url = "ws://15.164.48.244:8080/ws-chat"
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    private val model: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomId = intent.getLongExtra("storeId", 0)
        binding.run {
            chatBox.layoutManager =
                LinearLayoutManager(this@ChatActivity).apply { this.stackFromEnd = true }
            sendBtn.setOnClickListener {
                val message = binding.inputText.text
                if (!message.isNullOrBlank()) sendMessage(Chat(message.toString(), roomId))
                inputText.text = null
            }
            finishBtn.setOnClickListener {
                finish()
            }
        }
        model.run {
            loadChatMessage(roomId)
            currUserId.observe(this@ChatActivity) {
                binding.chatBox.adapter = ChatAdapter(listOf(), it)
            }
            chatList.observe(this@ChatActivity) {
                (binding.chatBox.adapter as ChatAdapter).addChat(it)
                val position =
                    (binding.chatBox.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if ( it.size >= 3 && (position == it.size - 2 || it.last().sender == currUserId.value))
                    binding.chatBox.smoothScrollToPosition(it.size - 1)
            }
        }
    }

    private fun sendMessage(chat: Chat) {
        val data = Gson().toJson(chat, Chat::class.java)
        stompClient.send("/pub/message", data).subscribe()
    }

    override fun onStart() {
        super.onStart()
        getStompConnection(roomId)
    }

    override fun onStop() {
        super.onStop()
        stompClient.disconnect()
    }

    @SuppressLint("CheckResult")
    private fun getStompConnection(roomId: Long) {
        stompClient.connect()
        stompClient.topic("/sub/$roomId").subscribe {
            val chat = Gson().fromJson(it.payload, Chat::class.java)
            model.addChat(chat)
        }
        stompClient.lifecycle().subscribe {
            when (it.type) {
                LifecycleEvent.Type.OPENED -> { Log.i("kww", "OPEND") }
                LifecycleEvent.Type.CLOSED -> { Log.i("kww", "CLOSED") }
                LifecycleEvent.Type.ERROR -> { Log.e("kww", it.exception.toString()) }
                else -> { Log.i("kww", it.message) }
            }
        }
    }
}
