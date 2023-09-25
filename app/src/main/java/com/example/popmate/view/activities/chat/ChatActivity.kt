package com.example.popmate.view.activities.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityChatBinding
import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.local.CurrUser
import com.example.popmate.model.repository.ApiClient
import com.google.gson.Gson
import okhttp3.OkHttpClient
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import java.util.concurrent.TimeUnit

class ChatActivity : BaseActivity<ActivityChatBinding>(R.layout.activity_chat) {

    private val TAG = "ChatActivity"
    private var roomId: Long = 0
    private lateinit var currUser: CurrUser

    private val url = "wss://popmate.xyz/ws-chat"
    //    private val url = "ws://10.0.2.2:8080/ws-chat"
    private val okHttp = OkHttpClient().newBuilder().pingInterval(10, TimeUnit.SECONDS).build()
    private val stompClient = Stomp.over(
        Stomp.ConnectionProvider.OKHTTP,
        url,
        mapOf(Pair("Authorization", ApiClient.getJwtToken())),
        okHttp
    )

    private val model: ChatViewModel by viewModels()
    lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomId = intent.getLongExtra("storeId", 0)
        val roomTitle = intent.getStringExtra("storeName")
        val linearLayoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }

        binding.apply {
            layoutPageTitle.titleText = roomTitle
            layoutPageTitle.imgArrow.setOnClickListener { finish() }
            chatBox.layoutManager = linearLayoutManager
            chatBox.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!model.chatFullyLoaded) {
                        val position = linearLayoutManager.findLastVisibleItemPosition().toFloat()
                        val totalAmount = chatAdapter.itemCount.toFloat()
                        if (position / totalAmount > 0.8) model.loadChatMessage(roomId)
                    }
                }
            })
            sendBtn.setOnClickListener {
                val message = binding.inputText.text
                if (!message.isNullOrBlank()) sendMessage(message.toString())
                inputText.text = null
            }
        }

        model.apply {
            enterRoom(roomId)
            currUser.observe(this@ChatActivity) {
                this@ChatActivity.currUser = it
                chatAdapter = ChatAdapter(emptyList(), it)
                binding.chatBox.adapter = chatAdapter
                binding.inputText.hint = it.nickname + binding.inputText.hint
            }
            chatList.observe(this@ChatActivity) {
                chatAdapter.addChat(it)
                val position = linearLayoutManager.findFirstVisibleItemPosition()
                if (it.isNotEmpty() && (position <= 2 || it.first().sender == currUser.value?.userId)) {
                    binding.chatBox.scrollToPosition(0)

                }
            }
        }
    }

    private fun sendMessage(message: String) {
        val chat = Chat(message, roomId, currUser.userId, currUser.nickname)
        val data = Gson().toJson(chat, Chat::class.java)
        stompClient.send("/pub/message", data).subscribe()
    }

    override fun onStart() {
        super.onStart()
        binding.chatBox.scrollToPosition(0)
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
            Log.d(TAG, "received: $chat")
            model.addChat(chat)
        }
        stompClient.lifecycle().subscribe {
            when (it.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i(TAG, "OPENED")
                }

                LifecycleEvent.Type.CLOSED -> {
                    Log.i(TAG, "CLOSED")
                }

                LifecycleEvent.Type.ERROR -> {
                    Log.e(TAG, it.exception.toString())
                }

                else -> {
                    Log.i(TAG, it.message)
                }
            }
        }
    }
}
