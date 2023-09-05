package com.example.popmate.view.activities.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityChatBinding
import com.example.popmate.databinding.ItemChatMineBinding
import com.example.popmate.databinding.ItemChatOthersBinding
import com.example.popmate.model.data.local.Chat

class ChatActivity : BaseActivity<ActivityChatBinding>(R.layout.activity_chat) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.chatBox.layoutManager = LinearLayoutManager(this )
        binding.chatBox.adapter = ChatAdapter(getTempMessages())
    }

    private fun getTempMessages(): List<Chat> {
        return listOf(
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕ㅣㅏㄴ머라ㅣ넘;리ㅏㅓㅁ나ㅣ헝니마호망니호ㅑㅐㅈ돔ㅎ라ㅣㅈㅁ돟라ㅣㅁ;너ㅗ아리ㅗㄶ마ㅣㅇㄴ머리암러하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "김우원", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
            Chat("64e8861662effb35e29084d0", "서명현", "안녕하세요", "testRoom","2023-08-25T19:45:13.646"),
        )
    }
}

class ChatAdapter(private val messages: List<Chat>) : RecyclerView.Adapter<ViewHolder>() {

    private val auth = "김우원"

    inner class MyChatViewHolder(private val binding: ItemChatMineBinding) :
        ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chatMessage.text = chat.message
            binding.timeStamp.text = chat.createdAt.substring(11, 16)
        }
    }

    inner class OtherChatViewHolder(private val binding: ItemChatOthersBinding) :
        ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chatMessage.text = chat.message
            binding.timeStamp.text = chat.createdAt.substring(11, 16)
            binding.sender.text = chat.sender
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            MY_CHAT -> {
                val view = ItemChatMineBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                MyChatViewHolder(view)
            }

            else -> {
                val view = ItemChatOthersBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                OtherChatViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MY_CHAT -> {
                (holder as MyChatViewHolder).bind(messages[position])
            }
            OTHER_CHAT -> {
                (holder as OtherChatViewHolder).bind(messages[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (auth == messages[position].sender) MY_CHAT
        else OTHER_CHAT
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem == newItem
            }
        }
        private const val MY_CHAT = 1
        private const val OTHER_CHAT = 2
    }
}
