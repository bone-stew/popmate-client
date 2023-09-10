package com.example.popmate.view.activities.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupDetailChatBinding

class PopupDetailChat : Fragment() {

    companion object {
        fun newInstance() = PopupDetailChat()
    }

    private lateinit var binding: FragmentPopupDetailChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_popup_detail_chat, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}
