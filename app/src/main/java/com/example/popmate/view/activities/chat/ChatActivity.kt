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
import com.example.popmate.model.data.local.CurrUser
import com.example.popmate.model.repository.ApiClient
import com.google.gson.Gson
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

class ChatActivity : BaseActivity<ActivityChatBinding>(R.layout.activity_chat) {

    private var roomId: Long = 0
    private val url = "wss://popmate.xyz/ws-chat"
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    private val model: ChatViewModel by viewModels()
    private lateinit var currUser: CurrUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomId = intent.getLongExtra("storeId", 0)
        binding.run {
            chatBox.layoutManager =
                LinearLayoutManager(this@ChatActivity).apply { this.stackFromEnd = true }
            sendBtn.setOnClickListener {
                val message = binding.inputText.text
                if (!message.isNullOrBlank()) sendMessage(message.toString())
                inputText.text = null
            }
            finishBtn.setOnClickListener {
                finish()
            }
        }
        model.run {
            loadChatMessage(roomId)
            currUser.observe(this@ChatActivity) {
                this@ChatActivity.currUser = it
                binding.chatBox.adapter = ChatAdapter(listOf(), it)
            }
            chatList.observe(this@ChatActivity) {
                (binding.chatBox.adapter as ChatAdapter).addChat(it)
                val position =
                    (binding.chatBox.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (it.size >= 3 && (position == it.size - 2 || it.last().sender == currUser.value?.userId)) binding.chatBox.smoothScrollToPosition(
                    it.size - 1
                )
            }
        }
    }

    private fun sendMessage(message: String) {
        val chat = Chat(message, roomId, currUser.userId, currUser.name)
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
        stompClient.connect(listOf(StompHeader("Authorization", ApiClient.getJwtToken())))
        stompClient.topic("/sub/$roomId").subscribe {
            val chat = Gson().fromJson(it.payload, Chat::class.java)
            model.addChat(chat)
        }
        stompClient.lifecycle().subscribe {
            when (it.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i("kww", "OPENED")
                }

                LifecycleEvent.Type.CLOSED -> {
                    Log.i("kww", "CLOSED")
                }

                LifecycleEvent.Type.ERROR -> {
                    Log.e("kww", it.exception.toString())
                }

                else -> {
                    Log.i("kww", it.message)
                }
            }
        }
    }
}
