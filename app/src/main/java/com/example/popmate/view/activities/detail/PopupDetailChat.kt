package com.example.popmate.view.activities.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popmate.R

class PopupDetailChat : Fragment() {

    companion object {
        fun newInstance() = PopupDetailChat()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popup_detail_chat, container, false)
    }
}
