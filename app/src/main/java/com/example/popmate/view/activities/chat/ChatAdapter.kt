package com.example.popmate.view.activities.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ItemChatMineBinding
import com.example.popmate.databinding.ItemChatOthersBinding
import com.example.popmate.model.data.local.Chat

class ChatAdapter(private var messages: List<Chat>, private val currUserId: Long) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val MY_CHAT = 1
        private const val OTHER_CHAT = 2
    }

    inner class MyChatViewHolder(private val binding: ItemChatMineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chatMessage.text = chat.message
            binding.timeStamp.text = chat.createdAt?.substring(11, 16)
        }
    }

    inner class OtherChatViewHolder(private val binding: ItemChatOthersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chatMessage.text = chat.message
            binding.timeStamp.text = chat.createdAt?.substring(11, 16)
            binding.sender.text = chat.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MY_CHAT -> {
                (holder as ChatAdapter.MyChatViewHolder).bind(messages[position])
            }

            OTHER_CHAT -> {
                (holder as ChatAdapter.OtherChatViewHolder).bind(messages[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currUserId == messages[position].sender) MY_CHAT
        else OTHER_CHAT
    }

    fun addChat(newChatList: List<Chat>) {
        messages = newChatList
        notifyDataSetChanged()
    }
}
