package com.example.popmate.view.fragments.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popmate.databinding.FragmentMyPageLoginBinding
import com.example.popmate.util.LessonLoginDialog
import com.example.popmate.view.activities.login.LoginActivity


class MyPageLoginFragment : Fragment() {
    private lateinit var binding : FragmentMyPageLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageLoginBinding.inflate(layoutInflater)

        binding.imgMypageNext.setOnClickListener {
            val dialog = LessonLoginDialog(requireContext())
            dialog.listener = object : LessonLoginDialog.LessonOkDialogClickedListener{
                override fun onOkClicked() {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            dialog.start()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}