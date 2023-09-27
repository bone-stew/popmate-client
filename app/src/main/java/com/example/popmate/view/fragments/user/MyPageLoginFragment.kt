package com.example.popmate.view.fragments.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popmate.databinding.FragmentMyPageLoginBinding
import com.example.popmate.util.LessonLoginDialog
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.activities.login.LoginActivity
import com.example.popmate.view.activities.user.TermsOfUseActivity


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
                    intent.putExtra("returnToActivity", MainActivity::class.java.name)
                    startActivity(intent)
                }
            }
            dialog.start()
        }

        binding.gonotion.setOnClickListener {
            val intent = Intent(context, TermsOfUseActivity::class.java)
            intent.putExtra("url", "https://naver.com") // 이동할 웹페이지 URL을 넣어주세요.
            context?.startActivity(intent)
        }

        binding.gogithub.setOnClickListener {
            val intent = Intent(context, TermsOfUseActivity::class.java)
            intent.putExtra("url", "https://github.com/bone-stew") // 이동할 웹페이지 URL을 넣어주세요.
            context?.startActivity(intent)
        }

        binding.layoutPrivacy.setOnClickListener{
            val intent = Intent(context, TermsOfUseActivity::class.java)
            intent.putExtra("url", "https://woowon.notion.site/29bc0080820145d9b4b655e1a534942c")
            context?.startActivity(intent)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}
