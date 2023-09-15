package com.example.popmate.view.activities.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupDetailChatBinding
import com.example.popmate.view.activities.chat.ChatAdapter

class PopupDetailChat : Fragment() {

    private lateinit var viewModel: PopupDetailViewModel
    private lateinit var binding: FragmentPopupDetailChatBinding

    companion object {
        fun newInstance() = PopupDetailChat()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[PopupDetailViewModel::class.java]
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_popup_detail_chat, container, false)
        binding.chatThumbnail.layoutManager = LinearLayoutManager(context)
        binding.chatThumbnail.adapter = ChatAdapter(emptyList(), null)
        viewModel.chat.observe(viewLifecycleOwner) {
            (binding.chatThumbnail.adapter as ChatAdapter).addChat(it)
        }
        return binding.root
    }
}
